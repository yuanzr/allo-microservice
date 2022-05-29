package com.dc.allo.room.service.room;

import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import java.util.List;

/**
 * description: 房间Service组件
 * date: 2020年04月17日 15:06
 * author: qinrenchuan
 */
public interface RoomService {
    /**
     * 根据主键（UID）查询房间全量信息
     * @param uid 房间主UID，room表主键
     * @return com.dc.allo.rpc.domain.room.Room
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:39
     */
    Room getByUid(Long uid);

    /**
     * 根据主键(UID)查询房间概要信息
     * @param uid 房间主UID，room表主键
     * @return com.dc.allo.rpc.domain.room.SimpleRoom
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:40
     */
    SimpleRoom getSimpleRoomByUid(Long uid);

    /**
     * 根据roomUid列表批量查询
     * @param roomUidList 房间主UID列表，room表主键
     * @return java.util.List<com.dc.allo.rpc.domain.room.Room>
     * @author qinrenchuan
     * @date 2020/4/29/0029 11:02
     */
    List<Room> queryByRoomUids(List<Long> roomUidList);

    /**
     * 查询在房的管理员人数
     * @param roomUid
     * @return java.lang.Integer
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:18
     */
    Integer getInRoomManagerNum(Long roomUid);

    /**
     * 获取在运行中的房间列表
     * @return java.util.List<com.dc.allo.common.domain.room.RoomVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:18
     */
    List<RoomVo> getHomeRunningRoomList();

    /**
     * 根据roomUid列表查询房间列表（简体）
     * @param roomUids
     * @return java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>
     * @author qinrenchuan
     * @date 2020/6/3/0003 15:23
     */
    List<SimpleRoom> querySimpleRoomsByRoomUids(List<Long> roomUids);
}
