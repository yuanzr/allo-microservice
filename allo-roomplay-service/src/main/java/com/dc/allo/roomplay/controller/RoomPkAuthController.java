package com.dc.allo.roomplay.controller;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dc.allo.common.annotation.Authorization;
import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.RoomConstant;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.domain.room.PbPkInviteResp;
import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.roomplay.service.pk.RoomPkService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.roomplay.PkSquareVo;
import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpReq;
import com.erban.main.proto.PbPush;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 跟RoomPkController内容没什么区别
 * 只是在请求路径上针对需要权限校验的接口加了auth/前缀
 */
@Slf4j
@RestController
@RequestMapping("/room/pk/pb")
public class RoomPkAuthController extends BaseController{
    @Autowired
    private RoomPkService roomPkService;
    @Reference
    private DcUserInfoService dcUserInfoService;

    /**
     * 普通用户提醒pk
     * @return
     */
    @RequestMapping(value = "/auth/wantPk", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbCommon$PbHttpBizResp",paramClassName ="com.erban.main.proto.PbCommon$PbHttpBizReq")
    public AlloResp wantPk(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        try {
            if(StringUtil.isEmpty(pbHttpBizReq.getUid()) || pbHttpBizReq.getRoomUid() ==0L){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            AlloResp<SimpleUserInfo> alloUserInfoResp = dcUserInfoService.getSimpleUserInfoByUid(Long.parseLong(pbHttpBizReq.getUid()));
            if(!alloUserInfoResp.isSuccess() || alloUserInfoResp.getData() == null){
                return AlloResp.failed(BusiStatus.USERNOTEXISTS);
            }
            PbPush.PbUserWantPkPush.Builder builder =PbPush.PbUserWantPkPush.newBuilder();
            builder.setUserNick(alloUserInfoResp.getData().getNick());
            builder.setUserId(alloUserInfoResp.getData().getUid());
            roomPkService.sendWantPkMsg(pbHttpBizReq.getRoomUid(),builder.build());
            return AlloResp.success();
        }catch (DCException e){
            log.error("wantPk service error,uid:{}",pbHttpBizReq.getUid(),e);
            return AlloResp.failed(e);
        }catch (Exception e) {
            log.error("wantPk error",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * pk广场
     * @return
     */
    @RequestMapping(value = "/auth/pkSquare", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbHttpResp$PbPkSquareResp",paramClassName ="com.erban.main.proto.PbHttpReq$PbPkSquareReq")
    public AlloResp pkSquare(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        Long roomUid = null;
        try {
            ByteString dataBytes = pbHttpBizReq.getData();
            PbHttpReq.PbPkSquareReq req = dataBytes == null ? PbHttpReq.PbPkSquareReq.getDefaultInstance() : PbHttpReq.PbPkSquareReq.parseFrom(dataBytes.toByteArray());
            if(req.getRoomUid() == 0L){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            roomUid = req.getRoomUid();
            PkSquareVo pkSquareVo = roomPkService.getPkSquareData(req);
            return AlloResp.success(pkSquareVo);
        } catch (Exception e) {
            log.error("pkSquare error,roomUid:{}",roomUid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
    /**
     * 发起PK
     * @return
     */
    @RequestMapping(value = "/auth/chooseRoomPk", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbHttpResp$PbPkInviteResp",paramClassName ="com.erban.main.proto.PbHttpReq$PbRoomReq")
    public AlloResp chooseRoomPk(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        Long roomUid = null;
        Long currentUid = null;
        Long targetRoomUid = null;
        try {
            ByteString dataBytes = pbHttpBizReq.getData();
            PbHttpReq.PbRoomReq pbRoomReq = dataBytes == null ? PbHttpReq.PbRoomReq.getDefaultInstance() : PbHttpReq.PbRoomReq.parseFrom(dataBytes.toByteArray());
            if(StringUtils.isEmpty(pbHttpBizReq.getUid()) || pbRoomReq.getRoomUid() == 0L || pbRoomReq.getTargetRoomUid() == 0L){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            currentUid = Long.parseLong(pbHttpBizReq.getUid());
            roomUid = pbRoomReq.getRoomUid();
            targetRoomUid = pbRoomReq.getTargetRoomUid();

            roomPkService.checkChoosePkTimeInterval(currentUid,roomUid);

            RoomPkRecord roomPkRecord = roomPkService.chooseRoomPk(currentUid,roomUid,targetRoomUid, RoomConstant.ChooseRoomPkUserType.NORMAL_USER);
            roomPkService.sendChooseRoomPkMsg(targetRoomUid,roomPkRecord);
            roomPkService.setChoosePkTime(currentUid,roomUid);
            return AlloResp.success(new PbPkInviteResp(roomPkRecord.getInviteInvalidTime()*1000L));
        }catch (DCException e){
            log.error("chooseRoomPk service error,roomUid:{}",roomUid,e);
            return AlloResp.failed(e);
        }catch (Exception e){
            log.error("chooseRoomPk error,roomUid:{}",roomUid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 目标房间通知是否同意pk
     * @return
     */
    @RequestMapping(value = "/auth/targetChooseResult", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbCommon$PbHttpBizResp",paramClassName ="com.erban.main.proto.PbHttpReq$PbPkTargetRoomChooseResultReq")
    public AlloResp targetChooseResult(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        Long targetRoomUid = null;
        try {
            ByteString dataBytes = pbHttpBizReq.getData();
            PbHttpReq.PbPkTargetRoomChooseResultReq resultReq = dataBytes == null ?PbHttpReq.PbPkTargetRoomChooseResultReq.getDefaultInstance() : PbHttpReq.PbPkTargetRoomChooseResultReq.parseFrom(dataBytes.toByteArray());
            if(resultReq.getRoomUid()==0L || resultReq.getTargetRoomUid()==0L || resultReq.getRecordId() == 0L){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            targetRoomUid = resultReq.getRoomUid();
            RoomPkRecord roomPkRecord = null;
            if (resultReq.getChooseResult()) {
                roomPkRecord = roomPkService.targetChooseAgree(resultReq);
                //通知两边房间Pk开始
                roomPkService.getPkInfoVo(targetRoomUid, roomPkRecord, true);
            }else{
                roomPkRecord = roomPkService.targetChooseReject(resultReq);
                roomPkService.targetRoomChooseResultPush(roomPkRecord);
            }
            return AlloResp.success();
        } catch (DCException e) {
            log.error("targetChooseResult service error,targetRoomUid:{}",targetRoomUid,e);
            return AlloResp.failed(e);
        } catch (Exception e) {
            log.error("targetChooseResult error,targetRoomUid:{}",targetRoomUid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 获取Pk中的房间列表
     * @return
     */
    @RequestMapping(value = "/auth/getRoomPkingList", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbHttpResp$PbPkingRoomListResp",paramClassName ="com.erban.main.proto.PbHttpReq$PbRoomPkingListReq")
    public AlloResp getRoomPkingList(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        Long uid = null;
        try {
            if(pbHttpBizReq.getUid() == null){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            uid = Long.parseLong(pbHttpBizReq.getUid());
            ByteString dataBytes = pbHttpBizReq.getData();
            PbHttpReq.PbRoomPkingListReq req = dataBytes == null ?PbHttpReq.PbRoomPkingListReq.getDefaultInstance() : PbHttpReq.PbRoomPkingListReq.parseFrom(dataBytes.toByteArray());
            return AlloResp.success(roomPkService.getRoomPkingList(uid,req));
        } catch (Exception e) {
            log.error("getRoomPkingList error,uid:{}",uid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 取消PK
     * @return
     */
    @RequestMapping(value = "/auth/cancelRoomPk", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbCommon$PbHttpBizResp",paramClassName ="com.erban.main.proto.PbHttpReq$PbRoomReq")
    public AlloResp cancelRoomPk(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        Long roomUid = null;
        try {
            ByteString dataBytes = pbHttpBizReq.getData();
            PbHttpReq.PbRoomReq pbRoomReq = dataBytes == null ? PbHttpReq.PbRoomReq.getDefaultInstance() : PbHttpReq.PbRoomReq.parseFrom(dataBytes.toByteArray());
            if(pbRoomReq.getRoomUid() == 0L){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            roomUid = pbRoomReq.getRoomUid();
            roomPkService.cancelRoomPk(roomUid);
            return AlloResp.success();
        }catch (DCException e){
            log.error("cancelRoomPk service error,roomUid:{}",roomUid,e);
            return AlloResp.failed(e);
        }catch (Exception e){
            log.error("cancelRoomPk error,roomUid:{}",roomUid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

}
