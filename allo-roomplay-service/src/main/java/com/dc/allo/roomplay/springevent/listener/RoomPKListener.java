package com.dc.allo.roomplay.springevent.listener;

import com.dc.allo.common.constants.RoomConstant;
import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.vo.RoomPkActivityUpLevelVo;
import com.dc.allo.roomplay.domain.vo.RoomPkEventVo;
import com.dc.allo.roomplay.service.pk.RoomPkService;
import com.dc.allo.roomplay.springevent.event.RoomPkEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class RoomPKListener implements ApplicationListener<RoomPkEvent> {

    private static final transient Logger logger = LoggerFactory.getLogger(RoomPKListener.class);

    @Autowired
    private RoomPkService roomPkService;

    @Override
    @Async
    public void onApplicationEvent(RoomPkEvent roomPkEvent) {
        Long roomUid = null;
        try {
            if(roomPkEvent.getSource() instanceof RoomPkEventVo) {
                RoomPkEventVo eventVo = (RoomPkEventVo) roomPkEvent.getSource();
                switch (eventVo.getType()){
                    case RoomConstant.RoomPkEventType.CANCEL_PK:
                        roomPkService.cancelRoomPk(eventVo.getPkRecord());
                        break;
                    case RoomConstant.RoomPkEventType.ACTIVITY_PRIZE_RECORD_UP:
                        RoomPkActivityUpLevelVo levelVo = JsonUtils.fromJson(eventVo.getDataJson(),RoomPkActivityUpLevelVo.class);
                        roomPkService.handlerHeatUpLevel(levelVo);
                        break;
                    case RoomConstant.RoomPkEventType.ACTIVITY_PRIZE_RECORD_OWNER:
                        List<RoomHeatValueTotay> list =JsonUtils.fromJson(eventVo.getDataJson(),new TypeReference<List<RoomHeatValueTotay>>() {
                            @Override
                            public Type getType() {
                                return super.getType();
                            }
                        });
                        roomPkService.handlerPkActivityRoomAward(list);
                        break;
                }
            }
        }catch (Exception e){
            logger.error("roomPk listen error,roomUid:{},error:{}",roomUid,e);
        }
    }
}
