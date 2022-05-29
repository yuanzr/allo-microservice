package com.dc.allo.task.test;

import com.dc.allo.rpc.api.utils.HttpUtils;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpResp;
import com.erban.main.proto.PbHttpResp.PbSignTodayStatusResp;
import com.erban.main.proto.PbHttpResp.PbUnViweFeedbackResp;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * description: PbSignTest
 *
 * @date: 2020年06月03日 18:02
 * @author: qinrenchuan
 */
@Slf4j
public class PbSignTest {

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
                        .setTicket("eyJhbGciOiJIUzI1NiJ9.eyJ0aWNrZXRfdHlwZSI6bnVsbCwidWlkIjo2MTAwNDMzNywidGlja2V0X2lkIjoiNzNlZGRkYjMtNWJlNC00MzAyLWJlMmItNTU0N2E0NmQzNzA0IiwiZXhwIjozNjAwLCJjbGllbnRfaWQiOiJlcmJhbi1jbGllbnQifQ.zI69ZqpTEa19E5TF7M_LSmOuxT8deMR8skUGelyXiaU")
                        .setUid("61004337")
                        .setRoomId(61004337)
                        .setTimestamp(System.currentTimeMillis());
        testGetSignStatusForTodayV2(builder);
    }

    /**
     * 签到状态查询
     * @param builder
     * @author qinrenchuan
     * @date 2020/6/3/0003 18:03
     */
    private static void testGetSignStatusForTodayV2(PbCommon.PbHttpBizReq.Builder builder) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/x-protobuf"),
                builder.build().toByteArray());
        Request request = new Request.Builder()
                // .url("http://gatewaybeta.allolike.com/allo-task-service/sign/pb/auth/getSignStatusV2")
                .url("http://localhost:30007/sign/pb/auth/getSignStatusV2")
                .post(body)
                .build();

        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            System.out.println("result: {}" + result.toString());
            System.out.println("=================================================================================");

            PbHttpResp.PbSignTodayStatusRespV2 resp =
                    PbHttpResp.PbSignTodayStatusRespV2.parseFrom(result.getData());
            System.out.println("signed: {}" + resp.getSigned());
            System.out.println("resp: {}" + resp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 签到状态查询
     * @param builder
     * @author qinrenchuan
     * @date 2020/6/3/0003 18:03
     */
    private static void testGetSignStatusForToday(PbCommon.PbHttpBizReq.Builder builder) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/x-protobuf"),
                builder.build().toByteArray());
        Request request = new Request.Builder()
                .url("http://localhost:30007/sign/pb/auth/getSignStatus")
                .post(body)
                .build();

        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            System.out.println("result: {}" + result.toString());
            System.out.println("=================================================================================");

            PbSignTodayStatusResp resp = PbSignTodayStatusResp.parseFrom(result.getData());
            System.out.println("signed: {}" + resp.getSigned());
            System.out.println("resp: {}" + resp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
