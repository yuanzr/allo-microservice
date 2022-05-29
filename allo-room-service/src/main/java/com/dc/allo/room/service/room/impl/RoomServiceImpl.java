package com.dc.allo.room.service.room.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.Constant.SysConfId;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.room.domain.room.RoomHot;
import com.dc.allo.room.domain.room.RunningRoomVo;
import com.dc.allo.room.mapper.room.RoomMapper;
import com.dc.allo.room.service.room.RoomHotService;
import com.dc.allo.room.service.room.RoomMemberService;
import com.dc.allo.room.service.room.RoomService;
import com.dc.allo.room.service.room.cache.RoomCache;
import com.dc.allo.rpc.api.sysconf.DcSysConfService;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.RoomMember;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * description: RoomServiceImpl
 *
 * @date: 2020年04月17日 15:06
 * @author: qinrenchuan
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomMemberService roomMemberService;
    @Autowired
    private RoomHotService roomHotService;

    @Autowired
    private RoomMapper roomMapper;

    @Reference
    private DcSysConfService dcSysConfService;

    /**
     * 根据主键（UID）查询房间全量信息
     * @param uid 房间主UID，room表主键
     * @return com.dc.allo.rpc.domain.room.Room
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:39
     */
    @Override
    public Room getByUid(Long uid) {
        Room room = this.roomCache.getByUid(uid);
        if (room == null) {
            room = roomMapper.getByUid(uid);
            if (room != null) {
                roomCache.addRoomCache(room);
            }
        }
        return room;
    }

    /**
     * 根据主键(UID)查询房间概要信息
     * @param uid 房间主UID，room表主键
     * @return com.dc.allo.rpc.domain.room.SimpleRoom
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:40
     */

    @Override
    public SimpleRoom getSimpleRoomByUid(Long uid) {
        Room room = getByUid(uid);
        if (room == null) {
            return null;
        }

        SimpleRoom simpleRoom = new SimpleRoom();
        BeanUtils.copyProperties(room, simpleRoom);
        return simpleRoom;
    }

    /**
     * 根据roomUid列表批量查询
     * @param roomUidList 房间主UID列表，room表主键
     * @return java.util.List<com.dc.allo.rpc.domain.room.Room>
     * @author qinrenchuan
     * @date 2020/4/29/0029 11:02
     */
    @Override
    public List<Room> queryByRoomUids(List<Long> roomUidList) {
        List<Room> rooms = roomCache.queryByRoomUids(roomUidList);
        if (roomUidList.size() == rooms.size()) {
            return rooms;
        }

        // 如果缓存只有部分数据，那么将从DB查询
        List<Long> cacheQueryUidList = new ArrayList<>();
        for (Room room : rooms) {
            cacheQueryUidList.add(room.getUid());
        }

        roomUidList.removeAll(cacheQueryUidList);
        if (roomUidList.size() > 0) {
            List<Room> roomList = roomMapper.queryByRoomUids(roomUidList);
            if (roomList != null && roomList.size() > 0) {
                roomCache.addRoomListCache(roomList);
                rooms.addAll(roomList);
            }
        }

        return rooms;
    }

    /**
     * 查询在房的管理员人数
     * @param roomUid
     * @return java.lang.Integer
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:18
     */
    @Override
    public Integer getInRoomManagerNum(Long roomUid) {
        List<RoomMember> roomMemberList = roomMemberService.getRoomManagerList(roomUid);
        List<Long> managerUidList = new ArrayList<Long>();
        if(CollectionUtils.isEmpty(roomMemberList)){
            managerUidList.add(roomUid);
        }else{
            managerUidList = roomMemberList.stream().map(member->member.getMemberUid()).collect(Collectors.toList());
            managerUidList.add(roomUid);
        }

        List<RoomVo> roomVos = roomCache.queryOnlineRoomManagerUids(managerUidList);

        if(CollectionUtils.isEmpty(roomVos)){
            return 0;
        }
        //在当前房间的管理员数
        int onCurrentRoomnum = 0;
        for(RoomVo roomVo : roomVos){
            if(roomVo == null || roomVo.getUid() ==null) {
                continue;
            }
            if(roomVo.getUid().equals(roomUid)){
                onCurrentRoomnum++;
            }
        }
        return onCurrentRoomnum;
    }

    /**
     * 获取在运行中的房间列表
     * @return java.util.List<com.dc.allo.common.domain.room.RoomVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:18
     */
    @Override
    public List<RoomVo> getHomeRunningRoomList() {
        List<RoomVo> roomVoList = new ArrayList<>();

        List<RunningRoomVo> runningRoomVos = roomCache.queryAllRunningRoom();

        if (runningRoomVos != null && runningRoomVos.size() > 0) {
            for (RunningRoomVo runningRoomVo : runningRoomVos) {
                Room room = getByUid(runningRoomVo.getUid());
                if (room == null) {
                    continue;
                }
                //判断是否是公聊大厅
                if (isPublicChatroom(room.getRoomId())) {
                    continue;
                }
                RoomVo roomVo = new RoomVo();
                BeanUtils.copyProperties(room, roomVo);
                if (room.getValid()) {
                    // 判断是否是热门房间
                    if (roomHotService.isHotRoomByUid(room.getUid())) {
                        RoomHot roomHot = roomHotService.getByRoomUid(room.getUid());
                        roomVo.setSeqNo(roomHot.getRoomSeq());
                    }
                    roomVo.setCount(runningRoomVo.getCount());
                    roomVoList.add(roomVo);
                }
            }
        } else {
            List<Room> roomList = roomMapper.queryValidRooms();
            runningRoomVos = new ArrayList<>();
            for (Room room : roomList) {
                //判断是否是公聊大厅
                if (isPublicChatroom(room.getRoomId())) {
                    continue;
                }
                RoomVo roomVo = new RoomVo();
                BeanUtils.copyProperties(room, roomVo);
                // 判断是否是热门房间
                if (roomHotService.isHotRoomByUid(room.getUid())) {
                    RoomHot roomHot = roomHotService.getByRoomUid(room.getUid());
                    roomVo.setSeqNo(roomHot.getRoomSeq());
                }

                RunningRoomVo runningRoomVo = new RunningRoomVo();
                runningRoomVo.setRoomId(roomVo.getRoomId());
                runningRoomVo.setUid(roomVo.getUid());
                runningRoomVo.setOnlineNum(1);
                runningRoomVos.add(runningRoomVo);

                roomVoList.add(roomVo);
            }
            roomCache.addRoomListCache(roomList);
            roomCache.addRunningRoomListCache(runningRoomVos);
        }
        return roomVoList;
    }

    /**
     * 判断是否是公聊房间
     * @param roomId
     * @return boolean
     * @author qinrenchuan
     * @date 2020/4/30/0030 15:21
     */
    private boolean isPublicChatroom(Long roomId) {
        AlloResp<String> sysConfResp = dcSysConfService.getSysConfValueById(SysConfId.public_chatroom_id);
        if (sysConfResp.getCode() != BusiStatus.SUCCESS.value().intValue()) {
            return false;
        }

        String sysConfIdStr = sysConfResp.getData();
        if (StringUtils.isNotBlank(sysConfIdStr) && roomId.equals(Long.valueOf(sysConfIdStr))) {
            return true;
        }

        return false;
    }

    /**
     * 根据roomUid列表查询房间列表（简体）
     * @param roomUids
     * @return java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>
     * @author qinrenchuan
     * @date 2020/6/3/0003 15:23
     */
    @Override
    public List<SimpleRoom> querySimpleRoomsByRoomUids(List<Long> roomUids) {
        List<SimpleRoom> rooms = roomCache.querySimpleRoomsByRoomUids(roomUids);
        if (rooms.size() == roomUids.size()) {
            return rooms;
        }

        // 如果缓存只有部分数据，那么将从DB查询
        List<Long> cacheQueryUidList = new ArrayList<>();
        for (SimpleRoom simpleRoom : rooms) {
            cacheQueryUidList.add(simpleRoom.getUid());
        }

        Iterator<Long> roomUidIterator = roomUids.iterator();
        while (roomUidIterator.hasNext()) {
            Long next = roomUidIterator.next();
            if (cacheQueryUidList.contains(next)) {
                roomUidIterator.remove();
            }
        }

        if (roomUids.size() > 0) {
            List<SimpleRoom> roomList = roomMapper.querySimpleRoomsByRoomUids(roomUids);
            if (roomList != null && roomList.size() > 0) {
                roomCache.addSimpleRoomListCache(roomList);
                rooms.addAll(roomList);
            }
        }

        return rooms;
    }
}
