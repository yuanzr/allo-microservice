package com.dc.allo.rank.config;

import com.dc.allo.common.component.delay.redis.DelayQueueListener;
import com.dc.allo.common.component.delay.redis.DelayQueueLooper;
import com.dc.allo.common.component.delay.redis.RedisDelayQueue;
import com.dc.allo.common.constants.RedisDelayQueueName;
import com.dc.allo.rank.redis.listener.BetDelayQueueListener;
import com.dc.allo.rank.redis.message.BetMessage;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Configuration
public class RedisDelayConfig {

    @Bean(name = "betDelayQueue")
    public RedisDelayQueue<BetMessage> betDelayEventQueue(@Qualifier("stringTemplate") RedisTemplate<String, String> stringRedisTemplate){
        RedisDelayQueue<BetMessage> delayQueue = new RedisDelayQueue(RedisDelayQueueName.DELAY_QUEUE_NAME_BET,stringRedisTemplate, BetMessage.class);
        return delayQueue;
    }

    @Bean(name = "betDelayQueueLooper")
    public DelayQueueLooper<BetMessage> betDelayEventLooper(RedisDelayQueue<BetMessage> betDelayEventQueue,@Qualifier("betDelayQueueListener") DelayQueueListener betDelayQueueListener,@Value("${redis-delay-queue.looper.fetchCount}")int fetchCount,@Value("${redis-delay-queue.looper.sleepTime}")int sleepTime){
        DelayQueueLooper delayQueueLooper = new DelayQueueLooper(betDelayEventQueue, betDelayQueueListener,fetchCount,sleepTime);
        return delayQueueLooper;
    }

    @Bean(name = "betDelayQueueListener")
    public BetDelayQueueListener betDelayQueueListener(){
        BetDelayQueueListener listener = new BetDelayQueueListener();
        return listener;
    }

}
