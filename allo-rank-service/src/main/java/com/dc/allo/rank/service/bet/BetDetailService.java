package com.dc.allo.rank.service.bet;

import com.dc.allo.rank.domain.bet.BetDetail;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rpc.domain.bet.BetWinSpiritInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
public interface BetDetailService {

    void check2CreateNextMonthTable();

    /**
     * 下注
     *
     * @param detail
     * @return
     */
    long bet(BetDetail detail, int payType);

    /**
     * 发送榜单消息
     *
     * @param actId
     * @param sendUid
     * @param payPrice
     */
    void sendRankRecord(long actId, long sendUid, long payPrice);

    /**
     * 统计每场游戏每个精灵分值
     *
     * @param actId
     * @param gameId
     * @return
     */
    List<BetSpiritAmount> statisticAmount(long actId, long gameId);

    /**
     * 统计每场游侠每个用户对每个精灵的分值
     *
     * @param actId
     * @param gameId
     * @return
     */
    List<BetSpiritAmount> statisticUidAmount(long actId, long gameId);

    /**
     * 设置每轮游戏结束时最终投注额
     *
     * @param actId
     * @param gameId
     * @param uid
     * @param spiritId
     * @param price
     */
    void statisticUserFinalAmountCache(long actId, long gameId, long uid, long spiritId, long price);

    /**
     * 获取用户投注额总额
     *
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    Map<String, String> getUserAmount(long actId, long gameId, long uid);

    /**
     * 获取某轮游戏每个精灵的投注总额
     *
     * @param actId
     * @param gameId
     * @return
     */
    Map<String, String> getSpiritAmount(long actId, long gameId);

    /**
     * 获取某轮游戏每个精灵支持数
     * @param actId
     * @param gameId
     * @return
     */
    Map<String, String> getSpiritSupport(long actId, long gameId);

    void setWinSpiritInfo(long actId, long gameId, BetWinSpiritInfo winSpiritInfo);

    BetWinSpiritInfo getWinSpiritInfo(long actId, long gameId);
}
