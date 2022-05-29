package com.dc.allo.finance.config;

import com.dc.allo.finance.interceptor.CommonInterceptor;
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

    @Bean
    public CommonInterceptor commonInterceptor() {
        return new CommonInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor());
    }
}