package com.dc.allo.rank.service.bet.award.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.i18n.IMessageSource;
import com.dc.allo.common.component.threadpool.DCCommonThreadPool;
import com.dc.allo.common.constants.*;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.LanguageUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.service.bet.BetSpiritService;
import com.dc.allo.rpc.api.msg.SendMsgService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.api.user.language.DcUserLanguageService;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rank.domain.bet.BetResult;
import com.dc.allo.rank.domain.bet.vo.*;
import com.dc.allo.rank.service.award.AwardService;
import com.dc.allo.rank.service.bet.BetDetailService;
import com.dc.allo.rank.service.bet.BetGameRoundService;
import com.dc.allo.rank.service.bet.BetResultService;
import com.dc.allo.rank.service.bet.award.AbstractAwardProcessor;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetAwardInfo;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangzhenjun on 2020/5/26.
 */
@Service
@Slf4j
public class OffcialAwardProcessor extends AbstractAwardProcessor {

    @Autowired
    BetDetailService betDetailService;

    @Autowired
    BetGameRoundService betGameRoundService;

    @Autowired
    BetResultService betResultService;

    @Autowired
    BetSpiritService betSpiritService;

    @Autowired
    AwardService awardService;

    @Autowired
    BetCache betCache;

    @Autowired
    DCCommonThreadPool dcCommonThreadPool;

    @Reference
    SendMsgService sendMsgService;

    @Resource(name = "messageNacosSource")
    protected IMessageSource messageSource;

    @Value("${SECRETARY_UID}")
    private long secretaryUid;

    @Reference
    DcUserLanguageService userLanguageService;

