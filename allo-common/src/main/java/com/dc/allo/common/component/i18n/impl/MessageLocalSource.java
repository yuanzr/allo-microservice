package com.dc.allo.common.component.i18n.impl;

import com.dc.allo.common.component.i18n.IMessageSource;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MessageLocalSource
 * @Description: 本地配置文件i18n
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2019/01/30/14:27
 */
@Component
public class MessageLocalSource implements IMessageSource {

    @Autowired
    private MessageSource messageSource;

    /**
     * 获取指定key的value
     * @param key
     * @return
     */
    @Override
    public String getMessage(String key){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key,null,null,locale);
    }

    /**
     * 获取指定key的value
     * @param key   i18n.properties中的key
     * @param args  new Object[]{name,phone,sex}
     *              properties中的占位符按照顺序{0},{1},{2}
     * @return
     */
    @Override
    public String getMessage(String key,Object[] args){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key,args,null,locale);
    }


}
