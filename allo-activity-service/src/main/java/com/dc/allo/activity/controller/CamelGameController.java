package com.dc.allo.activity.controller;

import com.dc.allo.activity.domain.vo.camel.BetGameRoundInfoResp;
import com.dc.allo.activity.domain.vo.camel.BetResultResp;
import com.dc.allo.activity.service.CamelGameService;
import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.domain.Check;
import com.dc.allo.rpc.domain.bet.BetGameRoundInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbHttpReq;
import com.erban.main.proto.PbHttpResp;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * Created by zhangzhenjun on 2020/6/15.
 */
@RestController
@Slf4j
@RequestMapping("/camelGame/pb")
public class CamelGameController extends BaseController {

    @Autowired
    CamelGameService camelGameService;

    @RequestMapping(value = "/getActInfo", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE, clzFullName = "com.erban.main.proto.PbHttpResp$PbBetActInfoResp", paramClassName = "com.erban.main.proto.PbHttpReq$PbBetActInfoReq")
    public AlloResp getActInfo(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        try {
            if (pbHttpBizReq.getData() == null) {
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            PbHttpReq.PbBetActInfoReq req = PbHttpReq.PbBetActInfoReq.parseFrom(pbHttpBizReq.getData());
            if(req.getActId()<=0){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            return camelGameService.getActInfo(req.getActId());
        } catch (InvalidProtocolBufferException e) {
            log.info(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/getCurGameRound", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE, clzFullName = "com.erban.main.proto.PbHttpResp$PbBetGameRoundInfoResp", paramClassName = "com.erban.main.proto.PbHttpReq$PbBetGameRoundInfoReq")
    public AlloResp getCurGameRound(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        try {
            if (pbHttpBizReq.getData() == null) {
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            long uid = 0;
            if(StringUtils.isNotBlank(pbHttpBizReq.getUid())){
                uid = Long.valueOf(pbHttpBizReq.getUid());
            }
            PbHttpReq.PbBetGameRoundInfoReq req = PbHttpReq.PbBetGameRoundInfoReq.parseFrom(pbHttpBizReq.getData());
            BetGameRoundInfo gameInfo = camelGameService.getCamelGameRound(req.getActId(), uid);
            BetGameRoundInfoResp resp = new BetGameRoundInfoResp();
            resp.setGameRoundInfo(gameInfo);
            return AlloResp.success(resp);
        } catch (InvalidProtocolBufferException e) {
            log.info(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/getGameRound", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE, clzFullName = "com.erban.main.proto.PbHttpResp$PbBetGetGameRoundResp", paramClassName = "com.erban.main.proto.PbHttpReq$PbBetGetGameRoundReq")
    public AlloResp getGameRound(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        try {
            if (pbHttpBizReq.getData() == null) {
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            long uid = 0;
            if(StringUtils.isNotBlank(pbHttpBizReq.getUid())){
                uid = Long.valueOf(pbHttpBizReq.getUid());
            }
            PbHttpReq.PbBetGetGameRoundReq req = PbHttpReq.PbBetGetGameRoundReq.parseFrom(pbHttpBizReq.getData());
            return camelGameService.getBetGameRound(req.getGameId(), uid);
        } catch (InvalidProtocolBufferException e) {
            log.info(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/auth/betGameRound", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE, clzFullName = "com.erban.main.proto.PbHttpResp$PbBetResp", paramClassName = "com.erban.main.proto.PbHttpReq$PbBetReq")
    public AlloResp betGameRound(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        try {
            PbHttpReq.PbBetReq req = PbHttpReq.PbBetReq.parseFrom(pbHttpBizReq.getData());
            AlloResp<Long> code = camelGameService.bet(req.getActId(), req.getGameId(), Long.valueOf(pbHttpBizReq.getUid()), req.getSpiritId(), req.getAmount());
            BetResultResp resp = new BetResultResp();
            if (code !=null ) {
                if(code.getCode() == BusiStatus.SUCCESS.value()) {
                    resp.setWallet(code.getData());
                }else{
                    return AlloResp.failed(code.getCode(),code.getMessage(),null);
                }
            }else {
                return AlloResp.failed(BusiStatus.SERVERERROR);
            }
            return AlloResp.success(resp);
        } catch (InvalidProtocolBufferException e) {
            log.info(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/pageBetResult", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE, clzFullName = "com.erban.main.proto.PbHttpResp$PbBetPageResultResp", paramClassName = "com.erban.main.proto.PbHttpReq$PbPbBetPageResultReq")
    public AlloResp pageBetResult(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        if(StringUtils.isBlank(pbHttpBizReq.getUid())){
            return AlloResp.failed(BusiStatus.PARAMERROR);
        }
        try {
            PbHttpReq.PbBetPageResultReq req = PbHttpReq.PbBetPageResultReq.parseFrom(pbHttpBizReq.getData());
            return camelGameService.pageResultInfo(req.getActId(), req.getId(),Long.valueOf(pbHttpBizReq.getUid()), req.getSuffix(),req.getPageSize());
        } catch (InvalidProtocolBufferException e) {
            log.info(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/pageGameRound", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE, clzFullName = "com.erban.main.proto.PbHttpResp$PbBetPageGameRoundResp", paramClassName = "com.erban.main.proto.PbHttpReq$PbPbBetPageGameRoundReq")
    public AlloResp pageGameRound(HttpServletRequest request) {
        PbCommon.PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        if(StringUtils.isBlank(pbHttpBizReq.getUid())){
            return AlloResp.failed(BusiStatus.PARAMERROR);
        }
        try {
            PbHttpReq.PbBetPageGameRoundReq req = PbHttpReq.PbBetPageGameRoundReq.parseFrom(pbHttpBizReq.getData());
            return camelGameService.pageGameRound(req.getActId(), req.getId(), req.getPageSize());
        } catch (InvalidProtocolBufferException e) {
            log.info(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

//    @RequestMapping(value = "/getCurGameRound", produces = "application/x-protobuf")
//    @ResponseBody
//    public PbCommon.PbHttpBizResp getCurGameRound(@RequestBody PbCommon.PbHttpBizReq request){
//        PbCommon.PbHttpBizResp.Builder builder = null;
//        try {
//            PbHttpReq.PbBetGameRoundInfoReq req =  PbHttpReq.PbBetGameRoundInfoReq.parseFrom(request.getData());
//            BetGameRoundInfo gameInfo = camelGameService.getCamelGameRound(req.getActId(), Long.valueOf(request.getUid()));
//            BetGameRoundInfoResp resp = new BetGameRoundInfoResp();
//            resp.setGameRoundInfo(gameInfo);
//            builder = PbUtils.genCommRespBaseBuilder(PbCommon.Module.httpModule_VALUE, PbHttpResp.PbBetGameRoundInfoResp.class.getName(), resp);
//            builder.setCode(BusiStatus.SUCCESS.value());
//        } catch (InvalidProtocolBufferException e) {
//            builder.setCode(BusiStatus.SERVERERROR.value());
//            log.info(e.getMessage(),e);
//        }
//        return builder.build();
//    }

//    @RequestMapping(value = "/betGameRound", produces = "application/x-protobuf")
//    @ResponseBody
//    public PbCommon.PbHttpBizResp betGameRound(@RequestBody PbCommon.PbHttpBizReq request) {
//        PbCommon.PbHttpBizResp.Builder builder = null;
//        try {
//            PbHttpReq.PbBetReq req = PbHttpReq.PbBetReq.parseFrom(request.getData());
//            long code = camelGameService.bet(req.getActId(), req.getGameId(), Long.valueOf(request.getUid()), req.getSpiritId(), req.getAmount());
//            BetResultResp resp = new BetResultResp();
//            if (code >= 0) {
//                resp.setWallet(code);
//            }
//            builder = PbUtils.genCommRespBaseBuilder(PbCommon.Module.httpModule_VALUE, PbHttpResp.PbBetResp.class.getName(), resp);
//            builder.setCode(Integer.valueOf(String.valueOf(code)));
//            if (code >= 0) {
//                builder.setCode(BusiStatus.SUCCESS.value());
//            }
//        } catch (InvalidProtocolBufferException e) {
//            builder.setCode(BusiStatus.SERVERERROR.value());
//            log.info(e.getMessage(), e);
//        }
//        return builder.build();
//    }
}
