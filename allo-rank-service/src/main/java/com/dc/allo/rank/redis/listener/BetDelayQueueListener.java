package com.dc.allo.rank.redis.listener;

import com.dc.allo.common.component.delay.redis.DelayQueueListener;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.vo.BetGameInfo;
import com.dc.allo.rank.redis.message.BetMessage;
import com.dc.allo.rank.service.bet.BetFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Component
@Slf4j
public class BetDelayQueueListener implements DelayQueueListener<BetMessage> {

    @Autowired
    BetFacadeService betFacadeService;

    @Override
    public void onMessage(BetMessage data) throws Exception {
        log.info(BetConstants.INFO_LOG+"receive msg from delay queue, msg: {}", data);
        if (data == null) {
            return;
        }
        try {
            int msgType = data.getMsgType();
            BetGameInfo gameInfo = data.getGameInfo();
            if(gameInfo==null){
                log.info(BetConstants.INFO_LOG+"delayQueue listener gameInfo is null");
                return;
            }
            long actId = gameInfo.getActId();
            long gameId = gameInfo.getGameId();
            log.info(BetConstants.INFO_LOG+"msgType:{} actId:{} gameId:{}",msgType,actId,gameId);
            switch (msgType){
                case BetConstants.GAME_EVENT_TYPE.STOP_BET:{
                    betFacadeService.stopBet(gameId);
                    break;
                }
                case BetConstants.GAME_EVENT_TYPE.SETTLE:{
                    betFacadeService.settleGame(actId,gameId);
                    break;
                }
                case BetConstants.GAME_EVENT_TYPE.STOP_GAME:{
                    betFacadeService.stopGame(actId,gameId);
                    break;
                }
                default:break;
            }
        }catch (Exception e){
            log.error(BetConstants.ERROR_LOG +" delay queue err... msg:{}",data,e);
        }
    }
}
