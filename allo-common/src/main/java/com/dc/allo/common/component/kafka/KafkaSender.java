package com.dc.allo.common.component.kafka;

import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Component
@Slf4j
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, int eventType, Object obj) {
        if (obj == null) {
            return;
        }
        String json;
        if (obj instanceof String) {
            json = (String) obj;
        } else {
            json = JsonUtils.toJson(obj);
        }

        KafkaEvent event = new KafkaEvent(eventType,json);
        String eventJson = JsonUtils.toJson(event);

        log.info("[kafkaSender] uuid:" + event.getContext() + ", " + eventJson);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, eventJson);
        log.info("[kafkaSender] uuid:" + event.getContext() + " done");
        try {
            SendResult<String, String> stringStringSendResult = send.get();
            log.info("[kafkaSender] sendResult:{}", stringStringSendResult);
        } catch (InterruptedException e) {
            log.error("[kafkaSender] error ", e);
        } catch (ExecutionException e) {
            log.error("[kafkaSender] error ", e);
        }
    }

}
