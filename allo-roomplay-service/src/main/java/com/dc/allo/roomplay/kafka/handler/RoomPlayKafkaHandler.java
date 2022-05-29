package com.dc.allo.roomplay.kafka.handler;

import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.kafka.message.GiftMessage;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.vo.RoomPkActivityUpLevelVo;
import com.dc.allo.roomplay.service.pk.RoomPkService;
import com.dc.allo.rpc.domain.finance.GiftSendRecord;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Component
@Slf4j
public class RoomPlayKafkaHandler {

    @Autowired
    private RoomPkService roomPkService;

    public void handler(KafkaEvent event) throws Exception{
        if (event != null) {
            log.info("roomPlayKafkaHandler accept event:{}", JsonUtils.toJson(event));
            String data = event.getData();
            if (StringUtils.isBlank(data)) {
                return;
            }
            switch (event.getEventType()) {
                case KafkaTopic.EventType.GIFT_SEND_EVENT: {
                    GiftMessage giftMessage = JsonUtils.fromJson(event.getData(), GiftMessage.class);
                    roomPkService.handleGiftMessage(giftMessage);
                }
                default:
                    break;
            }
        }
    }
}
