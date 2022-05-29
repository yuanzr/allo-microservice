package com.dc.allo.room.service.rpc.room;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.room.service.room.RoomService;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: DcRoomInfoServiceImpl
 *
 * @date: 2020年04月17日 15:57
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class DcRoomInfoServiceImpl implements DcRoomInfoService {

    @Autowired
    private RoomService roomService;

    /**
     * 根据主键（UID）查询房间全量信息
     * @param uid 房间主UID，room表主键
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:39
     */
    @Override
    public AlloResp<Room> getByUid(Long uid) {
        if (uid == null || uid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        Room room;
        try {
            room = roomService.getByUid(uid);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }

        return AlloResp.success(room);
    }

    /**
     * 根据主键(UID)查询房间概要信息
     * @param uid 房间主UID，room表主键
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:40
     */
    @Override
    public AlloResp<SimpleRoom> getSimpleRoomByUid(Long uid) {
        if (uid == null || uid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        SimpleRoom simpleRoom;
        try {
            simpleRoom = roomService.getSimpleRoomByUid(uid);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }

        return AlloResp.success(simpleRoom);
    }

    /**
     * 根据房间UID批量查询房间列表
     * @param roomUids
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.room.Room>>
     * @author qinrenchuan
     * @date 2020/4/29/0029 10:55
     */
    @Override
    public AlloResp<List<Room>> queryRoomBeanListByUids(List<Long> roomUids) {
        if (roomUids == null || roomUids.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            List<Room> rooms = roomService.queryByRoomUids(roomUids);
            return AlloResp.success(rooms);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    /**
     * 查询在房的管理员人数-RoomService.getInRoomManagerNum
     * @param roomUid
     * @return com.dc.allo.rpc.proto.AlloResp<java.lang.Integer>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:14
     */
    @Override
    public AlloResp<Integer> getInRoomManagerNum(Long roomUid) {
        if (roomUid == null || roomUid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            Integer managerNum = roomService.getInRoomManagerNum(roomUid);
            return AlloResp.success(managerNum);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    /**
     * 获取在运行中的房间列表
     * RoomService.getHomeRunningRoomList 要用旧格式的缓存格式
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.common.domain.room.RoomVo>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:14
     */
    @Override
    public AlloResp<List<RoomVo>> getHomeRunningRoomList() {
        try {
            List<RoomVo> roomVos = roomService.getHomeRunningRoomList();
            return AlloResp.success(roomVos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }


    /**
     * 根据roomuids查询room列表（简）
     * @param roomUids
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:33
     */
    @Override
    public AlloResp<List<SimpleRoom>> querySimpleRoomListByUids(List<Long> roomUids) {
        if (roomUids == null || roomUids.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR);
        }

        try {
            List<SimpleRoom> rooms = roomService.querySimpleRoomsByRoomUids(roomUids);
            return AlloResp.success(rooms);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR);
        }
    }

    /**
     * 根据roomuids查询房间（简）key-value(roomUid-> SimpleRoom)
     * @param roomUids
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:33
     */
    @Override
    public AlloResp<Map<Long, SimpleRoom>> querySimpleRoomMapByUids(List<Long> roomUids) {
        if (roomUids == null || roomUids.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR);
        }

        Map<Long, SimpleRoom> resultMap = new HashMap<>();

        try {
            List<SimpleRoom> rooms = roomService.querySimpleRoomsByRoomUids(roomUids);
            if (rooms == null || rooms.size() == 0) {
                return AlloResp.success(resultMap);
            }

            for (SimpleRoom simpleRoom : rooms) {
                resultMap.put(simpleRoom.getUid(), simpleRoom);
            }

            return AlloResp.success(resultMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR);
        }
    }
}
