package com.dc.allo.roomplay.service.rpc.pk.impl;

import com.dc.allo.common.component.SpringContextUtil;
import com.dc.allo.common.constants.RoomConstant;
import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.vo.RoomActivityRankVo;
import com.dc.allo.roomplay.domain.example.RoomHeatValueTotayExample;
import com.dc.allo.roomplay.domain.vo.RoomPkEventVo;
import com.dc.allo.roomplay.mapper.RoomHeatValueTotayMapper;
import com.dc.allo.roomplay.service.pk.RoomGiftService;
import com.dc.allo.roomplay.service.pk.RoomPkService;
import com.dc.allo.roomplay.service.pk.cache.RoomPkCache;
import com.dc.allo.roomplay.springevent.event.RoomPkEvent;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.roomplay.pk.DcRoomPkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.domain.room.RoomGiftSendVo;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.rpc.proto.AlloResp;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DcRoomPkServiceImpl implements DcRoomPkService {

    @Autowired
    private RoomPkService roomPkService;
    @Autowired
    private RoomPkCache roomPkCache;
    @Autowired
    private RoomGiftService roomGiftService;
    @Reference
    private DcRoomInfoService dcRoomInfoService;
    @Autowired
    private RoomHeatValueTotayMapper roomHeatValueTotayMapper;
    @Override
    public AlloResp cancelExpireRoomPkInvite() {
        try {
            roomPkService.cancelExpireRoomPkInvite();
        } catch (Exception e) {
            log.error("cancelExpireRoomPkInvite error ", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    @Override
    @Transactional
    public AlloResp handlerExpireRoomPking() {
        try {
            roomPkService.handlerExpireRoomPking();
        } catch (Exception e) {
            log.error("handlerExpireRoomPking error ", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    @Override
    public AlloResp updateTodayHeatValueRank() {
        try {
            boolean checkResult = roomPkService.checkRoomPkActivityDate();
            log.info("updateTodayHeatValueRank.result:{}",checkResult);
            if(checkResult) {
                roomPkService.updateTodayHeatValueRank();
            }
        } catch (Exception e) {
            log.error("updateTodayHeatValueRank error ", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    @Override
    public AlloResp roomCombatPowerCompute() {
        try {
            AlloResp<List<RoomVo>> homeRunningRoomListRsp = dcRoomInfoService.getHomeRunningRoomList();
            if (homeRunningRoomListRsp.getCode() != BusiStatus.SUCCESS.value().intValue()) {
                log.error("roomCombatPowerCompute.homeRunningRoomListRsp fail.code:{}",homeRunningRoomListRsp.getCode());
                return AlloResp.failed(BusiStatus.SERVERERROR);
            }

            List<RoomVo> roomList = homeRunningRoomListRsp.getData();
            if (CollectionUtils.isEmpty(roomList)) {
                log.error("roomCombatPowerCompute.roomList is null");
                return AlloResp.failed(BusiStatus.SERVERERROR);
            }
            List<Long> roomUids = roomList.stream().map(room -> room.getUid()).collect(Collectors.toList());
            Date now = new Date();
            Date fifteenDayDate = DateTimeUtil.getLastDay(now, 15);
            Date twoHourDate = DateTimeUtil.getLastHour(now, 2);
            List<RoomGiftSendVo> fifteenDaySendGiftList = roomGiftService.getRoomCombatPowerBySendGift(roomUids, fifteenDayDate);
            List<RoomGiftSendVo> twoHourSendGiftList = roomGiftService.getRoomCombatPowerBySendGift(roomUids, twoHourDate);
            Map<Long, Double> fifteenDaySendGiftMap = null;
            Map<Long, Double> twoHourSendGiftMap = null;
            if (!CollectionUtils.isEmpty(fifteenDaySendGiftList)) {
                fifteenDaySendGiftMap = fifteenDaySendGiftList.stream().collect(Collectors.toMap(RoomGiftSendVo::getRoomUid, a -> a.getTotalGoldNum().doubleValue(), (k1, k2) -> k1));
            }
            if (!CollectionUtils.isEmpty(twoHourSendGiftList)) {
                twoHourSendGiftMap = twoHourSendGiftList.stream().collect(Collectors.toMap(RoomGiftSendVo::getRoomUid, a -> a.getTotalGoldNum().doubleValue(), (k1, k2) -> k1));
            }
            if (fifteenDaySendGiftMap == null) {
                fifteenDaySendGiftMap = new HashMap<>();
            }
            if (twoHourSendGiftMap == null) {
                twoHourSendGiftMap = new HashMap<>();
            }
            Map<Long,Double> heatCombatPowerMap = roomPkCache.getRoomHeatAddCombatValue();
            Map<String, Double> roomCombatPowerMap = new HashMap<String, Double>();
            double fifteenDayGiftScore = 0d, twoHour = 0d;
            int personNum = 0;
            double heatCombatPower = 0d;
            for (RoomVo roomVo : roomList) {
                fifteenDayGiftScore = fifteenDaySendGiftMap.get(roomVo.getUid()) != null ? (fifteenDaySendGiftMap.get(roomVo.getUid()) / 15 / 24) * 0.6 : 0d;
                twoHour = twoHourSendGiftMap.get(roomVo.getUid()) != null ? twoHourSendGiftMap.get(roomVo.getUid()) / 2 * 0.5 : 0d;
                personNum = roomVo.getOnlineNum() == null ? 0 : roomVo.getOnlineNum();
                heatCombatPower = heatCombatPowerMap == null || heatCombatPowerMap.get(roomVo.getUid()) == null ? 0d : heatCombatPowerMap.get(roomVo.getUid());
                roomCombatPowerMap.put(roomVo.getUid().toString(), fifteenDayGiftScore + (personNum * 100 + twoHour) * 0.4 + heatCombatPower);
            }
            roomPkCache.saveRoomCombatPowerVoListToCache(roomCombatPowerMap);

            roomPkService.setPkSquareAllRoom(roomCombatPowerMap, roomList, roomUids);
        } catch (Exception e) {
            log.error("roomCombatPowerCompute error ", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    @Override
    public AlloResp computeRoomHeatValueAward(int day) {
        try {
            if(!roomPkService.checkRoomPkActivityDate()) {
                return AlloResp.success();
            }
            RoomActivityRankVo roomActivityRankVo = roomPkCache.getRoomActivityRankTodayVo(day);
            if (roomActivityRankVo == null) {
                return AlloResp.success();
            }
            List<RoomHeatValueTotay> roomHeatValueTotayList = roomActivityRankVo.getRoomHeatValueTotayList();
            if (CollectionUtils.isEmpty(roomHeatValueTotayList)) {
                return AlloResp.success();
            }
            log.info("computeRoomHeatValueAward.size:{}", roomHeatValueTotayList.size());
            for (RoomHeatValueTotay today : roomHeatValueTotayList) {
                roomHeatValueTotayMapper.insert(today);
            }
            RoomPkEventVo eventVo = new RoomPkEventVo();
            eventVo.setDataJson(JsonUtils.toJson(roomHeatValueTotayList));
            eventVo.setType(RoomConstant.RoomPkEventType.ACTIVITY_PRIZE_RECORD_OWNER);
            SpringContextUtil.getApplicationContext().publishEvent(new RoomPkEvent(eventVo));
            return AlloResp.success(roomHeatValueTotayList.size());
        }catch (Exception e) {
            log.error("computeRoomHeatValueAward error ", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @Override
    public AlloResp<List<RoomHeatValueTotay>> getHistoryHeatValueTodayList() {
        try {
            return AlloResp.success(roomHeatValueTotayMapper.selectByExample(new RoomHeatValueTotayExample()));
        } catch (Exception e) {
            log.error("getHistoryHeatValueTodayList error ", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
