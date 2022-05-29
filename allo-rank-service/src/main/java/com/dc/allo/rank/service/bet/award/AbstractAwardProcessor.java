package com.dc.allo.rank.service.bet.award;

import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
public abstract class AbstractAwardProcessor {

    @Autowired
    AwardProcessorProxy awardProcessorProxy;

    abstract public long doAward(BetActInfo actInfo,long gameId,long winer) throws Exception;

    abstract public BetConstants.BankerModelType getBankerModelType();

    @PostConstruct
    public void regist() {
        awardProcessorProxy.register(this);
    }
}
