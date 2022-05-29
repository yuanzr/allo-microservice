package com.dc.allo.rpc.api.roomplay.pk;

import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.rpc.proto.AlloResp;

import java.util.List;

public interface DcRoomPkService {
    AlloResp cancelExpireRoomPkInvite();
    AlloResp handlerExpireRoomPking();
    AlloResp updateTodayHeatValueRank();
    AlloResp roomCombatPowerCompute();
    AlloResp computeRoomHeatValueAward(int day);
    //获取热度值历史榜单
    AlloResp<List<RoomHeatValueTotay>> getHistoryHeatValueTodayList();
}
