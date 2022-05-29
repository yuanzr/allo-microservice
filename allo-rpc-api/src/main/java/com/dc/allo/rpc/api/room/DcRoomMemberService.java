package com.dc.allo.rpc.api.room;

import com.dc.allo.rpc.domain.room.RoomMember;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.List;
import java.util.Map;

public interface DcRoomMemberService {
    AlloResp<List<RoomMember>> getRoomManagerList(Long roomUid);
    /**
     * 获取用户在房间的角色 RoomConstant.RoomMemberRole
     */
    AlloResp<Integer> getRoomRole(Long roomUid,Long currentUid);
    /**
     * 获取当前在这个房间的管理员列表
     * RoomMemberService.getOnCurrentRoomManager 要用旧格式的缓存格式
     */
    AlloResp<List<Long>> getOnCurrentRoomManager(Long roomUid,List<Long> managerUidList);
    /**
     * 获取指定的房间所有管理员Uid
     * RoomMemberService.getRoomManagerList 要用旧格式的缓存格式
     */
    AlloResp<Map<Long,List<Long>>> getRoomManagerListBatch(List<Long> roomUids);
}
