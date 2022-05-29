package com.dc.allo.roomplay.beans.config;

import com.dc.allo.common.component.delay.redis.DelayQueueListener;
import com.dc.allo.common.component.delay.redis.DelayQueueLooper;
import com.dc.allo.common.component.delay.redis.RedisDelayQueue;
import com.dc.allo.common.constants.RedisDelayQueueName;
import com.dc.allo.roomplay.redis.listener.PkDelayQueueListener;
import com.dc.allo.roomplay.redis.message.PkMessage;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisDelayConfig {

    @Bean(name = "pkDelayQueue")
    public RedisDelayQueue<Sample> pkDelayEventQueue(@Qualifier("stringTemplate") RedisTemplate<String, String> stringRedisTemplate){
        RedisDelayQueue<Sample> delayQueue = new RedisDelayQueue(RedisDelayQueueName.DELAY_QUEUE_NAME_PK,stringRedisTemplate, PkMessage.class);
        return delayQueue;
    }

    @Bean(name = "pkDelayQueueLooper")
    public DelayQueueLooper<Sample> pkDelayEventLooper(RedisDelayQueue<Sample> pkDelayQueue,@Qualifier("pkDelayQueueListener") DelayQueueListener pkDelayQueueListener,@Value("${redis-delay-queue.looper.fetchCount}")int fetchCount,@Value("${redis-delay-queue.looper.sleepTime}")int sleepTime){
        DelayQueueLooper delayQueueLooper = new DelayQueueLooper(pkDelayQueue, pkDelayQueueListener,fetchCount,sleepTime);
        return delayQueueLooper;
    }

    @Bean(name = "pkDelayQueueListener")
    public PkDelayQueueListener pkDelayQueueListener(){
        PkDelayQueueListener listener = new PkDelayQueueListener();
        return listener;
    }

}
