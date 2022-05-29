package com.dc.allo.rank.service.bet.pay.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.MD5Utils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.vo.BetHttpReq;
import com.dc.allo.rank.domain.bet.vo.BetHttpResp;
import com.dc.allo.rank.service.bet.pay.AbstractPayProcessor;
import com.dc.allo.rpc.api.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by zhangzhenjun on 2020/5/28.
 */
@Service
@Slf4j
public class CoinsPayProcessor extends AbstractPayProcessor {

    @Value("${httpUrl.minusCoins}")
    private String httpUrl;

    private String appKey = "betAppKey";

    @Override
    public long doPay(long actId, long gameId, long uid, long payPrice, String seqId) throws Exception {
        log.info(BetConstants.INFO_LOG + " doPay bet gameId:{} uid:{} payPrice:{} seqId:{}", gameId, uid, payPrice, seqId);
        if (uid <= 0 || payPrice <= 0) {
            return BetConstants.GAME_ROUND_ERR.PAY_PRICE_ERR;
        }
        BetHttpReq req = new BetHttpReq();
        req.setUid(uid);
        byte objType = 108;
        req.setObjType(objType);
        req.setGoldNum(new BigDecimal(-payPrice));
        String sign = MD5Utils.md5(req.getUid() + "" + req.getObjType() + "" + req.getGoldNum(), appKey);
        req.setSign(sign);
        long goldNum = 0;
        String result = null;
        try {
            result = HttpUtils.doPost(httpUrl, req);
            log.info(BetConstants.INFO_LOG + " doPay minus coins url:{},result:{} ",httpUrl,JsonUtils.toJson(result));
            if (StringUtils.isNotBlank(result)) {
                BetHttpResp httpResp = JsonUtils.fromJson(result, BetHttpResp.class);
                if (httpResp != null) {
                    if (httpResp.getCode() == BusiStatus.SUCCESS.value() && httpResp.getData() != null) {
                        goldNum = httpResp.getData().getGoldNum().longValue();
                    } else if (httpResp.getCode() == 2103) {
                        return BetConstants.GAME_ROUND_ERR.PAY_NOT_ENOUGH;
                    } else {
                        return BetConstants.GAME_ROUND_ERR.PAY_ERR;
                    }
                } else {
                    return BetConstants.GAME_ROUND_ERR.PAY_ERR;
                }
            }else{
                return BetConstants.GAME_ROUND_ERR.PAY_ERR;
            }
        } catch (Exception e) {
            log.error(BetConstants.ERROR_LOG + " doPay minus coins err:{},uid:{},payNum{}", e.getMessage(), req.getUid(), req.getGoldNum(), e);
            return BetConstants.GAME_ROUND_ERR.PAY_ERR;
        }
        return goldNum;
    }

    @Override
    public BetConstants.PayType getPayType() {
        return BetConstants.PayType.COINS;
    }
}
