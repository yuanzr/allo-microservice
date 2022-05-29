package com.dc.allo.common.component.delay.redis;

/**
 *  延迟消息监听
 *  每个队列需要有自己的监听器，具体的业务处理逻辑不一样
 *
 * Created by zhangzhenjun on 2020/3/16
 */
public interface DelayQueueListener<T> {
    /**
     * 接受消息
     */
    void onMessage(T data) throws Exception;
}
