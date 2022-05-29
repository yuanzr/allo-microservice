package com.dc.allo.rpc.api.room;

import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.dc.allo.rpc.proto.AlloResp;

import java.util.List;
import java.util.Map;

/**
 * description: DcRoomInfoService
 * date: 2020年04月17日 15:56
 * author: qinrenchuan
 */
public interface DcRoomInfoService {
    /**
     * 根据主键（UID）查询房间全量信息
     * @param uid 房间主UID，room表主键
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:39
     */
    AlloResp<Room> getByUid(Long uid);

    /**
     * 根据主键(UID)查询房间概要信息
     * @param uid 房间主UID，room表主键
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:40
     */
    AlloResp<SimpleRoom> getSimpleRoomByUid(Long uid);

    /**
     * 获取房间列表
     * @param roomUids
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.room.Room>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:07
     */
    AlloResp<List<Room>> queryRoomBeanListByUids(List<Long> roomUids);

    /**
     * 查询在房的管理员人数-RoomService.getInRoomManagerNum
     * @param roomUid
     * @return com.dc.allo.rpc.proto.AlloResp<java.lang.Integer>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:14
     */
    AlloResp<Integer> getInRoomManagerNum(Long roomUid);

    /**
     * 获取在运行中的房间列表
     * RoomService.getHomeRunningRoomList 要用旧格式的缓存格式
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.common.domain.room.RoomVo>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:14
     */
    AlloResp<List<RoomVo>> getHomeRunningRoomList();

    /**
     * 根据roomuids查询room列表（简）
     * @param roomUids
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:33
     */
    AlloResp<List<SimpleRoom>> querySimpleRoomListByUids(List<Long> roomUids);

    /**
     * 根据roomuids查询房间（简）key-value(roomUid-> SimpleRoom)
     * @param roomUids
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:33
     */
    AlloResp<Map<Long, SimpleRoom>> querySimpleRoomMapByUids(List<Long> roomUids);
}
