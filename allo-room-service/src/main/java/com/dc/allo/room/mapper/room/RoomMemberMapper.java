package com.dc.allo.room.mapper.room;

import com.dc.allo.rpc.domain.room.RoomMember;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: RoomMemberMapper
 *
 * @date: 2020年04月30日 11:57
 * @author: qinrenchuan
 */
@Mapper
public interface RoomMemberMapper {

    /**
     * 根据房间主uid查询房间管理员列表
     * @param roomUid
     * @return java.util.List<com.dc.allo.rpc.domain.room.RoomMember>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:58
     */
    @Select({" SELECT "
                + " mem_record_id, room_uid, member_uid, "
                + " member_status, member_role, apply_time, "
                + " join_time, update_time "
            + " FROM room_member"
            + " WHERE member_status=2 AND member_role=2 AND room_uid=#{roomUid}"
            + " ORDER BY join_time DESC "})
    List<RoomMember> queryRoomMemberManagerByRoomUid(@Param("roomUid") Long roomUid);
}
