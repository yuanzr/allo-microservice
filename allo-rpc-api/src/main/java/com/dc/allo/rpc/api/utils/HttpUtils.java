package com.dc.allo.rpc.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenjinying on 2017/8/3.
 * mail: 415683089@qq.com
 */
@Slf4j
public class HttpUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES))
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();


    private HttpUtils() {
    }

    private static class SingletonHolder {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    private static final HttpUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public static String doGet(String url) throws IOException {
        return doGet(url, null);
    }

    public static String doGet(String url, OkHttpClient clientCustom) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return getInstance().run(request, clientCustom);
    }


    public static String doPost(String url) throws IOException {
        return doPostInner(url, JsonUtils.EMPTY_JSON, null);
    }

    public static String doPostCustom(String url, OkHttpClient clientCustom) throws IOException {
        return doPostInner(url, JsonUtils.EMPTY_JSON, clientCustom);
    }

    public static String doPost(String url, Object obj) throws IOException {
        return doPostInner(url, obj, null);
    }

    public static String doPostCustom(String url, Object obj, OkHttpClient clientCustom) throws IOException {
        return doPostInner(url, obj, clientCustom);
    }

    private static String doPostInner(String url, Object obj, OkHttpClient clientCustom) throws IOException {
        String json = JsonUtils.EMPTY_JSON;
        if (obj != null) {
            if (obj instanceof String) {
                json = (String) obj;
            } else {
                json = JsonUtils.toJson(obj);
            }
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return getInstance().run(request, clientCustom);
    }


    private String run(Request request, OkHttpClient clientCustom) throws IOException {

        if (clientCustom == null) {
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        } else {
            try (Response response = clientCustom.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }


    /**
     * 获取ip地址
     */
    public static String getIpAddress(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static <T> T doGetWithClass(String url, Map<String, String> params, Class<T> clazz) throws IOException {
        T respData = null;
        if (StringUtils.isBlank(url)) {
            return respData;
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();
        return getResponseWithClass(request, clazz);
    }

    public static <T> T doPostWithClass(String url, Map<String, String> params, Class<T> clazz) throws IOException {
        T respData = null;
        if (StringUtils.isBlank(url)) {
            return respData;
        }
        JSONObject jsonParam = new JSONObject();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                jsonParam.put(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body = RequestBody.create(JSON, jsonParam.toJSONString());
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .post(body)
                .build();
        return getResponseWithClass(request, clazz);
    }

    public static <T> T getResponseWithClass(Request request, Class<T> clazz) throws IOException {
        T respData = null;
        try (Response response = client.newCall(request).execute()) {
            if (response != null && response.code() == BusiStatus.SUCCESS.value()) {
                MediaType mediaType = response.body().contentType();
                if (mediaType.subtype().equals("x-protobuf")) {
                    return PbUtils.bytes2pb(response.body().bytes(), clazz);
                }
                String resp = response.body().string();
                if (isSuccessResp(resp)) {
                    JSONObject resJson = JSONObject.parseObject(resp);
                    if (clazz.equals(String.class)) {
                        respData = (T) resJson.getString("data");
                    } else {
                        respData = JSONObject.parseObject(resJson.getString("data"), clazz);
                    }
                }
            }
        }
        return respData;
    }

    private static boolean isSuccessResp(String resp) {
        if (StringUtils.isNotBlank(resp)) {
            JSONObject resJson = JSONObject.parseObject(resp);
            int result = resJson.getIntValue("result");
            return result == 0;
        }
        return false;
    }

    public static RequestBody genRequestBody(Map<String, String> map) {
        FormBody.Builder params = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(entry.getKey(), entry.getValue() + "");
        }
        FormBody formBody = params.build();
        return formBody;
    }

    public static String doPostWithBody(String url, RequestBody body) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new Exception("请求失败，code:" + response.code() + ",url:" + url);
        } else {
            String result = response.body() != null ? response.body().string() : "";
            return result;
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 1000; i++) {
            System.out.println(doGet("http://www.baidu.com"));
        }
    }
}
