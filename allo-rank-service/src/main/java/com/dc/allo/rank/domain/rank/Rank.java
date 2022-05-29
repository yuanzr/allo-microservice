package com.dc.allo.rank.domain.rank;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.constants.Constant.Rank.*;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import com.dc.allo.rpc.domain.rank.RankDetail;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
@Builder
@Slf4j
public class Rank implements IRank {

    private static String RANK_KEY_PREFIX = "act_center_ranks:";

    private static String ID_TYPE_WRAPPER_SPLIT_SYMBOL = "#id_type_split#";

    @Reference
    DcUserInfoService userInfoService;

    @Reference
    DcRoomInfoService roomInfoService;

    /**
     * 空对象
     */
    public final static Rank NULL = Rank.builder().build();

    /**
     *
     */
    StringRedisTemplate stringTemplate;

    /**
     * id
     */
    private Long id;

    /**
     * 榜单名
     */
    private String name;

    /**
     * 榜单key
     */
    private String key;

    /**
     * 计算方式（1按分值累计、2按次累计）
     */
    private RankCalcType calcType;

    /**
     * 根据业务id分割榜单（只记录该业务id的榜单数据流水，可以用来实现周星榜类型的榜单）
     */
    private boolean divideByBizId;

    /**
     * 数据源id
     */
    private Integer dataSourceId;

    /**
     * 榜单时间类型 1小时、2日、3周、4月、5年、99不限时
     */
    private RankTimeType timeType;

    /**
     * 成员类型(1用户，2所有房间  3分类下房间【将根据流水中的roomTypeId进行分类】)
     */
    private RankMemberType memberType;

    /**
     * 是否限定频道分类
     */
    private boolean specifyRoomType;

    /**
     * 频道分类id
     */
    private Integer roomTypeId;

    /**
     * 方向(0无方向 1接收方的榜单 2发送方的榜单  频道榜不存在方向的概念)
     */
    private RankDirection direction;

    /**
     * 是否生成关联榜单（对于用户榜：关联榜单是每个人的反方向子榜，对于频道榜：关联榜单是频道内送礼and收礼榜）
     */
    private Boolean genRelation;

    /**
     * 榜单记录开始时间（不可以为空）
     */
    private Date startTime;

    /**
     * 榜单记录结束时间（不可以为空）
     */
    private Date endTime;

    /**
     * 过期时间单位
     */
    private RankExpireTimeUnit expireUnit;

    /**
     * 过期时间值
     */
    private Integer expireValue;


