package com.dc.allo.rank.service.bet.pay;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rank.constants.BetConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@Slf4j
@Component
public class PayProcessorProxy {
    private Map<Integer, AbstractPayProcessor> processorMap = new HashMap<>();

    /**
     * 支付代理接口
     * @param actId
     * @param gameId
     * @param uid
     * @param payPrice     支付金额
     * @param payType      支付类型
     * @param seqId        支付唯一码，保障幂等
     * @return
     */
    public long pay(long actId, long gameId, long uid, long payPrice, int payType,String seqId) {
        if (actId <= 0) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        if (gameId <= 0) {
            return BetConstants.GAME_ROUND_ERR.NO_GAME;
        }
        if (uid <= 0) {
            return BetConstants.GAME_ROUND_ERR.PAY_USER_ERR;
        }
        if (payPrice <= 0) {
            return BetConstants.GAME_ROUND_ERR.PAY_PRICE_ERR;
        }
        try {
            AbstractPayProcessor processor = processorMap.get(payType);
            if (processor == null) {
                log.warn(BetConstants.INFO_LOG + "未找到匹配的支付方式  actId:{} gameId:{} payType:{} ", actId, gameId, payType);
                return BetConstants.GAME_ROUND_ERR.PAY_TYPE_ERR;
            }
            log.info(BetConstants.INFO_LOG + "bet pay actId:{},gameId:{},payType:{},uid:{},payPrice:{}",actId,gameId,payType,uid,payPrice);
            return processor.doPay(actId, gameId, uid, payPrice,seqId);
        } catch (Exception e) {
            log.error(BetConstants.ERROR_LOG + " 支付处理失败 actId:{} gameId:{} uid:{} payPrice:{} payType:{}", actId, gameId, uid, payPrice, payType, e);
            return BusiStatus.SERVER_BUSY.value();
        }
    }

    public void register(AbstractPayProcessor processor) {
        this.processorMap.put(processor.getPayType().val(), processor);
    }
}
