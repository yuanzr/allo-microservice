package com.dc.allo.finance.kafka;

import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.finance.handler.ActRamadanItemHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ActRamadanItemConsumer
 * @Description: 斋月活动发放/兑换碎片
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/4/20/16:30
 */
@Component
@Slf4j
public class ActRamadanItemConsumer {
    @Autowired
    private ActRamadanItemHandler actRamadanItemHandler;

    @KafkaListener(topics = KafkaTopic.Finance.DC_ACT_RAMADAN_ITEM)
    public void consumer(ConsumerRecord<String, String> consumerRecord) {
        String json = consumerRecord.value();
        log.info("[ActRamadanItemConsumer json:{}", json);
        if (StringUtils.isNotBlank(json)) {
            KafkaEvent event = JsonUtils.fromJson(json, KafkaEvent.class);
            actRamadanItemHandler.handler(event);
        }
    }


}
