package com.dc.allo.common.component.delay.redis;

import lombok.Data;

/**
 * 延迟消息结构体
 * 设计思路：在redis会存两份数据
 * Zset：key为队列名称，expireTime 为score，key为member
 * String：key为member ，data为值
 * Created by zhangzhenjun on 2020/3/16.
 */
@Data
public class DelayMessage<T> {
    /**
     *  存活时间，时间戳，毫秒
     *  到了该时间，消息就需要被消费了
     */
    private long expireTime;
    /**
     * 具体的消息内容
     */
    private T data;
    /**
     * 去重用的key
     */
    private String duplicateKey;

}
