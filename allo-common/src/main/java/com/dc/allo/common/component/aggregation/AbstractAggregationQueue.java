package com.dc.allo.common.component.aggregation;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/2.
 */
@Slf4j
public abstract class AbstractAggregationQueue<T> implements AggregationQueue<T> {

    /**
     * 10秒
     */
    public static final int LOCK_EXPIRED_TIME = 5;
    private String queueName;
    private Class<T> clazz;
    private final String logPre = "Aggregation";


    @Override
    public void enQueue(T msg) {
        try {
            if (msg == null) {
                return;
            }
            log.debug(logPre+" [enQueue],message={}", JsonUtils.toJson(msg));
            doEnQueue(msg);
        } catch (Exception ex) {
            log.error(logPre+" [enQueue] error", ex);
        }
    }

    @Override
    public List<T> deQueue(long count) {
        if (count <= 0) {
            return null;
        }
        return doDeQueue(count);
    }

    @Override
    public String getQueueName() {
        return RedisKeyUtil.appendCacheKeyByColon(this.queueName);
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public long getQueueSize(){
        return doGetQueueSize();
    }

    /**
     * 具体的入队操作，由子类实现
     */
    protected abstract void doEnQueue(T msg);

    /**
     * 具体的出队操作，由子类实现
     */
    protected abstract List<T> doDeQueue(long count);

    protected abstract long doGetQueueSize();

    public Class<T> getClazz() {
        return this.clazz;
    }

    public void setClazz(final Class<T> clazz) {
        this.clazz = clazz;
    }
}
