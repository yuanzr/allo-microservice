package com.dc.allo.biznotice.service.msg.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.model.im.IMResponse;
import io.rong.RongCloud;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RongCloudBaseService {
    public static String appKey;
    public static String appSecret;
    public static String API_SG01_ADDRESS;

    protected static RongCloud getRongCloudClient() {
        RongCloud rongCloud = RongCloud.getInstance(appKey,appSecret,API_SG01_ADDRESS);
        return rongCloud;
    }

    protected IMResponse getResponse(String result) {
        return result != null ? JSONObject.parseObject(result, IMResponse.class) : null;
    }

    public String getAppKey() {
        return appKey;
    }

    @Value("${RongCloudNetEase.appKey}")
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    @Value("${RongCloudNetEase.appSecret}")
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAPI_SG01_ADDRESS() {
        return API_SG01_ADDRESS;
    }

    @Value("${RongCloudNetEase.API_SG01_ADDRESS}")
    public void setAPI_SG01_ADDRESS(String API_SG01_ADDRESS) {
        this.API_SG01_ADDRESS = API_SG01_ADDRESS;
    }
}
