package com.dc.allo.activity.service;

import com.dc.allo.common.component.i18n.IMessageSource;
import com.dc.allo.common.utils.LanguageUtils;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * @ClassName: BaseService
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/10/14:40
 */
@Slf4j
@Service
public class BaseService {

    @Resource(name = "messageNacosSource")
    protected IMessageSource messageSource;


    /**
     * 获取指定key的value
     * @param key
     * @return
     */
    protected String getMessage(String key){
        return messageSource.getMessage(key);
    }


    /**
     * 获取指定key的value
     * @param key    i18n.properties中的key
     * @param args   new Object[]{name,phone,sex}
     *               LanguageUtils.setLanguage(language);
     * @return
     */
    protected String getMessage(String key,String...args){
        return messageSource.getMessage(key,args);
    }

    /**
     * 获取本次请求的语言环境
     * @return
     */
    protected String getLocalLanguage(){
        String language = LocaleContextHolder.getLocale().getLanguage();
        if (StringUtils.isBlank(language)){
            return LanguageUtils.ENGLISH;
        }
        return language;
    }

}
