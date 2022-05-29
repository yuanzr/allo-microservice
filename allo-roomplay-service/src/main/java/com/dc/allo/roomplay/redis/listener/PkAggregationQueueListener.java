package com.dc.allo.roomplay.redis.listener;

import com.dc.allo.common.component.aggregation.AggregationQueueListener;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/20.
 */
@Service
@Slf4j
public class PkAggregationQueueListener implements AggregationQueueListener<Sample> {

    @Override
    public void onMessage(List<Sample> data) throws Exception {
        log.info("receive msg from aggregation queue, msg: {}", JsonUtils.toJson(data));
        if (data == null) {
            return;
        }
        log.info("onMessage obj:{}", JsonUtils.toJson(data));
    }
}
