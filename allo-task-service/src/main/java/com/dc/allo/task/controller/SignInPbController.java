package com.dc.allo.task.controller;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.task.domain.resp.SignTodayStatusResp;
import com.dc.allo.task.service.SignInService;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: SignInPbController
 * @since 2020/06/15 22:07
 */
@Controller
@Slf4j
public class SignInPbController {

    @Autowired
    private SignInService signInService;

    /**
     * 签到状态查询
     * @param request
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/6/3/0003 17:35
     */
    /*@RequestMapping(value = "/sign/pb/auth/getSignStatus", produces = "application/x-protobuf")
    @ResponseBody
    public PbCommon.PbHttpBizResp getSignStatusForToday(@RequestBody PbCommon.PbHttpBizReq request) {

        PbCommon.PbHttpBizResp.Builder builder = null;
        try {
            // 公共请求参数
            log.info("getSignStatus uid:{}", request.getUid());

            Boolean signStatusForToday = signInService.getSignStatusForToday(
                    Long.valueOf(request.getUid()));

            SignTodayStatusResp statusResp = new SignTodayStatusResp();
            statusResp.setSigned(signStatusForToday);
            log.info("getSignStatus signVO: {}", statusResp);

            builder = PbUtils.genCommRespBaseBuilder(
                    PbCommon.Module.httpModule_VALUE,
                    PbHttpResp.PbSignTodayStatusResp.class.getName(), statusResp);
            builder.setCode(BusiStatus.SUCCESS.getCode());
            builder.setMessage(BusiStatus.SUCCESS.getReasonPhrase());
        }catch (Exception e) {
            log.error("getSignStatus failed", e);
            builder.setCode(BusiStatus.SERVER_BUSY.getCode());
            builder.setMessage(BusiStatus.SERVER_BUSY.getReasonPhrase());
        }
        return builder.build();
    }*/
}
