package com.dc.allo.finance.handler;

import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.finance.service.GiftSendRecordService;
import com.dc.allo.rpc.domain.finance.GiftSendRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Component
@Slf4j
public class GiftKafkaHandler {

    @Autowired
    GiftSendRecordService giftSendRecordService;

    public void handler(KafkaEvent event) {
        if (event != null) {
            log.info("kafka accept gift event:{}", JsonUtils.toJson(event));
            if (event.getEventType() == KafkaTopic.EventType.GIFT_SEND_EVENT) {
                GiftSendRecord record = JsonUtils.fromJson(event.getData(), GiftSendRecord.class);
                log.info("kafka accept gift obj:{}", JsonUtils.toJson(record));
                if (record != null) {
                    giftSendRecordService.addGiftSendRecord(record);
                }
            }
        }
    }
}
