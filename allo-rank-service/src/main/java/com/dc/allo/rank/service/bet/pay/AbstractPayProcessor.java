package com.dc.allo.rank.service.bet.pay;

import com.dc.allo.rank.constants.BetConstants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
public abstract class AbstractPayProcessor {

    @Autowired
    PayProcessorProxy awardProcessorProxy;

    abstract public long doPay(long actId, long gameId, long uid, long payPrice,String seqId) throws Exception;

    abstract public BetConstants.PayType getPayType();

    @PostConstruct
    public void regist() {
        awardProcessorProxy.register(this);
    }
}
