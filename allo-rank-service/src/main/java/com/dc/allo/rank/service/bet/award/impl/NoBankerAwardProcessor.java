package com.dc.allo.rank.service.bet.award.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.i18n.IMessageSource;
import com.dc.allo.common.component.threadpool.DCCommonThreadPool;
import com.dc.allo.common.constants.*;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.LanguageUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.service.bet.BetGameRoundService;
import com.dc.allo.rpc.api.msg.SendMsgService;
import com.dc.allo.rpc.api.user.language.DcUserLanguageService;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rank.domain.bet.BetResult;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetAwardInfo;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rank.service.award.AwardService;
import com.dc.allo.rank.service.bet.BetAwardConfigService;
import com.dc.allo.rank.service.bet.BetDetailService;
import com.dc.allo.rank.service.bet.BetResultService;
import com.dc.allo.rank.service.bet.award.AbstractAwardProcessor;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/26.
 */
@Service
@Slf4j
public class NoBankerAwardProcessor extends AbstractAwardProcessor {

    @Autowired
    BetDetailService betDetailService;

    @Autowired
    BetAwardConfigService betAwardConfigService;

    @Autowired
    BetResultService betResultService;

    @Autowired
    AwardService awardService;

    @Autowired
    BetCache betCache;

    @Autowired
    BetGameRoundService betGameRoundService;

    @Autowired
    DCCommonThreadPool dcCommonThreadPool;

    @Resource(name = "messageNacosSource")
    protected IMessageSource messageSource;

    @Value("${SECRETARY_UID}")
    private long secretaryUid;

    @Reference
    DcUserLanguageService userLanguageService;

    @Reference
    SendMsgService sendMsgService;

