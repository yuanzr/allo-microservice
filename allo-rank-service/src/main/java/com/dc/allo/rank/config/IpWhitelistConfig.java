package com.dc.allo.rank.config;

import com.dc.allo.rpc.aspect.whitelistip.IpCheckAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * ip 白名单配置
 *
 * 1.引入本配置文件：IpWhitelistConfig.class
 * 2.拦截的方法需要包含 HttpServletRequest 类型参数
 * 3.设置参数：url.config.iplist: http://syyy-config.yy.com/system/ipListConf
 * 4.如自定义控制ip是否通过，可继承 AbstractIpCheckAdapter 创建bean对象，则默认配置的黑白名单失效，第3步忽略
 *
 * 5.对需要拦截方法使用注解 @IpCheck
 *
 * Created by zhangzhenjun on 2020/5/18.
 */
@Configuration
public class IpWhitelistConfig {
    @Bean
    IpCheckAop getIpCheckAop() {
        return new IpCheckAop();
    }
}
