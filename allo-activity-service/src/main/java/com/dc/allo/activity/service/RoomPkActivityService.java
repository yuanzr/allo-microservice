package com.dc.allo.activity.service;

import com.dc.allo.activity.domain.vo.room.RoomActivityRankInfoVo;
import com.dc.allo.activity.domain.vo.room.RoomPkActivityInfoVo;
import com.dc.allo.common.vo.RoomActivityRankVo;

public interface RoomPkActivityService {
    RoomPkActivityInfoVo getRoomPkActivityInfoVo(Long roomUid);

    /**
     * 获取历史前几天的榜单
     * @return
     */
    RoomActivityRankInfoVo getRoomActivityRankHistoryVo();

    /**
     * 获取当天的榜单
     * @return
     */
    RoomActivityRankVo getRoomActivityRankTodayVo();
}
