package com.dc.allo.rank.service.bet.animation;

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
public class AnimationProcessorProxy {
    private Map<Integer, AbstractAnimationProcessor> processorMap = new HashMap<>();

    public long genAnimation(BetActInfo actInfo, long gameId, long winner) {
        if (actInfo == null) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        long actId = actInfo.getActId();
        int animationModel = actInfo.getAnimationModel();
        try {
            AbstractAnimationProcessor processor = processorMap.get(actInfo.getBankerModel());
            if (processor == null) {
                log.warn(BetConstants.INFO_LOG + "未找到匹配的动画方式  actId:{} banker:{} gameId:{}", actId, animationModel, gameId);
                return BetConstants.GAME_ROUND_ERR.NO_GAME;
            }
            return processor.doGenAnimation(actInfo, gameId, winner);
        } catch (Exception e) {
            log.error(BetConstants.ERROR_LOG + "动画处理失败 actId:{} banker:{} gameId:{}", actId, animationModel, gameId, e);
            return BusiStatus.SERVER_BUSY.value();
        }
    }

    public void register(AbstractAnimationProcessor processor) {
        this.processorMap.put(processor.getAnimationModelType().val(), processor);
    }
}
