package com.dc.allo.user.controller;

import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import com.dc.allo.rpc.domain.user.sysconf.SysconfResp;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.rpc.proto.roomplay.SamplesResp;
import com.dc.allo.user.service.sysconf.SysConfService;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpReq;
import com.erban.main.proto.PbHttpResp;
import com.erban.main.proto.PbSampleDemo;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Slf4j
@RestController
@RequestMapping("/sysconf")
public class SysconfController extends BaseController{

    @Autowired
    SysConfService sysConfService;

    @RequestMapping(value = "/pb/queryConfigVals", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE, clzFullName = "com.erban.main.proto.PbHttpResp$PbAppConfigResp", paramClassName = "com.erban.main.proto.PbHttpReq$PbAppConfigReq")
    public AlloResp queryConfigVals(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        try {
            if (pbHttpBizReq.getData() == null) {
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            PbHttpReq.PbAppConfigReq req = PbHttpReq.PbAppConfigReq.parseFrom(pbHttpBizReq.getData());
            SysconfResp resp = new SysconfResp();
            if(req !=null){
                List<String> ids = req.getConfigKeysList();
                Map<String,String> map = sysConfService.queryConfigVals(ids);
                resp.setConfigs(map);
            }
            return AlloResp.success(resp);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"server busy",null);
        }
    }
}
