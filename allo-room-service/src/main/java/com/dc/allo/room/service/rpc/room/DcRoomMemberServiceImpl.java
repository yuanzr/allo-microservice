package com.dc.allo.room.service.rpc.room;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.room.service.room.RoomMemberService;
import com.dc.allo.rpc.api.room.DcRoomMemberService;
import com.dc.allo.rpc.domain.room.RoomMember;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: DcRoomMemberServiceImpl
 *
 * @date: 2020年04月30日 17:10
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class DcRoomMemberServiceImpl implements DcRoomMemberService {

    @Autowired
    private RoomMemberService roomMemberService;

    /**
     * 根据roomUid查询管理员列表
     * @param roomUid
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.room.RoomMember>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:13
     */
    @Override
    public AlloResp<List<RoomMember>> getRoomManagerList(Long roomUid) {
        if (roomUid == null || roomUid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            List<RoomMember> list = roomMemberService.getRoomManagerList(roomUid);
            return AlloResp.success(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    /**
     * 获取用户在房间的角色 RoomConstant.RoomMemberRole
     * @param roomUid
     * @param currentUid
     * @return com.dc.allo.rpc.proto.AlloResp<java.lang.Byte> {@link  com.dc.allo.common.constants.RoomConstant.RoomMemberRole}
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:16
     */
    @Override
    public AlloResp<Integer> getRoomRole(Long roomUid, Long currentUid) {
        if (roomUid == null || roomUid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        if (currentUid == null || currentUid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            Byte userRole = roomMemberService.getRoomRoleByUid(roomUid, currentUid);
            return AlloResp.success((int)userRole);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }
    /**
     * 获取当前在这个房间的管理员列表
     * RoomMemberService.getOnCurrentRoomManager 要用旧格式的缓存格式
     * @param roomUid
     * @param managerUidList
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<java.lang.Long>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:25
     */
    @Override
    public AlloResp<List<Long>> getOnCurrentRoomManager(Long roomUid,List<Long> managerUidList) {
        if (roomUid == null || roomUid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        if (managerUidList == null || managerUidList.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        try {
            List<Long> list = roomMemberService.getOnCurrentRoomManager(roomUid, managerUidList);
            return AlloResp.success(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    /**
     * 获取指定的房间所有管理员Uid
     * RoomMemberService.getRoomManagerList 要用旧格式的缓存格式
     */
    @Override
    public AlloResp<Map<Long,List<Long>>> getRoomManagerListBatch(List<Long> roomUids) {
        if (roomUids == null || roomUids.size() <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        try {
            Map<Long,List<Long>> map = roomMemberService.getRoomManagerListBatch(roomUids);
            return AlloResp.success(map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }
}
