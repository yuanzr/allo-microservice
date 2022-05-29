package com.dc.allo.user.test;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.constants.Constant.SysConfId;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.rpc.domain.sysconf.SysConf;
import com.dc.allo.user.UserServiceApplication;
import com.dc.allo.user.service.sysconf.SysConfService;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpReq;
import com.erban.main.proto.PbHttpResp;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * description: SysConfServiceTest
 *
 * @date: 2020年04月30日 9:55
 * @author: qinrenchuan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Slf4j
public class SysConfServiceTest {

    @Autowired
    SysConfService sysConfService;

    @Test
    public void testGetSysConfig(){
        SysConf config = sysConfService.getSysConf(Constant.SysConfId.Auto_Log_Methods);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@:  "+config.toString());
    }

    @Test
    public void testGetSysConfValueById() {
        String sysConfVal = sysConfService.getSysConfValueById(SysConfId.Auto_Log_Methods);
        log.info(sysConfVal);
    }

    @Test
    public void testPbQueryConfigVals(){
        PbCommon.PbHttpBizReq.Builder builder = PbCommon.PbHttpBizReq.newBuilder();
        builder.setAppName("allo").setAppVersion("1.1.1").setChannel("ios").setTicket("asdfasdfasdfasdfasdf");
        PbHttpReq.PbAppConfigReq bizReq = PbHttpReq.PbAppConfigReq.newBuilder()
                .addConfigKeys("activity_end_time")
                .addConfigKeys("activity_start_time")
                .build();
        builder.setData(bizReq.toByteString());
        String json = JsonUtils.toJson(builder.build().toByteArray());
        log.info("@@@@@@@@@@@@ pbBytes: " + json);
        RequestBody body = RequestBody.create(MediaType.parse("application/x-protobuf"), builder.build().toByteArray());
        String url = "http://gatewaybeta.allolike.com/allo-user-service/sysconf/pb/queryConfigVals";
        try {
            Request request = new Request.Builder().url(url).post(body).build();
            PbCommon.PbHttpBizResp result = HttpUtils.getResponseWithClass(request, PbCommon.PbHttpBizResp.class);
//                log.info(result.toString());
            PbHttpResp.PbAppConfigResp resp = PbHttpResp.PbAppConfigResp.parseFrom(result.getData());
            log.info("########:   "+resp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
