package com.dc.allo.user.controller;

import com.dc.allo.common.component.i18n.IMessageSource;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.utils.LanguageUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.dc.allo.rpc.domain.Check;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @ClassName: BaseController
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2019/9/27/14:23
 */
@Controller
public class BaseController {

    @Value("${spring.application.name}")
    private String appName;

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

    @RequestMapping("/check")
    public AlloResp<Check> check(){
        Check check = new Check();
        check.setAppName(appName);
        check.setCheckTime(new Date());
        return AlloResp.success(check);
    }

    protected PbCommon.PbHttpBizReq getPbHttpBizReq(HttpServletRequest request){
        Object protoObject = request.getAttribute(Constant.proto_interceptor_attr_name);
        if (protoObject == null) {
            return PbCommon.PbHttpBizReq.getDefaultInstance();
        }
        return (PbCommon.PbHttpBizReq) protoObject;
    }

}
