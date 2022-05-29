package com.dc.allo.rank.config;

import com.dc.allo.common.component.threadpool.DCCommonThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangzhenjun on 2020/5/8.
 */
//@Configuration
public class ThreadPoolConfig {

    @Bean
    public DCCommonThreadPool dcCommonThreadPool(){
        return new DCCommonThreadPool();
    }
}
