package com.dc.allo.biznotice.test;

import com.dc.allo.rpc.api.utils.HttpUtils;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpReq.PbCommonBannerReq;
import com.erban.main.proto.PbHttpResp.PbCommonBannerResp;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * description: BannerControllerTest
 *
 * @date: 2020年05月08日 19:36
 * @author: qinrenchuan
 */
@Slf4j
public class BannerControllerTest {

    public static void main(String[] args) {
        PbCommon.PbHttpBizReq.Builder builder =
                PbCommon.PbHttpBizReq.newBuilder()
                        .setOs("android")
                        .setOsVersion("3.1.1")
                        .setNetWorkStatus("200")
                        .setChannel("4")
                        .setMachine("0001222")
                        .setDeviceId("99000000000123")
                        .setAppVersion("3.4.6")
                        .setAppName("allo")
                        .setTicket("111111111111111111111111111111111")
                        .setUid("61004350")
                        .setRoomId(61004350)
                        .setTimestamp(System.currentTimeMillis());
        testQueryCommonBanner(builder);
    }

    private static void testQueryCommonBanner(PbCommon.PbHttpBizReq.Builder builder) {
        PbCommonBannerReq commonBannerReq = PbCommonBannerReq.newBuilder()
                .setBannerKeys("9,10")
                .build();
        builder.setData(commonBannerReq.toByteString());

        RequestBody body = RequestBody.create(
                MediaType.parse("application/x-protobuf"),
                builder.build().toByteArray());
        Request request = new Request.Builder()
                //.url("http://localhost:30008/pb/banner/list")
                .url("https://gatewaybeta.allolike.com/allo-biz-notice/pb/auth/banner/list")
                .post(body)
                .build();
        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            System.out.println("result: {}" + result.toString());
            System.out.println("=================================================================================");

            PbCommonBannerResp pbCommonBannerResp = PbCommonBannerResp.parseFrom(result.getData());
            System.out.println("pbCommonBannerResp: {}" + pbCommonBannerResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
