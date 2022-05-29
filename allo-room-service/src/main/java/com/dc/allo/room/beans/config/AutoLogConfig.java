package com.dc.allo.room.beans.config;

import com.dc.allo.rpc.aspect.autolog.AutoLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangzhenjun on 2020/3/23.
 */
@Configuration
public class AutoLogConfig {

    @Bean
    public AutoLogAspect autoLog(){
        return new AutoLogAspect();
    }
}
