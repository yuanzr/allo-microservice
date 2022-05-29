package com.dc.allo.rank.service.bet.lottery;

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
public class LotteryProcessorProxy {
    private Map<Integer, AbstractLotteryProcessor> processorMap = new HashMap<>();

    public long lottery(BetActInfo actInfo, long gameId) {
        if (actInfo == null) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        long actId = actInfo.getActId();
        int bankerModel = actInfo.getBankerModel();
        try {
            AbstractLotteryProcessor processor = processorMap.get(actInfo.getBankerModel());
            if (processor == null) {
                log.warn(BetConstants.INFO_LOG + "未找到匹配的抽奖方式  actId:{} banker:{} gameId:{}", actId, bankerModel, gameId);
                return BetConstants.GAME_ROUND_ERR.NO_GAME;
            }
            return processor.doLottery(actInfo, gameId);
        } catch (Exception e) {
            log.error(BetConstants.ERROR_LOG + "抽奖器处理失败 actId:{} banker:{} gameId:{}", actId, bankerModel, gameId, e);
            return BusiStatus.SERVER_BUSY.value();
        }
    }

    public void register(AbstractLotteryProcessor processor) {
        this.processorMap.put(processor.getBankerModelType().val(), processor);
    }
}
