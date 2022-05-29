package com.dc.allo.room.service.room.cache;

import com.dc.allo.rpc.domain.room.RoomMember;
import java.util.List;

/**
 * description: RoomMemberCache
 * date: 2020年04月30日 11:27
 * author: qinrenchuan
 */
public interface RoomMemberCache {
    /**
     * 获取该管理员列表
     * @param roomUid
     * @return java.util.List<com.dc.allo.rpc.domain.room.RoomMember>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:31
     */
    List<RoomMember> getRoomManagerList(Long roomUid);

    /**
     * 设置房间管理员缓存
     * @param roomUid
     * @param list
     * @return void
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:53
     */
    void setRoomMemberManagerList(Long roomUid, List<RoomMember> list);
}
