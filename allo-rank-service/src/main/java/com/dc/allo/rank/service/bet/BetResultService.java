package com.dc.allo.rank.service.bet;

import com.dc.allo.rank.domain.bet.BetResult;
import com.dc.allo.rank.domain.bet.BetStatistic;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.page.Page;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
public interface BetResultService {
    /**
     * 检查并创建下月月表
     */
    void check2CreateNextMonthTable();

    /**
     * 保存奖励信息
     * @param betResult
     * @return
     */
    long saveResult(BetResult betResult);

    /**
     * 分页查询用户投注结果
     * @param actId
     * @param uid
     * @param month        月份 如 2020_05，默认本月
     * @param id           后台返回给客户端id，第一次传0
     * @param pageSize
     * @return
     */
    Page<BetResult> pageBetResult(long actId, long uid, String month,long id, int pageSize);

    /**
     * 获取奖励结果信息
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    List<BetResultInfo> getBetResult(long actId, long gameId, long uid);

    /**
     *
     * @param resultId
     * @param awardStatus
     * @Param pkgContent
     * @param remark
     * @return
     */
    long updateResultStatus(long resultId, int awardStatus,String pkgContent,String remark);

    /**
     * 查询某天活动ids
     * @param date
     * @return
     */
    List<Long> queryActIds(String date);

    /**
     * 按活动id统计某天数据
     * @param actId
     * @param date
     * @return
     */
    BetStatistic statistic(long actId,String date);

    /**
     * 按活动id统计某天获胜场次投注金额数
     * @param actId
     * @param date
     * @return
     */
    long statisticWinPay(long actId,String date);

    /**
     * 按游戏id统计每个精灵的投注及奖励情况
     * @param actId
     * @param gameId
     * @return
     */
    List<BetSpiritAmount> statisticSpiritByGameId(long actId,long gameId);

    /**
     * 更新动画记录
     * @param gameId
     * @param animations
     * @return
     */
    long batchUpdateAnimations(long gameId,String animations);
}
