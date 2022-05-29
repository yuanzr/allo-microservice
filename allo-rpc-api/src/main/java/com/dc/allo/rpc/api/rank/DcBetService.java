package com.dc.allo.rpc.api.rank;

import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundRecord;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.proto.AlloResp;

/**
 * Created by zhangzhenjun on 2020/5/27.
 */
public interface DcBetService {

    /**
     * 按id查询活动信息
     * @param actId
     * @return
     */
    AlloResp<BetActInfo> getActInfo(long actId);

    /**
     * 创建月表（定时任务用）
     * @return
     */
    AlloResp<Boolean> check2CreatNextMonthTable();

    /**
     *生成活动游戏数据（调用方指定调用方式，定时任务或其它）
     * @param actId
     * @return
     * NO_ACT = -10;           //活动不在进行中
     * NO_GAME = -20;          //无此游戏
     * NOT_GAMEING = -30;      //游戏不在进行中
     * 1                       //生成成功
     */
    AlloResp<Long> genGameRound(long actId);

    /**
     * 获得当前游戏数据
     * @param actId
     * @return
     */
    AlloResp<BetGameRoundInfo> getCurBetGameRound(long actId, long uid);

    /**
     * 按游戏id获取游戏数据
     * @param gameId
     * @param uid
     * @return
     */
    AlloResp<BetGameRoundInfo> getBetGameRound(long gameId,long uid);

    /**
     * 投注
     * @param actId
     * @param gameId
     * @param uid
     * @param spiritId
     * @param amount
     * @return
     */
    AlloResp<Long> bet(long actId,long gameId,long uid,long spiritId,int amount);

    /**
     * 分页查询历史游戏数据
     * @param actId
     * @param id
     * @param pageSize
     * @return
     */
    AlloResp<Page<BetGameRoundRecord>> pageBetGameRound(long actId,long id,int pageSize);

    /**
     * 分页查询用户投注结果
     * @param actId
     * @param uid
     * @param month        月份 如 2020_05，默认本月
     * @param id           后台返回给客户端id，第一次传0
     * @param pageSize
     * @return
     */
    AlloResp<Page<BetResultInfo>> pageBetResult(long actId, long uid, String month,long id, int pageSize);


    /**
     * 统计投注数据（定时任务用）
     * @return
     */
    AlloResp<Long> betStatistic();
}
