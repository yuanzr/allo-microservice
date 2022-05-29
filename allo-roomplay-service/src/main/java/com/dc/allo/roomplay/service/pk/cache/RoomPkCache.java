package com.dc.allo.roomplay.service.pk.cache;


import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.common.vo.RoomActivityRankVo;
import com.dc.allo.common.vo.RoomPkActivityUpLevelVo;
import com.dc.allo.rpc.domain.roomplay.PbPkingRoomVo;
import com.dc.allo.rpc.domain.roomplay.PkSquareOpponentVo;
import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserVo;

import java.util.List;
import java.util.Map;

public interface RoomPkCache {
    RoomPkRecord getRoomCurrentPkRecordFromCache(Long roomUid);
    Double getRoomCombatPowerByRoomUid(Long roomUid);
    Double getPkingRoomGold(Long roomUid);
    List<SimpleUserVo> getPkFourUsersByGold(Long recordId, String roomUid);
    List<PbPkingRoomVo> getPkingRoomListVoCache(Long uid);
    List<RoomPkRecord> getRoomPkRecordListFromCache();
    Map<Long,Double> getRoomUidGoldMap();
    Map<Long,Double> getRoomUidMapByCombat();
    void savePkingRoomListVoCache(Long uid,List<PbPkingRoomVo> pkingRoomVoList);
    int getUserTodayWantPkNum(Long uid);
    void addUserTodayWantPkNum(Long uid);
    void checkChoosePkTimeInterval(Long uid, Long roomUid,int choosePkTimeInterval);
    /**
     * 加锁 设置指定 key 的值<p></p>
     * 存在时会根据指定时间等待<p></p>
     * 如果存在则不设置 返回false<p></p>
     * 如果不存在则设置 返回true
     */
    boolean addRoomPkLock(String key,String lockValue);
    /**
     * 加锁 设置指定 key 的值<p></p>
     * 如果存在则不设置 返回false<p></p>
     * 如果不存在则设置 返回true
     */
    boolean addRoomPkLockByDontWait(String key,String lockValue);
    void delRoomPkLock(String key,String lockValue);
    void pkCancelDelPkCache(String roomUidStr,String targetRoomUidStr,String recordIStr);
    void pkEndDelPkCache(String roomUidStr, String targetRoomUidStr, String recordIStr);
    void saveRoomPkRecordToCache(Long roomUid, RoomPkRecord record);
    void incrRoomPkTodayWinNum(Long winRoomUid);
    Integer getRoomPkTodayRejectNum(Long roomUid, Long targetRoomUid);
    Long addRoomPkTodayRejectNum(Long roomUid, Long targetRoomUid);
    Long reduceRoomPkTodayRejectNum(Long roomUid, Long targetRoomUid);
    void updateRoomPkUidRecordCache(String recordIdStr, RoomPkRecord record);
    void setChoosePkTime(Long uid,Long roomUid,int choosePkTimeInterval);
    void delRoomPkRecordCache(Long roomUid, Long recordId);
    void saveRoomCombatPowerVoListToCache(Map<String, Double> roomCombatPowerMap);
    Integer getRoomPkTodayWinNum(Long winRoomUid);
    void savePkSquareOpponentVoListToCache(List<PkSquareOpponentVo> squareOpponentVoList);
    List<PkSquareOpponentVo> getPkSquareAllRoomFromCache();
    void saveRoomOpponentCache(Long roomUid,List<PkSquareOpponentVo> otherOpponentList);
    List<PkSquareOpponentVo> getRoomOpponentCache(Long roomUid);
    Double incryPkingRoomUserGold(Long recordId, Long userId, String roomUid, Double goldNum);
    Double incryPkingRoomGold(String roomUid, Double goldNum);
    RoomPkRecord getRoomPkRecordByRecordId(String recordId);
    /**
     * 不保证原子性
     */
    RoomPkActivityUpLevelVo addHeatValueByPk(Long roomUid, Long heatValue, String keyValue);
    /**
     * 不保证原子性
     */
    RoomPkActivityUpLevelVo addHeatValue(Long roomUid, Long heatValue);
    /**
     * 获取今天热度值添加次数
     */
    int getTodayHeatAddNum(Long roomUid,String keyValue);

    /**
     * 获取指定日期当天的所有房间热力值
     */
    void updateTodayHeatValueRank(String key);
    RoomActivityRankVo getRoomActivityRankTodayVo(int day);
    List<RoomHeatValueTotay> getHeatValueTodayList();

    void addRoomCombatValue(Long roomUid,Byte level);

    /**
     * 获取在pk活动中，因为热度值级数加的战力值
     * @return
     */
    Map<Long,Double> getRoomHeatAddCombatValue();
}
