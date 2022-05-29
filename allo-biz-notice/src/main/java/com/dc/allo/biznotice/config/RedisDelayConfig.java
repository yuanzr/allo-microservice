package com.dc.allo.biznotice.config;

import com.dc.allo.biznotice.delay.listener.FirebaseMsgDelayQueueListener;
import com.dc.allo.biznotice.delay.message.FirebaseMessage;
import com.dc.allo.common.component.delay.redis.DelayQueueListener;
import com.dc.allo.common.component.delay.redis.DelayQueueLooper;
import com.dc.allo.common.component.delay.redis.RedisDelayQueue;
import com.dc.allo.common.constants.RedisDelayQueueName;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisDelayConfig {
    //1.firebase队列
    @Bean(name = "fireBaseMsgDelayQueue")
    public RedisDelayQueue<FirebaseMessage> fireBaseMsgDelayQueue(@Qualifier("stringTemplate") RedisTemplate<String, String> stringRedisTemplate){
        RedisDelayQueue<FirebaseMessage> delayQueue = new RedisDelayQueue(RedisDelayQueueName.DELAY_QUEUE_FIREBASE_MESSAGE,stringRedisTemplate, FirebaseMessage.class);
        return delayQueue;
    }
    //2.firebase监听
    @Bean(name = "firebaseMsgDelayQueueListener")
    public FirebaseMsgDelayQueueListener firebaseMsgDelayQueueListener(){
        FirebaseMsgDelayQueueListener listener = new FirebaseMsgDelayQueueListener();
        return listener;
    }

    //3.关联队列与监听
    @Bean(name = "firebaseMsgDelayQueueLooper")
    public DelayQueueLooper<FirebaseMessage> firebaseMsgDelayQueueLooper(RedisDelayQueue<FirebaseMessage> fireBaseMsgDelayQueue,
                                                                        @Qualifier("firebaseMsgDelayQueueListener") DelayQueueListener firebaseMsgDelayQueueListener,
                                                                        @Value("${redis-delay-queue.looper.fetchCount}")int fetchCount,
                                                                        @Value("${redis-delay-queue.looper.sleepTime}")int sleepTime){
        DelayQueueLooper delayQueueLooper = new DelayQueueLooper(fireBaseMsgDelayQueue, firebaseMsgDelayQueueListener,fetchCount,sleepTime);
        return delayQueueLooper;
    }



}
