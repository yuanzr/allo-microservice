package com.dc.allo.roomplay.service.rpc.pk.impl;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.api.roomplay.DcHelloService;
import com.dc.allo.rpc.domain.roomplay.Hello;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;


/**
 * Created by zhangzhenjun on 2020/3/17.
 */
@Service
@Slf4j
public class DcHelloServiceImpl implements DcHelloService {

    @Override
    public void sayHello(Hello hello) {
        log.info(JsonUtils.toJson(hello));
    }
}
