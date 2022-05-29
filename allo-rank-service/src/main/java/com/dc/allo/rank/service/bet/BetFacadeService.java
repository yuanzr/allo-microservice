package com.dc.allo.rank.service.bet;

import com.dc.allo.rank.domain.bet.*;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundRecord;
import com.dc.allo.rpc.domain.bet.BetGameRoundInfo;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.domain.page.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
public interface BetFacadeService {
    /**
     * 添加投注活动（后台用）
     * @param betActConfig
     * @return
     */
    long addActConf(BetActConfig betActConfig);

    /**
     * 更新投注活动配置信息（后台用）
     * @param betActConfig
     * @return
     */
    long updateActConf(BetActConfig betActConfig);

    /**
     * 查所有活动配置（后台用）
     * @return
     */
    List<BetActConfig> queryAllAct();

    /**
     * 获取活动配置信息
     * @param actId
     * @return
     */
    BetActInfo getActInfo(long actId);

    /**
     * 增加投注精灵（后台用）
     * @param spirit
     * @return
     */
    long addSpirit(BetSpirit spirit);

    /**
     * 更新精灵
     * @param spirit
     * @return
     */
    long updateSpirit(BetSpirit spirit);

    /**
     * 分页获取投注精灵（后台用）
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<BetSpirit> pageSpirit(int pageNum, int pageSize);

    /**
     * 增加投注精灵配置信息（后台用）
     * @param spiritConfig
     * @return
     */
    long addSpiritConf(BetSpiritConfig spiritConfig);

    /**
     * 增加奖励配置信息（后台用）
     * @param awardConfig
     * @return
     */
    long addAwardConf(BetAwardConfig awardConfig);

    /**
     * 更新奖励配置（后台用）
     * @param awardConfig
     * @return
     */
    long updateAwardConf(BetAwardConfig awardConfig);

    /**
     * 查询奖励配置(后台用）
     * @param actId
     * @return
     */
    AdminPage<BetAwardConfig> queryAwardConfigs(long actId);

    /**
     * 查询某活动投注精灵（后台用）
     * @param actId
     * @return
     */
    AdminPage<BetSpiritInfo> querySpiritConfigs(long actId);

    /**
     * 更新投注精灵配置信息（后台用）
     * @param spiritConfig
     * @return
     */
    long updateSpiritConfig(BetSpiritConfig spiritConfig);

    /**
     *生成活动游戏数据
     * @param actId
     * @return
     * NO_ACT = -10;           //活动不在进行中
     * NO_GAME = -20;          //无此游戏
     * NOT_GAMEING = -30;      //游戏不在进行中
     * 1                       //生成成功
     */
    long genGameRound(long actId);

    /**
     * 获得游戏数据
     * @param gameId
     * @return
     */
    BetGameRoundInfo getBetGameRound(long gameId,long uid);

    /**
     * 获得当前游戏数据
     * @param actId
     * @return
     */
    BetGameRoundInfo getCurBetGameRound(long actId, long uid);

    /**
     * 判断是否存在下个月投注明细月表，不存在创建
     */
    void check2CreateBetDetailTable();

    /**
     * 判断是否存在下个月投注结果月表，不存在创建
     */
    void check2CreateBetResultTable();

    /**
     * 投注
     * @return
     */
    long bet(long actId,long gameId,long uid,long spiritId,int amount);


    /**
     * 停止投注
     * @param gameId
     * @return
     */
    long stopBet(long gameId);

    /**
     * 结算活动游戏
     * @param actId
     */
    void settleCurGame(long actId);

    /**
     * 结算游戏
     * @param actId
     * @param gameId
     */
    void settleGame(long actId,long gameId);

    /**
     * 停止游戏
     * @param actId
     * @param gameId
     */
    void stopGame(long actId, long gameId);

    /**
     * 获取用户投注总额
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    Map<String,String> getUserBetAmount(long actId, long gameId, long uid);


    /**
     * 获取某轮游戏每个精灵的投注总额
     * @param actId
     * @param gameId
     * @return
     */
    Map<String, String> getSpiritBetAmount(long actId, long gameId);

    /**
     * 获取某轮游戏每个精灵支持数
     * @param actId
     * @param gameId
     * @return
     */
    Map<String,String> getSpiritSupport(long actId,long gameId);

    /**
     * 分页查询用户投注结果
     * @param actId
     * @param uid
     * @param month        月份 如 2020_05，默认本月
     * @param id           后台返回给客户端id，第一次传0
     * @param pageSize
     * @return
     */
    Page<BetResultInfo> pageBetResult(long actId, long uid, String month,long id, int pageSize);

    /**
     * 统计数据（定时任务用）
     * @return
     */
    long betStatistic();

    /**
     * 分页查询投注统计信息（后台用）
     * @param actId
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<BetStatistic> pageBetStatistic(long actId,String date,long pageNum,int pageSize) throws ParseException;

    /**
     * 获取游戏统计数据（后台用）
     * @param gameId
     * @return
     */
    BetGameRoundRecord getGameRoundInfo(long gameId);

    /**
     * 分页获取游戏信息（后台用）
     * @param actId
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<BetGameRoundRecord> pageGameRound(long actId, String date, int pageNum, int pageSize);

    /**
     * 分页获取游戏信息（客户端用）
     * @param actId
     * @param id
     * @param pageSize
     * @return
     */
    Page<BetGameRoundRecord> pageGameRound(long actId, long id, int pageSize);

    /**
     * 更新动画记录
     * @param gameId
     * @param animations
     * @return
     */
    long updateResultAnimations(long gameId,String animations);
}