    @Override
    public long doAward(BetActInfo actInfo, long gameId, long winer) throws Exception {
        List<BetSpiritAmount> amounts = betDetailService.statisticUidAmount(actInfo.getActId(), gameId);
        double winPerAmount = staticsWinerPerAward(amounts, winer);
        //异步发奖
        try {
            dcCommonThreadPool.submit("betAwardThreadPool", () -> {
                sendAward(actInfo, gameId, winer, winPerAmount, amounts);
                return null;
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 1;
    }

    @Override
    public BetConstants.BankerModelType getBankerModelType() {
        return BetConstants.BankerModelType.NOBANKER;
    }

    /**
     * 统计获胜方每份奖励
     *
     * @param amounts
     * @param winSipirt
     * @return
     */
    private double staticsWinerPerAward(List<BetSpiritAmount> amounts, long winSipirt) {
        if (CollectionUtils.isEmpty(amounts) || winSipirt <= 0) {
            return 0;
        }
        long winerTotalAmount = 0;
        long losserTotalAmount = 0;
        for (BetSpiritAmount amount : amounts) {
            long spiritId = amount.getSpiritId();
            long totalAmount = amount.getTotalAmount();
            if (spiritId == winSipirt) {
                winerTotalAmount += totalAmount;
            } else {
                losserTotalAmount += totalAmount;
            }
        }
        if (winerTotalAmount > 0 && losserTotalAmount > 0) {
            BigDecimal w = new BigDecimal(winerTotalAmount);
            BigDecimal l = new BigDecimal(losserTotalAmount);
            //向下取整
            return l.divide(w, 2, RoundingMode.DOWN).doubleValue();
        } else {
            return 0;
        }
    }

    /**
     * 发奖
     *
     * @param actInfo
     * @param gameId
     * @param winer
     * @param winPerAmount
     * @param userAmounts
     */
    private void sendAward(BetActInfo actInfo, long gameId, long winer, double winPerAmount, List<BetSpiritAmount> userAmounts) {
        if (actInfo == null || CollectionUtils.isEmpty(userAmounts)) {
            return;
        }
        BetAwardInfo winAward = actInfo.getWinAwardInfo();
        BetAwardInfo losserAward = actInfo.getLossAwardInfo();
        long spiritId, resultId;
        long totalAmount = 0;
        long maxUserTotalAmount = 0;
        long luckUid = 0;
        int supportNum = 0;
        int awardStatus;
        String remark = null;
        String pkgContent = null;
        for (BetSpiritAmount userAmount : userAmounts) {
            spiritId = userAmount.getSpiritId();
            BetResult betResult = toBetResult(actInfo.getActId(), gameId, userAmount, winer, winPerAmount, "",actInfo.getDeductRate());
            resultId = betResultService.saveResult(betResult);
            if (resultId > 0) {
                long userTotalAmount;
                if (spiritId == winer) {
                    userTotalAmount = betResult.getTakeBackAmount() + betResult.getAwardAmount();
                    supportNum++;
                    ReleasePkgResult releasePkgResult = awardService.purchaseAwardPackage(userAmount.getUid(), winAward.getPackageId(), AwardEnums.PurchaseType.COINS.val(), userTotalAmount);
                    if (releasePkgResult.isResult()) {
                        awardStatus = 1;
                        remark = winAward.getPackageName() + "发放成功" ;
                        pkgContent = JsonUtils.toJson(releasePkgResult.getAwardOfPackages());
                    } else {
                        awardStatus = -1;
                        remark = winAward.getPackageName() + "发放失败" ;
                    }
                    sendMsg(betResult.getUid(),gameId,winer,userTotalAmount);
                    //发kafka累积榜单数据(胜方才累积）
                    betDetailService.sendRankRecord(betResult.getActId(), betResult.getUid(), userTotalAmount);
                } else {
                    if (losserAward == null) {
                        continue;
                    }
                    userTotalAmount = betResult.getTakeBackAmount();
                    ReleasePkgResult releasePkgResult = awardService.purchaseAwardPackage(userAmount.getUid(), losserAward.getPackageId(), AwardEnums.PurchaseType.COINS.val(), userTotalAmount);
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
                //累计总值和计算中奖最大的用户
                totalAmount += betResult.getPayTotalAmount();
                if (userTotalAmount > maxUserTotalAmount) {
                    maxUserTotalAmount = userTotalAmount;
                    luckUid = betResult.getUid();
                }
                betResultService.updateResultStatus(resultId, awardStatus,pkgContent, remark);
            }
        }
        //记录增加动画数据
        betResultService.batchUpdateAnimations(gameId, JsonUtils.toJson(betCache.getGameRoundAnimation(gameId)));
        //更新游戏每个精灵收益情况
        betGameRoundService.updateBetAmount(actInfo.getActId(),gameId);
    }

    /**
     * 构造结果对象
     *
     * @param actId
     * @param gameId
     * @param userAmount
     * @param winer
     * @param winerPerAward
     * @param remark
     * @return
     */
    private BetResult toBetResult(long actId, long gameId, BetSpiritAmount userAmount, long winer, double winerPerAward, String remark,double deductRate) {
        BetResult result = null;
        if (userAmount != null) {
            result = new BetResult();
            long spiritId = userAmount.getSpiritId();
            long uid = userAmount.getUid();
            long totalAmount = userAmount.getTotalAmount();
            long takeBackAmount = totalAmount;
            if(deductRate > 0){
                takeBackAmount = BigDecimal.valueOf(takeBackAmount*(1-deductRate)).longValue();
                winerPerAward = winerPerAward*(1-deductRate);
            }
            //获胜者
            if (spiritId == winer) {
                long awardAmount = BigDecimal.valueOf(takeBackAmount * winerPerAward).longValue();
                result.setAwardAmount(awardAmount);
                result.setIsWin(1);
            }
            result.setSpiritId(spiritId);
            result.setWinSpiritId(winer);
            result.setUid(uid);
            result.setPayTotalAmount(totalAmount);
            result.setTakeBackAmount(takeBackAmount);
            result.setCtime(new Date());
            result.setActId(actId);
            result.setGameId(gameId);
            result.setRemark(remark);
        }
        return result;
    }

    /**
     * 发秘书消息
     * @param uid
     * @param gameId
     * @param winner
     * @param awardAmount
     */
    private void sendMsg(long uid,long gameId,long winner,long awardAmount){
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
            }
        }catch (Exception e){
            log.error(BetConstants.ERROR_LOG+" sendMsg: get user language err:{} uid:{}",e.getMessage(),uid,e);
        }
        content = messageSource.getMessage(content);
        content = String.format(content, winner, awardAmount);
        String skipUri = "allolike://Jump/Pager/game/raceRecord?gameId="+gameId;
        List<String> targetUids = new ArrayList<>();
        targetUids.add(String.valueOf(uid));
        Attach attach = new Attach();
        attach.setFirst(Constant.DefineProtocol.CUSTOM_MESS_HEAD_SECRETARY);
        attach.setSecond(Constant.DefineProtocol.CUSTOM_MESS_SUB_SECRETARY_INTRACTION);
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("title", "竞猜消息");
        jsonObject.put("msg", content);
        jsonObject.put("routerType", 3);
        jsonObject.put("routerType", skipUri);
        jsonObject.put("routerValue", skipUri);
        attach.setData(jsonObject);
        try {
            sendMsgService.sendPrivateStatusMessage(secretaryUid+"", targetUids, RongCloudConstant.RongCloundMessageObjectName.GLOBAL_USER_TEXT,JsonUtils.toJson(attach) );
        }catch (Exception e){
            log.error(BetConstants.ERROR_LOG," sendMsg: err{}",e.getMessage(),e);
        }
    }
}
