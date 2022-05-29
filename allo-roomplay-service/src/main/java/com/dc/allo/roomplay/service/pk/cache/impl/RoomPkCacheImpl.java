package com.dc.allo.roomplay.service.pk.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.domain.room.RoomHeatValueTotal;
import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.common.vo.RoomActivityRankVo;
import com.dc.allo.common.vo.RoomPkActivityUpLevelVo;
import com.dc.allo.roomplay.service.pk.cache.RoomPkCache;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.roomplay.PbPkingRoomVo;
import com.dc.allo.rpc.domain.roomplay.PkSquareOpponentVo;
import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserVo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomPkCacheImpl implements RoomPkCache {
    @Autowired
    private RedisUtil redisUtil;
    @Reference
    private DcUserInfoService dcUserInfoService;
    private static final int RETRY_TIME = 5 * 1000;  //等待锁的时间
    private static final int EXPIRE_TIME = 5 * 1000; //锁超时的时间

    @Override
    public RoomPkRecord getRoomCurrentPkRecordFromCache(Long roomUid) {
        Object roomPkRecordIdStr = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_uid_record.name()), roomUid.toString());
        if (roomPkRecordIdStr == null) {
            return null;
        }
        Object roomPkRecordStr = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_record.name()), roomPkRecordIdStr.toString());
        if (roomPkRecordStr == null) {
            return null;
        }
        return JsonUtils.fromJson(roomPkRecordStr.toString(), RoomPkRecord.class);
    }

    @Override
    public Double getRoomCombatPowerByRoomUid(Long roomUid) {
        Double score = this.redisUtil.zScore(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_combat_power.name()), roomUid.toString());
        return score == null ? 0d : score;
    }

    @Override
    public Double getPkingRoomGold(Long roomUid) {
        Object goldStr = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_gold.name()), roomUid.toString());
        if (goldStr == null) {
            return 0d;
        }
        return Double.parseDouble(goldStr.toString());
    }

    @Override
    public List<SimpleUserVo> getPkFourUsersByGold(Long recordId, String roomUid) {
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_user_record.name(), recordId, roomUid);
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = this.redisUtil.zReverseRangeWithScores(key, 0L, 3L);
        if (CollectionUtils.isEmpty(typedTupleSet)) {
            return null;
        }
        List<SimpleUserVo> userVoList = new ArrayList<SimpleUserVo>(typedTupleSet.size());
        typedTupleSet.stream().forEach(vo -> {
            SimpleUserVo userVo = new SimpleUserVo();
            userVo.setUid(Long.parseLong(vo.getValue()));
            userVo.setGold(vo.getScore());
            userVoList.add(userVo);
        });
        return userVoList;
    }

    @Override
    public List<PbPkingRoomVo> getPkingRoomListVoCache(Long uid) {
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_user_zone.name(), uid);
        String voListStr = this.redisUtil.get(key);
        if (StringUtils.isEmpty(voListStr)) {
            return null;
        }
        return JsonUtils.fromJson(voListStr, new TypeReference<List<PbPkingRoomVo>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }

    @Override
    public List<RoomPkRecord> getRoomPkRecordListFromCache() {
        List<Object> recordStrList = this.redisUtil.hValues(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_record.name()));
        if (CollectionUtils.isEmpty(recordStrList)) {
            return null;
        }
        List<RoomPkRecord> roomPkRecords = new ArrayList<RoomPkRecord>(recordStrList.size());
        for (Object recordStr : recordStrList) {
            roomPkRecords.add(JsonUtils.fromJson(recordStr.toString(), RoomPkRecord.class));
        }
        return roomPkRecords;
    }

    @Override
    public Map<Long, Double> getRoomUidGoldMap() {
        Map<Object, Object> goldMap = this.redisUtil.hGetAll(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_gold.name()));
        if (goldMap == null) {
            return new HashMap();
        }
        Map<Long, Double> roomUidGoldMap = new HashMap<Long, Double>();
        Set<Object> roomUidSet = goldMap.keySet();
        for (Object roomUidStr : roomUidSet) {
            roomUidGoldMap.put(Long.parseLong(roomUidStr.toString()), Double.parseDouble(goldMap.get(roomUidStr).toString()));
        }
        return roomUidGoldMap;
    }

    @Override
    public Map<Long, Double> getRoomUidMapByCombat() {
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = this.redisUtil.zReverseRangeWithScores(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_combat_power.name()), 0L, -1L);
        if (CollectionUtils.isEmpty(typedTupleSet)) {
            return new HashMap();
        }
        Map<Long, Double> roomUidMap = new HashMap<Long, Double>();
        typedTupleSet.stream().forEach(vo -> {
            roomUidMap.put(Long.parseLong(vo.getValue()), vo.getScore());
        });
        return roomUidMap;
    }

    @Override
    public void savePkingRoomListVoCache(Long uid, List<PbPkingRoomVo> pkingRoomVoList) {
        if (CollectionUtils.isEmpty(pkingRoomVoList)) {
            return;
        }
        this.redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_user_zone.name(), uid), JsonUtils.toJson(pkingRoomVoList), 60 * 60);
    }

    @Override
    public int getUserTodayWantPkNum(Long uid) {
        Object userTodayWantPkNumStr = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_want_pk_num.name()), uid.toString());
        if (userTodayWantPkNumStr == null) {
            return 0;
        }
        return Integer.parseInt(userTodayWantPkNumStr.toString());
    }

    @Override
    public void addUserTodayWantPkNum(Long uid) {
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_want_pk_num.name());
        this.redisUtil.hIncrBy(key, uid.toString(), 1L);
        this.redisUtil.expire(key, DateTimeUtil.getDateCountdownSecond(DateTimeUtil.getNextHour(new Date(), -5)));
    }

    @Override
    public void checkChoosePkTimeInterval(Long uid, Long roomUid, int choosePkTimeInterval) {
        String beforeChoosePkTimeStr = this.redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.choose_pk_inteterval.name(), uid, roomUid));
        if (StringUtils.isEmpty(beforeChoosePkTimeStr)) {
            return;
        }
        long remainTime = System.currentTimeMillis() - Long.parseLong(beforeChoosePkTimeStr);
        //两次间隔没超过限制的间隔，返回提示
        if (remainTime < choosePkTimeInterval) {
            throw new DCException(BusiStatus.ROOM_PK_CHOOSE_PK_BUSY);
        }
    }

    @Override
    public boolean addRoomPkLock(String key, String lockValue) {
        return redisUtil.addLock(key, lockValue, RETRY_TIME, EXPIRE_TIME);
    }

    @Override
    public boolean addRoomPkLockByDontWait(String key, String lockValue) {
        return redisUtil.addlockByDontWait(key, lockValue, EXPIRE_TIME);
    }

    @Override
    public void delRoomPkLock(String key, String lockValue) {
        redisUtil.delLock(key, lockValue);
    }

    @Override
    public void pkCancelDelPkCache(String roomUidStr, String targetRoomUidStr, String recordIStr) {
        if (!StringUtils.isEmpty(roomUidStr)) {
            this.redisUtil.hDelete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_uid_record.name()), roomUidStr);
        }
        if (!StringUtils.isEmpty(targetRoomUidStr)) {
            this.redisUtil.hDelete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_uid_record.name()), targetRoomUidStr);
        }
        if (!StringUtils.isEmpty(recordIStr)) {
            this.redisUtil.hDelete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_record.name()), recordIStr);
        }
    }

    @Override
    public void pkEndDelPkCache(String roomUidStr, String targetRoomUidStr, String recordIStr) {
        this.redisUtil.hDelete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_gold.name()), roomUidStr);
        this.redisUtil.hDelete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_gold.name()), targetRoomUidStr);
        this.redisUtil.delete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_user_record.name(), recordIStr, roomUidStr));
        this.redisUtil.delete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_user_record.name(), recordIStr, targetRoomUidStr));
    }

    @Override
    public void saveRoomPkRecordToCache(Long roomUid, RoomPkRecord record) {
        String recordIdStr = record.getId().toString();
        this.redisUtil.hPut(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_uid_record.name()), roomUid.toString(), recordIdStr);
        this.redisUtil.hPut(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_uid_record.name()), record.getTargetRoomUid().toString(), recordIdStr);
        this.redisUtil.hPut(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_record.name()), recordIdStr, JsonUtils.toJson(record));
    }

    @Override
    public void incrRoomPkTodayWinNum(Long winRoomUid) {
        this.redisUtil.hIncrBy(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_win_num.name()), winRoomUid.toString(), 1L);
        this.redisUtil.expire(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_win_num.name()), DateTimeUtil.getDateCountdownSecond(new Date()));
    }

    @Override
    public Integer getRoomPkTodayRejectNum(Long roomUid, Long targetRoomUid) {
        String numStr = this.redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_reject.name(), roomUid, "#", targetRoomUid));
        if (StringUtils.isEmpty(numStr)) {
            return 0;
        }
        return Integer.parseInt(numStr);
    }

    @Override
    public Long addRoomPkTodayRejectNum(Long roomUid, Long targetRoomUid) {
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_reject.name(), roomUid, "#", targetRoomUid);
        Long totalNum = this.redisUtil.incrBy(key, 1L);
        this.redisUtil.expire(key, DateTimeUtil.getDateCountdownSecond(new Date()));
        return totalNum;
    }

    @Override
    public Long reduceRoomPkTodayRejectNum(Long roomUid, Long targetRoomUid) {
        return this.redisUtil.incrBy(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_reject.name(), roomUid, "#", targetRoomUid), -1);
    }

    @Override
    public void updateRoomPkUidRecordCache(String recordIdStr, RoomPkRecord record) {
        this.redisUtil.hPut(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_record.name()), recordIdStr, JsonUtils.toJson(record));
    }

    @Override
    public void setChoosePkTime(Long uid, Long roomUid, int choosePkTimeInterval) {
        this.redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.choose_pk_inteterval.name(), uid, roomUid), System.currentTimeMillis() + "", choosePkTimeInterval / 1000);
    }

    @Override
    public void delRoomPkRecordCache(Long roomUid, Long recordId) {
        this.redisUtil.hDelete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_record.name()), recordId.toString());
        this.redisUtil.hDelete(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_uid_record.name()), roomUid.toString());
    }

    @Override
    public void saveRoomCombatPowerVoListToCache(Map<String, Double> roomCombatPowerMap) {
        if (roomCombatPowerMap == null || roomCombatPowerMap.size() == 0) {
            return;
        }
        Set<ZSetOperations.TypedTuple<String>> values = new HashSet<ZSetOperations.TypedTuple<String>>(roomCombatPowerMap.size());
        roomCombatPowerMap.keySet().stream().forEach(key -> {
            values.add(new DefaultTypedTuple<String>(key, roomCombatPowerMap.get(key)));
        });
        this.redisUtil.zAdd(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_combat_power.name()), values);
        this.redisUtil.expire(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_combat_power.name()), 60 * 60);
    }

    @Override
    public Integer getRoomPkTodayWinNum(Long winRoomUid) {
        Object todayWinNumStr = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_today_win_num.name()), winRoomUid.toString());
        if (todayWinNumStr == null) {
            return 0;
        }
        return Integer.parseInt(todayWinNumStr.toString());
    }

    @Override
    public void savePkSquareOpponentVoListToCache(List<PkSquareOpponentVo> squareOpponentVoList) {
        if (CollectionUtils.isEmpty(squareOpponentVoList)) {
            return;
        }
        this.redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_square_all_room.name()), JsonUtils.toJson(squareOpponentVoList), 60 * 60);
    }

    @Override
    public List<PkSquareOpponentVo> getPkSquareAllRoomFromCache() {
        String pkSquareAllRoomStr = this.redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_square_all_room.name()));
        if (StringUtils.isEmpty(pkSquareAllRoomStr)) {
            return null;
        }
        return JsonUtils.fromJson(pkSquareAllRoomStr, new TypeReference<List<PkSquareOpponentVo>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }

    @Override
    public void saveRoomOpponentCache(Long roomUid, List<PkSquareOpponentVo> otherOpponentList) {
        if (CollectionUtils.isEmpty(otherOpponentList)) {
            return;
        }
        this.redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_square_user_zone.name(), roomUid), JsonUtils.toJson(otherOpponentList), 60 * 60);
    }

    @Override
    public List<PkSquareOpponentVo> getRoomOpponentCache(Long roomUid) {
        String voListStr = this.redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_square_user_zone.name(), roomUid.toString()));
        if (StringUtils.isEmpty(voListStr)) {
            return null;
        }
        return JsonUtils.fromJson(voListStr, new TypeReference<List<PkSquareOpponentVo>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }

    public Double incryPkingRoomUserGold(Long recordId, Long userId, String roomUid, Double goldNum) {
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_room_user_today_gift.name(), roomUid);
        this.redisUtil.zIncrementScore(key, userId.toString(), goldNum);
        this.redisUtil.expire(key, DateTimeUtil.getDateCountdownSecond(DateTimeUtil.getNextHour(new Date(), -5)));
        return this.redisUtil.zIncrementScore(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_user_record.name(), recordId, roomUid), userId.toString(), goldNum);
    }

    public Double incryPkingRoomGold(String roomUid, Double goldNum) {
        return this.redisUtil.hIncrByFloat(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pking_room_gold.name()), roomUid, goldNum);
    }

    @Override
    public RoomPkRecord getRoomPkRecordByRecordId(String recordId) {
        Object recordStr = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_record.name()), recordId.toString());
        if (recordStr == null) {
            return null;
        }
        return JsonUtils.fromJson(recordStr.toString(), RoomPkRecord.class);
    }

    @Override
    public RoomPkActivityUpLevelVo addHeatValueByPk(Long roomUid, Long heatValue, String keyValue) {
        RoomPkActivityUpLevelVo roomPkActivityUpLevelVo = addHeatValue(roomUid, heatValue);
        String key = RedisKeyUtil.appendCacheKeyByColon(keyValue);
        this.redisUtil.hIncrBy(key, roomUid.toString(), 1L);
        this.redisUtil.expire(key, DateTimeUtil.getDateCountdownSecond(DateTimeUtil.getNextHour(new Date(), -5)));
        return roomPkActivityUpLevelVo;
    }

    @Override
    public int getTodayHeatAddNum(Long roomUid, String keyValue) {
        Object addNum = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(keyValue), roomUid.toString());
        if (addNum == null) {
            return 0;
        }
        return Integer.parseInt(addNum.toString());
    }

    @Override
    public void updateTodayHeatValueRank(String key) {
        String date = DateTimeUtil.convertDate(DateTimeUtil.getNextHour(new Date(), -5), DateTimeUtil.DEFAULT_DATE_PATTERN);
        String scoreKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_totay_score.name(), date);
        Set<String> roomUidSet = this.redisUtil.zReverseRange(scoreKey, 0, 9);
        if (CollectionUtils.isEmpty(roomUidSet)) {
            return;
        }
        List<Object> todayRankObj = this.redisUtil.hMultiGet(key, Arrays.asList(roomUidSet.stream().toArray()));
        if (CollectionUtils.isEmpty(todayRankObj)) {
            return;
        }
        RoomActivityRankVo roomActivityRankVo = new RoomActivityRankVo();
        List<RoomHeatValueTotay> roomHeatValueTotayList = new ArrayList<RoomHeatValueTotay>();
        RoomHeatValueTotay roomHeatValueTotay = null;
        String userGiftkey = RedisKeyUtil.RedisKey.pk_room_user_today_gift.name();
        for (Object todayObj : todayRankObj) {
            roomHeatValueTotay = JsonUtils.fromJson(todayObj.toString(), RoomHeatValueTotay.class);
            String sendUserListKey = RedisKeyUtil.appendCacheKeyByColon(userGiftkey, roomHeatValueTotay.getRoomUid());
            Set<String> mvpUserSet = this.redisUtil.zReverseRange(sendUserListKey, 0, 0);
            if (!CollectionUtils.isEmpty(mvpUserSet)) {
                Long mvpUid = Long.parseLong(mvpUserSet.iterator().next());
                AlloResp<SimpleUserInfo> userResp = dcUserInfoService.getSimpleUserInfoByUid(mvpUid);
                if (userResp.isSuccess() && userResp.getData() != null) {
                    roomHeatValueTotay.setMvpUid(userResp.getData().getUid());
                    roomHeatValueTotay.setMvpAvatar(userResp.getData().getAvatar());
                    roomHeatValueTotay.setMvpNick(userResp.getData().getNick());
                }
            }
            roomHeatValueTotayList.add(roomHeatValueTotay);
        }
        roomHeatValueTotayList = roomHeatValueTotayList.stream().sorted(Comparator.comparing(RoomHeatValueTotay::getHeatValue, Comparator.nullsFirst(Comparator.naturalOrder())).reversed()).collect(Collectors.toList());

        roomActivityRankVo.setRoomHeatValueTotayList(roomHeatValueTotayList);
        String todayRankKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_ctivity_rank_today.name(), date);
        this.redisUtil.set(todayRankKey, JsonUtils.toJson(roomActivityRankVo));

    }

    @Override
    public RoomPkActivityUpLevelVo addHeatValue(Long roomUid, Long heatValue) {
        AlloResp<UserInfo> userResp = dcUserInfoService.getByUid(roomUid);
        String avatar = null, nick = null;
        if (userResp != null && userResp.isSuccess()) {
            avatar = userResp.getData().getAvatar();
            nick = userResp.getData().getNick();
        }
        Object roomHeatValueTotalObject = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_total.name()), roomUid.toString());
        RoomHeatValueTotal roomHeatValueTotal = null;
        if (roomHeatValueTotalObject != null) {
            roomHeatValueTotal = JsonUtils.fromJson(roomHeatValueTotalObject.toString(), RoomHeatValueTotal.class);
        }
        if (roomHeatValueTotal == null) {
            roomHeatValueTotal = new RoomHeatValueTotal();
            roomHeatValueTotal.setRoomUid(roomUid);
            roomHeatValueTotal.setLevel((byte) 0);
        }
        roomHeatValueTotal.setRoomAvatar(avatar);
        roomHeatValueTotal.setRoomTitle(nick);
        if (roomHeatValueTotal.getHeatValue() == null) {
            roomHeatValueTotal.setHeatValue(new BigDecimal(heatValue));
        } else {
            roomHeatValueTotal.setHeatValue(roomHeatValueTotal.getHeatValue().add(new BigDecimal(heatValue)));
        }

        boolean isUpLevel = updateHeatUpLevel(roomHeatValueTotal);
        this.redisUtil.hPut(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_total.name()), roomUid.toString(), JsonUtils.toJson(roomHeatValueTotal));

        addTodayHeatValue(roomUid, avatar, nick, heatValue);

        RoomPkActivityUpLevelVo roomPkActivityUpLevelVo = new RoomPkActivityUpLevelVo();
        roomPkActivityUpLevelVo.setRoomUid(roomHeatValueTotal.getRoomUid());
        roomPkActivityUpLevelVo.setLevel(roomHeatValueTotal.getLevel());
        roomPkActivityUpLevelVo.setUpLevel(isUpLevel);
        return roomPkActivityUpLevelVo;
    }

    public void addTodayHeatValue(Long roomUid, String avatar, String title, Long heatValue) {
        String date = DateTimeUtil.convertDate(DateTimeUtil.getNextHour(new Date(), -5), DateTimeUtil.DEFAULT_DATE_PATTERN);
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_totay.name(), date);
        String scoreKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_totay_score.name(), date);
        Object roomHeatValueTodayObject = this.redisUtil.hGet(key, roomUid.toString());
        RoomHeatValueTotay roomHeatValueTotay = null;
        if (roomHeatValueTodayObject != null) {
            roomHeatValueTotay = JsonUtils.fromJson(roomHeatValueTodayObject.toString(), RoomHeatValueTotay.class);
        }
        if (roomHeatValueTotay == null) {
            roomHeatValueTotay = new RoomHeatValueTotay();
            roomHeatValueTotay.setRoomUid(roomUid);
            roomHeatValueTotay.setDay(Byte.parseByte(DateTimeUtil.convertDate(DateTimeUtil.getNextHour(new Date(), -5), DateTimeUtil.SIMPLE_DAY_PATTERN)));
        }
        roomHeatValueTotay.setRoomAvatar(avatar);
        roomHeatValueTotay.setRoomTitle(title);
        if (roomHeatValueTotay.getHeatValue() == null) {
            roomHeatValueTotay.setHeatValue(new BigDecimal(heatValue));
        } else {
            roomHeatValueTotay.setHeatValue(roomHeatValueTotay.getHeatValue().add(new BigDecimal(heatValue)));
        }

        String roomUidStr = roomUid.toString();
        this.redisUtil.hPut(key, roomUidStr, JsonUtils.toJson(roomHeatValueTotay));
        this.redisUtil.zAdd(scoreKey, roomUidStr, roomHeatValueTotay.getHeatValue().doubleValue());
    }

    public boolean updateHeatUpLevel(RoomHeatValueTotal roomHeatValueTotal) {
        if (roomHeatValueTotal == null || roomHeatValueTotal.getHeatValue() == null) {
            return false;
        }
        boolean isUpLevel = false;
        Byte level = roomHeatValueTotal.getLevel();
        //大于最高级
        if (roomHeatValueTotal.getHeatValue().compareTo(new BigDecimal(3000)) >= 0) {
            if (level < 4) {
                isUpLevel = true;
                roomHeatValueTotal.setLevel((byte) 4);
            }
        }

        if (roomHeatValueTotal.getHeatValue().compareTo(new BigDecimal(1500)) >= 0) {
            if (level < 3) {
                isUpLevel = true;
                roomHeatValueTotal.setLevel((byte) 3);
            }
        }
        if (roomHeatValueTotal.getHeatValue().compareTo(new BigDecimal(500)) >= 0) {
            if (level < 2) {
                isUpLevel = true;
                roomHeatValueTotal.setLevel((byte) 2);
            }
        }
        if (roomHeatValueTotal.getHeatValue().compareTo(new BigDecimal(50)) >= 0) {
            if (level < 1) {
                isUpLevel = true;
                roomHeatValueTotal.setLevel((byte) 1);
            }
        }
        return isUpLevel;
    }

    @Override
    public RoomActivityRankVo getRoomActivityRankTodayVo(int day) {
        Date now = DateTimeUtil.getBeforeDay(DateTimeUtil.getNextHour(new Date(), -5), day);
        String beforeDay = DateTimeUtil.convertDate(now, DateTimeUtil.DEFAULT_DATE_PATTERN);
        Object roomActivityRankObj = this.redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_ctivity_rank_today.name(), beforeDay));
        RoomActivityRankVo roomActivityRankVo = null;
        if (roomActivityRankObj != null) {
            roomActivityRankVo = JsonUtils.fromJson(roomActivityRankObj.toString(), RoomActivityRankVo.class);
        }
        return roomActivityRankVo;
    }

    @Override
    public List<RoomHeatValueTotay> getHeatValueTodayList() {
        Date now = DateTimeUtil.getBeforeDay(DateTimeUtil.getNextHour(new Date(), -5), -1);
        String beforeDay = DateTimeUtil.convertDate(now, DateTimeUtil.DEFAULT_DATE_PATTERN);
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_totay.name(), beforeDay);
        List<Object> todayObjectList = this.redisUtil.hValues(key);
        if (CollectionUtils.isEmpty(todayObjectList)) {
            return null;
        }
        List<RoomHeatValueTotay> todayList = new ArrayList<>(todayObjectList.size());
        RoomHeatValueTotay roomHeatValueTotay = null;
        for (Object object : todayObjectList) {
            roomHeatValueTotay = JsonUtils.fromJson(object.toString(), RoomHeatValueTotay.class);
            todayList.add(roomHeatValueTotay);
        }
        return todayList;
    }

    @Override
    public void addRoomCombatValue(Long roomUid, Byte level) {
        if (roomUid == null || level == null) {
            return;
        }
        String combatPower = null;
        if (level == 1) {
            combatPower = "1000";
        }
        if (level == 2) {
            combatPower = "2000";
        }
        if (level == 3) {
            combatPower = "8000";
        }
        if (level == 4) {
            combatPower = "20000";
        }
        if (StringUtils.isEmpty(combatPower)) {
            return;
        }
        this.redisUtil.hPut(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.user_heat_combat_power.name()), roomUid.toString(), combatPower);
    }

    @Override
    public Map<Long, Double> getRoomHeatAddCombatValue() {
        Map<Object, Object> combatPowerMapObject = this.redisUtil.hGetAll(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.user_heat_combat_power.name()));
        if (combatPowerMapObject == null || combatPowerMapObject.size() == 0) {
            return null;
        }
        Map<Long, Double> combatPowerMap = new HashMap<Long, Double>();
        combatPowerMapObject.keySet().stream().forEach(key -> {
            combatPowerMap.put(Long.parseLong(key.toString()), Double.parseDouble(combatPowerMapObject.get(key).toString()));
        });

        return combatPowerMap;
    }
}
