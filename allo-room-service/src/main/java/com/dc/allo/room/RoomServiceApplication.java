package com.dc.allo.room;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhangzhenjun on 2020/3/21.
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
//@ComponentScan(basePackages={"com.dc.allo.sysconf.component","com.dc.allo.roomplay"},
//        useDefaultFilters = false,
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Component.class})
//        })
@ComponentScan(basePackages={"com.dc.allo.common.component","com.dc.allo.room"})
public class RoomServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomServiceApplication.class,args);
    }
}
