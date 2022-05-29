package com.dc.allo.rank.kafka;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.threadpool.DCCommonThreadPool;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.handler.RankDataStreamHandler;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/7.
 */
@Slf4j
@Component
public class RankDataStreamConsumer {

    @Autowired
    DCCommonThreadPool dcCommonThreadPool;

    @Autowired
    RankDataStreamHandler rankDataStreamHandler;

    @KafkaListener(topics = {KafkaTopic.Rank.DC_RANK_DATA_STREAM})
    public void handleDataStream(ConsumerRecord<String, String> record) {
        String value = record.value();
        long offset = record.offset();
        log.info("KafkaRankDataStreamConsumer receive message offset:{}, value:{}", offset, value);
        try {
            KafkaEvent kafkaEvent = JsonUtils.fromJson(value, KafkaEvent.class);
            if (kafkaEvent != null) {
                RankDataRecord rankDataRecord = JsonUtils.fromJson(kafkaEvent.getData(), RankDataRecord.class);
                log.info("KafkaRankDataStreamConsumer message parse RankDataRecord success. offset:{}, value:{} rankDataRecord:{}", offset, value, rankDataRecord);
                dcCommonThreadPool.execute(Constant.RankDataStream.DATA_STREAM_HANDLE_POOL, () -> rankDataStreamHandler.handleRankDtaStream(rankDataRecord));
            }
        } catch (Exception e) {
            log.error("KafkaRankDataStreamConsumer receive message error offset:{}, value:{}", offset, value, e);
        }
    }
}
