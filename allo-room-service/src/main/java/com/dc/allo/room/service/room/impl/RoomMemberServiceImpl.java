package com.dc.allo.room.service.room.impl;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.room.mapper.room.RoomMemberMapper;
import com.dc.allo.room.service.room.RoomMemberService;
import com.dc.allo.room.service.room.cache.RoomCache;
import com.dc.allo.room.service.room.cache.RoomMemberCache;
import com.dc.allo.rpc.domain.room.RoomMember;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * description: RoomMemberServiceImpl
 *
 * @date: 2020年04月30日 11:24
 * @author: qinrenchuan
 */
@Service
public class RoomMemberServiceImpl implements RoomMemberService {

    @Autowired
    private RoomMemberCache roomMemberCache;
    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomMemberMapper roomMemberMapper;

    /**
     * 获取该管理员列表
     * @param roomUid
     * @return java.util.List<com.dc.allo.rpc.domain.room.RoomMember>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:23
     */
    @Override
    public List<RoomMember> getRoomManagerList(Long roomUid) {
        List<RoomMember> roomManagerList = roomMemberCache.getRoomManagerList(roomUid);
        if (roomManagerList != null && roomManagerList.size() > 0) {
            return roomManagerList;
        }

        List<RoomMember> roomMembers = roomMemberMapper.queryRoomMemberManagerByRoomUid(roomUid);
        if (roomMembers != null && roomMembers.size() > 0) {
            roomMemberCache.setRoomMemberManagerList(roomUid, roomMembers);
        }
        return roomMembers;
    }

    /**
     * 批量查询管理员列表
     * @param roomUids
     * @return java.util.Map<java.lang.Long,java.util.List<java.lang.Long>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:54
     */
    @Override
    public Map<Long, List<Long>> getRoomManagerListBatch(List<Long> roomUids) {
        Map<Long, List<Long>> resultMap = new HashMap<>(roomUids.size());
        for (Long roomUid : roomUids) {
            List<Long> roomManagerList = getRoomManagerUids(roomUid);
            resultMap.put(roomUid, roomManagerList);
        }
        return resultMap;
    }


    /**
     * 查询管理员uid列表
     * @param roomUid
     * @return java.util.List<java.lang.Long>
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:59
     */
    private List<Long> getRoomManagerUids(Long roomUid) {
        List<Long>  uidList = new ArrayList<>();

        List<RoomMember> roomManagerList = getRoomManagerList(roomUid);
        if (roomManagerList != null && roomManagerList.size() > 0) {
            for (RoomMember roomMember : roomManagerList) {
                uidList.add(roomMember.getMemberUid());
            }
        }

        return uidList;
    }

    /**
     * 获取用户在房间的角色
     * @param roomUid
     * @param currentUid
     * @return java.lang.Byte  {@link  com.dc.allo.common.constants.RoomConstant.RoomMemberRole}
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:21
     */
    @Override
    public Byte getRoomRoleByUid(Long roomUid, Long currentUid) {
        if(roomUid.equals(currentUid)){
            return Constant.RoomMemberRole.OWNER;
        }
        List<RoomMember> members = this.getRoomManagerList(roomUid);
        if (CollectionUtils.isEmpty(members)) {
            return Constant.RoomMemberRole.MEMBER;
        }
        members = members.stream()
                .filter(member -> member.getMemberUid().equals(currentUid))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(members)) {
            return Constant.RoomMemberRole.MANAGER;
        }
        return Constant.RoomMemberRole.MEMBER;
    }

    /**
     * 取当前在这个房间的管理员列表
     * @param roomUid
     * @param managerUidList
     * @return java.util.List<java.lang.Long>
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:32
     */
    @Override
    public List<Long> getOnCurrentRoomManager(Long roomUid, List<Long> managerUidList) {
        RoomVo roomVo;
        List<Long> managerUidNewList = new ArrayList<Long>();
        for(Long managerUid : managerUidList){
            roomVo = roomCache.getOnlineRoomManagerByUid(managerUid);
            if(roomVo != null && roomVo.getUid().equals(roomUid)){
                managerUidNewList.add(managerUid);
            }
        }
        return managerUidNewList;
    }
}
