package com.dc.allo.roomplay.service.pk.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.RoomConstant;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.domain.room.RoomMicPositionVo;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.roomplay.service.pk.RoomCombatPowerService;
import com.dc.allo.roomplay.service.pk.cache.RoomPkCache;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.user.mic.DcUserMicService;
import com.dc.allo.rpc.domain.roomplay.PkSquareOpponentVo;
import com.dc.allo.rpc.domain.roomplay.PkSquareVo;
import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import com.dc.allo.rpc.proto.AlloResp;
import com.google.protobuf.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoomCombatPowerServiceImpl implements RoomCombatPowerService{

    @Autowired
    private RoomPkCache roomPkCache;
    @Reference
    private DcRoomInfoService dcRoomInfoService;
    @Reference
    private DcUserMicService dcUserMicService;

    @Override
    public Double getRoomCombatPowerByRoomUid(Long roomUid) {
        return roomPkCache.getRoomCombatPowerByRoomUid(roomUid);
    }


    /**
     * 拼装数据太多,依赖定时器更新
     */
    @Override
    public void setPkSquareAllRoom(Map<String, Double> uidAndScoreMap,List<RoomVo> roomList,List<Long> roomUids) {
        if (CollectionUtils.isEmpty(uidAndScoreMap)) {
            return;
        }
        Set<String> uidSet = uidAndScoreMap.keySet();
        List<PkSquareOpponentVo> squareOpponentVoList = new ArrayList<PkSquareOpponentVo>();
        Map<Long, RoomVo> roomMap = roomList.stream().collect(Collectors.toMap(RoomVo::getUid,vo -> vo,(k1,k2)->k1));
        List<RoomPkRecord> records = roomPkCache.getRoomPkRecordListFromCache();
        Map<Long,RoomPkRecord> roomPkMap = null;
        if(!CollectionUtils.isEmpty(records)){
            records = records.stream().filter(record->record.getPkStatus().equals(RoomConstant.RoomPkStatus.PKing)).collect(Collectors.toList());
        }
        if(!CollectionUtils.isEmpty(records)){
            roomPkMap = records.stream().collect(Collectors.toMap(RoomPkRecord::getRoomUid,vo -> vo,(k1,k2)->k1));
            roomPkMap.putAll(records.stream().collect(Collectors.toMap(RoomPkRecord::getTargetRoomUid,vo -> vo,(k1,k2)->k1)));
        }
        if(roomPkMap == null){
            roomPkMap = new HashMap<Long,RoomPkRecord>();
        }

        Long roomUid = null;
        PkSquareOpponentVo squareOpponentVo = null;
        for (String uidStr : uidSet) {
            roomUid = Long.parseLong(uidStr);
            squareOpponentVo = buildPkSquareOpponentVo(roomUid,roomMap,roomPkMap,uidAndScoreMap,uidStr);
            if(squareOpponentVo == null){
                continue;
            }
            squareOpponentVoList.add(squareOpponentVo);
        }
        roomPkCache.savePkSquareOpponentVoListToCache(squareOpponentVoList);
    }


    public PkSquareOpponentVo buildPkSquareOpponentVo(Long roomUid,Map<Long, RoomVo> roomMap,Map<Long,RoomPkRecord> roomPkMap,Map<String, Double> uidAndScoreMap,String uidStr){
        PkSquareOpponentVo squareOpponentVo = null;
        RoomVo roomVo = null;
        RoomPkRecord roomPkRecord = null;
        try {
            this.checkChooseRoomPkConditionOne(roomUid);

            squareOpponentVo = new PkSquareOpponentVo();
            squareOpponentVo.setRoomUid(roomUid);
            squareOpponentVo.setCurrentCombatPower(uidAndScoreMap.get(uidStr));
            roomVo = roomMap.get(roomUid);
            if (roomVo != null) {
                squareOpponentVo.setRoomAvatar(roomVo.getAvatar());
                squareOpponentVo.setRoomNick(roomVo.getTitle());
                squareOpponentVo.setRoomOnlineNum(roomVo.getOnlineNum());
            }
            squareOpponentVo.setTodayWinNum(roomPkCache.getRoomPkTodayWinNum(roomUid));

            roomPkRecord = roomPkMap.get(roomUid);
            if (roomPkRecord != null) {
                log.info("buildPkSquareOpponentVo.uid:{},targetUid:{},status:{}",roomPkRecord.getRoomUid(),roomPkRecord.getTargetRoomUid(),roomPkRecord.getPkStatus());
                squareOpponentVo.setPkStatus(roomPkRecord.getPkStatus());
            }
        }/*catch (ServiceException e){
            squareOpponentVo = null;
            log.error("buildPkSquareOpponentVo error,roomUid:{}",roomUid,e);
        }*/catch (Exception e){
            squareOpponentVo = null;
            log.error("buildPkSquareOpponentVo error,roomUid:{}",roomUid,e);
        }
        return squareOpponentVo;
    }

    /**
     * 该房间zone为0的则重新查询数据
     */
    @Override
    public void setOpponentList(PkSquareVo pkSquareVo){
        List<PkSquareOpponentVo> allRoom = roomPkCache.getPkSquareAllRoomFromCache();
        if(CollectionUtils.isEmpty(allRoom)){
            return;
        }
        Optional<PkSquareOpponentVo> currenRoom = allRoom.stream().filter(vo->vo.getRoomUid().equals(pkSquareVo.getRoomUid())).findFirst();
        //用户当前战力值
        Double currenCombatVal = 0d;

        if( currenRoom.isPresent() && currenRoom.get().getCurrentCombatPower() != null){
            currenCombatVal = currenRoom.get().getCurrentCombatPower();
        }
        //过滤当前用户的房间
        allRoom = allRoom.stream().filter(vo->!vo.getRoomUid().equals(pkSquareVo.getRoomUid())).collect(Collectors.toList());

        for(PkSquareOpponentVo vo : allRoom){
            if(vo.getCurrentCombatPower()==null){
                continue;
            }
            vo.setCurrentOpponentAbs(Math.abs(vo.getCurrentCombatPower()-currenCombatVal));
        }
        //按战力值接近的绝对值从小到大排序
        allRoom = allRoom.stream().sorted(Comparator.comparing(PkSquareOpponentVo::getCurrentOpponentAbs,Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
        if(allRoom.size()<=9){
            pkSquareVo.setDoughtyOpponentList(allRoom);
            return;
        }
        pkSquareVo.setDoughtyOpponentList(allRoom.subList(0,9));

        List<PkSquareOpponentVo> otherRoomList = allRoom.subList(pkSquareVo.getDoughtyOpponentList().size(),allRoom.size());
        otherRoomList = otherRoomList.stream().sorted(Comparator.comparing(PkSquareOpponentVo::getCurrentCombatPower,Comparator.nullsFirst(Comparator.naturalOrder())).reversed()).collect(Collectors.toList());
        pkSquareVo.setOtherOpponentList(otherRoomList);
        roomPkCache.saveRoomOpponentCache(pkSquareVo.getRoomUid(),pkSquareVo.getOtherOpponentList());
    }

    @Override
    public void setPageDataByZone(PkSquareVo pkSquareVo,int pageSize){
        List<PkSquareOpponentVo> otherOpponentList = pkSquareVo.getOtherOpponentList();
        if(CollectionUtils.isEmpty(otherOpponentList)){
            return;
        }
        pageSize = pageSize == 0 ? 50 : pageSize;
        if(otherOpponentList.size()<pageSize){
            return;
        }
        pkSquareVo.setOtherOpponentList(otherOpponentList.subList(0,pageSize));
    }

    @Override
    public void setPageDataByNotZone(PkSquareVo pkSquareVo,int page,int pageSize){
        List<PkSquareOpponentVo> otherOpponentList = roomPkCache.getRoomOpponentCache(pkSquareVo.getRoomUid());
        if(CollectionUtils.isEmpty(otherOpponentList)){
            return;
        }
        page = page == 0 ? 2 : page;
        pageSize = pageSize == 0 ? 50 : pageSize;
        int startIndex = (page-1)*pageSize;

        if(otherOpponentList.size()<startIndex){
            return;
        }
        int endIndex = page*pageSize;
        endIndex = endIndex > otherOpponentList.size() ? otherOpponentList.size() : endIndex;
        pkSquareVo.setOtherOpponentList(otherOpponentList.subList(startIndex,endIndex));
    }

    @Override
    public void checkChooseRoomPkConditionOne(Long roomUid) throws Exception {
        AlloResp<Map<String, RoomMicPositionVo>> roomMicListNotRealTimeResp = dcUserMicService.getRoomMicListNotRealTime(roomUid);
        if (roomMicListNotRealTimeResp.getCode() == BusiStatus.SUCCESS.value().intValue()) {
            Map<String, RoomMicPositionVo> micPositionVoMap = roomMicListNotRealTimeResp.getData();
            if (micPositionVoMap == null) {
                log.error("micPositionVoMap is null.roomUid:{}", roomUid);
                throw new DCException(BusiStatus.MIC_USER_NOT_ENOUGH);
            }
            Long upMicUsernum = micPositionVoMap.values().stream().filter(vo -> vo.getChatroomMember() != null).count();
            if (upMicUsernum < 3) {
                log.error("upMicUsernum<3,roomUid:{},upMicUsernum:{}", roomUid, upMicUsernum);
                throw new DCException(BusiStatus.MIC_USER_NOT_ENOUGH);
            }

            AlloResp<Integer> inRoomManagerNumResp = dcRoomInfoService.getInRoomManagerNum(roomUid);
            if (inRoomManagerNumResp.getCode() == BusiStatus.SUCCESS.value().intValue()) {
                int managerNum = inRoomManagerNumResp.getData();
                if (managerNum <1) {
                    log.error("managerNum<1,roomUid:{}", roomUid);
                    throw new DCException(BusiStatus.ROOM_PK_MANAGER_NOT_ENOUGH);
                }
            }
        }
    }
}
