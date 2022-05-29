package com.dc.allo.common.component.delay.java;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
@Slf4j
@Component
public class DelayQueueManager implements InitializingBean {
    // 线程池
    private ExecutorService executor;
    // 守护线程
    private Thread daemonThread;
    // 延时队列
    private DelayQueue<DelayTask<?>> delayQueue;

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = Executors.newFixedThreadPool(128);
        delayQueue = new DelayQueue<>();
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        daemonThread = new Thread(() -> {
            execute();
        });
        daemonThread.setName("DelayQueueMonitor");
        daemonThread.start();
    }

    /**
     * 执行任务
     */
    private void execute() {
        while (true) {
            int taskNum = delayQueue.size();
            log.info("当前延时任务数量:" + taskNum);
            try {
                // 从延时队列中获取任务
                DelayTask<?> delayTask = delayQueue.take();
                if (delayTask != null) {
                    Runnable task = delayTask.getTask();
                    if (null == task) {
                        continue;
                    }
                    // 提交到线程池执行task
                    executor.execute(task);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加任务
     *
     * @param task
     * @param time 延时时间
     * @param unit 时间单位
     */
    public void put(Runnable task, long time, TimeUnit unit) {
        // 获取延时时间
        long timeout = TimeUnit.NANOSECONDS.convert(time, unit);
        // 将任务封装成实现Delayed接口的消息体
        DelayTask<?> delayTask = new DelayTask<>(timeout, task);
        // 将消息体放到延时队列中
        delayQueue.put(delayTask);
        log.info("当前延时任务数量:" + delayQueue.size());
    }

    /**
     * 删除任务
     *
     * @param task
     * @return
     */
    public boolean removeTask(DelayTask task) {
        return delayQueue.remove(task);
    }
}
