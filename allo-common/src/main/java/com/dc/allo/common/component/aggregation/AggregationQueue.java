package com.dc.allo.common.component.aggregation;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/2.
 */
public interface AggregationQueue<T> {
    /**
     * 消息入队
     */
    void enQueue(T msg);

    /**
     * 消息出队，可指定出队数量
     */
    List<T> deQueue(long count);

    /**
     * 获取队列名称
     */
    String getQueueName();

    /**
     * 获取队列长度
     * @return
     */
    long getQueueSize();

    /**
     * 设置开始计时时间
     */
    void setStartTime(long timestamp);

    /**
     * 获取开始计时时间
     * @return
     */
    long getStartTime();
}
