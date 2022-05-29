package com.dc.allo.roomplay.service.pk.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.SpringContextUtil;
import com.dc.allo.common.component.delay.redis.DelayMessage;
import com.dc.allo.common.component.delay.redis.RedisDelayQueue;
import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.constants.*;
import com.dc.allo.common.domain.room.RoomHeatValuePrizeRecord;
import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.kafka.message.GiftMessage;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.common.vo.RoomOwnerHeatValueAward;
import com.dc.allo.common.vo.RoomPkActivityUpLevelVo;
import com.dc.allo.roomplay.constant.ConfigConstant;
import com.dc.allo.roomplay.domain.example.RoomPkRecordExample;
import com.dc.allo.roomplay.domain.vo.RoomPkEventVo;
import com.dc.allo.roomplay.domain.vo.RoomPkGoldVo;
import com.dc.allo.roomplay.domain.vo.RoomPkWinVo;
import com.dc.allo.roomplay.mapper.RoomHeatValueTotayMapper;
import com.dc.allo.roomplay.mapper.pk.*;
import com.dc.allo.roomplay.redis.message.PkMessage;
import com.dc.allo.roomplay.service.pk.RoomCombatPowerService;
import com.dc.allo.roomplay.service.pk.RoomPkService;
import com.dc.allo.roomplay.service.pk.cache.RoomPkCache;
import com.dc.allo.roomplay.springevent.event.RoomPkEvent;
import com.dc.allo.rpc.api.msg.SendMsgService;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.room.DcRoomMemberService;
import com.dc.allo.rpc.api.sysconf.DcSysConfService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.RoomMember;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.dc.allo.rpc.domain.roomplay.*;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserVo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoomPkServiceImpl implements RoomPkService {
    @Reference
    private DcRoomInfoService dcRoomInfoService;
    @Reference
    private DcUserInfoService dcUserInfoService;
    @Reference
    private SendMsgService sendMsgService;
    @Reference
    private DcRoomMemberService dcRoomMemberService;
    @Reference
    private DcSysConfService dcSysConfService;
    @Autowired
    private ConfigConstant configConstant;
    @Autowired
    private RoomPkCache roomPkCache;
    @Autowired
    private RoomCombatPowerService roomCombatPowerService;
    @Autowired
    private RoomPkMapper roomPkMapper;
    @Autowired
    private RoomPkRecordMapper roomPkRecordMapper;
    @Autowired
    private RoomPkSummaryMapper roomPkSummaryMapper;
    @Autowired
    private RoomPkRecordExpand roomPkRecordExpand;
    @Autowired
    private RoomHeatValuePrizeRecordMapper roomHeatValuePrizeRecordMapper;
    @Autowired
    private RoomHeatValueTotayMapper roomHeatValueTotayMapper;
    @Autowired
    @Qualifier("pkDelayQueue")
    private RedisDelayQueue redisDelayQueue;
    @Autowired
    private KafkaSender kafkaSender;

    //房间Pk输的惩罚措施和惩罚静态图
    private static String[][] punishmentList = new String[][]{
            {"https://img.scarllet.cn/pk_fangqie.svga", "https://img.scarllet.cn/pk_static_fanqie.png"},
            {"https://img.scarllet.cn/pk_guo.svga", "https://img.scarllet.cn/pk_static_tieguo.png"},
            {"https://img.scarllet.cn/pk_tuoxie.svga", "https://img.scarllet.cn/pk_static_xie.png"},
            {"https://img.scarllet.cn/pk_xueqiu.svga", "https://img.scarllet.cn/pk_static_xueqiu.png"},
            {"https://img.scarllet.cn/pk_zhadang", "https://img.scarllet.cn/pk_static_zhadang.png"}};

    //房间Pk中显示的武器样式 每一组有左右两边
    private static String[][] pkingArmsList = new String[][]{
            {"https://img.scarllet.cn/pk_zuobishou.svga", "https://img.scarllet.cn/pk_youbishou.svga"},
            {"https://img.scarllet.cn/pk_zuocuizi.svga", "https://img.scarllet.cn/pk_youcuizi.svga"},
            {"https://img.scarllet.cn/pk_zuofutou.svga", "https://img.scarllet.cn/pk_youfutou.svga"},
            {"https://img.scarllet.cn/pk_zuojian.svga", "https://img.scarllet.cn/pk_youjian.svga"}};

    private static final String pk_win = "https://img.scarllet.cn/pk_win.svga";
    private static final String pk_lose = "https://img.scarllet.cn/pk_lose.svga";
    //平局
    private static final String pk_not_win = "https://img.scarllet.cn/pk_pingju.svga";
    //房间pk发起pk的时间间隔(单位毫秒)
    private static final int choosePkTimeInterval = 60000;
    private static final Random punishmentRandom = new Random();

    @Override
    public PkInfoVo getPkInfo(Long roomUid) throws Exception {
        RoomPkRecord roomPkRecord = getRoomCurrentPkRecord(roomUid);
        if (roomPkRecord == null) {
            return buildInitPkInfoVo(roomUid);
        }
        if (roomPkRecord.getPkStatus().byteValue() != RoomConstant.RoomPkStatus.PKing) {
            return buildInitPkInfoVo(roomUid);
        }
        return getPkInfoVo(roomUid, roomPkRecord, false);
    }

    @Override
    public PbPkingRoomListVo getRoomPkingList(Long uid, PbHttpReq.PbRoomPkingListReq req) {
        PbPkingRoomListVo pbPkingRoomListVo = new PbPkingRoomListVo();
        if (req.getZone() != 0L) {
            pbPkingRoomListVo.setZone(req.getZone());
            pbPkingRoomListVo.setPkingRoomVoList(getPkingRoomPageDataByNotZone(uid, req.getPage(), req.getPageSize()));
            return pbPkingRoomListVo;
        }

        List<RoomPkRecord> roomPkRecords = roomPkCache.getRoomPkRecordListFromCache();
        if (CollectionUtils.isEmpty(roomPkRecords)) {
            return null;
        }
        roomPkRecords = roomPkRecords.stream().filter(record -> record.getPkStatus() != null && record.getPkStatus() == RoomConstant.RoomPkStatus.PKing).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roomPkRecords)) {
            return null;
        }
        pbPkingRoomListVo.setPkingRoomVoList(getPbPkingRoomVoList(uid, roomPkRecords));

        roomPkCache.savePkingRoomListVoCache(uid, pbPkingRoomListVo.getPkingRoomVoList());
        return pbPkingRoomListVo;
    }

    @Override
    public void sendWantPkMsg(Long roomUid, PbPush.PbUserWantPkPush invitePush) {
        AlloResp<String> todayWantNumResp = dcSysConfService.getSysConfValueById(CommonConstant.SysConfId.room_pk_today_want_pk_num);
        int todayWantNum = 3;
        if (todayWantNumResp.isSuccess() && !StringUtils.isEmpty(todayWantNumResp.getData())) {
            todayWantNum = Integer.parseInt(todayWantNumResp.getData());
        }
        int userTodayWantPkNum = roomPkCache.getUserTodayWantPkNum(invitePush.getUserId());
        if (userTodayWantPkNum > todayWantNum) {
            throw new DCException(BusiStatus.ROOM_PK_WANTPK_NOT_ENOUGH);
        }

        List<String> receiverUids = new ArrayList<String>();
        receiverUids.add(roomUid.toString());
        AlloResp<List<RoomMember>> roomManagerListResp = dcRoomMemberService.getRoomManagerList(roomUid);
        if (roomManagerListResp.isSuccess() && !CollectionUtils.isEmpty(roomManagerListResp.getData())) {
            receiverUids.addAll(roomManagerListResp.getData().stream().map(member -> member.getMemberUid().toString()).collect(Collectors.toList()));
        }
        PbCommon.PbBizPush.Builder pbBizPush = PbCommon.PbBizPush.newBuilder();
        pbBizPush.setOs(0);
        pbBizPush.setTimestamp(System.currentTimeMillis());
        pbBizPush.setModule(PbCommon.Module.pushModule.getNumber());
        pbBizPush.setMethod("PbUserWantPkPush");
        pbBizPush.setData(invitePush.toByteString());
        pbBizPush.setPushEvent(PbCommon.PushEvent.pushWantPk);
        pbBizPush.setPushType(PbCommon.PushType.pTypeUnicast);

        String objectName = RongCloudConstant.RongCloundMessageObjectName.PB_PUSH;
        AlloResp<Integer> response = null;
        try {
            response = this.sendMsgService.sendPrivateStatusMessage(configConstant.secretaryUid, receiverUids, objectName, Base64.encodeBase64String(pbBizPush.build().toByteArray()));
            if (response.isSuccess()) {
                roomPkCache.addUserTodayWantPkNum(invitePush.getUserId());
            }
        } catch (Exception e) {
            log.error("sendWantPkMsg error，roomUid={}", roomUid, e);
        }
    }

    @Override
    public PkSquareVo getPkSquareData(PbHttpReq.PbPkSquareReq pbPkSquareReq) {
        AlloResp<SimpleRoom> roomResp = dcRoomInfoService.getSimpleRoomByUid(pbPkSquareReq.getRoomUid());
        if (!roomResp.isSuccess()) {
            throw new DCException(BusiStatus.ROOMNOTEXIST);
        }
        SimpleRoom room = roomResp.getData();
        if (room == null) {
            throw new DCException(BusiStatus.ROOMNOTEXIST);
        }
        PkSquareVo pkSquareVo = new PkSquareVo();
        pkSquareVo.setCurrentCombatPower(roomCombatPowerService.getRoomCombatPowerByRoomUid(room.getUid()));
        pkSquareVo.setRoomUid(room.getUid());
        pkSquareVo.setRoomTitle(room.getTitle());
        pkSquareVo.setRoomAvatar(room.getAvatar());

        if (pbPkSquareReq.getZone() == 0L) {
            roomCombatPowerService.setOpponentList(pkSquareVo);
            roomCombatPowerService.setPageDataByZone(pkSquareVo, pbPkSquareReq.getPagerSize());
        } else {
            roomCombatPowerService.setPageDataByNotZone(pkSquareVo, pbPkSquareReq.getPager(), pbPkSquareReq.getPagerSize());
        }
        pkSquareVo.setZone(pbPkSquareReq.getZone() + 1);
        return pkSquareVo;
    }

    @Override
    public void checkChoosePkTimeInterval(Long uid, Long roomUid) {
        roomPkCache.checkChoosePkTimeInterval(uid, roomUid, choosePkTimeInterval);
    }

    @Override
    public RoomPkRecord chooseRoomPk(Long currentUid, Long roomUid, Long targetRoomUid, Byte chooseRoomPkUserType) throws Exception {
        String firstLockVal = null;
        String twoLockVal = null;
        RoomPkRecord pkRecord = null;
        try {
            firstLockVal = addRoomPkLock(roomUid, false);
            try {
                twoLockVal = addRoomPkLock(targetRoomUid, false);

                List<Long> uidList = new ArrayList();
                uidList.add(roomUid);
                uidList.add(targetRoomUid);
                AlloResp<List<Room>> roomsResp = dcRoomInfoService.queryRoomBeanListByUids(uidList);
                if (!roomsResp.isSuccess() || CollectionUtils.isEmpty(roomsResp.getData()) || roomsResp.getData().size() < 2) {
                    throw new DCException(BusiStatus.ROOMNOTEXIST);
                }
                Optional<Room> roomOptional = roomsResp.getData().stream().filter(currentRoom -> currentRoom.getUid().equals(roomUid)).findFirst();
                Room room = roomOptional.isPresent() ? roomOptional.get() : null;
                Optional<Room> targetRoomOptional = roomsResp.getData().stream().filter(currentRoom -> currentRoom.getUid().equals(targetRoomUid)).findFirst();
                Room targetRoom = targetRoomOptional.isPresent() ? targetRoomOptional.get() : null;

                checkRoomPkRecordStatus(room, targetRoom, roomUid, targetRoomUid);

                AlloResp<String> inviteInvalidTimeResp = dcSysConfService.getSysConfValueById(CommonConstant.SysConfId.room_pk_invite_invalid_time);
                Short inviteInvalidTime = 30;
                if (inviteInvalidTimeResp.isSuccess() && !StringUtils.isEmpty(inviteInvalidTimeResp.getData())) {
                    inviteInvalidTime = Short.parseShort(inviteInvalidTimeResp.getData());
                }

                //管理后台发起Pk不需要校验
                if (chooseRoomPkUserType == RoomConstant.ChooseRoomPkUserType.ADMIN) {
                    adminChooseRoomPk(currentUid, room, targetRoom, inviteInvalidTime);
                }
                if (chooseRoomPkUserType == RoomConstant.ChooseRoomPkUserType.NORMAL_USER) {
                    pkRecord = normalUserChooseRoomPk(currentUid, room, targetRoom, inviteInvalidTime);
                }
            } finally {
                delRoomPkLock(targetRoomUid, twoLockVal);
            }
        } finally {
            delRoomPkLock(roomUid, firstLockVal);
        }
        return pkRecord;
    }

    @Override
    public void setChoosePkTime(Long uid, Long roomUid) {
        roomPkCache.setChoosePkTime(uid, roomUid, choosePkTimeInterval);
    }

    @Override
    @Transactional
    public RoomPkRecord targetChooseAgree(PbHttpReq.PbPkTargetRoomChooseResultReq resultReq) throws Exception {
        String firstLockVal = null;
        String twoLockVal = null;
        RoomPkRecord roomPkRecord = null;
        try {
            firstLockVal = addRoomPkLock(resultReq.getTargetRoomUid(), false);
            try {
                twoLockVal = addRoomPkLock(resultReq.getRoomUid(), false);
                roomPkRecord = getRoomCurrentPkRecord(resultReq.getTargetRoomUid());
                checkTargetChooseResult(roomPkRecord);
                targetAgreePk(roomPkRecord);
                addRoomHeatValue(roomPkRecord, RoomConstant.RoomPkHeatValueEventType.SUC_OPEN);
            } finally {
                delRoomPkLock(resultReq.getRoomUid(), twoLockVal);
            }
        } finally {
            delRoomPkLock(resultReq.getTargetRoomUid(), firstLockVal);
        }
        return roomPkRecord;
    }

    @Override
    @Transactional
    public RoomPkRecord targetChooseReject(PbHttpReq.PbPkTargetRoomChooseResultReq resultReq) {
        String firstLockVal = null;
        String twoLockVal = null;
        RoomPkRecord roomPkRecord = null;
        try {
            firstLockVal = addRoomPkLock(resultReq.getTargetRoomUid(), false);
            try {
                twoLockVal = addRoomPkLock(resultReq.getRoomUid(), false);
                roomPkRecord = getRoomCurrentPkRecord(resultReq.getTargetRoomUid());
                checkTargetChooseResult(roomPkRecord);
                targetRejectPk(roomPkRecord, resultReq.getRoomUid(), resultReq.getTargetRoomUid());
            } finally {
                delRoomPkLock(resultReq.getRoomUid(), twoLockVal);
            }
        } finally {
            delRoomPkLock(resultReq.getTargetRoomUid(), firstLockVal);
        }
        return roomPkRecord;
    }

    @Override
    @Async
    public void targetRoomChooseResultPush(RoomPkRecord record) {
        PbPush.PbPkTargetRoomChooseResultPush.Builder builder = PbPush.PbPkTargetRoomChooseResultPush.newBuilder();
        builder.setChooseResult(false);
        builder.setRoomTitle(record.getTargetRoomTitle());
        builder.setRoomUid(record.getTargetRoomUid());
        builder.setRoomAvatar(record.getTargetRoomAvatar());
        builder.setRoomCombatPower(roomCombatPowerService.getRoomCombatPowerByRoomUid(record.getTargetRoomUid()));
        ByteString bodyString = builder.build().toByteString();
        PbCommon.PbBizPush pbBizPush = getPbBizPush(null, "PbPkTargetRoomChooseResultPush", bodyString, PbCommon.PushEvent.pushInvitePkResult, PbCommon.PushType.pTypeUnicast);
        List<Long> uidList = new ArrayList<Long>();
        uidList.add(record.getSponsorUid());
        sendPkPushPrivateMsg(uidList, pbBizPush);
    }

    @Override
    public List<RoomPkWinVo> getRoomPkMonthRank() {
        return roomPkRecordExpand.getRoomPkListOrderByWinNum(DateTimeUtil.getFisrtDayOfMonth());
    }

    @Override
    public List<RoomPkRecord> getRoomPkRecordList(Long roomUid, Byte type, Integer page, Integer pageSize) {
        Date startTime = null;
        switch (type) {
            //日
            case 1:
                startTime = DateTimeUtil.getNextDay(new Date(), 0, 0, 0, 0);
                break;
            //周
            case 2:
                startTime = DateTimeUtil.getMonday(new Date(), 0, 0, 0);
                break;
            //月
            case 3:
                startTime = DateTimeUtil.getFisrtDayOfMonth();
                break;
        }
        return roomPkRecordExpand.getRoomPkRecordList(roomUid, startTime);
    }

    @Override
    @Transactional
    public void cancelRoomPk(Long roomUid) {
        RoomPkRecord roomPkRecord = getRoomCurrentPkRecord(roomUid);
        if (roomPkRecord == null) {
            return;
        }
        cancelRoomPkUpdateData(roomPkRecord);
    }

    /**
     * 目标房间拒绝PK
     *
     * @param record
     * @param roomUid       目标房主Uid
     * @param targetRoomUid 发起Pk房间房主Uid
     */
    public void targetRejectPk(RoomPkRecord record, Long roomUid, Long targetRoomUid) {
        Long totalNum = null;
        try {
            record.setPkStatus(RoomConstant.RoomPkStatus.REJECT_PK);
            updateRoomPkRecordDB(record);
            totalNum = roomPkCache.addRoomPkTodayRejectNum(targetRoomUid, roomUid);
            roomPkCache.delRoomPkRecordCache(roomUid, record.getId());
        } catch (Exception e) {
            log.error("targetRejectPk.error,roomuid:{}", roomUid, e);
            if (totalNum != null && totalNum > 0) {
                roomPkCache.reduceRoomPkTodayRejectNum(roomUid, targetRoomUid);
            }
            throw e;
        }
    }

    /**
     * 目标房间同意PK
     *
     * @param record
     */
    public void targetAgreePk(RoomPkRecord record) throws Exception {
        try {
            AlloResp<String> roomPkTimeResp = dcSysConfService.getSysConfValueById(CommonConstant.SysConfId.room_pk_time);
            Integer roomPkTime = 7;
            if (roomPkTimeResp.isSuccess() && !StringUtils.isEmpty(roomPkTimeResp.getData())) {
                roomPkTime = Integer.parseInt(roomPkTimeResp.getData());
            }
            String recordIdStr = record.getId().toString();
            Date now = new Date();
            record.setPkStartTime(now);
            record.setPkEndTime(DateTimeUtil.getNextMinute(now, roomPkTime));
            record.setPkStatus(RoomConstant.RoomPkStatus.PKing);
            //更新db
            updateRoomPkRecordDB(record);
            //对应对应的pk记录缓存
            roomPkCache.updateRoomPkUidRecordCache(recordIdStr, record);
            //发送pk结束消息到延迟队列
            sendPkEndMessageToMQ(record);
        } catch (Exception e) {
            log.error("targetAgreePk error,roomUid:{},targetUid:{}", record.getRoomUid(), record.getTargetRoomUid());
            String recordId = record == null ? null : record.getId().toString();
            roomPkCache.pkCancelDelPkCache(record.getRoomUid().toString(), record.getTargetRoomUid().toString(), recordId);
            throw e;
        }
    }


    public void checkTargetChooseResult(RoomPkRecord record) {
        //没有发起Pk的房间的Pk记录或PK的状态不是请求Pk中 不做处理
        if (record == null || record.getPkStatus().byteValue() != RoomConstant.RoomPkStatus.PLEASE_PK) {
            throw new DCException(BusiStatus.CANT_LAUNCH_PK);
        }
        if (record.getInviteInvalidTime() != null && (record.getInviteInvalidTime() * 1000 + record.getCreateTime().getTime()) < System.currentTimeMillis()) {
            throw new DCException(BusiStatus.ROOM_PK_ALREADY_CANCEL);
        }
    }

    /**
     * 管理后台发起Pk
     * 异常则回滚Cache数据 抛出异常
     */
    public void adminChooseRoomPk(Long currentUid, Room room, Room targetRoom, Short inviteInvalidTime) throws Exception {
        AlloResp<String> roomPkTimeResp = dcSysConfService.getSysConfValueById(CommonConstant.SysConfId.room_pk_time);
        Integer roomPkTime = 7;
        if (roomPkTimeResp.isSuccess() && !StringUtils.isEmpty(roomPkTimeResp.getData())) {
            roomPkTime = Integer.parseInt(roomPkTimeResp.getData());
        }
        Date now = new Date();
        RoomPkRecord roomPkRecord = null;
        try {
            roomPkRecord = addRoomPkRecord(currentUid, room, targetRoom, now, DateTimeUtil.getNextMinute(now, roomPkTime), inviteInvalidTime);
            saveRoomPkRecordToCache(room.getUid(), roomPkRecord);
            sendAdminChoosePkPushVo(roomPkRecord, inviteInvalidTime);
            //发送pk结束消息到延迟队列
            sendPkEndMessageToMQ(roomPkRecord);
            //通知两边房间Pk开始
            getPkInfoVo(room.getUid(), roomPkRecord, true);
        } catch (Exception e) {
            log.error("adminChooseRoomPk error,roomUid:{},targetUid:{}", room.getUid(), targetRoom.getUid());
            String recordId = roomPkRecord == null ? null : roomPkRecord.getId().toString();
            roomPkCache.pkCancelDelPkCache(room.getUid().toString(), targetRoom.getUid().toString(), recordId);
            throw e;
        }
    }

    /**
     * 普通用户发起Pk
     */
    public RoomPkRecord normalUserChooseRoomPk(Long currentUid, Room room, Room targetRoom, Short inviteInvalidTime) throws Exception {
        checkChooseRoomPk(currentUid, room.getUid(), targetRoom.getUid());
        return chooseRoomPk(currentUid, room, targetRoom, inviteInvalidTime);
    }

    /**
     * 异常则回滚Cache数据 抛出异常
     */
    public RoomPkRecord chooseRoomPk(Long currentUid, Room room, Room targetRoom, Short inviteInvalidTime) throws Exception {
        RoomPkRecord roomPkRecord = null;
        try {
            roomPkRecord = addRoomPkRecord(currentUid, room, targetRoom, inviteInvalidTime);
            saveRoomPkRecordToCache(room.getUid(), roomPkRecord);
        } catch (Exception e) {
            log.error("chooseRoomPk error,roomUid:{},targetUid:{}", room.getUid(), targetRoom.getUid());
            String recordId = roomPkRecord == null ? null : roomPkRecord.getId().toString();
            roomPkCache.pkCancelDelPkCache(room.getUid().toString(), targetRoom.getUid().toString(), recordId);
            throw e;
        }
        return roomPkRecord;
    }

    public void sendPkEndMessageToMQ(RoomPkRecord record) {
        PkMessage message = buildPkMessage(record, RoomConstant.RoomPkStatus.PK_END);      //组装消息
        DelayMessage<PkMessage> delayMessage = new DelayMessage<>();
        delayMessage.setData(message);
        delayMessage.setDuplicateKey(UUID.randomUUID().toString());
        delayMessage.setExpireTime(record.getPkEndTime().getTime());
        redisDelayQueue.enQueue(delayMessage);
    }

    @Transactional
    @Override
    public void handleRoomPkMsg(PkMessage pkMessage) throws Exception {
        if (pkMessage.getPkType() == RoomConstant.RoomPkStatus.PK_END) {
            handleRoomPkEndMsg(pkMessage.getRoomUid(), pkMessage.getTargetRoomUid(), pkMessage.getPkRecordId(), true);
        }
    }

    @Transactional
    public void handleRoomPkEndMsg(Long roomUid, Long targetRoomUid, Long recordId, boolean isWait) throws Exception {
        String fistLockVal = null;
        String twoLockVal = null;
        try {
            fistLockVal = addRoomPkLock(roomUid, isWait);
            try {
                twoLockVal = addRoomPkLock(targetRoomUid, isWait);

                RoomPkRecord roomPkRecord = getRoomPkRecordByRecordId(recordId);
                if (roomPkRecord == null) {
                    log.error("handleRoomPkEndMsg.recordId:{}", recordId);
                    return;
                }
                if (roomPkRecord.getPkStatus() != RoomConstant.RoomPkStatus.PKing) {
                    log.error("handleRoomPkEndMsg.record.pkStatus != pking,Id:{},status:{}", recordId, roomPkRecord.getPkStatus());
                    return;
                }
                AlloResp<Room> roomAlloResp = dcRoomInfoService.getByUid(roomPkRecord.getRoomUid());
                if (!roomAlloResp.isSuccess() || roomAlloResp.getData() == null) {
                    throw new DCException(BusiStatus.ROOMNOTEXIST);
                }
                Room room = roomAlloResp.getData();
                if (room.getOnlineNum() == null) {
                    room.setOnlineNum(0);
                }
                AlloResp<Room> targetRoomAlloResp = dcRoomInfoService.getByUid(roomPkRecord.getTargetRoomUid());
                if (!targetRoomAlloResp.isSuccess() || targetRoomAlloResp.getData() == null) {
                    throw new DCException(BusiStatus.ROOMNOTEXIST);
                }
                Room targetRoom = targetRoomAlloResp.getData();
                if (targetRoom.getOnlineNum() == null) {
                    targetRoom.setOnlineNum(0);
                }
                roomPkRecord.setPkStatus(RoomConstant.RoomPkStatus.PK_END);
                roomPkRecord.setRoomTotalGold(new BigDecimal(getPkingRoomGold(roomPkRecord.getRoomUid())));
                roomPkRecord.setTargetTotalGold(new BigDecimal(getPkingRoomGold(roomPkRecord.getTargetRoomUid())));
                roomPkRecord.setRoomTotalPerson(room.getOnlineNum().shortValue());
                roomPkRecord.setTargetTotalPerson(targetRoom.getOnlineNum().shortValue());
                //设置PK结果
                setRoomPkRecordResultWinAndLose(roomPkRecord);
                //设置输的惩罚措施
                String punishmentStaticImg = setRoomPkPunishment(roomPkRecord);
                //更新DB
                updateRoomPkRecordDB(roomPkRecord);
                //更新总记录
                updateRoomPkSummaryDB(roomPkRecord);
                PbPush.PbPkResultPush.Builder resultPush = builderPkResultPushBuild(roomPkRecord);
                //发送本次Pk结束的消息
                sendRoomPkResultNotice(roomPkRecord, resultPush, punishmentStaticImg);
                //删除本次Pk的相关缓存
                pkEndDelPkCache(roomPkRecord);
                log.info("handleRoomPkEndMsg.id:{},result:{}", recordId, roomPkRecord.getPkResult());
                if (roomPkRecord.getPkResult() != RoomConstant.RoomPkResult.NO_WIN) {
                    log.info("handleRoomPkEndMsg.id:{},winUid:{}", recordId, roomPkRecord.getWinUid());
                    roomPkCache.incrRoomPkTodayWinNum(roomPkRecord.getWinUid());
                    addRoomHeatValue(roomPkRecord, RoomConstant.RoomPkHeatValueEventType.PK_WIN);
                }
                addRoomHeatValue(roomPkRecord, RoomConstant.RoomPkHeatValueEventType.PK_GIFT_TOTAL);
            } finally {
                delRoomPkLock(targetRoomUid, twoLockVal);
            }
        } finally {
            delRoomPkLock(roomUid, fistLockVal);
        }
    }

    public PbPush.PbPkResultPush.Builder builderPkResultPushBuild(RoomPkRecord roomPkRecord) {
        if (roomPkRecord.getPkResult() == RoomConstant.RoomPkResult.NO_WIN) {
            PbPush.PbPkResultPush.Builder pkResultPush = PbPush.PbPkResultPush.newBuilder();
            pkResultPush.setDraw(true);
            return pkResultPush;
        }
        List<SimpleUserVo> winVoList = getPkFourUserVoListByGold(roomPkRecord.getId(), roomPkRecord.getWinUid().toString());
        List<SimpleUserVo> loseVoList = getPkFourUserVoListByGold(roomPkRecord.getId(), roomPkRecord.getLoseUid().toString());
        //设置用户信息
        setUserInfo(winVoList, loseVoList);
        Double winRoomToTotalGold = getPkingRoomGold(roomPkRecord.getWinUid());
        Double loseRoomToTotalGold = getPkingRoomGold(roomPkRecord.getWinUid());
        PbPush.PbPkResultPush.Builder pkResultPush = PbPush.PbPkResultPush.newBuilder();
        pkResultPush.setWinRoomId(roomPkRecord.getWinUid());
        pkResultPush.setLoseRoomId(roomPkRecord.getLoseUid());
        pkResultPush.setPunishSrc(roomPkRecord.getPunishment());
        pkResultPush.setRecordId(roomPkRecord.getId());
        pkResultPush.setWinTotalGold(winRoomToTotalGold);
        pkResultPush.setLoseTotalGold(loseRoomToTotalGold);
        pkResultPush.setDraw(false);
        setWinAndLoseTitle(roomPkRecord, pkResultPush);
        setWinAndLoseUserList(winVoList, loseVoList, pkResultPush);
        return pkResultPush;
    }

    public void setWinAndLoseUserList(List<SimpleUserVo> winVoList, List<SimpleUserVo> loseVoList, PbPush.PbPkResultPush.Builder builder) {
        List<PbUser.PbSimpleUserVo> pbWinList = null;
        List<PbUser.PbSimpleUserVo> pbLoseList = null;
        PbUser.PbSimpleUserVo.Builder pbUserVo = null;
        if (!CollectionUtils.isEmpty(winVoList)) {
            pbWinList = new ArrayList<PbUser.PbSimpleUserVo>(winVoList.size());
            for (SimpleUserVo winVo : winVoList) {
                pbUserVo = PbUser.PbSimpleUserVo.newBuilder();
                if (!StringUtils.isEmpty(winVo.getAvatar())) {
                    pbUserVo.setAvatar(winVo.getAvatar());
                }
                if (winVo.getErbanNo() != null) {
                    pbUserVo.setErbanNo(winVo.getErbanNo());
                }
                if (winVo.getGold() != null) {
                    pbUserVo.setGold(winVo.getGold());
                }
                if (winVo.getGender() != null) {
                    pbUserVo.setGender(winVo.getGender());
                }
                if (!StringUtils.isEmpty(winVo.getNick())) {
                    pbUserVo.setNick(winVo.getNick());
                }
                pbUserVo.setUid(winVo.getUid());
                pbWinList.add(pbUserVo.build());
            }
        }
        if (!CollectionUtils.isEmpty(loseVoList)) {
            pbLoseList = new ArrayList<PbUser.PbSimpleUserVo>(loseVoList.size());
            for (SimpleUserVo loseVo : loseVoList) {
                pbUserVo = PbUser.PbSimpleUserVo.newBuilder();
                if (!StringUtils.isEmpty(loseVo.getAvatar())) {
                    pbUserVo.setAvatar(loseVo.getAvatar());
                }
                if (loseVo.getErbanNo() != null) {
                    pbUserVo.setErbanNo(loseVo.getErbanNo());
                }
                if (loseVo.getGold() != null) {
                    pbUserVo.setGold(loseVo.getGold());
                }
                if (loseVo.getGender() != null) {
                    pbUserVo.setGender(loseVo.getGender());
                }
                if (!StringUtils.isEmpty(loseVo.getNick())) {
                    pbUserVo.setNick(loseVo.getNick());
                }
                pbUserVo.setUid(loseVo.getUid());
                pbLoseList.add(pbUserVo.build());
            }
        }
        if (!CollectionUtils.isEmpty(pbWinList)) {
            builder.addAllWinUserVoList(pbWinList);
        }
        if (!CollectionUtils.isEmpty(pbLoseList)) {
            builder.addAllLoseUserVoList(pbLoseList);
        }
    }

    public void setWinAndLoseTitle(RoomPkRecord record, PbPush.PbPkResultPush.Builder builder) {
        if (record.getWinUid().longValue() == record.getRoomUid() && !StringUtils.isEmpty(record.getRoomTitle())) {
            builder.setWinRoomTitle(record.getRoomTitle());
        }
        if (record.getWinUid().longValue() == record.getTargetRoomUid() && !StringUtils.isEmpty(record.getTargetRoomTitle())) {
            builder.setWinRoomTitle(record.getTargetRoomTitle());
        }
        if (record.getLoseUid().longValue() == record.getRoomUid() && !StringUtils.isEmpty(record.getRoomTitle())) {
            builder.setLoseRoomTitle(record.getRoomTitle());
        }
        if (record.getLoseUid().longValue() == record.getTargetRoomUid() && !StringUtils.isEmpty(record.getTargetRoomTitle())) {
            builder.setLoseRoomTitle(record.getTargetRoomTitle());
        }
    }

    public void sendRoomPkResultNotice(RoomPkRecord roomPkRecord, PbPush.PbPkResultPush.Builder resultPush, String punishmentStaticImg) throws Exception {
        if (roomPkRecord.getPkResult().byteValue() == RoomConstant.RoomPkResult.NO_WIN) {
            PbResource.PbSvgaText.Builder drawTextBuild1 = PbResource.PbSvgaText.newBuilder();
            drawTextBuild1.setAlignment(PbResource.SvgaTextAlignment.SvgaTextAlignmentCenter);
            drawTextBuild1.setSize(30);
            drawTextBuild1.setColor("#ffffff");
            drawTextBuild1.setKey("zhu_biao_ti");
            drawTextBuild1.setText("Draw!");

            PbResource.PbSvgaText.Builder drawTextBuild2 = PbResource.PbSvgaText.newBuilder();
            drawTextBuild2.setAlignment(PbResource.SvgaTextAlignment.SvgaTextAlignmentCenter);
            drawTextBuild2.setSize(30);
            drawTextBuild2.setColor("#ffffff");
            drawTextBuild2.setKey("fu_wen");
            drawTextBuild2.setText("Draw! Please work harder next time.");
            List drawTextList = new ArrayList();
            drawTextList.add(drawTextBuild1.build());
            drawTextList.add(drawTextBuild2.build());

            PbResource.PbSvgaInfo.Builder drawSvga = PbResource.PbSvgaInfo.newBuilder();
            drawSvga.setUrl(pk_not_win);
            drawSvga.addAllTexts(drawTextList);
            resultPush.setDrawSvga(drawSvga);
        } else {
            PbResource.PbSvgaText.Builder loseTextBuild = PbResource.PbSvgaText.newBuilder();
            loseTextBuild.setAlignment(PbResource.SvgaTextAlignment.SvgaTextAlignmentCenter);
            loseTextBuild.setSize(20);
            loseTextBuild.setColor("#ffffff");
            loseTextBuild.setKey("cheng_fa_wen");
            loseTextBuild.setText("please accept the punishment");
            List loseTextList = new ArrayList();
            loseTextList.add(loseTextBuild.build());

            String winRoomAvatar = roomPkRecord.getRoomAvatar();
            String loseRoomAvatar = roomPkRecord.getTargetRoomAvatar();
            if (roomPkRecord.getWinUid().equals(roomPkRecord.getTargetRoomUid())) {
                winRoomAvatar = roomPkRecord.getTargetRoomAvatar();
                loseRoomAvatar = roomPkRecord.getRoomAvatar();
            }
            PbResource.PbSvgaImage.Builder loseImageBuild = PbResource.PbSvgaImage.newBuilder();
            loseImageBuild.setKey("Defeat_Head_2");
            loseImageBuild.setValue(loseRoomAvatar);
            PbResource.PbSvgaImage.Builder loseImageBuildTwo = PbResource.PbSvgaImage.newBuilder();
            loseImageBuildTwo.setKey("cheng_fa_biao_qing");
            loseImageBuildTwo.setValue(punishmentStaticImg);

            List loseImageList = new ArrayList();
            loseImageList.add(loseImageBuild.build());
            loseImageList.add(loseImageBuildTwo.build());

            PbResource.PbSvgaImage.Builder winImageBuild = PbResource.PbSvgaImage.newBuilder();
            winImageBuild.setKey("win_Head_1");
            winImageBuild.setValue(winRoomAvatar);
            List winImageList = new ArrayList();
            winImageList.add(winImageBuild.build());

            PbResource.PbSvgaInfo.Builder winSvga = PbResource.PbSvgaInfo.newBuilder();
            PbResource.PbSvgaInfo.Builder loseSvga = PbResource.PbSvgaInfo.newBuilder();
            winSvga.setUrl(pk_win);
            winSvga.addAllImages(winImageList);

            loseSvga.setUrl(pk_lose);
            loseSvga.addAllTexts(loseTextList);
            loseSvga.addAllImages(loseImageList);

            resultPush.setWinSvga(winSvga);
            resultPush.setLoseSvga(loseSvga);
        }
        if (roomPkRecord.getPkResult() == RoomConstant.RoomPkResult.NO_WIN) {
            sendRoomPkResultNotice(roomPkRecord.getRoomUid(), roomPkRecord.getTargetRoomUid(), resultPush.build());
        } else {
            sendRoomPkResultNotice(roomPkRecord.getWinUid(), roomPkRecord.getLoseUid(), resultPush.build());
        }
    }

    private void sendRoomPkResultNotice(Long winRoomUid, Long loseRoomUid, PbPush.PbPkResultPush pkResultPush) throws Exception {
        PbCommon.PbBizPush pbBizPush = getPbBizPush(winRoomUid, "PbPkResultPush", pkResultPush.toByteString(), PbCommon.PushEvent.pushPkResult, PbCommon.PushType.pTypeBroadcast);
        AlloResp<Integer> result = null;
        try {
            result = sendMsgService.sendSendChatRoomMsg(winRoomUid, configConstant.secretaryUid, Base64.encodeBase64String(pbBizPush.toByteArray()), RongCloudConstant.RongCloundMessageObjectName.PB_PUSH);
        } catch (Exception e) {
            log.error("sendRoomPkResultNotice.roomUid:{},result:{}", winRoomUid, result == null ? null : result.getCode(), e);
        }
        pbBizPush = getPbBizPush(loseRoomUid, "PbPkResultPush", pkResultPush.toByteString(), PbCommon.PushEvent.pushPkResult, PbCommon.PushType.pTypeBroadcast);
        try {
            sendMsgService.sendSendChatRoomMsg(loseRoomUid, configConstant.secretaryUid, Base64.encodeBase64String(pbBizPush.toByteArray()), RongCloudConstant.RongCloundMessageObjectName.PB_PUSH);
        } catch (Exception e) {
            log.error("sendRoomPkResultNotice.roomUid:{},result:{}", loseRoomUid, result, e);
        }
    }

    /**
     * PK平局 更新总记录
     */
    public void updateRoomPkSummaryDBByNotWin(RoomPkRecord record) {
        boolean isInsert = false;
        BigDecimal leftCombatPower = new BigDecimal(roomCombatPowerService.getRoomCombatPowerByRoomUid(record.getRoomUid()));
        RoomPkSummary roomPkSummary = roomPkSummaryMapper.selectByPrimaryKey(record.getRoomUid());
        if (roomPkSummary == null) {
            roomPkSummary = new RoomPkSummary();
            roomPkSummary.setRoomUid(record.getRoomUid());
            roomPkSummary.setDrawTimes(1);
            roomPkSummary.setTotalGold(record.getRoomTotalGold());
            roomPkSummary.setCreateTime(new Date());
            isInsert = true;
        } else {
            if (roomPkSummary.getDrawTimes() == null) {
                roomPkSummary.setDrawTimes(0);
            }
            if (roomPkSummary.getTotalGold() == null) {
                roomPkSummary.setTotalGold(new BigDecimal(0));
            }
            roomPkSummary.setDrawTimes(roomPkSummary.getDrawTimes() + 1);
            roomPkSummary.setTotalGold(roomPkSummary.getTotalGold().add(record.getRoomTotalGold()));
            roomPkSummary.setUpdateTime(new Date());
        }
        if (roomPkSummary.getOnlineMaxPerson() == null) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        } else if (roomPkSummary.getOnlineMaxPerson() < record.getRoomTotalPerson().intValue()) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        }
        if (roomPkSummary.getHistoryMaxScore() == null) {
            roomPkSummary.setHistoryMaxScore(leftCombatPower);
        } else if (roomPkSummary.getHistoryMaxScore().compareTo(leftCombatPower) < 0) {
            roomPkSummary.setHistoryMaxScore(leftCombatPower);
        }
        if (isInsert) {
            roomPkSummaryMapper.insertSelective(roomPkSummary);
        } else {
            roomPkSummaryMapper.updateByPrimaryKeySelective(roomPkSummary);
        }

        isInsert = false;
        BigDecimal rightCombatPower = new BigDecimal(roomCombatPowerService.getRoomCombatPowerByRoomUid(record.getTargetRoomUid()));
        roomPkSummary = roomPkSummaryMapper.selectByPrimaryKey(record.getTargetRoomUid());
        if (roomPkSummary == null) {
            roomPkSummary = new RoomPkSummary();
            roomPkSummary.setRoomUid(record.getTargetRoomUid());
            roomPkSummary.setDrawTimes(1);
            roomPkSummary.setTotalGold(record.getTargetTotalGold());
            roomPkSummary.setCreateTime(new Date());
            isInsert = true;
        } else {
            if (roomPkSummary.getDrawTimes() == null) {
                roomPkSummary.setDrawTimes(0);
            }
            if (roomPkSummary.getTotalGold() == null) {
                roomPkSummary.setTotalGold(new BigDecimal(0));
            }
            roomPkSummary.setDrawTimes(roomPkSummary.getDrawTimes() + 1);
            roomPkSummary.setTotalGold(roomPkSummary.getTotalGold().add(record.getTargetTotalGold()));
            roomPkSummary.setUpdateTime(new Date());
        }
        if (roomPkSummary.getOnlineMaxPerson() == null) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        } else if (roomPkSummary.getOnlineMaxPerson() < record.getRoomTotalPerson().intValue()) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        }
        if (roomPkSummary.getHistoryMaxScore() == null) {
            roomPkSummary.setHistoryMaxScore(rightCombatPower);
        } else if (roomPkSummary.getHistoryMaxScore().compareTo(rightCombatPower) < 0) {
            roomPkSummary.setHistoryMaxScore(rightCombatPower);
        }
        if (isInsert) {
            roomPkSummaryMapper.insertSelective(roomPkSummary);
        } else {
            roomPkSummaryMapper.updateByPrimaryKeySelective(roomPkSummary);
        }

    }

    public void updateRoomPkSummaryDB(RoomPkRecord record) {
        if (record.getPkResult() == RoomConstant.RoomPkResult.NO_WIN) {
            updateRoomPkSummaryDBByNotWin(record);
            return;
        }
        boolean isInsert = false;
        BigDecimal winCombatPower = new BigDecimal(roomCombatPowerService.getRoomCombatPowerByRoomUid(record.getWinUid()));
        //下面是有输赢情况的处理
        RoomPkSummary roomPkSummary = roomPkSummaryMapper.selectByPrimaryKey(record.getWinUid());
        if (roomPkSummary == null) {
            roomPkSummary = new RoomPkSummary();
            roomPkSummary.setRoomUid(record.getWinUid());
            roomPkSummary.setWinTimes(1);
            roomPkSummary.setTotalGold(record.getRoomTotalGold());
            roomPkSummary.setCreateTime(new Date());
            isInsert = true;
        } else {
            if (roomPkSummary.getWinTimes() == null) {
                roomPkSummary.setWinTimes(0);
            }
            if (roomPkSummary.getTotalGold() == null) {
                roomPkSummary.setTotalGold(new BigDecimal(0));
            }
            roomPkSummary.setWinTimes(roomPkSummary.getWinTimes() + 1);
            roomPkSummary.setTotalGold(roomPkSummary.getTotalGold().add(record.getRoomTotalGold()));
            roomPkSummary.setUpdateTime(new Date());
        }
        if (roomPkSummary.getOnlineMaxPerson() == null) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        } else if (roomPkSummary.getOnlineMaxPerson() < record.getRoomTotalPerson().intValue()) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        }
        if (roomPkSummary.getHistoryMaxScore() == null) {
            roomPkSummary.setHistoryMaxScore(winCombatPower);
        } else if (roomPkSummary.getHistoryMaxScore().compareTo(winCombatPower) < 0) {
            roomPkSummary.setHistoryMaxScore(winCombatPower);
        }
        if (isInsert) {
            roomPkSummaryMapper.insertSelective(roomPkSummary);
        } else {
            roomPkSummaryMapper.updateByPrimaryKeySelective(roomPkSummary);
        }

        isInsert = false;
        BigDecimal loseCombatPower = new BigDecimal(roomCombatPowerService.getRoomCombatPowerByRoomUid(record.getLoseUid()));
        roomPkSummary = roomPkSummaryMapper.selectByPrimaryKey(record.getLoseUid());
        if (roomPkSummary == null) {
            roomPkSummary = new RoomPkSummary();
            roomPkSummary.setRoomUid(record.getLoseUid());
            roomPkSummary.setLoseTimes(1);
            roomPkSummary.setTotalGold(record.getTargetTotalGold());
            roomPkSummary.setCreateTime(new Date());
            isInsert = true;
        } else {
            if (roomPkSummary.getLoseTimes() == null) {
                roomPkSummary.setLoseTimes(0);
            }
            if (roomPkSummary.getTotalGold() == null) {
                roomPkSummary.setTotalGold(new BigDecimal(0));
            }
            roomPkSummary.setLoseTimes(roomPkSummary.getLoseTimes() + 1);
            roomPkSummary.setTotalGold(roomPkSummary.getTotalGold().add(record.getTargetTotalGold()));
            roomPkSummary.setUpdateTime(new Date());
        }
        if (roomPkSummary.getOnlineMaxPerson() == null) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        } else if (roomPkSummary.getOnlineMaxPerson() < record.getRoomTotalPerson().intValue()) {
            roomPkSummary.setOnlineMaxPerson(record.getRoomTotalPerson().intValue());
        }
        if (roomPkSummary.getHistoryMaxScore() == null) {
            roomPkSummary.setHistoryMaxScore(loseCombatPower);
        } else if (roomPkSummary.getHistoryMaxScore().compareTo(loseCombatPower) < 0) {
            roomPkSummary.setHistoryMaxScore(loseCombatPower);
        }
        if (isInsert) {
            roomPkSummaryMapper.insertSelective(roomPkSummary);
        } else {
            roomPkSummaryMapper.updateByPrimaryKeySelective(roomPkSummary);
        }
    }

    /**
     * 设置惩罚措施svga和惩罚措施静态图
     *
     * @param roomPkRecord
     * @return
     */
    public String setRoomPkPunishment(RoomPkRecord roomPkRecord) {
        int randomNum = punishmentRandom.nextInt(punishmentList.length);
        roomPkRecord.setPunishment(punishmentList[randomNum][0]);
        return punishmentList[randomNum][1];
    }

    /**
     * 设置房间PK中的赢方和输方
     */
    public void setRoomPkRecordResultWinAndLose(RoomPkRecord roomPkRecord) {
        int result = roomPkRecord.getRoomTotalGold().compareTo(roomPkRecord.getTargetTotalGold());
        if (result == 0) {
            roomPkRecord.setPkResult(RoomConstant.RoomPkResult.NO_WIN);
        }
        if (result > 0) {
            roomPkRecord.setPkResult(RoomConstant.RoomPkResult.ROOM_WIN);
            roomPkRecord.setWinUid(roomPkRecord.getRoomUid());
            roomPkRecord.setLoseUid(roomPkRecord.getTargetRoomUid());
        }
        if (result < 0) {
            roomPkRecord.setPkResult(RoomConstant.RoomPkResult.TARGET_WIN);
            roomPkRecord.setWinUid(roomPkRecord.getTargetRoomUid());
            roomPkRecord.setLoseUid(roomPkRecord.getRoomUid());
        }
        //如果是平局 就不用再往下执行 下面都是设置胜方信息的
        if (result == 0) {
            return;
        }
        Double roomComcat = roomCombatPowerService.getRoomCombatPowerByRoomUid(roomPkRecord.getWinUid());
        roomComcat = roomComcat == null ? 0d : roomComcat;
        roomPkRecord.setWinCombatPower(new BigDecimal(roomComcat));
        //送礼前四名
        List<SimpleUserVo> fourUserList = getPkFourUserVoListByGold(roomPkRecord.getId(), roomPkRecord.getWinUid().toString());
        if (!CollectionUtils.isEmpty(fourUserList)) {
            roomPkRecord.setWinMvpUid(fourUserList.get(0).getUid());
            AlloResp<SimpleUserInfo> userResp = dcUserInfoService.getSimpleUserInfoByUid(roomPkRecord.getWinMvpUid());
            if (userResp.isSuccess() && userResp.getData() != null) {
                roomPkRecord.setWinMvpAvatar(userResp.getData().getAvatar());
            }
        }
    }

    public RoomPkRecord getRoomPkRecordByRecordId(Long recordId) {
        RoomPkRecord roomPkRecord = roomPkCache.getRoomPkRecordByRecordId(recordId.toString());
        if (roomPkRecord == null) {
            roomPkRecord = roomPkRecordMapper.selectByPrimaryKey(recordId);
        }
        return roomPkRecord;
    }

    /**
     * 校验发起pk的条件 这个后台发起不需要校验
     */
    public void checkChooseRoomPk(Long currentUid, Long roomUid, Long targetRoomUid) throws Exception {
        if (currentUid == null || roomUid == null || targetRoomUid == null) {
            log.error("checkChooseRoomPk error,cunUid:{},roomUid:{},targetUid:{}", currentUid, roomUid, targetRoomUid);
            throw new DCException(BusiStatus.PARAMERROR);
        }
        int todayRejectNum = roomPkCache.getRoomPkTodayRejectNum(roomUid, targetRoomUid);
        if (todayRejectNum > 4) {
            log.error("todayRejectNum>4,roomUid:{},todayRejectNum:{}", roomUid, todayRejectNum);
            throw new DCException(BusiStatus.PK_TODAY_REJECT_EXCESSIVE);
        }
        AlloResp<Integer> roleResp = dcRoomMemberService.getRoomRole(roomUid, currentUid);
        if (!roleResp.isSuccess() || roleResp.getData() == null) {
            log.info("checkChooseRoomPk,resp null");
            throw new DCException(BusiStatus.NOAUTHORITY);
        }
        if (roleResp.getData() != Constant.RoomMemberRole.OWNER.intValue() && roleResp.getData() != Constant.RoomMemberRole.MANAGER.intValue()) {
            log.error("currentUser is normal user.currentUid:{},role:{}", currentUid, roleResp.getData());
            throw new DCException(BusiStatus.NOAUTHORITY);
        }
        roomCombatPowerService.checkChooseRoomPkConditionOne(roomUid);
    }

    public PkMessage buildPkMessage(RoomPkRecord record, Byte pkType) {
        PkMessage message = new PkMessage();
        message.setPkType(pkType);//消息的业务类型
        message.setPkRecordId(record.getId());
        message.setRoomUid(record.getRoomUid());
        message.setTargetRoomUid(record.getTargetRoomUid());
        return message;
    }

    public RoomPkRecord addRoomPkRecord(Long currentUid, Room room, Room targetRoom, Short inviteInvalidTime) {
        RoomPkRecord roomPkRecord = new RoomPkRecord();
        roomPkRecord.setRoomUid(room.getUid());
        roomPkRecord.setRoomTitle(room.getTitle());
        roomPkRecord.setRoomAvatar(room.getAvatar());
        roomPkRecord.setTargetRoomUid(targetRoom.getUid());
        roomPkRecord.setTargetRoomTitle(targetRoom.getTitle());
        roomPkRecord.setTargetRoomAvatar(targetRoom.getAvatar());
        roomPkRecord.setSponsorUid(currentUid);
        roomPkRecord.setPkStatus(RoomConstant.RoomPkStatus.PLEASE_PK);
        roomPkRecord.setCreateTime(new Date());
        roomPkRecord.setInviteInvalidTime(inviteInvalidTime);
        roomPkRecordMapper.insert(roomPkRecord);
        return roomPkRecord;
    }

    /**
     * 后台发起 添加Pk记录
     */
    public RoomPkRecord addRoomPkRecord(Long currentUid, Room room, Room targetRoom, Date now, Date endDate, Short inviteInvalidTime) {
        RoomPkRecord roomPkRecord = new RoomPkRecord();
        roomPkRecord.setRoomUid(room.getUid());
        roomPkRecord.setRoomTitle(room.getTitle());
        roomPkRecord.setRoomAvatar(room.getAvatar());
        roomPkRecord.setTargetRoomUid(targetRoom.getUid());
        roomPkRecord.setTargetRoomTitle(targetRoom.getTitle());
        roomPkRecord.setTargetRoomAvatar(targetRoom.getAvatar());
        roomPkRecord.setSponsorUid(currentUid);
        roomPkRecord.setCreateTime(now);
        roomPkRecord.setPkStartTime(now);
        roomPkRecord.setPkEndTime(endDate);
        roomPkRecord.setPkStatus(RoomConstant.RoomPkStatus.PKing);
        roomPkRecord.setInviteInvalidTime(inviteInvalidTime);
        roomPkRecordMapper.insert(roomPkRecord);
        return roomPkRecord;
    }

    /**
     * 校验pk双方当前的pk状态 这个无论后台还是用户自己发起都要校验
     */
    public void checkRoomPkRecordStatus(Room room, Room targetRoom, Long roomUid, Long targetRoomUid) {
        if (room == null) {
            log.error("checkRoomPkRecordStatus room is null,roomUid:{}", roomUid);
            throw new DCException(BusiStatus.ROOMNOTEXIST);
        }
        if (targetRoom == null) {
            log.error("checkRoomPkRecordStatus targetRoom is null,roomUid:{}", targetRoomUid);
            throw new DCException(BusiStatus.ROOMNOTEXIST);
        }
        RoomPkRecord roomPkRecord = getRoomCurrentPkRecord(roomUid);
        checkRoomPkRecordStatus(roomPkRecord);
        roomPkRecord = getRoomCurrentPkRecord(targetRoomUid);
        checkRoomPkRecordStatus(roomPkRecord);
    }

    public void checkRoomPkRecordStatus(RoomPkRecord roomPkRecord) {
        if (roomPkRecord == null) {
            return;
        }
        if (roomPkRecord.getPkStatus() == null) {
            return;
        }
        if (roomPkRecord.getPkStatus().byteValue() == RoomConstant.RoomPkStatus.PLEASE_PK) {
            this.checkRoomPleasePK(roomPkRecord);
            throw new DCException(BusiStatus.ROOM_PLEASE_PK);
        }
        if (roomPkRecord.getPkStatus().byteValue() == RoomConstant.RoomPkStatus.PKing) {
            throw new DCException(BusiStatus.ROOM_PKING);
        }
    }

    public void sendAdminChoosePkPushVo(RoomPkRecord roomPkRecord, Short inviteInvalidTime) {
        try {
            Double roomCombat = roomCombatPowerService.getRoomCombatPowerByRoomUid(roomPkRecord.getRoomUid());
            Double targetRoomCombat = roomCombatPowerService.getRoomCombatPowerByRoomUid(roomPkRecord.getTargetRoomUid());
            PbPush.PbAdminChoosePkPush.Builder builder = PbPush.PbAdminChoosePkPush.newBuilder();
            builder.setRoomUid(roomPkRecord.getRoomUid());
            builder.setRoomTitle(roomPkRecord.getRoomTitle());
            builder.setRoomAvatar(roomPkRecord.getRoomAvatar());
            builder.setPkTime(inviteInvalidTime * 1000L);
            builder.setRecordId(roomPkRecord.getId());
            builder.setCombatPower(roomCombat);
            sendAdminChoosePkNotice(roomPkRecord.getTargetRoomUid(), builder.build());

            builder.clear();

            builder = PbPush.PbAdminChoosePkPush.newBuilder();
            builder.setRoomUid(roomPkRecord.getTargetRoomUid());
            builder.setRoomTitle(roomPkRecord.getTargetRoomTitle());
            builder.setRoomAvatar(roomPkRecord.getTargetRoomAvatar());
            builder.setPkTime(inviteInvalidTime * 1000L);
            builder.setRecordId(roomPkRecord.getId());
            builder.setCombatPower(targetRoomCombat);
            sendAdminChoosePkNotice(roomPkRecord.getRoomUid(), builder.build());
        } catch (Exception e) {
            log.error("sendAdminChoosePkPushVo err,roomUid:{}", roomPkRecord.getRoomUid());
        }
    }

    /**
     * 发送后台发起Pk的消息
     */
    private void sendAdminChoosePkNotice(Long roomUid, PbPush.PbAdminChoosePkPush push) throws Exception {
        PbCommon.PbBizPush.Builder pbBizPush = PbCommon.PbBizPush.newBuilder();
        pbBizPush.setOs(0);
        pbBizPush.setTimestamp(System.currentTimeMillis());
        pbBizPush.setRoomUid(roomUid);
        pbBizPush.setModule(PbCommon.Module.pushModule.getNumber());
        pbBizPush.setMethod("PbAdminChoosePkPush");
        pbBizPush.setPushEvent(PbCommon.PushEvent.pushAdminChoosePk);
        pbBizPush.setData(push.toByteString());
        pbBizPush.setPushType(PbCommon.PushType.pTypeBroadcast);
        sendMsgService.sendSendChatRoomMsg(roomUid, configConstant.secretaryUid, Base64.encodeBase64String(pbBizPush.build().toByteArray()), RongCloudConstant.RongCloundMessageObjectName.PB_PUSH);
    }

    /**
     * Pk结束 删除本次Pk相关缓存
     */
    public void pkEndDelPkCache(RoomPkRecord roomPkRecord) {
        String roomUidStr = roomPkRecord.getRoomUid().toString();
        String targetRoomUidStr = roomPkRecord.getTargetRoomUid().toString();
        String recordIStr = roomPkRecord.getId().toString();

        roomPkCache.pkCancelDelPkCache(roomUidStr, targetRoomUidStr, recordIStr);
        roomPkCache.pkEndDelPkCache(roomUidStr, targetRoomUidStr, recordIStr);
    }

    /**
     * 保存pk记录进缓存<p></p>
     * 有两级缓存<p></p>
     * 在查记录时 先根据roomUid找对应的pk记录id 再根据pk记录id找对应的pk记录<p></p>
     * 第一级 roomUid->pkRecordId<p></p>
     * 第二级 pkRecordId->pkRecord<p></p>
     */
    public void saveRoomPkRecordToCache(Long roomUid, RoomPkRecord record) {
        if (record == null) {
            return;
        }
        roomPkCache.saveRoomPkRecordToCache(roomUid, record);
    }

    /**
     * 发送发起pk消息
     * 发给对方房间所有管理员和房主
     */
    @Async
    @Override
    public void sendChooseRoomPkMsg(Long targetRoomUid, RoomPkRecord roomPkRecord) throws Exception {
        AlloResp<Room> roomResp = dcRoomInfoService.getByUid(roomPkRecord.getRoomUid());
        if (!roomResp.isSuccess() || roomResp.getData() == null) {
            return;
        }
        Room room = roomResp.getData();
        PbPush.PbRecivePkInvitePush invitePush = getPbRecivePkInvitePush(roomPkRecord, room);

        PbCommon.PbBizPush pbBizPush = getPbBizPush(targetRoomUid, "PbRecivePkInvitePush", invitePush.toByteString(), PbCommon.PushEvent.pushAskPk, PbCommon.PushType.pTypeUnicast);
        AlloResp<List<RoomMember>> roomMemberListResp = dcRoomMemberService.getRoomManagerList(targetRoomUid);
        List<Long> managerUidList = new ArrayList<Long>();
        managerUidList.add(targetRoomUid);
        if (roomMemberListResp.isSuccess() && !CollectionUtils.isEmpty(roomMemberListResp.getData())) {
            managerUidList.addAll(roomMemberListResp.getData().stream().map(member -> member.getMemberUid()).collect(Collectors.toList()));
        }
        AlloResp<List<Long>> managerUidListResp = dcRoomMemberService.getOnCurrentRoomManager(targetRoomUid, managerUidList);
        ;
        if (!managerUidListResp.isSuccess() || CollectionUtils.isEmpty(managerUidListResp.getData())) {
            log.error("target room not manager on room.targetRoomUid:{}", targetRoomUid);
            return;
        }
        managerUidList = managerUidListResp.getData();
        sendPkPushPrivateMsg(managerUidList, pbBizPush);
    }

    @Override
    @Transactional
    public void cancelRoomPk(RoomPkRecord record) {
        try {
            log.info("cancelExpireRoomPkInvite.cancelRoomPk,recordId:{}", record.getId());
            cancelRoomPkUpdateData(record);
        } catch (Exception e) {
            log.error("cancelExpireRoomPkInvite.cancelRoomPk,recordId:{}", record.getId(), e);
        }
    }

    public void sendPkPushPrivateMsg(List<Long> targetUidList, PbCommon.PbBizPush pbBizPush) {
        List<String> receiverUids = targetUidList.stream().map(uid -> uid.toString()).collect(Collectors.toList());
        String objectName = RongCloudConstant.RongCloundMessageObjectName.PB_PUSH;
        AlloResp<Integer> response = null;
        try {
            response = this.sendMsgService.sendPrivateStatusMessage(configConstant.secretaryUid, receiverUids, objectName, Base64.encodeBase64String(pbBizPush.toByteArray()));
        } catch (Exception e) {
            if (response == null) {
                log.error("sendPkPushPrivateMsg error,resp is null");
                return;
            }
            log.error("sendPkPushPrivateMsg error，resp.code={},resp.msg:{}", response.getCode(), response.getMessage(), e);
        }
    }

    public void cancelRoomPkUpdateData(RoomPkRecord roomPkRecord) {
        String firstLockVal = null;
        String twoLockVal = null;
        try {
            firstLockVal = addRoomPkLock(roomPkRecord.getRoomUid(), false);
            try {
                twoLockVal = addRoomPkLock(roomPkRecord.getTargetRoomUid(), false);
                roomPkRecord = getRoomCurrentPkRecord(roomPkRecord.getRoomUid());
                checkCancelRoomPkResult(roomPkRecord);

                roomPkRecord.setPkStatus(RoomConstant.RoomPkStatus.PK_CANCEL);
                //更新DB
                updateRoomPkRecordDB(roomPkRecord);
                //删除本次Pk的相关缓存
                roomPkCache.pkCancelDelPkCache(roomPkRecord.getRoomUid().toString(), roomPkRecord.getTargetRoomUid().toString(), roomPkRecord.getId().toString());
            } finally {
                delRoomPkLock(roomPkRecord.getTargetRoomUid(), twoLockVal);
            }
        } finally {
            delRoomPkLock(roomPkRecord.getRoomUid(), firstLockVal);
        }
    }

    public void checkCancelRoomPkResult(RoomPkRecord roomPkRecord) {
        //没有发起Pk的房间的Pk记录或PK的状态不是请求Pk中 不做处理
        if (roomPkRecord == null || roomPkRecord.getPkStatus().byteValue() != RoomConstant.RoomPkStatus.PLEASE_PK) {
            throw new DCException(BusiStatus.PK_CANCEL_FAIL);
        }
    }

    public void updateRoomPkRecordDB(RoomPkRecord record) {
        roomPkRecordMapper.updateByPrimaryKeySelective(record);
    }

    public PbCommon.PbBizPush getPbBizPush(Long roomUid, String method, ByteString bodyByteString, PbCommon.PushEvent pushEvent, PbCommon.PushType pushType) {
        PbCommon.PbBizPush.Builder pbBizPush = PbCommon.PbBizPush.newBuilder();
        pbBizPush.setOs(0);
        pbBizPush.setTimestamp(System.currentTimeMillis());
        pbBizPush.setModule(PbCommon.Module.pushModule.getNumber());
        if (roomUid != null) {
            pbBizPush.setRoomUid(roomUid);
        }
        pbBizPush.setMethod(method);
        pbBizPush.setData(bodyByteString);
        pbBizPush.setPushEvent(pushEvent);
        pbBizPush.setPushType(pushType);
        return pbBizPush.build();
    }

    /**
     * 获取邀请PkPush
     *
     * @return
     */
    public PbPush.PbRecivePkInvitePush getPbRecivePkInvitePush(RoomPkRecord roomPkRecord, Room room) {
        AlloResp<String> roomPkTimeResp = dcSysConfService.getSysConfValueById(CommonConstant.SysConfId.room_pk_time);
        Long roomPkTime = 0L;
        if (roomPkTimeResp.isSuccess() && !StringUtils.isEmpty(roomPkTimeResp.getData())) {
            roomPkTime = Long.parseLong(roomPkTimeResp.getData()) * 60 * 1000;
        }
        Double roomCombatPower = roomCombatPowerService.getRoomCombatPowerByRoomUid(room.getUid());
        roomCombatPower = roomCombatPower == null ? 0d : roomCombatPower;

        PbPush.PbRecivePkInvitePush.Builder builder = PbPush.PbRecivePkInvitePush.newBuilder();
        builder.setInviteRoomUid(room.getUid());
        builder.setInviteRoomTitle(room.getTitle());
        builder.setInviteRoomAvatar(roomPkRecord.getRoomAvatar());
        builder.setInviteCombatPower(roomCombatPower);
        builder.setPkTime(roomPkTime);
        builder.setRecordId(roomPkRecord.getId());
        builder.setInvitePkValidTime(roomPkRecord.getInviteInvalidTime() * 1000L);
        return builder.build();
    }

    /**
     * 判断发起Pk发生的异常是不是在邀请PK的状态<p></p>
     * 如果是的话,检查该记录是不是过了有效期还未取消
     */
    public void checkRoomPleasePK(RoomPkRecord record) {
        if (record.getCreateTime() == null || record.getInviteInvalidTime() == null || (record.getInviteInvalidTime() * 1000 + record.getCreateTime().getTime()) > System.currentTimeMillis()) {
            return;
        }
        RoomPkEventVo eventVo = new RoomPkEventVo();
        eventVo.setPkRecord(record);
        eventVo.setType(RoomConstant.RoomPkEventType.CANCEL_PK);
        SpringContextUtil.getApplicationContext().publishEvent(new RoomPkEvent(eventVo));
    }

    /**
     * 加锁并返回锁
     *
     * @return
     */
    public String addRoomPkLock(Long roomUid, boolean isWait) {
        boolean isSuccess = false;
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_room_lock.name(), roomUid);
        String lockVal = String.valueOf(System.nanoTime());
        if (isWait) {
            isSuccess = roomPkCache.addRoomPkLock(key, lockVal);
        } else {
            isSuccess = roomPkCache.addRoomPkLockByDontWait(key, lockVal);
        }
        if (!isSuccess) {
            throw new DCException(BusiStatus.SERVERBUSY);
        }
        return lockVal;
    }

    public void delRoomPkLock(Long roomUid, String lockVal) {
        if (StringUtils.isEmpty(lockVal)) {
            log.error("chooseRoomPk.uid:{} lockVal is null", roomUid);
            return;
        }
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_pk_room_lock.name(), roomUid);
        roomPkCache.delRoomPkLock(key, lockVal);
    }

    /**
     * 线程不安全 不保证并发性
     *
     * @param roomUid
     * @return
     */
    public RoomPkRecord getRoomCurrentPkRecord(Long roomUid) {
        RoomPkRecord roomPkRecord = roomPkCache.getRoomCurrentPkRecordFromCache(roomUid);
        if (roomPkRecord == null) {
            roomPkRecord = roomPkMapper.getCurrentPkRecord(roomUid);
            //这里不更新数据到Cache,因为容易造成room_pk_record有该房间多条记录存在
        }
        return roomPkRecord;
    }

    /**
     * 没有Pk记录 初始化PkInfoVo
     *
     * @param roomUid
     * @return
     */
    public PkInfoVo buildInitPkInfoVo(Long roomUid) {
        AlloResp<Room> roomAlloResp = dcRoomInfoService.getByUid(roomUid);
        if (roomAlloResp.getCode() != BusiStatus.SUCCESS.value()) {
            log.error("buildInitPkInfoVo.room is null.roomUid:{},code:{}", roomUid, roomAlloResp.getCode());
            return null;
        }
        PkInfoVo pkInfoVo = new PkInfoVo();
        pkInfoVo.setLeftRoomUid(roomUid);
        if (roomAlloResp.getData() != null) {
            pkInfoVo.setLeftRoomTitle(roomAlloResp.getData().getTitle());
            pkInfoVo.setLeftRoomAvatar(roomAlloResp.getData().getAvatar());
            pkInfoVo.setPkStatus(RoomConstant.RoomPkStatus.NOT_PK);
            pkInfoVo.setLeftRoomCombatPower(roomCombatPowerService.getRoomCombatPowerByRoomUid(roomUid));
        }
        return pkInfoVo;
    }

    /**
     * 发送本次详情
     * 组装数据
     * 这里的left代表本方房间 right代表对方房间
     */
    @Async
    @Override
    public PkInfoVo getPkInfoVo(Long roomUid, RoomPkRecord roomPkRecord, boolean isSendMsg) throws Exception {
        if (roomPkRecord == null) {
            return null;
        }
        boolean isLeft = roomPkRecord.getRoomUid().longValue() == roomUid ? true : false;
        Long leftRoomUid = isLeft ? roomPkRecord.getRoomUid() : roomPkRecord.getTargetRoomUid();
        Long rightRoomUid = isLeft ? roomPkRecord.getTargetRoomUid() : roomPkRecord.getRoomUid();
        String leftRoomTitle = isLeft ? roomPkRecord.getRoomTitle() : roomPkRecord.getTargetRoomTitle();
        String rightRoomTitle = isLeft ? roomPkRecord.getTargetRoomTitle() : roomPkRecord.getRoomTitle();
        String leftRoomAvatar = isLeft ? roomPkRecord.getRoomAvatar() : roomPkRecord.getTargetRoomAvatar();
        String rightRoomAvatar = isLeft ? roomPkRecord.getTargetRoomAvatar() : roomPkRecord.getRoomAvatar();

        Double leftGold = null;
        Double rightGold = null;

        if (leftGold == null) {
            leftGold = getPkingRoomGold(leftRoomUid);
        }

        if (rightGold == null) {
            rightGold = getPkingRoomGold(rightRoomUid);
        }

        PkInfoVo leftPkInfo = new PkInfoVo();
        leftPkInfo.setRecordId(roomPkRecord.getId());
        leftPkInfo.setLeftRoomUid(leftRoomUid);
        leftPkInfo.setRightRoomUid(rightRoomUid);
        leftPkInfo.setLeftRoomTitle(leftRoomTitle);
        leftPkInfo.setRightRoomTitle(rightRoomTitle);
        leftPkInfo.setLeftRoomAvatar(leftRoomAvatar);
        leftPkInfo.setRightRoomAvatar(rightRoomAvatar);
        leftPkInfo.setPkStartTime(roomPkRecord.getPkStartTime());
        leftPkInfo.setPkEndTime(roomPkRecord.getPkEndTime());
        leftPkInfo.setLeftTotalGold(leftGold);
        leftPkInfo.setRightTotalGold(rightGold);
        leftPkInfo.setPkStatus(roomPkRecord.getPkStatus());
        leftPkInfo.setLeftRoomCombatPower(roomCombatPowerService.getRoomCombatPowerByRoomUid(roomUid));
        setPkArms(leftPkInfo);

        List<SimpleUserVo> leftVoList = getPkFourUserVoListByGold(roomPkRecord.getId(), leftRoomUid.toString());
        List<SimpleUserVo> rightVoList = getPkFourUserVoListByGold(roomPkRecord.getId(), rightRoomUid.toString());
        //设置用户信息
        setUserInfo(leftVoList, rightVoList);

        leftPkInfo.setLeftUserVoList(leftVoList);
        leftPkInfo.setRightUserVoList(rightVoList);

        if (isSendMsg) {
            Class<PbHttpResp.PbPkInfoResp> respClazz = PbHttpResp.PbPkInfoResp.class;
            Method respBuliderMethod = respClazz.getMethod("newBuilder");
            Message.Builder builder = (Message.Builder) respBuliderMethod.invoke(null, null);

            //发送左房数据到左房
            sendPkInfoRoomNotice(leftRoomUid, leftPkInfo, builder);
            //发送右房数据到右房
            sendPkInfoRoomNotice(rightRoomUid, getRightPkInfoVo(leftPkInfo), builder);
        }
        return leftPkInfo;
    }

    public Double getPkingRoomGold(Long roomUid) {
        return roomPkCache.getPkingRoomGold(roomUid);
    }

    public void setPkArms(PkInfoVo leftPkInfo) {
        int randomNum = punishmentRandom.nextInt(pkingArmsList.length);
        leftPkInfo.setLeftPkArms(pkingArmsList[randomNum][0]);
        leftPkInfo.setRightPkArms(pkingArmsList[randomNum][1]);
    }

    public List<SimpleUserVo> getPkFourUserVoListByGold(Long recordId, String roomUid) {
        return roomPkCache.getPkFourUsersByGold(recordId, roomUid);
    }

    public void setUserInfo(List<SimpleUserVo> leftVoList, List<SimpleUserVo> rightVoList) {
        List<Long> uidList = new ArrayList<Long>();
        if (!CollectionUtils.isEmpty(leftVoList)) {
            uidList.addAll(leftVoList.stream().map(vo -> vo.getUid()).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(rightVoList)) {
            uidList.addAll(rightVoList.stream().map(vo -> vo.getUid()).collect(Collectors.toList()));
        }
        if (CollectionUtils.isEmpty(uidList)) {
            return;
        }
        AlloResp<List<UserInfo>> alloResp = dcUserInfoService.getUserList(uidList);
        Map<Long, UserInfo> userMap = new HashMap<Long, UserInfo>();
        if (alloResp.isSuccess() && alloResp.getData() != null) {
            userMap = alloResp.getData().stream().collect(Collectors.toMap(UserInfo::getUid, a -> a, (k1, k2) -> k1));
        }
        UserInfo user = null;
        if (!CollectionUtils.isEmpty(leftVoList)) {
            for (SimpleUserVo vo : leftVoList) {
                user = userMap.get(vo.getUid());
                if (user == null) {
                    continue;
                }
                vo.setAvatar(user.getAvatar());
                vo.setErbanNo(user.getErbanNo());
                vo.setUid(user.getUid());
                vo.setNick(user.getNick());
                vo.setGender(user.getGender());
            }
        }
        if (!CollectionUtils.isEmpty(rightVoList)) {
            for (SimpleUserVo vo : rightVoList) {
                user = userMap.get(vo.getUid());
                if (user == null) {
                    continue;
                }
                vo.setAvatar(user.getAvatar());
                vo.setErbanNo(user.getErbanNo());
                vo.setUid(user.getUid());
                vo.setNick(user.getNick());
                vo.setGender(user.getGender());
            }
        }
    }

    private void sendPkInfoRoomNotice(Long roomUid, PkInfoVo pkInfoVo, Message.Builder builder) throws Exception {
        String returnJson = JSONObject.toJSONString(pkInfoVo);
        builder.clear();
        GeneratedMessageV3 respObj = PbUtils.json2pb(returnJson, builder);

        PbCommon.PbBizPush.Builder pbBizPush = PbCommon.PbBizPush.newBuilder();
        pbBizPush.setOs(0);
        pbBizPush.setTimestamp(System.currentTimeMillis());
        pbBizPush.setRoomUid(roomUid);
        pbBizPush.setModule(PbCommon.Module.httpModule.getNumber());
        pbBizPush.setMethod("PbPkInfoResp");
        pbBizPush.setPushEvent(PbCommon.PushEvent.pushPkInfo);
        pbBizPush.setData(respObj.toByteString());
        pbBizPush.setPushType(PbCommon.PushType.pTypeBroadcast);
        sendMsgService.sendSendChatRoomMsg(roomUid, configConstant.secretaryUid, Base64.encodeBase64String(pbBizPush.build().toByteArray()), RongCloudConstant.RongCloundMessageObjectName.PB_PUSH);
    }

    /**
     * 把Info中的left跟right数据互换位置 left的数据放到右 右的数据放到左
     */
    public PkInfoVo getRightPkInfoVo(PkInfoVo pkInfoVo) {
        PkInfoVo rightPkInfo = new PkInfoVo();
        rightPkInfo.setLeftRoomUid(pkInfoVo.getRightRoomUid());
        rightPkInfo.setRightRoomUid(pkInfoVo.getLeftRoomUid());
        rightPkInfo.setLeftRoomTitle(pkInfoVo.getRightRoomTitle());
        rightPkInfo.setRightRoomTitle(pkInfoVo.getLeftRoomTitle());
        rightPkInfo.setLeftRoomAvatar(pkInfoVo.getRightRoomAvatar());
        rightPkInfo.setRightRoomAvatar(pkInfoVo.getLeftRoomAvatar());
        rightPkInfo.setLeftTotalPerson(pkInfoVo.getRightTotalPerson());
        rightPkInfo.setRightTotalPerson(pkInfoVo.getLeftTotalPerson());
        rightPkInfo.setLeftPkArms(pkInfoVo.getLeftPkArms());
        rightPkInfo.setRightPkArms(pkInfoVo.getRightPkArms());

        rightPkInfo.setPkStartTime(pkInfoVo.getPkStartTime());
        rightPkInfo.setPkEndTime(pkInfoVo.getPkEndTime());
        rightPkInfo.setLeftTotalGold(pkInfoVo.getRightTotalGold());
        rightPkInfo.setRightTotalGold(pkInfoVo.getLeftTotalGold());
        rightPkInfo.setLeftUserVoList(pkInfoVo.getRightUserVoList());
        rightPkInfo.setRightUserVoList(pkInfoVo.getLeftUserVoList());
        return rightPkInfo;
    }

    public List<PbPkingRoomVo> getPkingRoomPageDataByNotZone(Long uid, int page, int pageSize) {
        List<PbPkingRoomVo> pbPkingRoomVoList = roomPkCache.getPkingRoomListVoCache(uid);
        if (CollectionUtils.isEmpty(pbPkingRoomVoList)) {
            return null;
        }
        page = page == 0 ? 2 : page;
        pageSize = pageSize == 0 ? 50 : pageSize;
        int startIndex = (page - 1) * pageSize;

        if (pbPkingRoomVoList.size() < startIndex) {
            return null;
        }
        int endIndex = page * pageSize;
        endIndex = endIndex > pbPkingRoomVoList.size() ? pbPkingRoomVoList.size() : endIndex;
        return pbPkingRoomVoList.subList(startIndex, endIndex);
    }

    public List<PbPkingRoomVo> getPbPkingRoomVoList(Long uid, List<RoomPkRecord> roomPkRecords) {
        List<Long> roomUidList = roomPkRecords.stream().map(record -> record.getRoomUid()).collect(Collectors.toList());
        roomUidList.addAll(roomPkRecords.stream().map(record -> record.getTargetRoomUid()).collect(Collectors.toList()));
        roomUidList = roomUidList.stream().distinct().collect(Collectors.toList());

        Map<Long, Double> roomGoldMap = roomPkCache.getRoomUidGoldMap();
        Map<Long, Double> roomUidCombatMap = roomPkCache.getRoomUidMapByCombat();

        AlloResp<List<Room>> roomListResp = dcRoomInfoService.queryRoomBeanListByUids(roomUidList);
        List<Room> roomList = roomListResp.isSuccess() ? roomListResp.getData() : Collections.EMPTY_LIST;
        if (CollectionUtils.isEmpty(roomList)) {
            roomList = Collections.EMPTY_LIST;
        }
        Map<Long, Room> roomMap = roomList.stream().collect(Collectors.toMap(Room::getUid, vo -> vo, (k1, k2) -> k1));

        AlloResp<Map<Long, List<Long>>> memberMapResp = dcRoomMemberService.getRoomManagerListBatch(roomUidList);
        Map<Long, List<Long>> memberMap = memberMapResp.isSuccess() ? memberMapResp.getData() : null;
        if (memberMap == null) {
            memberMap = new HashMap<>();
        }
        PbPkingRoomVo pbPkingRoomVo = null;
        List<Long> memberList = null;
        boolean isAdmin = false;
        List<PbPkingRoomVo> pbPkingRoomVos = new ArrayList();
        //pk房房主是自己
        PbPkingRoomVo roomOwnerPk = null;
        //pk房管理员是自己
        List<PbPkingRoomVo> managePk = new ArrayList<PbPkingRoomVo>();

        for (RoomPkRecord roomPkRecord : roomPkRecords) {
            pbPkingRoomVo = buildPbPkingRoomVo(roomPkRecord, roomGoldMap, roomUidCombatMap, roomMap);
            //如果pk房房主是自己则放首位
            if (pbPkingRoomVo.getLeftRoomUid().equals(uid) || pbPkingRoomVo.getRightRoomUid().equals(uid)) {
                roomOwnerPk = pbPkingRoomVo;
                continue;
            }
            memberList = memberMap.get(roomPkRecord.getRoomUid());
            if (memberList != null && memberList.contains(uid)) {
                managePk.add(pbPkingRoomVo);
                isAdmin = true;
            }
            if (isAdmin) {
                continue;
            }
            memberList = memberMap.get(roomPkRecord.getTargetRoomUid());
            if (memberList != null && memberList.contains(uid)) {
                managePk.add(pbPkingRoomVo);
                isAdmin = true;
            }
            if (isAdmin) {
                continue;
            }
            pbPkingRoomVos.add(pbPkingRoomVo);
        }
        if (roomOwnerPk != null) {
            pbPkingRoomVos.add(0, roomOwnerPk);
        }
        if (!CollectionUtils.isEmpty(managePk)) {
            int index = 1;
            if (roomOwnerPk == null) {
                index = 0;
            }
            pbPkingRoomVos.addAll(index, managePk);
        }
        return pbPkingRoomVos;
    }

    public PbPkingRoomVo buildPbPkingRoomVo(RoomPkRecord record, Map<Long, Double> roomGoldMap, Map<Long, Double> roomUidCombatMap, Map<Long, Room> roomMap) {
        PbPkingRoomVo pbPkingRoomVo = new PbPkingRoomVo();
        pbPkingRoomVo.setLeftRoomUid(record.getRoomUid());
        if (roomMap.get(record.getRoomUid()) != null) {
            pbPkingRoomVo.setLeftRoomAvatar(roomMap.get(record.getRoomUid()).getAvatar());
            pbPkingRoomVo.setLeftPersonNum(roomMap.get(record.getRoomUid()).getOnlineNum());
        }
        pbPkingRoomVo.setLeftRoomNick(record.getRoomTitle());
        if (roomGoldMap.get(record.getRoomUid()) != null) {
            pbPkingRoomVo.setLeftTotalGold(roomGoldMap.get(record.getRoomUid()));
        }
        pbPkingRoomVo.setLeftComcatPower(0d);
        if (roomUidCombatMap.get(record.getRoomUid()) != null) {
            pbPkingRoomVo.setLeftComcatPower(roomUidCombatMap.get(record.getRoomUid()));
        }
        pbPkingRoomVo.setRightRoomUid(record.getTargetRoomUid());
        if (roomMap.get(record.getTargetRoomUid()) != null) {
            pbPkingRoomVo.setRightRoomAvatar(roomMap.get(record.getTargetRoomUid()).getAvatar());
            pbPkingRoomVo.setRightPersonNum(roomMap.get(record.getTargetRoomUid()).getOnlineNum());
        }
        pbPkingRoomVo.setRightRoomNick(record.getTargetRoomTitle());
        if (roomGoldMap.get(record.getTargetRoomUid()) != null) {
            pbPkingRoomVo.setRightTotalGold(roomGoldMap.get(record.getTargetRoomUid()));
        }
        pbPkingRoomVo.setRightComcatPower(0d);
        if (roomUidCombatMap.get(record.getTargetRoomUid()) != null) {
            pbPkingRoomVo.setRightComcatPower(roomUidCombatMap.get(record.getTargetRoomUid()));
        }
        return pbPkingRoomVo;
    }

    @Override
    public void handleGiftMessage(GiftMessage giftMessage) throws Exception {
        if (giftMessage.getSendUid() == null || giftMessage.getRoomUid() == null) {
            return;
        }
        RoomPkRecord roomPkRecord = getRoomCurrentPkRecord(giftMessage.getRoomUid());
        if (roomPkRecord == null) {
            log.warn("pk handleGiftMessage record is null,roomUid:{}", giftMessage.getRoomUid());
            return;
        }
        if (roomPkRecord.getPkStatus().byteValue() != RoomConstant.RoomPkStatus.PKing) {
            log.warn("pk handleGiftMessage status != pking,roomUid:{},status:{}", giftMessage.getRoomUid(), roomPkRecord.getPkStatus());
            return;
        }
        //pk已经结束
        if (roomPkRecord.getPkEndTime().getTime() < System.currentTimeMillis()) {
            log.warn("pk handleGiftMessage pkEndTime>now,roomUid:{},status:{}", giftMessage.getRoomUid(), roomPkRecord.getPkEndTime().getTime());
            return;
        }
        String roomUid = giftMessage.getRoomUid().toString();
        Double goldNum = giftMessage.getGoldNum().doubleValue();
        //增加本次pk本房间的用户送礼流水
        roomPkCache.incryPkingRoomUserGold(roomPkRecord.getId(), giftMessage.getSendUid(), roomUid, goldNum);
        //增加本次pk本房间的用户送礼总流水
        roomPkCache.incryPkingRoomGold(roomUid, goldNum);
        //下发pk详情给客户端
        getPkInfoVo(giftMessage.getRoomUid(), roomPkRecord, true);
    }

    /**
     * 加pk活动热度锁
     *
     * @return
     */
    public String addPkActivityHeatLock(Long roomUid) {
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_activity_heat_lock.name(), roomUid);
        String lockVal = String.valueOf(System.nanoTime());
        boolean isSuccess = roomPkCache.addRoomPkLock(key, lockVal);
        if (!isSuccess) {
            throw new DCException(BusiStatus.SERVERBUSY);
        }
        return lockVal;
    }

    public void delPkActivityHeatLock(Long roomUid, String lockVal) {
        if (StringUtils.isEmpty(lockVal)) {
            log.error("delPkActivityHeatLock.uid:{} lockVal is null", roomUid);
            return;
        }
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_activity_heat_lock.name(), roomUid);
        roomPkCache.delRoomPkLock(key, lockVal);
    }

    public boolean checkRoomPkActivityDate() {
        try {
            String dateStr = null;
            AlloResp<String> pkActvityDateResp = dcSysConfService.getSysConfValueById(CommonConstant.SysConfId.room_pk_activity_date);
            if (pkActvityDateResp.isSuccess() && !StringUtils.isEmpty(pkActvityDateResp.getData())) {
                dateStr = pkActvityDateResp.getData();
            }
            if (StringUtils.isEmpty(dateStr)) {
                log.info("addRoomHeatValue.dateStr is null");
                return false;
            }
            log.info("addRoomHeatValue.dateStr:{}", dateStr);
            Date startTime = DateTimeUtil.convertStrToDate(dateStr.split("#")[0]);
            Date endTime = DateTimeUtil.convertStrToDate(dateStr.split("#")[1]);
            Long now = System.currentTimeMillis();
            if (now < startTime.getTime() || now > endTime.getTime()) {
                return false;
            }
        } catch (Exception e) {
            log.error("checkRoomPkActivityDate error", e);
            return false;
        }
        return true;
    }

    public void addRoomHeatValue(RoomPkRecord roomPkRecord, byte eventType) {
        if (roomPkRecord == null) {
            log.error("addRoomHeatValue roomPkRecord null");
        }
        if (!checkRoomPkActivityDate()) {
            log.error("checkRoomPkActivityDate false");
            return;
        }
        try {
            switch (eventType) {
                case RoomConstant.RoomPkHeatValueEventType.SUC_OPEN:
                    String keyName = RedisKeyUtil.RedisKey.suc_pk_today_num.name();
                    //开启PK,双方各加5点热度值
                    addHeatValueByPk(roomPkRecord.getRoomUid(), 5, keyName);
                    addHeatValueByPk(roomPkRecord.getTargetRoomUid(), 5, keyName);
                    break;
                case RoomConstant.RoomPkHeatValueEventType.PK_WIN:
                    addHeatValueByPk(roomPkRecord.getWinUid(), 20, RedisKeyUtil.RedisKey.pk_win_today_num.name());
                    break;
                case RoomConstant.RoomPkHeatValueEventType.PK_GIFT_TOTAL:
                    addHeatValueByGift(roomPkRecord.getRoomUid(), roomPkRecord.getRoomTotalGold());
                    addHeatValueByGift(roomPkRecord.getTargetRoomUid(), roomPkRecord.getTargetTotalGold());
                    break;
            }
        } catch (Exception e) {
            log.error("addRoomHeatValue.uid:{},target:{},eventType:{}", roomPkRecord.getRoomUid(), roomPkRecord.getTargetRoomUid(), eventType, e);
        }
    }

    /**
     * 成功开启PK和获得PK胜利加热度值
     */
    public void addHeatValueByPk(Long roomUid, Integer heatValue, String keyValue) {
        String lockVal = addPkActivityHeatLock(roomUid);
        try {
            int todayNum = roomPkCache.getTodayHeatAddNum(roomUid, keyValue);
            log.info("addHeatValueByPk.key:{},num:{}", keyValue, todayNum);
            if (todayNum >= 5) {
                return;
            }
            RoomPkActivityUpLevelVo roomPkActivityUpLevelVo = roomPkCache.addHeatValueByPk(roomUid, heatValue.longValue(), keyValue);
            updateHeatUpLevel(roomPkActivityUpLevelVo);
        } finally {
            delPkActivityHeatLock(roomUid, lockVal);
        }
    }


    public void addHeatValueByGift(Long roomUid, BigDecimal giftTotal) {
        long heatValue = getHeatValueByGift(giftTotal);

        if (heatValue <= 0) {
            log.info("addHeatValueByGift.roomUid:{},gift:{},heat<0:{}", roomUid, giftTotal, heatValue);
            return;
        }
        log.info("addHeatValueByGift.roomUid:{},gift:{},heat:{}", roomUid, giftTotal, heatValue);
        RoomPkActivityUpLevelVo roomPkActivityUpLevelVo = null;
        String lockVal = addPkActivityHeatLock(roomUid);
        try {
            roomPkActivityUpLevelVo = roomPkCache.addHeatValue(roomUid, heatValue);
        } finally {
            delPkActivityHeatLock(roomUid, lockVal);
        }
        if (roomPkActivityUpLevelVo == null) {
            return;
        }
        updateHeatUpLevel(roomPkActivityUpLevelVo);
    }

    public long getHeatValueByGift(BigDecimal giftTotal) {
        int giftTotalInt = giftTotal.intValue();
        if (giftTotalInt > 1000000) {
            return giftTotalInt / 1000 * 8;
        }
        if (giftTotalInt > 100000) {
            return giftTotalInt / 1000 * 4;
        }
        if (giftTotalInt > 10000) {
            return giftTotalInt / 1000 * 2;
        }
        if (giftTotalInt > 1000) {
            return giftTotalInt / 1000;
        }
        return 0;
    }

    public void updateHeatUpLevel(RoomPkActivityUpLevelVo levelVo) {
        if (!levelVo.isUpLevel()) {
            return;
        }
        RoomPkEventVo eventVo = new RoomPkEventVo();
        eventVo.setDataJson(JsonUtils.toJson(levelVo));
        eventVo.setType(RoomConstant.RoomPkEventType.ACTIVITY_PRIZE_RECORD_UP);
        SpringContextUtil.getApplicationContext().publishEvent(new RoomPkEvent(eventVo));
    }

    @Override
    public void handlerHeatUpLevel(RoomPkActivityUpLevelVo levelVo) {
        roomPkCache.addRoomCombatValue(levelVo.getRoomUid(), levelVo.getLevel());
        if (levelVo.getLevel() == 1) {
            sendRoomOwnerHeatValueAward(levelVo.getRoomUid(), new BigDecimal(500), null, null);
            return;
        }
        if (levelVo.getLevel() == 2) {
            sendRoomOwnerHeatValueAward(levelVo.getRoomUid(), new BigDecimal(1500), null, null);
            return;
        }
        if (levelVo.getLevel() == 3) {
            sendRoomOwnerHeatValueAward(levelVo.getRoomUid(), new BigDecimal(5000), 5, 15);
            return;
        }
        if (levelVo.getLevel() == 4) {
            sendRoomOwnerHeatValueAward(levelVo.getRoomUid(), new BigDecimal(10000), 6, 15);
            return;
        }
    }

    @Override
    public void handlerPkActivityRoomAward(List<RoomHeatValueTotay> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Date startTime = DateTimeUtil.getBeforeDay(DateTimeUtil.getNextHour(new Date(), -5), -1);
        Date endTime = DateTimeUtil.getEndTimeOfDay(startTime);
        List<Long> roomuidList = list.stream().map(vo -> vo.getRoomUid()).collect(Collectors.toList());
        List<RoomPkGoldVo> goldVoList = roomPkRecordExpand.getRoomPkTodayGold(roomuidList, startTime, endTime);
        Map<Long, BigDecimal> uidGoldMap = null;
        if (!CollectionUtils.isEmpty(goldVoList)) {
            uidGoldMap = goldVoList.stream().collect(Collectors.toMap(RoomPkGoldVo::getRoomUid, vo -> vo.getGold(), (k1, k2) -> k1));
        }
        goldVoList = roomPkRecordExpand.getRoomPkTodayGoldByTargeUid(roomuidList, startTime, endTime);
        if (uidGoldMap == null) {
            uidGoldMap = CollectionUtils.isEmpty(goldVoList) ? null : goldVoList.stream().collect(Collectors.toMap(RoomPkGoldVo::getRoomUid, vo -> vo.getGold(), (k1, k2) -> k1));
        } else if (!CollectionUtils.isEmpty(goldVoList)) {
            for (RoomPkGoldVo goldVo : goldVoList) {
                if (uidGoldMap.get(goldVo.getRoomUid()) != null) {
                    uidGoldMap.put(goldVo.getRoomUid(), uidGoldMap.get(goldVo.getRoomUid()).add(goldVo.getGold()));
                } else {
                    uidGoldMap.put(goldVo.getRoomUid(), goldVo.getGold());
                }
            }
        }
        handlerRoomOwnerAward(list, uidGoldMap);
        handlerRoomSendGiftAward(list, uidGoldMap);
    }

    public void handlerRoomOwnerAward(List<RoomHeatValueTotay> list, Map<Long, BigDecimal> uidGoldMap) {
        try {
            BigDecimal totalGold = null;
            RoomHeatValueTotay today = null;
            BigDecimal gold = null;
            List<RoomHeatValuePrizeRecord> prizeRecordList = new ArrayList<RoomHeatValuePrizeRecord>();
            List<RoomOwnerHeatValueAward> awardList = new ArrayList<RoomOwnerHeatValueAward>(list.size());
            BigDecimal goldCondition = new BigDecimal(100000);
            for (int i = 0; i < list.size(); i++) {
                today = list.get(i);
                totalGold = uidGoldMap.get(today.getRoomUid());
                if (totalGold == null) {
                    continue;
                }
                if (totalGold.compareTo(goldCondition) < 0) {
                    log.info("handlerRoomOwnerAward.uid:{},total:{} < goldCondition", today.getRoomUid(), totalGold);
                    continue;
                }
                gold = getOwnerGold(i, totalGold);
                awardList.add(buildAward(today.getRoomUid(), gold));
                prizeRecordList.add(buildPrizeRecord(today.getRoomUid(), gold, totalGold));
            }
            if (CollectionUtils.isEmpty(prizeRecordList)) {
                return;
            }
            kafkaSender.send(KafkaTopic.Finance.DC_ROOM, KafkaTopic.EventType.DC_ROOM_PK_ACTIVITY_OWNER_AWARD, JSONObject.toJSONString(awardList));

            for (RoomHeatValuePrizeRecord record : prizeRecordList) {
                roomHeatValuePrizeRecordMapper.insert(record);
            }
        } catch (Exception e) {
            log.error("handlerRoomOwnerAward error ", e);
        }
    }

    public void handlerRoomSendGiftAward(List<RoomHeatValueTotay> list, Map<Long, BigDecimal> uidGoldMap) {
        try {
            BigDecimal totalGold = null;
            RoomHeatValueTotay today = null;
            BigDecimal gold = null;
            RoomOwnerHeatValueAward award = null;
            List<RoomHeatValuePrizeRecord> prizeRecordList = new ArrayList<RoomHeatValuePrizeRecord>();
            List<RoomOwnerHeatValueAward> awardList = new ArrayList<RoomOwnerHeatValueAward>(list.size());
            BigDecimal goldCondition = new BigDecimal(100000);
            for (int i = 0; i < list.size(); i++) {
                today = list.get(i);
                totalGold = uidGoldMap.get(today.getRoomUid());
                if (totalGold == null) {
                    continue;
                }
                if (totalGold.compareTo(goldCondition) < 0) {
                    log.info("handlerRoomSendGiftAward.uid:{},total:{} < goldCondition", today.getRoomUid(), totalGold);
                    continue;
                }
                award = buildSendGiftAward(today, i);
                awardList.add(award);
                prizeRecordList.add(buildSendGiftPrizeRecord(today, award, totalGold));
            }
            if (CollectionUtils.isEmpty(prizeRecordList)) {
                return;
            }
            kafkaSender.send(KafkaTopic.Finance.DC_ROOM, KafkaTopic.EventType.DC_ROOM_PK_ACTIVITY_SEND_GIFT_AWARD, JSONObject.toJSONString(awardList));

            for (RoomHeatValuePrizeRecord record : prizeRecordList) {
                roomHeatValuePrizeRecordMapper.insert(record);
            }
        } catch (Exception e) {
            log.error("handlerRoomSendGiftAward error", e);
        }
    }

    public RoomHeatValuePrizeRecord buildSendGiftPrizeRecord(RoomHeatValueTotay today, RoomOwnerHeatValueAward award, BigDecimal total) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("noble:").append(award.getNobleId()).append(":").append(award.getNobleDay()).append(",car:").append(award.getCarId()).append(":").append(award.getCarDay());
        stringBuilder.append(",head:").append(award.getHeadwearId()).append(":").append(award.getHeadwearDay());
        RoomHeatValuePrizeRecord record = new RoomHeatValuePrizeRecord();
        record.setRoomUid(today.getRoomUid());
        record.setTotalGoldNum(total);
        record.setUid(today.getMvpUid().toString());
        record.setMvpAvatar(today.getMvpAvatar());
        record.setMvpNick(today.getMvpAvatar());
        record.setUserReward(stringBuilder.toString());
        record.setType(RoomConstant.RoomPkActivityAwardType.SEND);
        record.setCreateTime(new Date());
        return record;
    }

    public RoomHeatValuePrizeRecord buildPrizeRecord(Long roomUid, BigDecimal gold, BigDecimal total) {
        RoomHeatValuePrizeRecord record = new RoomHeatValuePrizeRecord();
        record.setGoldNum(gold);
        record.setRoomUid(roomUid);
        record.setTotalGoldNum(total);
        record.setType(RoomConstant.RoomPkActivityAwardType.OWNER);
        record.setCreateTime(new Date());
        return record;
    }

    public BigDecimal getOwnerGold(int i, BigDecimal totalGold) {
        if (i == 0) {
            return totalGold.multiply(new BigDecimal(0.1));
        }
        if (i == 1) {
            return totalGold.multiply(new BigDecimal(0.05));
        }
        if (i == 2) {
            return totalGold.multiply(new BigDecimal(0.03));
        }
        return totalGold.multiply(new BigDecimal(0.01));
    }

    public RoomOwnerHeatValueAward buildAward(Long roomUid, BigDecimal gold) {
        RoomOwnerHeatValueAward roomOwnerHeatValueAward = new RoomOwnerHeatValueAward();
        roomOwnerHeatValueAward.setRoomUid(roomUid);
        roomOwnerHeatValueAward.setGoldNum(gold);
        return roomOwnerHeatValueAward;
    }

    public RoomOwnerHeatValueAward buildSendGiftAward(RoomHeatValueTotay today, int rankPosition) {
        Integer nobleId = null, nobleDay = null;
        Integer carId = null, carDay = null;
        Integer headId = null, headDay = null;
        RoomOwnerHeatValueAward sendGiftHeatValueAward = new RoomOwnerHeatValueAward();
        sendGiftHeatValueAward.setRoomUid(today.getRoomUid());
        sendGiftHeatValueAward.setUid(today.getMvpUid());
        if (rankPosition == 0) {
            nobleId = 6;
            nobleDay = 15;
            carId = 93;
            carDay = 15;
            headId = 145;
            headDay = 15;
        }
        if (rankPosition == 1) {
            nobleId = 5;
            nobleDay = 15;
            carId = 93;
            carDay = 10;
            headId = 145;
            headDay = 10;
        }
        if (rankPosition == 2) {
            nobleId = 4;
            nobleDay = 5;
            carId = 93;
            carDay = 5;
        }
        if (rankPosition > 2) {
            headId = 145;
            headDay = 5;
        }
        sendGiftHeatValueAward.setNobleId(nobleId);
        sendGiftHeatValueAward.setNobleDay(nobleDay);
        sendGiftHeatValueAward.setCarId(carId);
        sendGiftHeatValueAward.setCarDay(carDay);
        sendGiftHeatValueAward.setHeadwearId(headId);
        sendGiftHeatValueAward.setHeadwearDay(headDay);
        return sendGiftHeatValueAward;
    }

    public void sendRoomOwnerHeatValueAward(Long roomUid, BigDecimal gold, Integer nobleId, Integer day) {
        RoomOwnerHeatValueAward roomOwnerHeatValueAward = new RoomOwnerHeatValueAward();
        roomOwnerHeatValueAward.setRoomUid(roomUid);
        roomOwnerHeatValueAward.setGoldNum(gold);
        roomOwnerHeatValueAward.setNobleId(nobleId);
        roomOwnerHeatValueAward.setNobleDay(day);
        roomOwnerHeatValueAward.setType(RoomConstant.RoomPkActivityAwardType.OWNER);
        kafkaSender.send(KafkaTopic.Finance.DC_ROOM, KafkaTopic.EventType.DC_ROOM_PK_ACTIVITY_UP, JSONObject.toJSONString(roomOwnerHeatValueAward));

        RoomHeatValuePrizeRecord record = new RoomHeatValuePrizeRecord();
        record.setGoldNum(gold);
        record.setRoomUid(roomUid);
        record.setType(RoomConstant.RoomPkActivityAwardType.OWNER);
        record.setCreateTime(new Date());
        if (nobleId != null) {
            record.setUserReward("nobleId:" + nobleId + ",day:" + day);
        }
        roomHeatValuePrizeRecordMapper.insert(record);

    }

    public List<RoomPkRecord> getExpirePkRecordListFromDb(Byte status) {
        RoomPkRecordExample example = new RoomPkRecordExample();
        example.createCriteria().andPkStatusEqualTo(status);
        return roomPkRecordMapper.selectByExample(example);
    }

    @Override
    public void cancelExpireRoomPkInvite() throws Exception {
        List<RoomPkRecord> roomPkRecordList = this.getExpirePkRecordListFromDb(RoomConstant.RoomPkStatus.PLEASE_PK);
        if (CollectionUtils.isEmpty(roomPkRecordList)) {
            return;
        }
        Long nowTime = System.currentTimeMillis();
        roomPkRecordList = roomPkRecordList.stream().filter(record -> record.getInviteInvalidTime() != null && (record.getInviteInvalidTime() * 1000 + record.getCreateTime().getTime()) < nowTime).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roomPkRecordList)) {
            return;
        }
        for (RoomPkRecord record : roomPkRecordList) {
            try {
                this.cancelRoomPk(record);
            } catch (Exception e) {
                log.error("cancelExpireRoomPkInvite record error,recordId:{}", record.getId(), e);
            }
        }
    }

    @Override
    public void handlerExpireRoomPking() throws Exception {
        List<RoomPkRecord> roomPkRecordList = this.getExpirePkRecordListFromDb(RoomConstant.RoomPkStatus.PKing);
        if (CollectionUtils.isEmpty(roomPkRecordList)) {
            return;
        }
        long now = System.currentTimeMillis();
        roomPkRecordList = roomPkRecordList.stream().filter(record -> record.getPkEndTime().getTime() < now).collect(Collectors.toList());
        for (RoomPkRecord record : roomPkRecordList) {
            try {
                this.handleRoomPkEndMsg(record.getRoomUid(), record.getTargetRoomUid(), record.getId(), false);
            } catch (Exception e) {
                log.error("handlerExpireRoomPking record error,recordId:{}", record.getId(), e);
            }
        }
    }

    @Override
    public void updateTodayHeatValueRank() throws Exception {
        String date = DateTimeUtil.convertDate(DateTimeUtil.getNextHour(new Date(), -5), DateTimeUtil.DEFAULT_DATE_PATTERN);
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_totay.name(), date);
        roomPkCache.updateTodayHeatValueRank(key);
    }

    @Override
    public void setPkSquareAllRoom(Map<String, Double> uidAndScoreMap, List<RoomVo> roomList, List<Long> roomUids) {
        roomCombatPowerService.setPkSquareAllRoom(uidAndScoreMap, roomList, roomUids);
    }
}
