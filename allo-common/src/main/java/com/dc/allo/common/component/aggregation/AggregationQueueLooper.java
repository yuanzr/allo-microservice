package com.dc.allo.common.component.aggregation;

import com.dc.allo.common.utils.JsonUtils;
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
 * Created by zhangzhenjun on 2020/4/2.
 */
@Slf4j
@Scope("prototype")
public class AggregationQueueLooper<T> implements InitializingBean, DisposableBean {

    private final String logPre = "Aggregation";
    private int triggerCnt;
    private int triggerSeconds;
    private int sleepTime;

    private final static long ONE_MINUTES = 60;
    private final static int CORE_POOL_SIZE = 0;
    /**
     * 执行任务线程池
     */
    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("AggregationQueueLooper-pool-%d")
            .setDaemon(true).build();
    private ExecutorService listenerThread = new ThreadPoolExecutor(CORE_POOL_SIZE, 2,
            ONE_MINUTES, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            threadFactory);
    private ExecutorService onMessagePool = new ThreadPoolExecutor(CORE_POOL_SIZE, 10,
            ONE_MINUTES, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            threadFactory);

    private AggregationQueue<T> aggregationQueue;
    private AggregationQueueListener<T> listener;

    /**
     * 启停控制标志
     */
    private volatile AtomicBoolean shutdown = new AtomicBoolean(false);

    public AggregationQueueLooper(AggregationQueue<T> aggregationQueue, AggregationQueueListener<T> listener, int triggerCnt, int triggerSeconds,int sleepTime) {
        this.aggregationQueue = aggregationQueue;
        this.listener = listener;
        this.triggerCnt = triggerCnt;
        this.triggerSeconds = triggerSeconds;
        this.sleepTime = sleepTime;
    }

    @Override
    public void destroy() throws Exception {
        try {
            log.info(logPre+" [QueueLooper] destroy is starting...");
            shutdown.compareAndSet(false, true);
            listenerThread.shutdown();
            log.info(logPre+" [QueueLooper] destroy is ending...");
        } catch (Exception e) {
            log.error(logPre+" shutdown,exception:{}", e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(logPre + " [EventLooper] is starting queue:{},key:{},listener:{},triggerCnt:{},trggerSconds:{},sleepTime:{}", aggregationQueue.getClass().getName()
                , aggregationQueue.getQueueName(), listener.getClass().getName(), triggerCnt, triggerSeconds, sleepTime);
        aggregationQueue.setStartTime(System.currentTimeMillis());
        listenerThread.execute(new Runnable() {
            @Override
            public void run() {
                while (!shutdown.get()) {
                    try {
                        long count = aggregationQueue.getQueueSize();
                        if (count <= 0) {
                            AggregationQueueLooper.this.sleep();
                            continue;
                        }
                        //数量满足要求
                        if (count >= triggerCnt) {
                            onMessage();
                        } else {
                            long startTime = aggregationQueue.getStartTime();
                            long curTime = System.currentTimeMillis();
                            long stepTime = (curTime - startTime) / 1000;
                            log.info(logPre+" stepTime:{}",stepTime);
                            //时间满足要求,队列全部出队
                            if (stepTime >= triggerSeconds) {
                                List<T> list = aggregationQueue.deQueue(count);
                                if (CollectionUtils.isEmpty(list)) {
                                    aggregationQueue.setStartTime(curTime);
                                    AggregationQueueLooper.this.sleep();
                                    continue;
                                }
                                aggregationQueue.setStartTime(curTime);
                                onMessage(list);
                            }
                        }
                        AggregationQueueLooper.this.sleep();
                    } catch (Exception e) {
                        log.warn(logPre + " [run] Exception => {}", e.getMessage(), e);
                    }
                }
            }
        } );
    }

    private void onMessage(List<T> list){
        onMessagePool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    listener.onMessage(list);
                } catch (Exception e) {
                    log.error(logPre+" [onMessagePool] Exception => {}", e.getMessage(), e);
                }
            }
        });
    }

    private void onMessage(){
        List<T> list = aggregationQueue.deQueue(triggerCnt);
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        log.info(logPre + " [EventLooper] msg:{}", JsonUtils.toJson(list));
        onMessage(list);
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.warn(logPre+" Thread is interrupted!", e);
        }
    }
}
