package com.dc.allo.biznotice;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by zhangzhenjun on 2020/4/17.
 */
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan(basePackages={"com.dc.allo.common.component","com.dc.allo.biznotice"})
@MapperScan(basePackages="com.dc.allo.biznotice.mapper")
public class BizNoticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BizNoticeApplication.class, args);
    }
}
