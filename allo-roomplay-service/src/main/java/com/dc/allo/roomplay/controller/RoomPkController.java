package com.dc.allo.roomplay.controller;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.RoomConstant;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.domain.room.PbPkInviteResp;
import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.roomplay.service.pk.RoomPkService;
import com.dc.allo.rpc.api.roomplay.pk.DcRoomPkService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.domain.roomplay.PkInfoVo;
import com.dc.allo.rpc.domain.roomplay.PkSquareVo;
import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpReq;
import com.erban.main.proto.PbHttpResp;
import com.erban.main.proto.PbPush;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/room/pk/pb")
public class RoomPkController  extends BaseController{
    @Autowired
    private RoomPkService roomPkService;
    @Reference
    private DcUserInfoService dcUserInfoService;
    @Reference
    private DcRoomPkService dcRoomPkService;

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES))
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    @RequestMapping(value = "/computeRoomHeatValueAward", method = RequestMethod.POST)
    public AlloResp computeRoomHeatValueAward(HttpServletRequest request) {
        try {
            dcRoomPkService.computeRoomHeatValueAward(0);
            return AlloResp.success();
        } catch (Exception e) {
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }


    /**
     * 获取PK信息
     * @return
     */
    @RequestMapping(value = "/getPkInfo", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbHttpResp$PbPkInfoResp",paramClassName ="com.erban.main.proto.PbHttpReq$PbRoomReq")
    public AlloResp getPkInfo(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        Long roomUid = null;
        try {
            ByteString dataBytes = pbHttpBizReq.getData();
            PbHttpReq.PbRoomReq pbRoomReq = dataBytes == null ? PbHttpReq.PbRoomReq.getDefaultInstance() : PbHttpReq.PbRoomReq.parseFrom(dataBytes.toByteArray());
            if(pbRoomReq.getRoomUid() == 0L){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            roomUid = pbRoomReq.getRoomUid();
            PkInfoVo pkInfoVo = roomPkService.getPkInfo(pbRoomReq.getRoomUid());
            return AlloResp.success(pkInfoVo);
        } catch (Exception e) {
            log.error("getPkInfo error,roomUid:{}",roomUid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 普通用户提醒pk
     * @return
     */
    @RequestMapping(value = "/wantPk", method = RequestMethod.POST)
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
    @RequestMapping(value = "/pkSquare", method = RequestMethod.POST)
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
    @RequestMapping(value = "/chooseRoomPk", method = RequestMethod.POST)
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
    @RequestMapping(value = "/targetChooseResult", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,clzFullName = "com.erban.main.proto.PbCommon$PbHttpBizResp",paramClassName ="com.erban.main.proto.PbHttpReq$PbPkTargetRoomChooseResultReq")
    public AlloResp targetChooseResult(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq= getPbHttpBizReq(request);
        Long targetRoomUid = null;
        try {
            ByteString dataBytes = pbHttpBizReq.getData();
            PbHttpReq.PbPkTargetRoomChooseResultReq resultReq = dataBytes == null ?PbHttpReq.PbPkTargetRoomChooseResultReq.getDefaultInstance() : PbHttpReq.PbPkTargetRoomChooseResultReq.parseFrom(dataBytes.toByteArray());
            if(resultReq.getRoomUid()==0L||resultReq.getTargetRoomUid()==0L||resultReq.getRecordId()==0L){
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
    @RequestMapping(value = "/getRoomPkingList", method = RequestMethod.POST)
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
     *  获取房间Pk榜
     * @return
     */
    @RequestMapping(value = "/getRoomPkMonthRank", method = RequestMethod.POST)
    public AlloResp getRoomPkMonthRank(HttpServletRequest request) {
        try {
            return AlloResp.success(roomPkService.getRoomPkMonthRank());
        } catch (Exception e) {
            log.error("getRoomPkMonthRank error",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     *  获取房间战绩
     *  type 1 日榜 当天的战绩 2周榜 当周的 3月榜 当月的
     * @return
     */
    @RequestMapping(value = "/getRoomPkRecordList", method = RequestMethod.POST)
    public AlloResp getRoomPkRecordList(Long roomUid,Byte type,Integer page,Integer pageSize) {
        try {
            return AlloResp.success(roomPkService.getRoomPkRecordList(roomUid,type,page,pageSize));
        } catch (Exception e) {
            log.error("getRoomPkRecordList error",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 取消PK
     * @return
     */
    @RequestMapping(value = "/cancelRoomPk", method = RequestMethod.POST)
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

    @RequestMapping(value="/test", method = RequestMethod.POST)
    public AlloResp test(HttpServletRequest httpServletRequest) {
        PbCommon.PbHttpBizReq.Builder requestBuild = PbCommon.PbHttpBizReq.newBuilder();
        requestBuild.setUid("61004340");
        requestBuild.setRoomUid(61004340L);
        requestBuild.setTicket("1");
        PbHttpReq.PbPkTargetRoomChooseResultReq.Builder builder =  PbHttpReq.PbPkTargetRoomChooseResultReq.newBuilder();
        PbHttpReq.PbPkSquareReq.Builder builder2 =  PbHttpReq.PbPkSquareReq.newBuilder();
        PbHttpReq.PbRoomReq.Builder builder3 =  PbHttpReq.PbRoomReq.newBuilder();
        PbHttpReq.PbRoomPkingListReq.Builder builder4 =  PbHttpReq.PbRoomPkingListReq.newBuilder();

        try {
            builder3.setRoomUid(61004340L);
            builder3.setTargetRoomUid(61004377L);

            builder2.setRoomUid(61004377L);
            builder.setChooseResult(true);

            requestBuild.setData(builder3.build().toByteString());
            RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), requestBuild.build().toByteArray());
          /*  Request request = new Request.Builder().url("http://localhost:8080/room/pk/pb/targetChooseResult").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkInviteResp samplesResp = PbHttpResp.PbPkInviteResp.parseFrom(result.getData());
                busiResult.setData(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
          /*  Request request = new Request.Builder().url("http://localhost:8080/room/pk/pb/getPkInfo").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkInfoResp samplesResp = PbHttpResp.PbPkInfoResp.parseFrom(result.getData());
                busiResult.setData(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
          /*  Request request = new Request.Builder().url("http://apibeta.allolike.com/room/pk/pb/getRoomPkingList").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkingRoomListResp samplesResp = PbHttpResp.PbPkingRoomListResp.parseFrom(result.getData());
                busiResult.setData(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        /*    Request request = new Request.Builder().url("http://apibeta.allolike.com/room/pk/pb/pkSquare").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkSquareResp samplesResp = PbHttpResp.PbPkSquareResp.parseFrom(result.getData());
                busiResult.setData(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
          /*  Request request = new Request.Builder().url("http://localhost:8080/room/pk/pb/cancelRoomPk").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbCommon.PbHttpBizResp samplesResp = PbCommon.PbHttpBizResp.parseFrom(result.getData());
                busiResult.setData(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
           /* Request request = new Request.Builder().url("http://localhost:8080/room/pk/pb/getRoomPkingList").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkingRoomListResp samplesResp = PbHttpResp.PbPkingRoomListResp.parseFrom(result.getData());
                busiResult.setData(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            /*Request request = new Request.Builder().url("http://localhost:30001/room/pk/pb/pkSquare").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkInviteResp samplesResp = PbHttpResp.PbPkInviteResp .parseFrom(result.getData());
                return AlloResp.success(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
           /* Request request = new Request.Builder().url("http://localhost:30001/room/pk/pb/wantPk").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkInviteResp samplesResp = PbHttpResp.PbPkInviteResp .parseFrom(result.getData());
                return AlloResp.success(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
          /*  Request request = new Request.Builder().url("http://localhost:30001/room/pk/pb/chooseRoomPk").post(body).build();
            try {
                PbCommon.PbHttpBizResp result = this.getResponseWithClass(request,PbCommon.PbHttpBizResp.class);
                PbHttpResp.PbPkInviteResp samplesResp = PbHttpResp.PbPkInviteResp .parseFrom(result.getData());
                return AlloResp.success(samplesResp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return AlloResp.success();
    }
    public static <T> T getResponseWithClass(Request request, Class<T> clazz) throws IOException {
        T respData = null;
        try (Response response = client.newCall(request).execute()) {
            MediaType mediaType = response.body().contentType();
            if(mediaType.subtype().equals("x-protobuf")){
                return PbUtils.bytes2pb(response.body().bytes(),clazz);
            }
            String resp = response.body().string();
            if (isSuccessResp(resp)) {
                JSONObject resJson = JSONObject.parseObject(resp);
                if (clazz.equals(String.class)) {
                    respData = (T) resJson.getString("data");
                } else {
                    respData = JSONObject.parseObject(resJson.getString("data"), clazz);
                }
            }
        }
        return respData;
    }
    private static boolean isSuccessResp(String resp) {
        if (!StringUtils.isEmpty(resp)) {
            JSONObject resJson = JSONObject.parseObject(resp);
            int result = resJson.getIntValue("result");
            return result == 0;
        }
        return false;
    }
}
