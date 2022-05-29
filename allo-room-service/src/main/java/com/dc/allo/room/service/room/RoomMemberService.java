package com.dc.allo.room.service.room;

import com.dc.allo.rpc.domain.room.RoomMember;
import java.util.List;
import java.util.Map;

/**
 * description: RoomMemberService
 * date: 2020年04月30日 11:23
 * author: qinrenchuan
 */
public interface RoomMemberService {
    /**
     * 获取该管理员列表
     * @param roomUid
     * @return java.util.List<com.dc.allo.rpc.domain.room.RoomMember>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:23
     */
    List<RoomMember> getRoomManagerList(Long roomUid);

    /**
     * 批量查询管理员列表
     * @param roomUids
     * @return java.util.Map<java.lang.Long,java.util.List<java.lang.Long>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:54
     */
    Map<Long, List<Long>> getRoomManagerListBatch(List<Long> roomUids);

    /**
     * 获取用户在房间的角色
     * @param roomUid
     * @param currentUid
     * @return java.lang.Byte  {@link  com.dc.allo.common.constants.RoomConstant.RoomMemberRole}
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:21
     */
    Byte getRoomRoleByUid(Long roomUid, Long currentUid);

    /**
     * 取当前在这个房间的管理员列表
     * @param roomUid
     * @param managerUidList
     * @return java.util.List<java.lang.Long>
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:32
     */
    List<Long> getOnCurrentRoomManager(Long roomUid, List<Long> managerUidList);


}
