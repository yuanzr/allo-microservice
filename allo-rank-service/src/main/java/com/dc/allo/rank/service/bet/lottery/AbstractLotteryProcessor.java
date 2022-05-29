package com.dc.allo.rank.service.bet.lottery;

import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
public abstract class AbstractLotteryProcessor {

    @Autowired
    LotteryProcessorProxy lotteryProcessorProxy;

    abstract public long doLottery(BetActInfo actInfo,long gameId) throws Exception;

    abstract public BetConstants.BankerModelType getBankerModelType();

    @PostConstruct
    public void regist() {
        lotteryProcessorProxy.register(this);
    }
}
