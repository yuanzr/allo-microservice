package com.dc.allo.roomplay.service.pk;

import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.rpc.domain.roomplay.PkSquareVo;

import java.util.List;
import java.util.Map;

public interface RoomCombatPowerService {
    Double getRoomCombatPowerByRoomUid(Long roomUid);
    void setOpponentList(PkSquareVo pkSquareVo);
    void setPageDataByZone(PkSquareVo pkSquareVo,int pageSize);
    void setPageDataByNotZone(PkSquareVo pkSquareVo,int page,int pageSize);
    void checkChooseRoomPkConditionOne(Long roomUid) throws Exception;
    void setPkSquareAllRoom(Map<String, Double> uidAndScoreMap, List<RoomVo> roomList, List<Long> roomUids);
}
