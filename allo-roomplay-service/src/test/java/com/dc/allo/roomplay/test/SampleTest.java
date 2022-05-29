package com.dc.allo.roomplay.test;

import com.dc.allo.common.component.aggregation.RedisAggregationQueue;
import com.dc.allo.common.component.delay.java.DelayQueueManager;
import com.dc.allo.common.component.delay.redis.DelayMessage;
import com.dc.allo.common.component.delay.redis.RedisDelayQueue;
import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.component.threadpool.DCCommonThreadPool;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.roomplay.Application;
import com.dc.allo.roomplay.service.pk.SampleService;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbSampleDemo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class SampleTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    DelayQueueManager delayQueueManager;

    @Autowired
    DCCommonThreadPool threadPool;

    @Autowired
    @Qualifier("pkDelayQueue")
    private RedisDelayQueue redisDelayQueue;

    @Autowired
    @Qualifier("pkAggregationQueue")
    RedisAggregationQueue redisAggregationQueue;

  /*  @Reference
    DcSysConfigService configService;*/

    @Test
    public void testSetUser(){
        sampleService.setUser(1111);
    }

    @Test
    public void testGetUser(){
        log.info("@@@@@@@@@@@@@@@" + sampleService.getUser(1111));
    }

    @Test
    public void addSample(){
        Sample sample = new Sample();
        sample.setUsername("test111");
        sample.setPassword("test111");
        sample.setPhone("12345678");
        sample.setHeadimg("headimg");
        sampleService.addAdmin(sample);
    }

    @Test
    public void querySamples(){
        log.info(JsonUtils.toJson(sampleService.querySamples()));
    }

    @Test
    public void updateSamplePhone(){
        sampleService.updateSamplePhone(35, "2222222222");
    }

    @Test
    public void pageAdmins(){
        log.info(JsonUtils.toJson(sampleService.pageAdmins(0,9)));
        log.info(JsonUtils.toJson(sampleService.pageAdmins(10, 19)));
    }

    @Test
    public void kafkaSender(){
        Sample sample = new Sample();
        sample.setUsername("kafka");
        sample.setPassword("sadfasdfasdf");
        sample.setHeadimg("***(**23423)");
        kafkaSender.send(KafkaTopic.RoomPlay.DC_SAMPLE_TEST, KafkaTopic.EventType.SAMPLE_EVENT, sample);
    }

    @Test
    public void testLocalDelayQueue(){
        delayQueueManager.put(() -> {
            log.info("this is local delay queue excute");
        }, 15, TimeUnit.SECONDS);
        int i=0;
        while (true){
            log.info("wait delay queue"+i);
            try {
                Thread.sleep(1000);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i == 15){
                break;
            }
        }
    }

    @Test
    public void testThreadPool(){
        try {
            threadPool.submit("sampleTestThreadPool", () -> {
                log.info("asyn thread pool excute");
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRedisDelayQueue(){
        Sample sample = new Sample();
        sample.setUsername("redisDelaySample");
        sample.setPassword("asdfasdfasdf");
        sample.setPhone("21231231");
        sample.setCreateTime(System.currentTimeMillis() + "");

        DelayMessage<Sample> message = new DelayMessage<>();
        message.setData(sample);
        message.setDuplicateKey(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        message.setExpireTime(now+5000);

        redisDelayQueue.enQueue(message);

        int i=0;
        while (true){
            log.info("wait redis delay queue"+i);
            try {
                Thread.sleep(1000);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i == 15){
                break;
            }
        }
    }

    @Test
    public void testHttpPbCoverter(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf");
        PbSampleDemo.PbSamplesReq bizReq = PbSampleDemo.PbSamplesReq.newBuilder().setOffset(0).setPageSize(5).build();
        builder.setData(bizReq.toByteString());
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
        String authUrl = "http://localhost:30000/allo-roomplay-service/auth/testGateway";
        String url = "http://localhost:30000/allo-roomplay-service/testGateway";
        String url1 = "http://localhost:30001/testGateway";
        StopWatch st = new StopWatch("auth");
        st.start("getaway auth filter and route");
        int num = 2;
        for(int i=0;i<num;i++) {
//            Request request = new Request.Builder().url(authUrl).post(body).build();
//            try {
//                PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
//            log.info(result.toString());
//                PbSampleDemo.PbSamplesResp samplesResp = PbSampleDemo.PbSamplesResp.parseFrom(result.getData());
////            log.info("@@@@@@:   "+samplesResp.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        st.stop();
        st.start("gateway route");
        for(int i=0;i<num;i++) {
//            Request request = new Request.Builder().url(url).post(body).build();
//            try {
//                PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
////            log.info(result.toString());
//                PbSampleDemo.PbSamplesResp samplesResp = PbSampleDemo.PbSamplesResp.parseFrom(result.getData());
////            log.info(samplesResp.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        st.stop();
        st.start("no gateway");
        for(int i=0;i<num;i++) {
            Request request = new Request.Builder().url(url1).post(body).build();
            try {
                PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
//                log.info(result.toString());
                PbSampleDemo.PbSamplesResp samplesResp = PbSampleDemo.PbSamplesResp.parseFrom(result.getData());
//                log.info("########:   "+samplesResp.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        st.stop();
        log.info(st.prettyPrint());
    }

    @Test
    public void testSysconfig(){
       /* AlloResp<SysConfig> resp = configService.getSysConfById(Constant.SysConfId.Auto_Log_Methods);
        log.info("@@@@@@@@@@@@@@@@@@@ :"+resp.toString());*/
    }

    @Test
    public void testRedisAggregationQueue(){
        Sample sample = new Sample();
        sample.setUsername("redisAggregationSample");
        sample.setPassword("asdfasdfasdf");
        sample.setPhone("21231231");
        sample.setCreateTime(System.currentTimeMillis() + "");

        int i=0;
        while (true){
            log.info("wait redis aggregation queue"+i);
            try {
                Thread.sleep(1000);
                sample.setId(i);
                redisAggregationQueue.enQueue(sample);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i == 9){
                break;
            }
        }

    }

    @Test
    public void testPbBytes(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf");
        PbSampleDemo.PbSamplesReq bizReq = PbSampleDemo.PbSamplesReq.newBuilder().setOffset(0).setPageSize(5).build();
        builder.setData(bizReq.toByteString());
        String json = JsonUtils.toJson(builder.build().toByteArray());
        log.info("@@@@@@@@@@@@ pbBytes: "+json);
        byte[] bytes = JsonUtils.fromJson(json, new TypeReference<byte[]>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        try {
            PbCommon.PbHttpBizReq req = PbCommon.PbHttpBizReq.parseFrom(bytes);
            log.info("req +++++++++ "+req.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
