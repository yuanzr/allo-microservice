package com.dc.allo.rank.config;

import com.dc.allo.common.component.chain.ChainManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangzhenjun on 2020/5/8.
 */
//@Configuration
public class ChainManagerConfig {

    @Bean
    public ChainManager chainManager(){
        return  new ChainManager();
    }
}
