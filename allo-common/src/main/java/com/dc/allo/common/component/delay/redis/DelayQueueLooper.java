package com.dc.allo.common.component.delay.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * 延迟队列轮询
 * Created by zhangzhenjun on 2020/3/16
 *
 */
@Scope("prototype")
@Slf4j
public class DelayQueueLooper<T> implements InitializingBean, DisposableBean {

    private final String LOG_PRE = "redisDelayQueue";

    /**
     * 获取数量
     */
    private int fetchCount;

    /**
     * 获取不到数据时，每次休眠的秒数
     */
    private long sleepTime = 1;
    private final static long ONE_MINUTES = 60;
    private final static int CORE_POOL_SIZE = 0;
    /**
     * 执行任务线程池
     */
    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("DelayQueueLooper-pool-%d")
            .setDaemon(true).build();
    private ExecutorService listenerThread = new ThreadPoolExecutor(CORE_POOL_SIZE, 2,
            ONE_MINUTES, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            threadFactory);
    private ExecutorService onMessagePool = new ThreadPoolExecutor(CORE_POOL_SIZE, 50,
            ONE_MINUTES, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            threadFactory);

    private DelayQueue<T> delayQueue;
    private DelayQueueListener<T> listener;

    /**
     * 启停控制标志
     */
    private volatile AtomicBoolean shutdown = new AtomicBoolean(false);

    public DelayQueueLooper(RedisDelayQueue<T> delayQueue, DelayQueueListener<T> listener, int fetchCount, int sleepTime) {
        this.delayQueue = delayQueue;
        this.listener = listener;
        this.fetchCount = fetchCount;
        this.sleepTime = sleepTime;
    }

    @Override
    public void destroy() throws Exception {
        try {
            log.info(LOG_PRE+"[DelayQueueLooper] destroy is starting...");
            shutdown.compareAndSet(false, true);
            listenerThread.shutdown();
            onMessagePool.shutdown();
            log.info(LOG_PRE+"[DelayQueueLooper] destroy is ending...");
        } catch (Exception e) {
            log.error(LOG_PRE+"shutdown,exception:{}", e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(LOG_PRE+"[DelayEventLooper] is starting queue:{},key:{},listener:{}", new Object[]{delayQueue.getClass().getName()
                , delayQueue.getQueueName(), listener.getClass().getName()});

        listenerThread.execute(new Runnable() {
            @Override
            public void run() {
                while (!shutdown.get()) {
                    try {
                        List<DelayMessage<T>> list = delayQueue.deQueue(getFetchCount());
                        if (CollectionUtils.isEmpty(list)) {
                            DelayQueueLooper.this.sleep();
                            continue;
                        }
                        log.info(LOG_PRE+"[DelayEventLooper] msg:{}", JSON.toJSONString(list));
                        for (DelayMessage<T> delayEntryMessage : list) {
                            final DelayMessage<T> message = delayEntryMessage;
                            onMessagePool.execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        listener.onMessage(message.getData());
                                    } catch (Exception e) {
                                        log.error(LOG_PRE+"[onMessagePool] Exception => {}", e.getMessage(), e);
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        log.warn(LOG_PRE+"[run] Exception => {}", e.getMessage(), e);
                    }
                }
            }
        });
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.warn(LOG_PRE+"Thread is interrupted!", e);
        }
    }

    public DelayQueueListener<T> getListener() {
        return this.listener;
    }

    public void setListener(final DelayQueueListener<T> listener) {
        this.listener = listener;
    }

    public DelayQueue<T> getDelayQueue() {
        return this.delayQueue;
    }

    public void setDelayQueue(final DelayQueue<T> delayQueue) {
        this.delayQueue = delayQueue;
    }

    public int getFetchCount() {
        return this.fetchCount;
    }

    public void setFetchCount(final int fetchCount) {
        this.fetchCount = fetchCount;
    }

    public long getSleepTime() {
        return this.sleepTime;
    }

    public void setSleepTime(final long sleepTime) {
        this.sleepTime = sleepTime;
    }
}

