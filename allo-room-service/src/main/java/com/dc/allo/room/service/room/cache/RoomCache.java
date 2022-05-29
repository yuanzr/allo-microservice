package com.dc.allo.room.service.room.cache;

import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.room.domain.room.RunningRoomVo;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import java.util.List;

/**
 * description: RoomCache
 * date: 2020年04月17日 15:08
 * author: qinrenchuan
 */
public interface RoomCache {
    /**
     * 根据主键（UID）查询房间全量信息
     * @param uid 房间主UID，room表主键
     * @return com.dc.allo.rpc.domain.room.Room
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:39
     */
    Room getByUid(Long uid);

    /**
     * 将Room信息保存到redis
     * @param room
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:47
     */
    void addRoomCache(Room room);

    /**
     * 根据房间主uid批量查询
     * @param roomUids 房间主uid列表
     * @return java.util.List<com.dc.allo.rpc.domain.room.Room>
     * @author qinrenchuan
     * @date 2020/4/29/0029 11:05
     */
    List<Room> queryByRoomUids(List<Long> roomUids);

    /**
     * 批量保存room列表到缓存
     * @param roomList
     * @return void
     * @author qinrenchuan
     * @date 2020/4/29/0029 14:40
     */
    void addRoomListCache(List<Room> roomList);

    /**
     * 根据UID列表查询所在的房间
     * @param
     * @return java.util.List<java.lang.String>
     * @author qinrenchuan
     * @date 2020/4/30/0030 14:07
     */
    List<RoomVo> queryOnlineRoomManagerUids(List<Long> uidList);

    /**
     * 根据管理员用户id查询他所在的房间
     * @param uid
     * @return com.dc.allo.common.domain.room.RoomVo
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:45
     */
    RoomVo getOnlineRoomManagerByUid(Long uid);

    /**
     * 查询所有正在运行的房间
     * @param
     * @return java.util.List<com.dc.allo.room.domain.room.RunningRoomVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 15:16
     */
    List<RunningRoomVo> queryAllRunningRoom();

    /**
     * 设置缓存
     * @param runningRoom
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:55
     */
    void setRunningRoom(RunningRoomVo runningRoom);

    /**
     * 批量设置运行中的房间
     * @param runningRoomVos
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:02
     */
    void addRunningRoomListCache(List<RunningRoomVo> runningRoomVos);

    /**
     * 根据roomUid列表查询房间列表（简体）
     * @param roomUids
     * @return java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>
     * @author qinrenchuan
     * @date 2020/6/3/0003 15:28
     */
    List<SimpleRoom> querySimpleRoomsByRoomUids(List<Long> roomUids);

    /**
     * 批量保存room列表到缓存
     * @param roomList
     * @return void
     * @author qinrenchuan
     * @date 2020/6/3/0003 16:03
     */
    void addSimpleRoomListCache(List<SimpleRoom> roomList);
}
