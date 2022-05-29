package com.dc.allo.user.kafka.consumer;

import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.user.kafka.handler.UserKafkaHandler;
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
public class UserKafkaConsumer {

    @Autowired
    private UserKafkaHandler userKafkaHandler;

    @KafkaListener(topics = KafkaTopic.Finance.DC_ROOM)
    public void roomConsumer(ConsumerRecord<String, String> consumerRecord) {
        String json = consumerRecord.value();
        log.info("roomPlayKafka roomConsumer json", json);
        if (StringUtils.isBlank(json)) {
            return;
        }
        try {
            KafkaEvent event = JsonUtils.fromJson(json, KafkaEvent.class);
            userKafkaHandler.handler(event);
        }catch (Exception e){
            log.error("roomPlayKafka roomConsumer error",e);
        }
    }
}
