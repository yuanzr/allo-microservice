package com.dc.allo.rank.service.rank.chain;

import com.dc.allo.common.component.chain.AbstractChainNode;
import com.dc.allo.common.component.chain.ChainRegister;
import com.dc.allo.rank.domain.rank.RankRecordContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/7.
 */
@Component
public class ScoreCalculateProcessor extends AbstractChainNode<RankRecordContext> {
    @Override
    public boolean doProcess(RankRecordContext context) throws Exception {
        //todo 分值配置处理逻辑，如对特定的礼物或特定的时间进行按系数计算分值

        return true;
    }

    @Override
    public ChainRegister belongTo() {
        return ChainRegister.RANK_DATA_PROCESS_CHAIN;
    }

    @Override
    public Integer priority() {
        return 3;
    }
}
