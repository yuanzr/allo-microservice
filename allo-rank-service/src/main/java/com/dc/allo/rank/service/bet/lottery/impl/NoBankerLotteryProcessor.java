package com.dc.allo.rank.service.bet.lottery.impl;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rank.service.bet.BetGameRoundService;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.dc.allo.rank.service.bet.lottery.AbstractLotteryProcessor;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@Service
public class NoBankerLotteryProcessor extends AbstractLotteryProcessor {

    @Autowired
    BetGameRoundService gameRoundService;

    @Autowired
    BetCache betCache;

    @Override
    public long doLottery(BetActInfo actInfo,long gameId) throws Exception {
        return checkWiner(actInfo,gameId);
    }

    @Override
    public BetConstants.BankerModelType getBankerModelType() {
        return BetConstants.BankerModelType.NOBANKER;
    }

    /**
     * 选出胜方
     */
    private long checkWiner(BetActInfo actInfo,long gameId) {
        BetGameRound gameRound = gameRoundService.getBetGameRound(gameId, false);
        long validGameStatus = BetConstants.validGameStatus(actInfo,gameRound);
        if(validGameStatus<0){
            return validGameStatus;
        }
        List<BetSpiritInfo> spiritInfos = JsonUtils.fromJson(gameRound.getSpirits(), new TypeReference<List<BetSpiritInfo>>() {});
        BetSpiritInfo winer = BetConstants.doLottery(spiritInfos);

        if(winer!=null){
            gameRound.setWinSpiritId(winer.getSpiritId());
            betCache.setCurGameRound(gameRound);
            betCache.setBetGameRound(gameRound);
            return winer.getSpiritId();
        }
        return BetConstants.GAME_ROUND_ERR.NO_WINNER;
    }


}
