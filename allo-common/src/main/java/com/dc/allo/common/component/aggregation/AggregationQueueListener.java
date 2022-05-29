package com.dc.allo.common.component.aggregation;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/2.
 */
public interface AggregationQueueListener<T> {
    /**
     * 接收消息
     */
    void onMessage(List<T> data) throws Exception;
}
