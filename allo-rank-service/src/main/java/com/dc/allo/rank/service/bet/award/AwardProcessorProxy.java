package com.dc.allo.rank.service.bet.award;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@Slf4j
@Component
public class AwardProcessorProxy {
    private Map<Integer, AbstractAwardProcessor> processorMap = new HashMap<>();

    public long award(BetActInfo actInfo,long gameId,long winer) {
        if (actInfo == null) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        long actId = actInfo.getActId();
        int bankerModel = actInfo.getBankerModel();
        try {
            AbstractAwardProcessor processor = processorMap.get(actInfo.getBankerModel());
            if (processor == null) {
                log.warn(BetConstants.INFO_LOG + "未找到匹配的发奖方式  actId:{} banker:{}",actId, bankerModel);
                return BetConstants.GAME_ROUND_ERR.NO_GAME;
            }
            return processor.doAward(actInfo,gameId,winer);
        } catch (Exception e) {
            log.error(BetConstants.ERROR_LOG + "发奖器处理失败 actId:{} banker:{}", actId, bankerModel, e);
            return BusiStatus.SERVER_BUSY.value();
        }
    }

    public void register(AbstractAwardProcessor processor) {
        this.processorMap.put(processor.getBankerModelType().val(), processor);
    }
}
