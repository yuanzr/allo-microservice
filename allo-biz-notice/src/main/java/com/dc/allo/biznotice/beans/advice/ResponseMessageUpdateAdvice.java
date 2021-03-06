package com.dc.allo.biznotice.beans.advice;


import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.component.i18n.IMessageSource;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import com.google.protobuf.Message;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@ControllerAdvice(basePackages = {"com.dc.allo.biznotice.controller", "com.dc.allo.biznotice.service"})
public class ResponseMessageUpdateAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseMessageUpdateAdvice.class);

    @Resource(name = "messageNacosSource")
    protected IMessageSource messageSource;

    protected String getMessage(String key) {
        return messageSource.getMessage(key);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (obj instanceof AlloResp) {
            AlloResp body = (AlloResp) obj;
            String message = getMessage(body.getMessage());
            body.setMessage(StringUtils.isEmpty(message) ? body.getMessage() : message);
            //??????true ?????????ProtoBuf?????? ?????????null ????????????????????????protoBufTrans()?????????????????????
            return protoBufTrans(body, methodParameter) ? null : body;
        }
        return obj;
    }

    /**
     * ???????????????????????????ProtoBuf??????<p></p>
     * ??????true ????????????
     */
    public boolean protoBufTrans(AlloResp body, MethodParameter methodParameter) {
        AutoPbTrans autoPbTrans = methodParameter.getMethodAnnotation(AutoPbTrans.class);
        if (autoPbTrans == null) {
            return false;
        }
        PbCommon.PbHttpBizResp.Builder common = null;
        Class<Message> respClazz = getMessageClass(autoPbTrans.clzFullName());
        String classSimpleName = getClassSimpleName(respClazz);

        //??????data???????????????????????????????????????pb?????????????????????
        if (body.getData() == null) {
            dataNullReturnResponse(body, autoPbTrans, classSimpleName);
            return true;
        }

        try {
            common = PbUtils.genCommRespBaseBuilder(autoPbTrans.module(),autoPbTrans.clzFullName(), body.getData());
            common.setCode(body.getCode());
            common.setMessage(body.getMessage());
        } catch (Exception e) {
            logger.error("protoBufTrans error", e);
            common = exceptionReturnPbHttpBizResp(autoPbTrans, classSimpleName);
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        PbUtils.httpPbResponse(common.build(), response);
        return true;
    }

    public Class<Message> getMessageClass(String classFullName) {
        if (StringUtils.isEmpty(classFullName)) {
            return null;
        }
        try {
            return (Class<Message>) Class.forName(classFullName);
        } catch (Exception e) {
            logger.error("getMessageClass error", e);
            return null;
        }
    }

    public String getClassSimpleName(Class<Message> messageClass) {
        if (messageClass == null) {
            return null;
        }
        return messageClass.getSimpleName();
    }

    public void dataNullReturnResponse(AlloResp body, AutoPbTrans autoPbTrans, String classSimpleName) {
        PbCommon.PbHttpBizResp.Builder common = PbUtils.genCommRespBaseBuilder(autoPbTrans.module(), classSimpleName);
        common.setCode(body.getCode());
        common.setMessage(body.getMessage());
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        PbUtils.httpPbResponse(common.build(), response);
    }

    public PbCommon.PbHttpBizResp.Builder exceptionReturnPbHttpBizResp(AutoPbTrans autoPbTrans, String classSimpleName) {
        PbCommon.PbHttpBizResp.Builder common = PbUtils.genCommRespBaseBuilder(autoPbTrans.module(), classSimpleName);
        common.setCode(BusiStatus.BUSIERROR.value());
        String message = getMessage(BusiStatus.BUSIERROR.getReasonPhrase());
        common.setMessage(StringUtils.isEmpty(message) ? BusiStatus.BUSIERROR.getReasonPhrase() : message);
        return common;
    }
}
