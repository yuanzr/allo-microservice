package com.dc.allo.rank;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan(basePackages={"com.dc.allo.common.component","com.dc.allo.rank"})
@EnableMethodCache(basePackages = { "com.dc.allo.rank" })
@EnableCreateCacheAnnotation
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
