package com.dc.allo.common.component.delay.redis;

import java.util.List;

/**
 * 延迟队列接口
 * Created by zhangzhenjun on 2020/3/16
 */
public interface DelayQueue<T> {
    /**
     * 消息入队
     */
    void enQueue(DelayMessage<T> delayMessage);

    /**
     * 消息出队，可指定出队数量
     */
    List<DelayMessage<T>> deQueue(int count);

    /**
     * 获取队列名称
     */
    String getQueueName();

    /**
     * 删除队列中的消息
     * @param duplicateKey
     */
    boolean delMsg(String duplicateKey);
}
