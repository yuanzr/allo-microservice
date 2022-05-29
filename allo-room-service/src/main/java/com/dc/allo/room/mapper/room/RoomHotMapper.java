package com.dc.allo.room.mapper.room;

import com.dc.allo.room.domain.room.RoomHot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: RoomHotMapper
 *
 * @date: 2020年04月30日 16:23
 * @author: qinrenchuan
 */
@Mapper
public interface RoomHotMapper {
    /**
     * 根据房间主uid查询热门房间
     * @param roomUid
     * @return com.dc.allo.room.domain.room.RoomHot
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:25
     */
    @Select({"SELECT uid, room_seq, start_live_time, end_live_time FROM room_hot WHERE uid=#{roomUid}"})
    RoomHot getByRoomUid(@Param("roomUid") Long roomUid);
}
