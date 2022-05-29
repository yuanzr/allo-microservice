package com.dc.allo.roomplay.controller;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.roomplay.service.pk.SampleService;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import com.dc.allo.rpc.proto.roomplay.SamplesResp;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbSampleDemo;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/24.
 */
@Controller
public class TestPbCoverterController {

    @Autowired
    SampleService sampleService;

    @RequestMapping(value = "/testPbConverter", produces = "application/x-protobuf")
    @ResponseBody
    public PbCommon.PbHttpBizResp testPbCoverter(@RequestBody PbCommon.PbHttpBizReq request){
        PbCommon.PbHttpBizResp.Builder builder = null;
        try {
            PbSampleDemo.PbSamplesReq req =  PbSampleDemo.PbSamplesReq.parseFrom(request.getData());
            List<Sample> samples = sampleService.pageAdmins(req.getOffset(), req.getPageSize());
            SamplesResp resp = new SamplesResp();
            resp.setSamples(samples);
            builder = PbUtils.genCommRespBaseBuilder(PbCommon.Module.httpModule_VALUE, PbSampleDemo.PbSamplesResp.class.getName(),resp);
            builder.setCode(BusiStatus.SUCCESS.value());
        } catch (InvalidProtocolBufferException e) {
            builder.setCode(BusiStatus.SERVERERROR.value());
            e.printStackTrace();
        }
        return builder.build();
    }

    @RequestMapping(value = "/auth/testGateway", produces = "application/x-protobuf")
    @ResponseBody
    public PbCommon.PbHttpBizResp testAuthGateWay(@RequestBody PbCommon.PbHttpBizReq request){
        PbCommon.PbHttpBizResp.Builder builder = null;
        try {
            PbSampleDemo.PbSamplesReq req =  PbSampleDemo.PbSamplesReq.parseFrom(request.getData());
            List<Sample> samples = sampleService.pageAdmins(req.getOffset(), req.getPageSize());
            SamplesResp resp = new SamplesResp();
            resp.setSamples(samples);
            builder = PbUtils.genCommRespBaseBuilder(PbCommon.Module.httpModule_VALUE, PbSampleDemo.PbSamplesResp.class.getName(),resp);
            builder.setCode(BusiStatus.SUCCESS.value());
        } catch (InvalidProtocolBufferException e) {
            builder.setCode(BusiStatus.SERVERERROR.value());
            e.printStackTrace();
        }
        return builder.build();
    }

    @RequestMapping(value = "/testGateway", produces = "application/x-protobuf")
    @ResponseBody
    public PbCommon.PbHttpBizResp testGateWay(@RequestBody PbCommon.PbHttpBizReq request){
        PbCommon.PbHttpBizResp.Builder builder = null;
        try {
            PbSampleDemo.PbSamplesReq req =  PbSampleDemo.PbSamplesReq.parseFrom(request.getData());
            List<Sample> samples = sampleService.pageAdmins(req.getOffset(), req.getPageSize());
            SamplesResp resp = new SamplesResp();
            resp.setSamples(samples);
            builder = PbUtils.genCommRespBaseBuilder(PbCommon.Module.httpModule_VALUE, PbSampleDemo.PbSamplesResp.class.getName(), resp);
            builder.setCode(BusiStatus.SUCCESS.value());
        } catch (InvalidProtocolBufferException e) {
            builder.setCode(BusiStatus.SERVERERROR.value());
            e.printStackTrace();
        }
        return builder.build();
    }
}
