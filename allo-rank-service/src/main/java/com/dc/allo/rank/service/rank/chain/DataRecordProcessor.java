package com.dc.allo.rank.service.rank.chain;

import com.dc.allo.common.component.chain.AbstractChainNode;
import com.dc.allo.common.component.chain.ChainRegister;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.domain.rank.RankRecordContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Slf4j
@Component
public class DataRecordProcessor extends AbstractChainNode<RankRecordContext> {

    @Override
    public boolean doProcess(RankRecordContext context) throws Exception {
        if (context == null || context.getRank() == null || context.getRecord() == null) {
            log.warn(Constant.Rank.WARN + "DataRecordProcessor 上下文异常 context:{}", context);
            return false;
        }
        //将数据记录到榜单
        double afterRecordScore = context.getRank().record(context.getRecord());
        context.setScoreAfterRecord(afterRecordScore);
        return true;
    }

    @Override
    public ChainRegister belongTo() {
        return ChainRegister.RANK_DATA_PROCESS_CHAIN;
    }

    @Override
    public Integer priority() {
        return 5;
    }
}
