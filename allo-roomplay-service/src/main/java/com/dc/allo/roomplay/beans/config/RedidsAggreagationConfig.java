package com.dc.allo.roomplay.beans.config;

import com.dc.allo.common.component.aggregation.AggregationQueueListener;
import com.dc.allo.common.component.aggregation.AggregationQueueLooper;
import com.dc.allo.common.component.aggregation.RedisAggregationQueue;
import com.dc.allo.common.constants.RedisAggregationQueueName;
import com.dc.allo.roomplay.redis.listener.PkAggregationQueueListener;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by zhangzhenjun on 2020/4/2.
 */
@Configuration
public class RedidsAggreagationConfig {

    @Bean(name = "pkAggregationQueue")
    public RedisAggregationQueue<Sample> pkDelayEventQueue(@Qualifier("stringTemplate") RedisTemplate<String, String> stringRedisTemplate){
        RedisAggregationQueue<Sample> aggregationQueue = new RedisAggregationQueue(RedisAggregationQueueName.AGGREGATION_QUEUE_NAME_SAMPLE,stringRedisTemplate,Sample.class);
        return aggregationQueue;
    }

    @Bean(name = "pkAggregationQueueLooper")
    public AggregationQueueLooper<Sample> pkDelayEventLooper(RedisAggregationQueue<Sample> pkAggregationQueue,@Qualifier("pkAggregationQueueListener") AggregationQueueListener pkAggregationQueueListener,@Value("${redis-aggregation-queue.looper.triggerCnt}")int triggerCnt,@Value("${redis-aggregation-queue.looper.triggerSeconds}")int triggerSeconds,@Value("${redis-aggregation-queue.looper.sleepTime}")int sleepTime){
        AggregationQueueLooper aggregationQueueLooper = new AggregationQueueLooper(pkAggregationQueue, pkAggregationQueueListener,triggerCnt,triggerSeconds,sleepTime);
        return aggregationQueueLooper;
    }

    @Bean(name = "pkAggregationQueueListener")
    public AggregationQueueListener pkAggregationQueueListener(){
        AggregationQueueListener listener = new PkAggregationQueueListener();
        return listener;
    }
}
