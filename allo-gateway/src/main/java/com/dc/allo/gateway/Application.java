package com.dc.allo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhangzhenjun on 2020/3/25.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"com.dc.allo.common.component","com.dc.allo.gateway"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
