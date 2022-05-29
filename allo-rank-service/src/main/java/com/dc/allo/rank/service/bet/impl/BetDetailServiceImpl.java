package com.dc.allo.rank.service.bet.impl;

import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.BetDetail;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rank.service.bet.pay.PayProcessorProxy;
import com.dc.allo.rpc.domain.bet.BetWinSpiritInfo;
import com.dc.allo.rank.mapper.bet.BetDetailMapper;
import com.dc.allo.rank.service.bet.BetDetailService;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Service
@Slf4j
public class BetDetailServiceImpl implements BetDetailService {
    @Autowired
    BetDetailMapper betDetailMapper;

    @Autowired
    BetCache betCache;

    @Autowired
    PayProcessorProxy payProcessorProxy;

    @Autowired
    KafkaSender kafkaSender;

    private boolean valid(BetDetail detail) {
        boolean flag = false;
        if (detail != null && detail.getActId() > 0 && detail.getGameId() > 0 && detail.getPayPrice() > 0 && detail.getSpiritId() > 0 && detail.getUid() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 入库
     *
     * @param detail
     * @return
     */
    private long save(BetDetail detail) {
        return betDetailMapper.add(detail);
    }

    /**
     * 扣金币
     *
     * @param uid
     * @param price
     * @return
     */
    private long pay(long actId, long gameId, long uid, long price, int payType, String seqId) {
        return payProcessorProxy.pay(actId, gameId, uid, price, payType, seqId);
    }

    @Override
    public void check2CreateNextMonthTable() {
        int nextMonthExist = betDetailMapper.existNextMonthTable();
        if (nextMonthExist < 1) {
            betDetailMapper.createNextMonthTable();
        }
    }

    @Override
    public long bet(BetDetail detail, int payType) {
        long result = -1;
        if (!valid(detail)) {
            return result;
        }
        //是否结算中
        boolean isSettling = betCache.isSettling(detail.getGameId());
        if (isSettling) {
            return BetConstants.GAME_ROUND_ERR.SETTING;
        }
        long detailId = save(detail);
        if (detailId > 0) {
            long thirdPayStatus = pay(detail.getActId(), detail.getGameId(), detail.getUid(), detail.getPayPrice(), payType, detail.getPaySeqId());
            int payStatus = -1;
            String remark = "";
            if (thirdPayStatus >= 0) {
                payStatus = 1;
                //累积某局游戏投注次数
                betCache.incrBet(detail.getGameId(), detail.getUid());
                //累积值存缓存
                betCache.statisticUserCurAmount(detail.getActId(), detail.getGameId(), detail.getUid(), detail.getSpiritId(), detail.getPayPrice());
            } else {
                remark = "支付状态码：" + thirdPayStatus;
            }
            betDetailMapper.updateDetailResult(detail.getId(), payStatus, remark);
            result = thirdPayStatus;
            log.info(BetConstants.INFO_LOG+" bet result:{} payStatus:{} gameId:{} uid:{} spiritId:{} payPrice:{}",result,payStatus,detail.getGameId(),detail.getUid(),detail.getSpiritId(),detail.getPayPrice());
        }
        return result;
    }

    /**
     * 发送成功投注事件生成榜单
     *
     * @param actId
     * @param sendUid
     * @param payPrice
     */
    @Override
    public void sendRankRecord(long actId, long sendUid, long payPrice) {
        RankDataRecord record = new RankDataRecord();
        record.setAppKey(Constant.App.ALLO);
        record.setDataSourceKey(BetConstants.GAME_RANK_CONFIG.DATA_SOURCE_KEY + "-" + actId);
        record.setDataSourceSecret(BetConstants.GAME_RANK_CONFIG.DATA_SOURCE_SECRET + "-" + actId);
        record.setTimestamp(System.currentTimeMillis());
        record.setScore(Double.valueOf(String.valueOf(payPrice)));
        record.setSendId(String.valueOf(sendUid));
        record.setRecvId(String.valueOf(sendUid));
        kafkaSender.send(KafkaTopic.Rank.DC_RANK_DATA_STREAM, KafkaTopic.EventType.DC_RANK_BET_CAMAL, record);
    }

    @Override
    public List<BetSpiritAmount> statisticAmount(long actId, long gameId) {
        return betDetailMapper.statisticAmount(actId, gameId);
    }

    @Override
    public List<BetSpiritAmount> statisticUidAmount(long actId, long gameId) {
        return betDetailMapper.statisticUidAmount(actId, gameId);
    }

    @Override
    public void statisticUserFinalAmountCache(long actId, long gameId, long uid, long spiritId, long price) {
        betCache.statisticUserFinalAmount(actId, gameId, uid, spiritId, price);
    }

    @Override
    public Map<String, String> getUserAmount(long actId, long gameId, long uid) {
        Map<String, String> userAmount = betCache.getUserAmount(actId, gameId, uid);
        if (MapUtils.isEmpty(userAmount)) {
            List<BetSpiritAmount> amounts = betDetailMapper.statisticAmountByUid(actId, gameId, uid);
            userAmount = new HashMap<>();
            for (BetSpiritAmount amount : amounts) {
                if (amount != null) {
                    userAmount.put(String.valueOf(amount.getSpiritId()), String.valueOf(amount.getTotalAmount()));
                }
            }
            if (MapUtils.isNotEmpty(userAmount)) {
                betCache.statisticUserFinalAmounts(actId, gameId, uid, userAmount);
            }
        }
        return userAmount;
    }

    @Override
    public Map<String, String> getSpiritAmount(long actId, long gameId) {
        Map<String,String> spiritAmount =  betCache.getSpiritAmount(actId, gameId);
        if (MapUtils.isEmpty(spiritAmount)) {
            List<BetSpiritAmount> amounts = betDetailMapper.statisticAmount(actId, gameId);
            if(CollectionUtils.isNotEmpty(amounts)){
                spiritAmount = new HashMap<>(amounts.size());
                for(BetSpiritAmount amount:amounts){
                    spiritAmount.put(String.valueOf(amount.getSpiritId()),String.valueOf(amount.getTotalAmount()));
                }
            }
            if(MapUtils.isNotEmpty(spiritAmount)){
                betCache.statisticSpiritAmounts(actId,gameId,spiritAmount);
            }
        }
        return spiritAmount;
    }

    @Override
    public Map<String, String> getSpiritSupport(long actId, long gameId) {
        return betCache.getSpiritSupport(actId,gameId);
    }

    @Override
    public void setWinSpiritInfo(long actId, long gameId, BetWinSpiritInfo winSpiritInfo) {
        betCache.setWinSpiritInfo(actId, gameId, winSpiritInfo);
    }

    @Override
    public BetWinSpiritInfo getWinSpiritInfo(long actId, long gameId) {
        return betCache.getWinSpiritInfo(actId, gameId);
    }
}
