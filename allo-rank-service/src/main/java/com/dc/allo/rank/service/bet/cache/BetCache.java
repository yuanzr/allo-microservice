package com.dc.allo.rank.service.bet.cache;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rpc.domain.bet.BetAnimationInfo;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.bet.BetWinSpiritInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
public interface BetCache {

    RedisUtil getRedisUtil();

    String getBetLockKey(long gameId,long uid);

    /**
     * 当前游戏缓存
     * @param gameRound
     */
    void setCurGameRound(BetGameRound gameRound);

    /**
     * 获取当前游戏缓存
     * @param actId
     * @return
     */
    BetGameRound getBetCurGameRound(long actId) ;

    /**
     * 设置某局游戏缓存
     * @param gameRound
     */
    void setBetGameRound(BetGameRound gameRound);

    /**
     * 获取游戏缓存
     * @param gameId
     * @return
     */
    BetGameRound getBetGameRound(long gameId);

    /**
     * 投注数
     * @param gameId
     * @param uid
     * @return
     */
    long incrBet(long gameId,long uid);

    /**
     * 是否已投注
     * @param gameId
     * @param uid
     * @return
     */
    boolean hasBet(long gameId,long uid);

    /**
     * 是否结算中
     * @param gameId
     * @return
     */
    boolean isSettling(long gameId);

    /**
     * 统计投注额
     * @param actId
     * @param gameId
     * @param uid
     * @param spiritId
     * @param price
     */
    void statisticUserCurAmount(long actId,long gameId,long uid,long spiritId,long price);

    /**
     * 设置每轮游戏结束时最终投注额
     * @param actId
     * @param gameId
     * @param uid
     * @param spiritId
     * @param price
     */
    void statisticUserFinalAmount(long actId,long gameId,long uid,long spiritId,long price);

    /**
     * 设置某局游戏精灵投注额
     * @param actId
     * @param gameId
     * @param spiritAmounts
     */
    void statisticSpiritAmounts(long actId,long gameId,Map<String,String> spiritAmounts);

    /**
     * 设置某局游戏用户投注额
     * @param actId
     * @param gameId
     * @param uid
     * @param userAmounts
     */
    void statisticUserFinalAmounts(long actId,long gameId,long uid,Map<String,String> userAmounts);

    /**
     * 获取用户投注额总额
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    Map<String,String> getUserAmount(long actId, long gameId, long uid);

    /**
     * 设置获胜精灵信息
     * @param actId
     * @param gameId
     * @param winSpiritInfo
     */
    void setWinSpiritInfo(long actId, long gameId, BetWinSpiritInfo winSpiritInfo);

    /**
     * 获取获胜精灵信息
     * @param actId
     * @param gameId
     * @return
     */
    BetWinSpiritInfo getWinSpiritInfo(long actId, long gameId);

    /**
     * 获取某轮游戏每个精灵的投注总额
     * @param actId
     * @param gameId
     * @return
     */
    Map<String, String> getSpiritAmount(long actId, long gameId);

    /**
     * 获取某轮游戏每个精灵的支持总数
     * @param actId
     * @param gameId
     * @return
     */
    Map<String,String > getSpiritSupport(long actId,long gameId);

    /**
     * 用户比赛结果缓存
     * @param actId
     * @param gameId
     * @param uid
     * @param results
     */
    void setBetResult(long actId,long gameId,long uid,List<BetResultInfo> results);

    /**
     * 获取用户比赛结果缓存
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    List<BetResultInfo> queryBetResult(long actId,long gameId,long uid);

    /**
     * 设置游戏动画
     * @param gameId
     * @param animations
     */
    void setGameRoundAnimation(long gameId,String animations);

    /**
     * 获取游戏动画
     * @param gameId
     * @return
     */
    BetAnimationInfo getGameRoundAnimation(long gameId);

    /**
     * 设置某轮游戏精灵排名
     * @param gameId
     * @param spiritId
     * @param score
     */
    void setGameRoundRank(long gameId,long spiritId,int score);

    /**
     * 获取某轮游戏精灵排名id（正序）
     * @param gameId
     * @param size
     * @return
     */
    List<Long> getGameRoundRank(long gameId,int size);

}
