package com.dc.allo.biznotice.beans.config;

import com.dc.allo.biznotice.beans.interceptor.CommonInterceptor;
import com.dc.allo.biznotice.beans.interceptor.ProtoBufInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: InterceptorConfig
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2019/9/27/14:19
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ProtoBufInterceptor protoBufInterceptor;

    @Bean
    public CommonInterceptor commonInterceptor() {
        return new CommonInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor());
        registry.addInterceptor(protoBufInterceptor);
    }
}