    @Override
    public double record(RankDataRecord record) {
        double scoreAfterRecord = 0.0;
        try {
            //榜单当前时间key
            String key = getCurrentRankKey(record.getBizId());
            //本次累分id
            String recordId = getRankRecordId(record);
            //关联榜单当前时间key
            String relationRankKey = getCurrentRelationRankKey(recordId, record.getBizId());
            //关联累分id
            String relationRecordId = getRelationRankRecordId(record);
            //失效时间
            int expireTime = getExpireTime();
            final double score = RankCalcType.COUNT == calcType ? (double) record.getCount() : record.getScore();
            List<Object> resultList = stringTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
                redisConnection.zIncrBy(key.getBytes(), score, recordId.getBytes());
                redisConnection.expire(key.getBytes(), expireTime);
                if (genRelation) {
                    redisConnection.zIncrBy(relationRankKey.getBytes(), score, relationRecordId.getBytes());
                    redisConnection.expire(relationRankKey.getBytes(), expireTime);
                }
                return null;
            });
            if (CollectionUtils.isNotEmpty(resultList)) {
                scoreAfterRecord = (double) resultList.get(0);
            }
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "记录分值失败 rank:{} record:{}", this, record, e);
        }
        return scoreAfterRecord;
    }

    @Override
    public String getCurrentRankKey() {
        return getLatestRankKey(0, null);
    }

    @Override
    public String getCurrentRankKey(String divideKey) {
        return getLatestRankKey(0, divideKey);
    }

    @Override
    public String getCurrentRelationRankKey(String id) {
        return getLatestRelationRankKey(id, 0, null);
    }

    @Override
    public String getCurrentRelationRankKey(String id, String divideKey) {
        return getLatestRelationRankKey(id, 0, divideKey);
    }

    @Override
    public String getLatestRankKey(Integer offset, String divideKey) {
        String key = RANK_KEY_PREFIX + "ds[" + this.dataSourceId + "]:" + this.key;
        if (offset == null || offset < 0) {
            offset = 0;
        }
        return getRankKey(key, offset, divideKey);
    }

    @Override
    public String getLatestRelationRankKey(String id, Integer offset, String divideKey) {
        String key = RANK_KEY_PREFIX + "ds[" + this.dataSourceId + "]:" + this.key + ":relation[" + id + "]";
        if (offset == null || offset < 0) {
            offset = 0;
        }
        return getRankKey(key, offset, divideKey);
    }

    @Override
    public List<RankDetail> queryRankListByTimeOffset(int offset, int page, int rows, String divideKey) {
        List<RankDetail> rankDetails = new ArrayList<>();
        try {
            String rankKey = getLatestRankKey(offset, divideKey);
            Set<ZSetOperations.TypedTuple<String>> set = getRankListFromRedis(rankKey, page, rows);
            rankDetails = tupleSetToRankList(set);
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "查询榜单数据失败 rank:{} offset:{} page:{} rows:{} ", this, offset, page, rows, e);
        }
        return rankDetails;
    }

    @Override
    public List<RankDetail> queryRelationRankListByTimeOffset(String rankId, int offset, int page, int rows, String divideKey) {
        List<RankDetail> rankDetails = new ArrayList<>();
        try {
            //目前关联榜单的榜单成员只有用户类型
            if (RankMemberType.USER.equals(memberType)) {
                rankId = userTypeRankIdWrapper(rankId);
            } else {
                rankId = channelTypeRankIdWrapper(rankId);
            }
            String rankKey = getLatestRelationRankKey(rankId, offset, divideKey);
            Set<ZSetOperations.TypedTuple<String>> set = getRankListFromRedis(rankKey, page, rows);
            rankDetails = tupleSetToRankList(set);
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "查询榜单数据失败 rank:{} offset:{} page:{} rows:{} ", this, offset, page, rows, e);
        }
        return rankDetails;
    }

    @Override
    public RankDetail queryOneRankByTimeOffset(String rankId, int offset, String divideKey) {
        if (RankMemberType.USER.equals(memberType)) {
            rankId = userTypeRankIdWrapper(rankId);
        } else {
            rankId = channelTypeRankIdWrapper(rankId);
        }
        String rankKey = getLatestRankKey(offset, divideKey);
        RankDetail detail = new RankDetail();
        detail.setId(unWrapperRankId(rankId).trim());
        detail.setRankIdType(unWrapperRankTypeId(rankId));
        detail.setRank(queryOneRank(rankKey, rankId));
        detail.setScore(queryOneScore(rankKey, rankId));
        return detail;
    }

    @Override
    public List<RankDetail> queryRangeRankList(String rankId, int offset,long begin, long end, String divideKey) {
        List<RankDetail> rankDetails = new ArrayList<>();
        try {
            String rankKey = getLatestRankKey(offset, divideKey);
            long newEnd = end - 1;
            if (newEnd <= 0) {
                newEnd = 1;
            }

            Set<ZSetOperations.TypedTuple<String>> set = getRankRangeFromRedis(rankKey, begin - 1, newEnd);
            log.info("queryRangeRankList rankKey:{}, begin:{}, end:{}", rankKey, begin, end);
            log.info("queryRangeRankList set:{}", set);
            rankDetails = tupleSetToRankList(set);
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "查询榜单数据失败 rank:{} offset:{} begin:{} end:{} ", this, offset, begin, end, e);
        }
        return rankDetails;
    }

    @Override
    public String getRankRecordId(RankDataRecord record) {
        String id = null;
        if (RankMemberType.USER.equals(memberType)) {
            if (RankDirection.GET.equals(direction)) {
                id = userTypeRankIdWrapper(record.getRecvId());
            } else if (RankDirection.SEND.equals(direction)) {
                id = userTypeRankIdWrapper(record.getSendId());
            }
        }
        if (RankMemberType.ROOM.equals(memberType)) {
            id = channelTypeRankIdWrapper(record.getRoomId());
        }
        return id;
    }

    @Override
    public String getRelationRankRecordId(RankDataRecord record) {
        String id = null;
        //用户榜的关联榜单方向与主榜的方向相反
        if (RankMemberType.USER.equals(memberType)) {
            if (RankDirection.GET.equals(direction)) {
                id = userTypeRankIdWrapper(record.getSendId());
            } else if (RankDirection.SEND.equals(direction)) {
                id = userTypeRankIdWrapper(record.getRecvId());
            }
        }
        //由于频道榜不存在方向，因此频道榜单的关联榜单方向采用主榜配置的方向
        if (RankMemberType.ROOM.equals(memberType)) {
            if (RankDirection.GET.equals(direction)) {
                id = userTypeRankIdWrapper(record.getRecvId());
            } else if (RankDirection.SEND.equals(direction)) {
                id = userTypeRankIdWrapper(record.getSendId());
            }
        }
        return id;
    }

    @Override
    public boolean isEnded() {
        Date now = new Date();
        try {
            if (endTime !=null) {
                return endTime.before(now);
            }else{
                //没有设置结束时间，默认不结束
                return false;
            }
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "校验榜单有效时间失败 startTime:" + startTime + " endTime:" + endTime, e);
        }
        return false;
    }

    /**
     * 用户类型榜单id包装，便于在查询榜单时，知道当前id是用户id
     *
     * @param id
     * @return
     */
    private String userTypeRankIdWrapper(String id) {
        return id + ID_TYPE_WRAPPER_SPLIT_SYMBOL + RankIdType.USER.val();
    }

    /**
     * 频道类型榜单id包装,便于在查询榜单时，知道当前id是频道id
     *
     * @param id
     * @return
     */
    private String channelTypeRankIdWrapper(String id) {
        return id + ID_TYPE_WRAPPER_SPLIT_SYMBOL + RankIdType.ROOM.val();
    }

    private String unWrapperRankId(String idAndType) {
        String[] strArr = idAndType.split(ID_TYPE_WRAPPER_SPLIT_SYMBOL);
        if (strArr.length > 0) {
            return strArr[0];
        }
        return null;
    }

    private Integer unWrapperRankTypeId(String idAndType) {
        String[] strArr = idAndType.split(ID_TYPE_WRAPPER_SPLIT_SYMBOL);
        if (strArr.length > 1) {
            return Integer.parseInt(strArr[1]);
        }
        return null;
    }

    private List<RankDetail> tupleSetToRankList(Set<ZSetOperations.TypedTuple<String>> set) {
        List<RankDetail> rankDetails = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(set)) {
            long rankIndex = 1;
            for (ZSetOperations.TypedTuple<String> tuple : set) {
                RankDetail detail = new RankDetail();
                String idAndType = tuple.getValue();
                double score = tuple.getScore();
                detail.setId(unWrapperRankId(idAndType));
                detail.setRankIdType(unWrapperRankTypeId(idAndType));
                detail.setScore(score);
                detail.setRank(rankIndex++);
                rankDetails.add(detail);
            }
        }
        return rankDetails;
    }

    private Set<ZSetOperations.TypedTuple<String>> getRankListFromRedis(String key, int page, int rows) {
        try {
            return stringTemplate.opsForZSet().reverseRangeByScoreWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, (page - 1) * rows, rows);
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "从redis获取榜单失败 key:{} rows:{} rows:{}", this, page, rows, e);
        }
        return null;
    }

    private Set<ZSetOperations.TypedTuple<String>> getRankRangeFromRedis(String key, long begin, long end) {
        try {
            return stringTemplate.opsForZSet().reverseRangeByScoreWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, begin, end);
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "从redis获取榜单失败 key:{} offset:{} rows:{}", this, begin, end, e);
        }
        return null;
    }

    private Long queryOneRank(String rankKey, String rankId) {
        try {
            Long rank = stringTemplate.opsForZSet().reverseRank(rankKey, rankId);
            return rank == null ? 0L : rank + 1;
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "查询单个id榜单失败 rank:{} rankKey:{} rankId:{}", this, rankKey, rankId, e);
        }
        return 0L;
    }

    private Double queryOneScore(String rankKey, String rankId) {
        try {
            Double score = stringTemplate.opsForZSet().score(rankKey, rankId);
            return score == null ? 0.0 : score;
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "查询单个id榜单分值失败 rank:{} rankKey:{} rankId:{}", this, rankKey, rankId, e);
        }
        return 0.0;
    }

    private String getRankKey(String key, Integer offset, String divideKey) {
        //计算时间偏移
        Calendar now = Calendar.getInstance();
        switch (timeType) {
            case HOUR:
                now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - offset);
                break;
            case DAY:
                now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) - offset);
                break;
            case WEEK:
                now.setFirstDayOfWeek(Calendar.MONDAY);
                now.set(Calendar.WEEK_OF_YEAR, now.get(Calendar.WEEK_OF_YEAR) - offset);
                break;
            case MONTH:
                now.set(Calendar.MONTH, now.get(Calendar.MONTH) - offset);
                break;
            case YEAR:
                now.set(Calendar.YEAR, now.get(Calendar.YEAR) - offset);
                break;
            default:
                break;
        }
        key = appendOtherSuffix(key, divideKey);
        key += getKeyTimeSuffix(now);
        return key;
    }

    /**
     * 拼接频道分类&业务id后缀
     *
     * @param key
     * @return
     */
    private String appendOtherSuffix(String key, String divideKey) {
        //如果指定了频道分类榜，拼接roomTypeId后缀
        if (specifyRoomType && roomTypeId != null) {
            key = appendChannelType(key);
        }
        //如果bizId不为空，且record中的bizId属于榜单配置的bizId，拼接bizId后缀
        if (divideByBizId && StringUtils.isNotEmpty(divideKey)) {
            key = appendBizId(key, divideKey);
        }
        return key;
    }

    private String appendChannelType(String key) {
        return key + ":channelType[" + roomTypeId + "]";
    }

    private String appendBizId(String key, String divideKey) {
        return key + ":biz[" + divideKey + "]";
    }

    /**
     * key时间后缀
     *
     * @param date
     * @return
     */
    protected String getKeyTimeSuffix(Calendar date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        String dateStr = timeFormat.format(date.getTime());
        String suffix = "";
        switch (timeType) {
            case HOUR:
                suffix += ":" + dateStr.substring(0, 13);
                break;
            case DAY:
                suffix += ":" + dateStr.substring(0, 10);
                break;
            case WEEK:
                date.setFirstDayOfWeek(Calendar.MONDAY);
                suffix += ":" + dateStr.substring(0, 4) + ":" + date.get(Calendar.WEEK_OF_YEAR);
                break;
            case MONTH:
                suffix += ":" + dateStr.substring(0, 7);
                break;
            case YEAR:
                suffix += ":" + dateStr.substring(0, 4);
                break;
            default:
                break;
        }
        return suffix;
    }

    public int getExpireTime() {
        int expireTime = 0;
        if (expireUnit != null && expireValue != null) {
            switch (expireUnit) {
                case SECOND:
                    expireTime = expireValue;
                    break;
                case MINUTE:
                    expireTime = expireValue * 60;
                    break;
                case HOUR:
                    expireTime = expireValue * 60 * 60;
                    break;
                case DAY:
                    expireTime = expireValue * 60 * 60 * 24;
                    break;
                case WEEK:
                    expireTime = expireValue * 60 * 60 * 24 * 7;
                    break;
                case MONTH:
                    expireTime = expireValue * 60 * 60 * 24 * 30;
                    break;
                case YEAR:
                    expireTime = expireValue * 60 * 60 * 24 * 365;
                    break;
            }
        }
        return expireTime;
    }

    public static boolean isNull(Rank rank) {
        return rank == null || Rank.NULL.equals(rank);
    }

    public static boolean isNotNull(Rank rank) {
        return !isNull(rank);
    }
}
