package com.dc.allo.activity.service.cache;

import com.dc.allo.activity.domain.vo.room.RoomActivityRankInfoVo;
import com.dc.allo.activity.domain.vo.room.RoomPkActivityInfoVo;
import com.dc.allo.common.vo.RoomActivityRankVo;

public interface RoomPkActivityCache {
    RoomPkActivityInfoVo getRoomPkActivityInfoVo(Long roomUid);
    RoomActivityRankInfoVo getRoomActivityRankHistoryVo();
    void setRoomActivityRankHistoryVo(RoomActivityRankInfoVo roomActivityRankHistoryVo);
    RoomActivityRankVo getRoomActivityRankTodayVo();
}