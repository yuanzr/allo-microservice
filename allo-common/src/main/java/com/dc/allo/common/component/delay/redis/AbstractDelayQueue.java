package com.dc.allo.common.component.delay.redis;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * 抽象消息队列
 * Created by zhangzhenjun on 2020/3/16
 */
@Slf4j
public abstract class AbstractDelayQueue<T> implements DelayQueue<T> {

    /**
     * 10秒
     */
    public static final int LOCK_EXPIRED_TIME = 5;
    private String queueName;
    private Class<T> clazz;
    private boolean shouldReturnSingle = false;

    @Override
    public void enQueue(DelayMessage<T> delayMessage) {
        try {
            if (delayMessage == null) {
                return;
            }
            if (StringUtils.isEmpty(delayMessage.getDuplicateKey())) {
                delayMessage.setDuplicateKey(genDuplicateKey());
            }
            log.debug("[enQueue],message={}", JsonUtils.toJson(delayMessage));
            doEnQueue(delayMessage);
        } catch (Exception ex) {
            log.error("[enQueue] error", ex);
        }
    }

    @Override
    public List<DelayMessage<T>> deQueue(int count) {
        if (count <= 0) {
            return null;
        }

        return doDeQueue(count);
    }

    @Override
    public boolean delMsg(String duplicateKey){
        return delFromQueue(duplicateKey);
    }

    protected String genDuplicateKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * 具体的入队操作，由子类实现
     */
    protected abstract void doEnQueue(DelayMessage<T> delayMessage);

    /**
     * 具体的出队操作，由子类实现
     */
    protected abstract List<DelayMessage<T>> doDeQueue(int count);

    protected abstract boolean delFromQueue(String duplicateKey);

    @Override
    public String getQueueName() {
        return RedisKeyUtil.appendCacheKeyByColon(this.queueName);
    }

    public void setQueueName(final String queueName) {
        this.queueName = queueName;
    }

    public Class<T> getClazz() {
        return this.clazz;
    }

    public void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public boolean isShouldReturnSingle() {
        return this.shouldReturnSingle;
    }

    public void setShouldReturnSingle(final boolean shouldReturnSingle) {
        this.shouldReturnSingle = shouldReturnSingle;
    }
}
