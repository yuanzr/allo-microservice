package com.dc.allo.finance.kafka;

import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.finance.handler.GiftKafkaHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Component
@Slf4j
public class GiftKafkaConsumer {

    @Autowired
    GiftKafkaHandler giftKafkaHandler;

    @KafkaListener(topics = KafkaTopic.Finance.DC_GIFT_SEND)
    public void consumer(ConsumerRecord<String, String> consumerRecord) {
        String json = consumerRecord.value();
        log.info("[kafka consumer json:{}", json);
        if (StringUtils.isNotBlank(json)) {
            KafkaEvent event = JsonUtils.fromJson(json, KafkaEvent.class);
            giftKafkaHandler.handler(event);
        }
    }
}
