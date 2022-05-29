package com.dc.allo.room.mapper.room;

import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: RoomMapper
 * date: 2020年04月17日 15:51
 * author: qinrenchuan
 */
@Mapper
public interface RoomMapper {
    /**
     * 根据UID查询用户信息
     * @param uid
     * @return com.dc.allo.rpc.domain.room.Room
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:52
     */
    @Select({"SELECT "
                + " uid, room_id, room_pwd, title, avatar, valid, online_num  "
            + " FROM room "
            + "WHERE uid=#{uid} "})
    Room getByUid(@Param("uid") Long uid);

    /**
     * 根据房主id批量查询
     * @param list
     * @return java.util.List<com.dc.allo.rpc.domain.room.Room>
     * @author qinrenchuan
     * @date 2020/4/29/0029 14:04
     */
    @Select({"<script>"
                + "SELECT "
                    + " uid, room_id, room_pwd, title, avatar,valid, online_num "
                + " FROM room "
                + "WHERE 1=1 "
                    + "<if test=\"list != null and list.size() > 0\"> "
                    + " AND uid in"
                        + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
                        +   "(#{item}) "
                        + "</foreach>"
                    + "</if>"
            + "</script>"})
    List<Room> queryByRoomUids(@Param("list") List<Long> list);
    
    /**
     * 查询打开的房间
     * @param  
     * @return java.util.List<com.dc.allo.rpc.domain.room.Room>
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:46
     */
    @Select({"SELECT uid, room_id, room_pwd, title, avatar, valid, online_num  FROM room WHERE valid=1"})
    List<Room> queryValidRooms();

    /**
     * 根据房主id批量查询(简体)
     * @param roomUids
     * @return java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>
     * @author qinrenchuan
     * @date 2020/6/3/0003 16:00
     */
    @Select({"<script>"
                + "SELECT "
                    + " uid, room_id, title, avatar "
                + " FROM room "
                    + "WHERE 1=1 "
                    + "<if test=\"list != null and list.size() > 0\"> "
                        + " AND uid in"
                            + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
                            +   "(#{item}) "
                            + "</foreach>"
                    + "</if>"
            + "</script>"})
    List<SimpleRoom> querySimpleRoomsByRoomUids(@Param("list") List<Long> roomUids);
}
