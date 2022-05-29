package com.dc.allo.task.controller;

import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.PrizeTypeEnum;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.task.constant.TaskConstant;
import com.dc.allo.task.domain.entity.PrizeRankActItem;
import com.dc.allo.task.domain.resp.SignTodayStatusResp;
import com.dc.allo.task.domain.resp.SignTodayStatusRespV2;
import com.dc.allo.task.domain.vo.SignRecordPrizeVO;
import com.dc.allo.task.domain.vo.SignRecordVO;
import com.dc.allo.task.domain.vo.SignVO;
import com.dc.allo.task.domain.vo.TaskPrizeDetailVO;
import com.dc.allo.task.service.SignInService;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbCommon.PbHttpBizReq;
import com.erban.main.proto.PbCommon.PbHttpBizResp;
import com.erban.main.proto.PbHttpResp;
import com.erban.main.proto.PbHttpResp.PbSignTodayStatusResp;
import com.erban.main.proto.PbHttpResp.PbSignTodayStatusResp.Builder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: SingInController
 *
 * @date: 2020年05月26日 17:43
 * @author: qinrenchuan
 */
@RestController
@Slf4j
public class SingInController extends BaseController {

    @Autowired
    private SignInService signInService;



    /**
     * 签到
     * @param uid
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/5/26/0026 17:44
     */
    @PostMapping("/sign/h5/auth/task/sign")
    public AlloResp doSignIn(Long uid) {
        try {
            List<TaskPrizeDetailVO> prizeVOS = new ArrayList<>();
            List<PrizeRankActItem> prizeRankActItems = signInService.signIn(uid);
            convertPrize(prizeRankActItems, prizeVOS);
            return AlloResp.success(prizeVOS);
        } catch (Exception e) {
            log.error("doSignIn error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 签到记录
     * @param uid
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/5/26/0026 17:49
     */
    @GetMapping("/sign/h5/auth/task/signRecord")
    public AlloResp signRecord(Long uid) {
        try {
            SignRecordVO signRecordVO = signInService.signRecord(uid);
            fillPrize(signRecordVO);

            return AlloResp.success(signRecordVO);
        } catch (Exception e) {
            log.error("signRecord error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 签到状态查询： 如果返回data全为protobuf的默认值的时候，data为空，前端无法正常解析
     * @param request
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/6/3/0003 17:35
     * @deprecated 该接口遗弃， 使用 {@link SingInController#getSignStatusForTodayV2}
     */
    @RequestMapping(value = "/sign/pb/auth/getSignStatus", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,
            clzFullName = "com.erban.main.proto.PbHttpResp$PbSignTodayStatusResp",
            paramClassName = "com.erban.main.proto.PbCommon$PbHttpBizReq")
    @Deprecated
    public AlloResp getSignStatusForToday(HttpServletRequest request) {
        try {
            // 公共请求参数
            PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
            log.info("getSignStatus uid:{}", pbHttpBizReq.getUid());

            Boolean signStatusForToday = signInService.getSignStatusForToday(
                    Long.valueOf(pbHttpBizReq.getUid()));
            SignTodayStatusResp statusResp = new SignTodayStatusResp();
            statusResp.setSigned(signStatusForToday);
            log.info("getSignStatus signVO: {}", statusResp);

            return AlloResp.success(statusResp);
        }catch (Exception e) {
            log.error("getSignStatus failed", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 签到状态查询: 增加字段处理protobuf data全为默认值时返回我空的问题
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/8/10/0010 15:02
     */
    @RequestMapping(value = "/sign/pb/auth/getSignStatusV2", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,
            clzFullName = "com.erban.main.proto.PbHttpResp$PbSignTodayStatusRespV2",
            paramClassName = "com.erban.main.proto.PbCommon$PbHttpBizReq")
    public AlloResp getSignStatusForTodayV2(HttpServletRequest request) {
        try {
            // 公共请求参数
            PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);

            Long uid = Long.valueOf(pbHttpBizReq.getUid());
            log.info("getSignStatusV2 uid:{}", uid);

            Boolean signStatusForToday = signInService.getSignStatusForToday(uid);
            SignTodayStatusRespV2 statusResp = new SignTodayStatusRespV2();
            if (signStatusForToday) {
                statusResp.setSigned(TaskConstant.SignStatus.SIGNED);
            } else {
                statusResp.setSigned(TaskConstant.SignStatus.NOT_SIGNED);
            }
            //statusResp.setUid(uid);
            log.info("getSignStatusV2 signVO: {}", statusResp);

            return AlloResp.success(statusResp);
        }catch (Exception e) {
            log.error("getSignStatusV2 failed", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }




    /**
     * 填充奖励数据
     * @param signRecordVO
     * @return void
     * @author qinrenchuan
     * @date 2020/5/27/0027 17:07
     */
    private void fillPrize(SignRecordVO signRecordVO) {
        if (signRecordVO == null) {
            return;
        }
        List<SignRecordPrizeVO> signRecordPrizeVOS = signRecordVO.getSignRecordPrizeVOS();
        if (signRecordPrizeVOS == null || signRecordPrizeVOS.size() == 0) {
            return;
        }

        for (SignRecordPrizeVO signRecordPrizeVO : signRecordPrizeVOS) {
            List<PrizeRankActItem> prizeRankActItems = signRecordPrizeVO.getPrizeRankActItems();
            List<TaskPrizeDetailVO> prizeDetailVOS = new ArrayList<>();
            convertPrize(prizeRankActItems, prizeDetailVOS);
            signRecordPrizeVO.setPrizeRankActItems(new ArrayList<>());

            signRecordPrizeVO.setPrizeVOS(prizeDetailVOS);
        }

    }

    /**
     * 奖品类型转换
     * @param prizeRankActItems
     * @param prizeDetailVOS
     * @author qinrenchuan
     * @date 2020/5/27/0027 17:07
     */
    private void convertPrize(List<PrizeRankActItem> prizeRankActItems, List<TaskPrizeDetailVO> prizeDetailVOS) {
        if (prizeRankActItems != null && prizeRankActItems.size() > 0) {
            for (PrizeRankActItem prizeItem : prizeRankActItems) {
                TaskPrizeDetailVO prizeVO = new TaskPrizeDetailVO();
                prizeVO.setPrizeId(prizeItem.getActPrizeId());
                prizeVO.setPrizeType(prizeItem.getPrizeType());
                prizeVO.setPrizePic(prizeItem.getPrizeImgUrl());
                prizeVO.setUnit(prizeItem.getTimeUnit());
                if (PrizeTypeEnum.coins.getValue() == prizeItem.getPrizeType().intValue() ||
                        PrizeTypeEnum.gift.getValue() == prizeItem.getPrizeType().intValue()) {
                    prizeVO.setPrizeNum(prizeItem.getPrizeValue());
                } else {
                    prizeVO.setPrizeNum(prizeItem.getTimeNum());
                }
                prizeVO.setPrizeName(getMessage(prizeItem.getPrizeName()));
                prizeDetailVOS.add(prizeVO);
            }
        }
    }
}
