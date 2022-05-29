package com.dc.allo.activity;

import com.dc.allo.activity.service.CamelGameService;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpReq;
import com.erban.main.proto.PbHttpResp;
import com.erban.main.proto.PbSampleDemo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by zhangzhenjun on 2020/6/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class CamelGameTest {

    @Autowired
    CamelGameService camelGameService;

    @Test
    public void testGetCamelGame(){
        log.info(JsonUtils.toJson(camelGameService.getBetGameRound(90, 61004336)));
    }

    @Test
    public void testPbGetActInfo(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(61004336 + "");
        PbHttpReq.PbBetGameRoundInfoReq bizReq = PbHttpReq.PbBetGameRoundInfoReq.newBuilder().setActId(1).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
//        String url = "http://gatewaybeta.allolike.com/allo-activity-service/camelGame/pb/getActInfo";
//        String url = "http://localhost:30005/camelGame/pb/getActInfo";
        String url = "http://gateway-sys.allolike.com/allo-activity-service/camelGame/pb/getActInfo";

        Request request = new Request.Builder().url(url).post(body).build();
        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            log.info(result.toString());
            PbHttpResp.PbBetActInfoResp samplesResp = PbHttpResp.PbBetActInfoResp.parseFrom(result.getData());
            log.info(samplesResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPbGetCamelGame(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(61004336 + "");
        PbHttpReq.PbBetGameRoundInfoReq bizReq = PbHttpReq.PbBetGameRoundInfoReq.newBuilder().setActId(1).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
//        String url = "http://gatewaybeta.allolike.com/allo-activity-service/camelGame/pb/getCurGameRound";
//        String url = "http://localhost:30005/camelGame/pb/getCurGameRound";
        String url = "http://gateway-sys.allolike.com/allo-activity-service/camelGame/pb/getCurGameRound";

        Request request = new Request.Builder().url(url).post(body).build();
        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            log.info(result.toString());
            PbHttpResp.PbBetGameRoundInfoResp samplesResp = PbHttpResp.PbBetGameRoundInfoResp.parseFrom(result.getData());
            log.info(samplesResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPbGetCamelGameByGameId(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(61004336 + "");
        PbHttpReq.PbBetGetGameRoundReq bizReq = PbHttpReq.PbBetGetGameRoundReq.newBuilder().setGameId(90L).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
        String url = "http://gatewaybeta.allolike.com/allo-activity-service/camelGame/pb/getGameRound";
//        String url = "http://localhost:30005/camelGame/pb/getGameRound";

        Request request = new Request.Builder().url(url).post(body).build();
        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            log.info(result.toString());
            PbHttpResp.PbBetGetGameRoundResp samplesResp = PbHttpResp.PbBetGetGameRoundResp.parseFrom(result.getData());
            log.info(samplesResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPbPageCamelGame(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(61004336 + "");
        PbHttpReq.PbBetPageGameRoundReq bizReq = PbHttpReq.PbBetPageGameRoundReq.newBuilder().setActId(1).setId(0).setPageSize(2).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
//        String url = "http://gatewaybeta.allolike.com/allo-activity-service/camelGame/pb/getCurGameRound";
        String url = "http://localhost:30005/camelGame/pb/pageGameRound";

        Request request = new Request.Builder().url(url).post(body).build();
        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            log.info(result.toString());
            PbHttpResp.PbBetPageGameRoundResp samplesResp = PbHttpResp.PbBetPageGameRoundResp.parseFrom(result.getData());
            log.info(samplesResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPbPageBetResult(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(7009543 + "");
        PbHttpReq.PbBetPageGameRoundReq bizReq = PbHttpReq.PbBetPageGameRoundReq.newBuilder().setActId(1).setId(0).setPageSize(2).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
//        String url = "http://gatewaybeta.allolike.com/allo-activity-service/camelGame/pb/pageBetResult";
//        String url = "http://localhost:30005/camelGame/pb/pageBetResult";
        String url = "http://gateway-sys.allolike.com/allo-activity-service/camelGame/pb/pageBetResult";

        Request request = new Request.Builder().url(url).post(body).build();
        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            log.info(result.toString());
            PbHttpResp.PbBetPageResultResp samplesResp = PbHttpResp.PbBetPageResultResp.parseFrom(result.getData());
            log.info(samplesResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPbBetCamelGame(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(61004336 + "");
        PbHttpReq.PbBetReq bizReq = PbHttpReq.PbBetReq.newBuilder().setActId(1).setSpiritId(8).setAmount(100).setGameId(85).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
        String url = "http://localhost:30005/camelGame/betGameRound";

        Request request = new Request.Builder().url(url).post(body).build();
        try {
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
            log.info(result.toString());
            PbHttpResp.PbBetResp samplesResp = PbHttpResp.PbBetResp.parseFrom(result.getData());
            log.info(samplesResp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(61004336 + "");
        PbHttpReq.PbBetGameRoundInfoReq bizReq = PbHttpReq.PbBetGameRoundInfoReq.newBuilder().setActId(1).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
        String url = "http://gateway.allolike.com/allo-activity-service/camelGame/pb/getCurGameRound";
//        String url = "http://localhost:30005/camelGame/pb/getCurGameRound";

        for(int i=0;i<20;i++) {
            Request request = new Request.Builder().url(url).post(body).build();
            try {
                PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
                System.out.println(result.toString());
                PbHttpResp.PbBetGameRoundInfoResp samplesResp = PbHttpResp.PbBetGameRoundInfoResp.parseFrom(result.getData());
                System.out.println(samplesResp.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
//        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf").setUid(61004336 + "");
//        PbHttpReq.PbBetReq bizReq = PbHttpReq.PbBetReq.newBuilder().setActId(1).setAmount(100).setGameId(18075).setSpiritId(4).build();
//        builder.setData(bizReq.toByteString());
//        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
//        String url = "http://gatewaybeta.allolike.com/allo-activity-service/camelGame/pb/betGameRound";
////        String url = "http://localhost:30005/camelGame/pb/getCurGameRound";
//
//        Request request = new Request.Builder().url(url).post(body).build();
//        try {
//            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
//            log.info(result.toString());
//            PbHttpResp.PbBetResp samplesResp = PbHttpResp.PbBetResp.parseFrom(result.getData());
//            System.out.println(samplesResp.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
