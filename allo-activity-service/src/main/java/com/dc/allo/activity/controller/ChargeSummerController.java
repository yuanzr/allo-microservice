package com.dc.allo.activity.controller;

import com.dc.allo.activity.domain.vo.recharge.FirstRechargePrizeVO;
import com.dc.allo.activity.domain.vo.recharge.PrizeRankActItem;
import com.dc.allo.activity.service.ChargeSummerService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.PrizeTypeEnum;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ChargeSummerController
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/10/11:53
 */
@Slf4j
@RestController
@RequestMapping("/activity/charge/summmer")
public class ChargeSummerController {

    @Autowired
    private ChargeSummerService chargeSummerService;

    /**
     * 查询夏日充值奖品列表
     * @param uid
     * @return
     */
    @RequestMapping(value = "/h5/prizes/list", method = RequestMethod.GET)
    public AlloResp getChargeSummerPrizeList(Long uid) {
        try {
            AlloResp result = chargeSummerService.getChargeSummerPrizeList(uid);
            return result;
        } catch (Exception e) {
            log.error("getChargeSummerPrizeList error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }


    /**
     * 领取奖励
     * @param uid
     * @param rankDateType  1首次 3累充
     * @param jobName       任务名
     * @return
     */
    @RequestMapping(value = "/h5/prizes/get", method = RequestMethod.GET)
    public AlloResp getPrizeAward(Long uid, Byte rankDateType,String jobName) {
        try {
            AlloResp result = chargeSummerService.getPrizeAward(uid,rankDateType,jobName);
            return result;
        } catch (Exception e) {
            log.error("getChargeSummerPrizeList error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 领取奖励
     * @param uid
     * @param rankDateType  1首次 3累充
     * @param jobName       任务名
     * @return
     */
    @RequestMapping(value = "/h5/auth/prizes/get", method = RequestMethod.GET)
    public AlloResp getPrizeAwardAuth(Long uid, Byte rankDateType,String jobName) {
        try {
            AlloResp result = chargeSummerService.getPrizeAward(uid,rankDateType,jobName);
            return result;
        } catch (Exception e) {
            log.error("getChargeSummerPrizeList error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
