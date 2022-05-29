package com.dc.allo.common.component.i18n;


/**
 * i18n国际化接口
 * 目前实现方式有:
 *    1.本地i18n配置;
 *    2.nacos配置中心:
 *    3.数据库;
 */
public interface IMessageSource {

     String getMessage(String key);

     String getMessage(String key, Object[] args);
}
