package com.dc.allo.rank.service.bet;


import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundRecord;
import com.dc.allo.rpc.domain.bet.BetAnimationData;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.domain.page.Page;

import java.util.List;
import java.util.Map;


/**
 * Created by zhangzhenjun on 2020/5/20.
 */
public interface BetGameRoundService {

    /**
     * 根据活动信息生成游戏
     * @param info
     * @return
     */
    long genGameRound(BetActInfo info);

    /**
     * 获取游戏信息
     * @param gameId
     * @return
     */
    BetGameRound getBetGameRound(long gameId,boolean useCache);

    /**
     * 获取当前游戏信息
     * @param actId
     * @return
     */
    BetGameRound getCurBetGameRound(long actId);

    /**
     * 当前游戏停止投注
     * @param actId
     * @return
     */
    long stopCurGameBet(long actId);

    long stopGameBet(long gameId);

    /**
     * 结算游戏
     * @param actInfo
     * @param gameId
     * @return
     */
    long settleGameRound(BetActInfo actInfo, long gameId);

    long stopGameRound(BetActInfo actInfo, long gameId);

    /**
     * 更新游戏每个精灵投注情况
     * @param actId
     * @param gameId
     * @return
     */
    long updateBetAmount(long actId,long gameId);

    /**
     * 更新游戏动画数据
     * @param gameId
     * @param animations
     * @return
     */
    long updateAnimations(long gameId,String animations);

    Map<Long,BetAnimationData> getAnimations(long gameId);

    /**
     * 获取游戏统计信息
     * @param gameId
     * @return
     */
    BetGameRoundRecord get(long gameId);

    /**
     * 转对象
     * @param game
     * @return
     */
    BetGameRoundRecord assembleGameInfo(BetGameRound game);

    /**
     * 管理后台显示分页数据
     * @param actId
     * @param date
     * @param offset
     * @param pageSize
     * @return
     */
    AdminPage<BetGameRound> page(long actId, String date, long offset,int pageSize);

    /**
     * 客户端显示分页数据
     * @param actId
     * @param id
     * @param pageSize
     * @return
     */
    Page<BetGameRoundRecord> pageGameRound(long actId,long id,int pageSize);

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
