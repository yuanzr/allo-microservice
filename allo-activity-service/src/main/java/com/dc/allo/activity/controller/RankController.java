package com.dc.allo.activity.controller;

import com.dc.allo.activity.domain.vo.rank.DelRankCacheReq;
import com.dc.allo.activity.service.ActivityRankService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.rank.DcRankService;
import com.dc.allo.rpc.domain.activity.ActivityInfo;
import com.dc.allo.rpc.domain.rank.RankQueryReq;
import com.dc.allo.rpc.domain.rank.RankQueryResp;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangzhenjun on 2020/6/19.
 */
@RestController
@Slf4j
@RequestMapping("/rank/h5")
public class RankController {

    @Autowired
    ActivityRankService activityRankService;

    @Reference
    DcRankService rankService;

    @Value("${spring.profiles.active}")
    String profile;

    @RequestMapping(value = "/queryRank", method = RequestMethod.POST)
    public AlloResp<RankQueryResp> queryRank(@RequestBody RankQueryReq req) {
        try {
            AlloResp<RankQueryResp> rank = activityRankService.queryRank(req);
            return rank;
        } catch (Exception e) {
            log.error("queryRank error,req:{}",req.toString(),e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/getActInfo")
    public AlloResp<ActivityInfo> getActInfo(@RequestParam(value = "actId", defaultValue = "1") long actId) {
        try {
            AlloResp<ActivityInfo> actInfo = rankService.getAcitivityInfo(actId);
            return actInfo;
        } catch (Exception e) {
            log.error("getActInfo error,actId:{}",actId,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/triggerRankAutoAward")
    public AlloResp<Boolean> triggerRankAutoAward(){
        try{
            if("product".equals(profile)){
                return AlloResp.failed(BusiStatus.SERVERERROR.value(),"profile err",false);
            }
           return rankService.autoReward();
        }catch (Exception e){
            log.error("triggerRankAutoAward err{}",e.getMessage(),e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @RequestMapping(value = "/delRankCache",method = RequestMethod.POST)
    public AlloResp<Boolean> delRankCache(@RequestBody DelRankCacheReq req){
        try{
            return activityRankService.delRankCache(req.getKey(),req.getRankRedisKey());
        }catch (Exception e){
            log.error("triggerRankAutoAward err{}",e.getMessage(),e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
