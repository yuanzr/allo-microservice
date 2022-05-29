package com.dc.allo.activity.service.impl;

import com.dc.allo.activity.service.cache.RoomPkActivityCache;
import com.dc.allo.activity.service.RoomPkActivityService;
import com.dc.allo.activity.domain.vo.room.RoomActivityRankInfoVo;
import com.dc.allo.activity.domain.vo.room.RoomPkActivityInfoVo;
import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.common.vo.RoomActivityRankVo;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.roomplay.pk.DcRoomPkService;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.dc.allo.rpc.proto.AlloResp;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomPkActivityServiceImpl implements RoomPkActivityService {
    @Reference
    private DcRoomInfoService dcRoomInfoService;
    @Autowired
    private RoomPkActivityCache roomPkActivityCache;
    @Reference
    private DcRoomPkService dcRoomPkService;
    @Override
    public RoomPkActivityInfoVo getRoomPkActivityInfoVo(Long roomUid) {
        RoomPkActivityInfoVo infoVo = roomPkActivityCache.getRoomPkActivityInfoVo(roomUid);
        if(StringUtils.isEmpty(infoVo.getRoomAvatar())||StringUtils.isEmpty(infoVo.getRoomTitle())){
            AlloResp<SimpleRoom> roomResp = dcRoomInfoService.getSimpleRoomByUid(roomUid);
            if(roomResp.isSuccess() && roomResp.getData() != null){
                infoVo.setRoomTitle(roomResp.getData().getTitle());
                infoVo.setRoomAvatar(roomResp.getData().getAvatar());
            }
        }
        return infoVo;
    }

    @Override
    public RoomActivityRankInfoVo getRoomActivityRankHistoryVo() {
        AlloResp<List<RoomHeatValueTotay>> alloResp = dcRoomPkService.getHistoryHeatValueTodayList();
        List<RoomHeatValueTotay> list = null;
        if(alloResp.isSuccess() && !CollectionUtils.isEmpty(alloResp.getData())){
            list = alloResp.getData();
        }
        RoomActivityRankInfoVo roomActivityRankInfoVo = null;
        if(!CollectionUtils.isEmpty(list)) {
            roomActivityRankInfoVo= new RoomActivityRankInfoVo();
            Map<Byte,List<RoomHeatValueTotay>> dayMap = list.stream().collect(Collectors.groupingBy(vo->vo.getDay()));
            List<RoomActivityRankVo> roomActivityRankList = new ArrayList<RoomActivityRankVo>(dayMap.size());
            dayMap.keySet().stream().sorted().forEach(day->{
                RoomActivityRankVo rankVo = new RoomActivityRankVo();
                rankVo.setRoomHeatValueTotayList(dayMap.get(day));
                roomActivityRankList.add(rankVo);
            });
            roomActivityRankInfoVo.setRoomActivityRankList(roomActivityRankList);
        }
        if(roomActivityRankInfoVo == null){
            roomActivityRankInfoVo= new RoomActivityRankInfoVo();
        }
        return roomActivityRankInfoVo;
    }

    @Override
    public RoomActivityRankVo getRoomActivityRankTodayVo() {
        RoomActivityRankVo rankVo= roomPkActivityCache.getRoomActivityRankTodayVo();
        if(rankVo == null){
            rankVo = new RoomActivityRankVo();
        }
        rankVo.setRemainTime(DateTimeUtil.getDateCountdownSecond(DateTimeUtil.getNextHour(new Date(),-5)));
        return rankVo;
    }
}
