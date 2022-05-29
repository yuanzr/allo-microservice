package com.dc.allo.rank.service.bet.lottery.impl;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rank.service.bet.BetDetailService;
import com.dc.allo.rank.service.bet.BetGameRoundService;
import com.dc.allo.rank.service.bet.lottery.AbstractLotteryProcessor;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@Service
@Slf4j
public class OffcialLotteryProcessor extends AbstractLotteryProcessor {

    @Autowired
    BetDetailService betDetailService;

    @Autowired
    BetGameRoundService gameRoundService;

    @Override
    public long doLottery(BetActInfo actInfo, long gameId) throws Exception {
        if (actInfo == null) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }

        BetGameRound gameRound = gameRoundService.getBetGameRound(gameId, false);
        long validGameStatus = BetConstants.validGameStatus(actInfo, gameRound);
        if (validGameStatus < 0) {
            return validGameStatus;
        }
        return checkWiner(actInfo, gameRound);
    }

    @Override
    public BetConstants.BankerModelType getBankerModelType() {
        return BetConstants.BankerModelType.OFFCIAL;
    }

    /**
     * 选出获胜精灵
     *
     * @param actInfo
     * @param gameRound
     * @return
     */
    private long checkWiner(BetActInfo actInfo, BetGameRound gameRound) {

        long actId = actInfo.getActId();
        long warnScore = actInfo.getWarnScore();
        List<BetSpiritInfo> spiritInfos = JsonUtils.fromJson(gameRound.getSpirits(), new TypeReference<List<BetSpiritInfo>>() {
        });
        BetSpiritInfo winer = BetConstants.doLottery(spiritInfos);

        if (winer == null) {
            return BetConstants.GAME_ROUND_ERR.NO_WINNER;
        }

        long winSpiritId = winer.getSpiritId();

        //有设置告警分值，需重选最优方案
        if (warnScore > 0) {
            List<BetSpiritAmount> amounts = betDetailService.statisticAmount(actId, gameRound.getId());
            long totalAmount = 0;
            int amountSize = amounts.size();
            if (amountSize == 0) {
                return winSpiritId;
            }
            Map<Long, Long> amountMap = new HashMap<>(amountSize);
            Map<Long, Double> oddsMap = new HashMap<>(amountSize);
            for (BetSpiritAmount amount : amounts) {
                totalAmount += amount.getTotalAmount();
                amountMap.put(amount.getSpiritId(), amount.getTotalAmount());
            }
            for (BetSpiritInfo spiritInfo : spiritInfos) {
                oddsMap.put(spiritInfo.getSpiritId(), spiritInfo.getOdds());
            }
            log.info(BetConstants.INFO_LOG + " lotteryProxy offcial model: winSpirit:{} winScore:{} amountMap:{} oddsMap:{}", winer, warnScore, JsonUtils.toJson(amountMap), JsonUtils.toJson(oddsMap));
            long winSpiritAmount = amountMap.get(winSpiritId) == null ? 0 : amountMap.get(winSpiritId);
            double winSpiritOdds = oddsMap.get(winSpiritId) == null ? 0 : oddsMap.get(winSpiritId);
            double oddsAmount = winSpiritAmount * winSpiritOdds;
            double profit = totalAmount - oddsAmount;
            //盈利小于0并且亏损额超出预警值，触发重选获胜精灵
            if (profit < 0 && Math.abs(profit) >= warnScore) {
                log.info(BetConstants.INFO_LOG + " lotteryProxy offcial model: winSpirit:{} oddsAmount:{} winScore:{} more than warnScore ,will be select best spirit", winer, oddsAmount, warnScore);
                winSpiritId = selectBestSpirit(amountMap, spiritInfos);
            }
        }

        return winSpiritId;
    }

    /**
     * 选择赔付最少金额的精灵
     *
     * @param amountMap
     * @param spiritInfos
     * @return
     */
    private long selectBestSpirit(Map<Long, Long> amountMap, List<BetSpiritInfo> spiritInfos) {
        double oddsAmount;
        long bestSpiritId = 0;
        long payAmount;
        double bestOddsAmount = Double.MAX_VALUE;
        for (BetSpiritInfo spiritInfo : spiritInfos) {
            payAmount = amountMap.get(spiritInfo.getSpiritId()) == null ? 0 : amountMap.get(spiritInfo.getSpiritId());
            oddsAmount = payAmount * spiritInfo.getOdds();
            if (oddsAmount < bestOddsAmount) {
                bestOddsAmount = oddsAmount;
                bestSpiritId = spiritInfo.getSpiritId();
            }
        }
        log.info("lotteryProxy offcial model: selectBestSpirit:{} bestOddsAmount:{}", bestSpiritId, bestOddsAmount);
        return bestSpiritId;
    }


}
