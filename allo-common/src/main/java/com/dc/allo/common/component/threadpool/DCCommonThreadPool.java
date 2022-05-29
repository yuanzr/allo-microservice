package com.dc.allo.common.component.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc: 线程池（同进程内共享，也可以自定义线程组）
 * @Author: goldsudo
 * @Date: 2019/12/26 20:17
 */
@Component
@Slf4j
public class DCCommonThreadPool implements InitializingBean {
    private int DEFAULT_CORE_POOL_SIZE = 5;
    private int DEFAULT_MAX_POOL_SIZE = 200;
    private int DEFAULT_QUEUE_SIZE = 2048;
    private int NORMAL_CORE_POOL_SIZE = 5;
    private int NORMAL_MAX_POOL_SIZE = 100;
    private int NORMAL_QUEUE_SIZE = 1024;
    private long KEEP_ALIVE_TIME = 1;
    private TimeUnit TIME_UNIT = TimeUnit.MINUTES;
    private boolean DEFAULT_ALLOW_CORE_THREAD_TIMEOUT = false;
    private boolean NORMAL_ALLOW_CORE_THREAD_TIMEOUT = true;
    private ThreadPoolExecutor defaultThreadPool = null;
    private ConcurrentHashMap<String, ThreadPoolExecutor> threadPoolNameMap = null;
    private ReentrantLock initPoolLock = new ReentrantLock();
    private RejectedExecutionHandler defaultRejectedExecutionHandler;

    public void execute(Runnable r) {
        execute(defaultThreadPool, r);
    }

    private void execute(ThreadPoolExecutor threadPoolExecutor, Runnable r) {
        threadPoolExecutor.execute(r);
    }

