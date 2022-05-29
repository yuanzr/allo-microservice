package com.dc.allo.activity.controller;

import com.dc.allo.activity.service.ActChargeCamelService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/activity/charge/camel")
public class ActChargeCamelController {
    @Autowired
    private ActChargeCamelService actChargeCamelService;

    @GetMapping(value = "/h5/auth/reward/get")
    public AlloResp getActChargeCamelRewardAuth(@RequestParam(value = "level") Byte level,@RequestParam(value = "uid")Long uid) {
        try {
            AlloResp result = actChargeCamelService.getPrizeAward(level, uid);
            return result;
        } catch (Exception e) {
            log.error("getActChargeCamelRewardAuth error", e);
            AlloResp<Object> failed = AlloResp.failed(BusiStatus.SERVERERROR);
            failed.setMessage(e.getMessage());
            return failed;
        }
    }


    @GetMapping(value = "/h5/reward/test")
    public AlloResp testPrizeAward(@RequestParam(value = "level") Byte level,@RequestParam(value = "uid")Long uid) {
        try {
            AlloResp result = actChargeCamelService.testPrizeAward( level, uid);
            return result;
        } catch (Exception e) {
            log.error("getActChargeCamelReward error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
