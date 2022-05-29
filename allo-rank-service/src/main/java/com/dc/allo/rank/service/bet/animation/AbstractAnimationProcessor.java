package com.dc.allo.rank.service.bet.animation;

import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
public abstract class AbstractAnimationProcessor {

    @Autowired
    AnimationProcessorProxy animationProcessorProxy;

    abstract public long doGenAnimation(BetActInfo actInfo,long gameId,long winner) throws Exception;

    abstract public BetConstants.AnimationModelType getAnimationModelType();

    @PostConstruct
    public void regist() {
        animationProcessorProxy.register(this);
    }
}
