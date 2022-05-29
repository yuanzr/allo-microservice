package com.dc.allo.roomplay.mapper.pk;

import com.dc.allo.roomplay.domain.vo.RoomPkGoldVo;
import com.dc.allo.roomplay.domain.vo.RoomPkWinVo;
import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RoomPkRecordExpand {
    RoomPkRecord getPkingRecord(Long uid);
    /**
     * 获取pk胜场最多的前10名房间
     * @return
     */
    List<RoomPkWinVo> getRoomPkListOrderByWinNum(Date startTime);

    List<RoomPkRecord> getRoomPkRecordList(@Param("roomUid") Long roomUid, @Param("startTime") Date startTime);

    List<RoomPkGoldVo> getRoomPkTodayGold(@Param("roomUidList") List<Long> roomUidList, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    List<RoomPkGoldVo> getRoomPkTodayGoldByTargeUid(@Param("roomUidList") List<Long> roomUidList, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}