    @Override
    public long doAward(BetActInfo actInfo, long gameId, long winer) throws Exception {
        BetGameRound gameRound = betGameRoundService.getBetGameRound(gameId, true);
        List<BetSpiritAmount> amounts = betDetailService.statisticUidAmount(actInfo.getActId(), gameId);
        double winOdds = getWinOdds(gameRound, winer);
        //异步发奖
        try {
            dcCommonThreadPool.submit("betAwardThreadPool", () -> {
                sendAward(actInfo, gameId, winer, winOdds, amounts);
                return null;
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 1;
    }

    @Override
    public BetConstants.BankerModelType getBankerModelType() {
        return BetConstants.BankerModelType.OFFCIAL;
    }

    /**
     * 获取胜方赔率
     * @param gameRound
     * @param winer
     * @return
     */
    private double getWinOdds(BetGameRound gameRound, long winer) {
        List<BetSpiritInfo> spiritInfos = JsonUtils.fromJson(gameRound.getSpirits(), new TypeReference<List<BetSpiritInfo>>() {
        });
        double winOdds = 1;
        for (BetSpiritInfo spiritInfo : spiritInfos) {
            if (spiritInfo.getSpiritId() == winer) {
                winOdds = spiritInfo.getOdds();
                break;
            }
        }
        return winOdds;
    }

    /**
     * 礼包发奖
     * @param actInfo
     * @param gameId
     * @param winer
     * @param winOdds
     * @param userAmounts
     */
    private void sendAward(BetActInfo actInfo, long gameId, long winer, double winOdds, List<BetSpiritAmount> userAmounts) {
        if (actInfo == null || CollectionUtils.isEmpty(userAmounts)) {
            return;
        }
        BetAwardInfo winAward = actInfo.getWinAwardInfo();
        BetAwardInfo losserAward = actInfo.getLossAwardInfo();
        long spiritId, resultId;
        long totalAmount = 0;           //本局总投注额
        long maxUserTotalAmount = 0;
        long luckUid = 0;               //幸运用户id，获奖最多用户
        int supportNum = 0;             //获胜精灵支持数
        int awardStatus = 0;
        String remark = null;
        String pkgContent =null;
        Map<Long,BetSpiritAmount> amountMap = new HashMap<>();
        for (BetSpiritAmount userAmount : userAmounts) {
            spiritId = userAmount.getSpiritId();
            BetResult betResult = toBetResult(actInfo.getActId(), gameId, userAmount, winer, winOdds, "");
            resultId = betResultService.saveResult(betResult);
            long payTotalAmount = betResult.getPayTotalAmount();
            long awardAmount = betResult.getAwardAmount();
            long userTotalAmount = 0;
            if (resultId > 0) {
                //官方坐庄模式
                if (spiritId == winer) {
                    userTotalAmount = payTotalAmount + awardAmount;
                    supportNum++;
                    try {
                        ReleasePkgResult releasePkgResult = awardService.purchaseAwardPackage(userAmount.getUid(), winAward.getPackageId(), AwardEnums.PurchaseType.COINS.val(), userTotalAmount);
                        log.info(BetConstants.INFO_LOG+" award winner release result:{}",JsonUtils.toJson(releasePkgResult));
                        if (releasePkgResult.isResult()) {
                            awardStatus = 1;
                            remark = winAward.getPackageName() + "发放成功";
                            pkgContent = JsonUtils.toJson(releasePkgResult.getAwardOfPackages());
                        } else {
                            awardStatus = -1;
                            remark = winAward.getPackageName() + "发放失败";
                        }
                    }catch (Exception e){
                        awardStatus = -1;
                        remark = winAward.getPackageName() + "发放失败" + e.getMessage();
                        log.error(e.getMessage(),e);
                    }
                    //发kafka累积榜单数据(胜方才累积）
                    betDetailService.sendRankRecord(betResult.getActId(), betResult.getUid(), userTotalAmount);
                    sendMsg(betResult.getUid(),gameId,winer,userTotalAmount);
                }else{
                    userTotalAmount = payTotalAmount;
                    if (losserAward == null) {
                        continue;
                    }
                    ReleasePkgResult releasePkgResult = awardService.purchaseAwardPackage(userAmount.getUid(), losserAward.getPackageId(), AwardEnums.PurchaseType.COINS.val(), userTotalAmount);
                    log.info(BetConstants.INFO_LOG+" award losser release result:{}",JsonUtils.toJson(releasePkgResult));
                    if (releasePkgResult.isResult()) {
                        awardStatus = 1;
                        remark = losserAward.getPackageName() + "发放成功" ;
                        pkgContent = JsonUtils.toJson(releasePkgResult.getAwardOfPackages());
                    } else {
                        awardStatus = -1;
                        remark = losserAward.getPackageName() + "发放失败" ;
                    }
                    sendMsg(betResult.getUid(),gameId,winer,0);
                }
                betResultService.updateResultStatus(betResult.getId(), awardStatus,pkgContent, remark);
                //累计总值和计算中奖最大的用户
                totalAmount += payTotalAmount;
                if (userTotalAmount > maxUserTotalAmount) {
                    maxUserTotalAmount = userTotalAmount;
                    luckUid = betResult.getUid();
                }
                //累积精灵统计数据至map
                putMap(amountMap,spiritId,payTotalAmount,userTotalAmount);
            }
        }
        //投注统计数据入库
        if(MapUtils.isNotEmpty(amountMap)){
            BetSpiritAmount betSpiritAmount = null;
            Iterator<Map.Entry<Long, BetSpiritAmount>> it = amountMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<Long, BetSpiritAmount> et = it.next();
                betSpiritAmount = et.getValue();
                betSpiritService.updateSpiritConfigAmount(betSpiritAmount.getSpiritId(),actInfo.getActId(),betSpiritAmount.getTotalAmount(),betSpiritAmount.getTotalAwardAmount());
            }
            //记录增加动画数据
            betResultService.batchUpdateAnimations(gameId, JsonUtils.toJson(betCache.getGameRoundAnimation(gameId)));
            //更新游戏每个精灵收益情况
            betGameRoundService.updateBetAmount(actInfo.getActId(),gameId);
        }
    }

    /**
     * 构造结果对象
     * @param actId
     * @param gameId
     * @param userAmount
     * @param winer
     * @param winOdds
     * @param remark
     * @return
     */
    private BetResult toBetResult(long actId, long gameId, BetSpiritAmount userAmount, long winer, double winOdds, String remark) {
        BetResult result = null;
        if (userAmount != null) {
            result = new BetResult();
            long spiritId = userAmount.getSpiritId();
            result.setRemark(remark);
            long uid = userAmount.getUid();
            long totalAmount = userAmount.getTotalAmount();
            //获胜者
            if (spiritId == winer) {
                long awardAmount = BigDecimal.valueOf(totalAmount * winOdds).longValue();
                result.setAwardAmount(awardAmount);
                result.setIsWin(1);
            } else {
                result.setRemark("losser no award");
            }
            result.setSpiritId(spiritId);
            result.setWinSpiritId(winer);
            result.setUid(uid);
            result.setPayTotalAmount(totalAmount);
            result.setTakeBackAmount(totalAmount);
            result.setCtime(new Date());
            result.setActId(actId);
            result.setGameId(gameId);
        }
        return result;
    }

    /**
     * 统计数据累积至map
     * @param amountMap
     * @param spiritId
     * @param payTotalAmount
     * @param userTotalAmount
     */
    private void putMap(Map<Long,BetSpiritAmount> amountMap,long spiritId,long payTotalAmount,long userTotalAmount){
        if(amountMap==null){
            amountMap = new HashMap<>();
        }
        BetSpiritAmount spiritAmount = amountMap.get(spiritId);
        //用户总额大于投注额，表示获胜得了奖励
        long awardAmount = userTotalAmount>payTotalAmount?userTotalAmount:0;
        if(spiritAmount == null){
            spiritAmount = new BetSpiritAmount();
            spiritAmount.setSpiritId(spiritId);
            spiritAmount.setTotalAmount(payTotalAmount);
            spiritAmount.setTotalAwardAmount(awardAmount);
            amountMap.put(spiritId,spiritAmount);
        }else{
            spiritAmount.setTotalAmount(spiritAmount.getTotalAmount()+payTotalAmount);
            spiritAmount.setTotalAwardAmount(spiritAmount.getTotalAwardAmount()+awardAmount);
        }
    }

    /**
     * 发秘书消息
     * @param uid
     * @param gameId
     * @param winner
     * @param awardAmount
     */
    private void sendMsg(long uid,long gameId,long winner,long awardAmount){
        log.info(BetConstants.INFO_LOG+" sendMsg uid:{} gameId:{} winner:{} awardAmount:{}",uid,gameId,winner,awardAmount);
        if(uid<=0||gameId<=0){
            return;
        }
        String content = "bet-result-message-loss";
        if(awardAmount>0){
            content = "bet-result-message-win";
        }
        try {
            AlloResp<String> resp = userLanguageService.getUserLanguageByUid(uid);
            if(resp!=null&& resp.getCode() == BusiStatus.SUCCESS.value()) {
                LanguageUtils.setLanguage(resp.getData());
                content = messageSource.getMessage(content);
                content = String.format(content, winner, awardAmount);
            }
        }catch (Exception e){
            log.error(BetConstants.ERROR_LOG+" sendMsg: get user language err:{} uid:{}",e.getMessage(),uid,e);
        }

        String skipUri = "allolike://Jump/Pager/game/raceRecord?gameId="+gameId;
        List<String> targetUids = new ArrayList<>();
        targetUids.add(String.valueOf(uid));
        Attach attach = new Attach();
        attach.setFirst(Constant.DefineProtocol.CUSTOM_MESS_HEAD_SECRETARY);
        attach.setSecond(Constant.DefineProtocol.CUSTOM_MESS_SUB_SECRETARY_INTRACTION);
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("title", "竞猜消息");
        jsonObject.put("msg", content);
        jsonObject.put("routerType", 34);
        jsonObject.put("routerValue", skipUri);
        attach.setData(jsonObject);
        log.info(BetConstants.INFO_LOG+" sendMsg: attach:{}",JsonUtils.toJson(attach));
        try {
            sendMsgService.sendPrivateStatusMessage(secretaryUid+"", targetUids, RongCloudConstant.RongCloundMessageObjectName.GLOBAL_USER_TEXT,JsonUtils.toJson(attach) );
        }catch (Exception e){
            log.error(BetConstants.ERROR_LOG," sendMsg: err{}",e.getMessage(),e);
        }
    }
}