    /**
     * 使用默认配置的自定义线程池执行任务
     * 第一次使用时会按照配置异步创建指定名称的线程池
     * 因此第一批提交的任务会交给默认线程池执行
     *
     * @param poolName
     * @param callable
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable) throws Exception {
        execute(getPoolByName(poolName, NORMAL_CORE_POOL_SIZE, NORMAL_MAX_POOL_SIZE, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, null), callable);
    }

    /**
     * 自定义拒绝策略
     *
     * @param poolName
     * @param callable
     * @param handler
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable, RejectedExecutionHandler handler) throws Exception {
        execute(getPoolByName(poolName, NORMAL_CORE_POOL_SIZE, NORMAL_MAX_POOL_SIZE, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, handler), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable, int corePoolSize, int maxPoolSize) throws Exception {
        execute(getPoolByName(poolName, corePoolSize, maxPoolSize, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, null), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、拒绝策略
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param handler
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable, int corePoolSize, int maxPoolSize, RejectedExecutionHandler handler) throws Exception {
        execute(getPoolByName(poolName, corePoolSize, maxPoolSize, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, handler), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param queueSize
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable, int corePoolSize, int maxPoolSize, int queueSize) throws Exception {
        execute(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, null), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度、拒绝策略
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param queueSize
     * @param handler
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable, int corePoolSize, int maxPoolSize, int queueSize, RejectedExecutionHandler handler) throws Exception {
        execute(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, handler), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度、超核心线程保活时间、时间单位、是否允许销毁限制核心线程
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable, int corePoolSize, int maxPoolSize, int queueSize, long keepAliveTime, TimeUnit timeUnit, boolean allowCoreThreadTimeOut) throws Exception {
        execute(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, keepAliveTime, timeUnit, allowCoreThreadTimeOut, null), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度、超核心线程保活时间、时间单位、是否允许销毁限制核心线程、拒绝策略
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param queueSize
     * @param keepAliveTime
     * @param timeUnit
     * @param handler
     * @param allowCoreThreadTimeOut
     * @return
     * @throws Exception
     */
    public void execute(String poolName, Runnable callable, int corePoolSize, int maxPoolSize, int queueSize, long keepAliveTime, TimeUnit timeUnit, RejectedExecutionHandler handler, boolean allowCoreThreadTimeOut) throws Exception {
        execute(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, keepAliveTime, timeUnit, allowCoreThreadTimeOut, handler), callable);
    }


    private <V> Future<V> submit(ThreadPoolExecutor threadPoolExecutor, Callable<V> callable) throws Exception {
        return threadPoolExecutor.submit(callable);
    }

    /**
     * 使用默认线程池执行任务
     *
     * @param callable
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(Callable<V> callable) throws Exception {
        return submit(defaultThreadPool, callable);
    }

    /**
     * 使用默认配置的自定义线程池执行任务
     * 第一次使用时会按照配置异步创建指定名称的线程池
     * 因此第一批提交的任务会交给默认线程池执行
     *
     * @param poolName
     * @param callable
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable) throws Exception {
        return submit(getPoolByName(poolName, NORMAL_CORE_POOL_SIZE, NORMAL_MAX_POOL_SIZE, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, null), callable);
    }

    /**
     * 自定义拒绝策略
     *
     * @param poolName
     * @param callable
     * @param handler
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable, RejectedExecutionHandler handler) throws Exception {
        return submit(getPoolByName(poolName, NORMAL_CORE_POOL_SIZE, NORMAL_MAX_POOL_SIZE, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, handler), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable, int corePoolSize, int maxPoolSize) throws Exception {
        return submit(getPoolByName(poolName, corePoolSize, maxPoolSize, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, null), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、拒绝策略
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param handler
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable, int corePoolSize, int maxPoolSize, RejectedExecutionHandler handler) throws Exception {
        return submit(getPoolByName(poolName, corePoolSize, maxPoolSize, NORMAL_QUEUE_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, handler), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param queueSize
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable, int corePoolSize, int maxPoolSize, int queueSize) throws Exception {
        return submit(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, null), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度、拒绝策略
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param queueSize
     * @param handler
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable, int corePoolSize, int maxPoolSize, int queueSize, RejectedExecutionHandler handler) throws Exception {
        return submit(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, KEEP_ALIVE_TIME, TIME_UNIT, NORMAL_ALLOW_CORE_THREAD_TIMEOUT, handler), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度、超核心线程保活时间、时间单位、是否允许销毁限制核心线程
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable, int corePoolSize, int maxPoolSize, int queueSize, long keepAliveTime, TimeUnit timeUnit, boolean allowCoreThreadTimeOut) throws Exception {
        return submit(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, keepAliveTime, timeUnit, allowCoreThreadTimeOut, null), callable);
    }

    /**
     * 自定义池内核心线程数、池内最大线程数、任务等待队列长度、超核心线程保活时间、时间单位、是否允许销毁限制核心线程、拒绝策略
     *
     * @param poolName
     * @param callable
     * @param corePoolSize
     * @param maxPoolSize
     * @param queueSize
     * @param keepAliveTime
     * @param timeUnit
     * @param handler
     * @param allowCoreThreadTimeOut
     * @param <V>
     * @return
     * @throws Exception
     */
    public <V> Future<V> submit(String poolName, Callable<V> callable, int corePoolSize, int maxPoolSize, int queueSize, long keepAliveTime, TimeUnit timeUnit, RejectedExecutionHandler handler, boolean allowCoreThreadTimeOut) throws Exception {
        return submit(getPoolByName(poolName, corePoolSize, maxPoolSize, queueSize, keepAliveTime, timeUnit, allowCoreThreadTimeOut, handler), callable);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化线程池组
        threadPoolNameMap = new ConcurrentHashMap<>();
        //默认拒绝策略
        defaultRejectedExecutionHandler = (r, executor) -> {
            log.warn("dc-sysconf-pool tasks overflow. corePoolSize:{} maximumPoolSize:{} activeCount:{} currentPoolSize:{}",
                    executor.getCorePoolSize(),
                    executor.getMaximumPoolSize(),
                    executor.getActiveCount(),
                    executor.getPoolSize()
            );
            if (!executor.isShutdown()) {
                executor.getQueue().poll();
                executor.execute(r);
            }
        };
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("dc-sysconf-pool-%d").build();
        //根据默认配置构造线程池
        defaultThreadPool = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TIME_UNIT, new ArrayBlockingQueue<>(
                DEFAULT_QUEUE_SIZE), namedThreadFactory, defaultRejectedExecutionHandler);
        //线程预热
        defaultThreadPool.prestartCoreThread();
        //是否核心线程闲置销毁
        defaultThreadPool.allowCoreThreadTimeOut(DEFAULT_ALLOW_CORE_THREAD_TIMEOUT);
        log.info("dc-sysconf-pool init finished :[default-pool].");
    }

    private ThreadPoolExecutor getPoolByName(final String poolName, final int corePoolSizeParam, final int maxPoolSizeParam, final int queueSize, final long keepAliveTime, final TimeUnit timeUnit, final boolean allowCoreThreadTimeOut, final RejectedExecutionHandler handler) throws Exception {
        if (StringUtils.isBlank(poolName)) {
            return getDefaultPool();
        }
        ThreadPoolExecutor pool = threadPoolNameMap.get(poolName);
        if (pool == null) {
            //异步初始化当前线程池
            defaultThreadPool.submit(() -> {
                while (initPoolLock.isLocked()) {
                    try {
                        log.info("dc-sysconf-pool waiting for init lock of pool:[{}].", poolName);
                        //创建一个线程池的平均时间为65761 ns-->0.065761 ms
                        //睡眠1ms足够其他获取到锁的线程创建好线程池
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        log.error("dc-sysconf-pool async init pool wait lock fail. poolName:{}.", poolName, e);
                    }
                }
                ThreadPoolExecutor thisPool = threadPoolNameMap.get(poolName);
                if (thisPool != null) {
                    log.info("dc-sysconf-pool pool:[{}] init first check ok and return.", poolName);
                    return;
                }
                initPoolLock.lock();
                log.info("dc-sysconf-pool get init lock of pool:[{}].", poolName);
                try {
                    //double-check
                    thisPool = threadPoolNameMap.get(poolName);
                    if (thisPool != null) {
                        log.info("dc-sysconf-pool pool:[{}] init second check ok and return.", poolName);
                        return;
                    }
                    String name = "dc-sysconf-pool-group:[" + poolName + "]";
                    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(name + "-%d").build();
                    thisPool = new ThreadPoolExecutor(corePoolSizeParam, maxPoolSizeParam, keepAliveTime,
                            timeUnit, new ArrayBlockingQueue<>(queueSize), namedThreadFactory, handler != null
                            ? handler
                            : (r, executor) -> {
                        log.warn(name + " tasks overflow. corePoolSize:{} maximumPoolSize:{} activeCount:{} currentPoolSize:{}",
                                executor.getCorePoolSize(),
                                executor.getMaximumPoolSize(),
                                executor.getActiveCount(),
                                executor.getPoolSize()
                        );
                        if (!executor.isShutdown()) {
                            executor.getQueue().poll();
                            executor.execute(r);
                        }
                    });
                    //是否核心线程闲置销毁
                    thisPool.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
                    threadPoolNameMap.put(poolName, thisPool);
                    log.info("dc-sysconf-pool init finished pool:[{}].", poolName);
                } finally {
                    initPoolLock.unlock();
                }
            });
            //借调默认线程池执行第一批任务
            log.info("dc-sysconf-pool pool:[{}] is preparing, so borrow a thread from [default-pool] for this task.", poolName);
            return defaultThreadPool;
        }
        return pool;
    }

    private ThreadPoolExecutor getDefaultPool() {
        return defaultThreadPool;
    }

}
