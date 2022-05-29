package com.dc.allo.rpc.api.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erban.main.proto.PbCommon;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import com.google.protobuf.util.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * Created by zhangzhenjun on 2020/3/12.
 */
public class PbUtils {

    private static final Logger logger = LoggerFactory.getLogger(PbUtils.class);

    public static PbCommon.PbHttpBizResp.Builder genCommRespBaseBuilder(int module,String className) {
        return PbCommon.PbHttpBizResp.newBuilder()
                .setModule(module)
                .setTimestamp(System.currentTimeMillis())
                .setMethod(className);
    }


    public static PbCommon.PbHttpBizResp.Builder genCommRespBaseBuilder(int module,String clzFullName,Object obj) {
        PbCommon.PbHttpBizResp.Builder respBuilder = PbCommon.PbHttpBizResp.newBuilder()
                .setModule(module)
                .setTimestamp(System.currentTimeMillis());
        if(obj != null && !StringUtils.isBlank(clzFullName)){
            GeneratedMessageV3 respObj = null;
            try {
                // 获取resp的json
//                StopWatch stopWatch = new StopWatch("pb_transTime");
//                stopWatch.start("java2json");
                String returnJson = JSONObject.toJSONString(obj);
//                stopWatch.stop();

                // 包装返回
//                stopWatch.start("class4name&invoke_build");
                Class<Message> respClazz = (Class<Message>)Class.forName(clzFullName);
                Method respBuliderMethod = respClazz.getMethod("newBuilder");
                Message.Builder builder = (Message.Builder)respBuliderMethod.invoke(null,null);
//                stopWatch.stop();

                // respObj就是pb里定义的Resp
//                stopWatch.start("json2pb");
                respObj = PbUtils.json2pb(returnJson,builder);
//                stopWatch.stop();
//                logger.info(stopWatch.prettyPrint());
                respBuilder.setData(respObj.toByteString());
                respBuilder.setMethod(respClazz.getSimpleName());
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }
        return respBuilder;
    }

    public static <T> T bytes2pb(byte[] data,Class<T> clazz){
        if(data != null) {
            try {
                Class<Message> respClazz = (Class<Message>) Class.forName(clazz.getName());
                Method respBuliderMethod = respClazz.getMethod("newBuilder");
                Message.Builder builder = (Message.Builder) respBuliderMethod.invoke(null, null);
                builder.mergeFrom(data);
                return (T) builder.build();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * json数据转换为pb对象
     */
    public static <T> T json2pb(String json, Message.Builder builder) throws TextFormat.ParseException, InvalidProtocolBufferException {
        if (builder == null) {
            return null;
        }

        JsonFormat.parser().ignoringUnknownFields().merge(json, builder);
        return (T) builder.build();
    }

    /**
     * Object数据转换为pb对象
     */
    public static <T> T Object2pb(Object entity, Message.Builder builder) throws TextFormat.ParseException, InvalidProtocolBufferException {
        if (builder == null || entity == null) {
            return null;
        }
        return json2pb(JSON.toJSONString(entity), builder);
    }

    /**
     * http 输出pb流
     * @param message
     * @param response
     */
    public static void httpPbResponse(Message message,HttpServletResponse response){
        response.setContentType("application/x-protobuf");
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            message.toBuilder().build().writeTo(out);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
    }

}
