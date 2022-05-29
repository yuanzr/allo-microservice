package com.dc.allo.activity.service;

import com.dc.allo.activity.domain.vo.camel.BetActInfoResp;
import com.dc.allo.activity.domain.vo.camel.BetGameRoundInfoResp;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundRecord;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.proto.AlloResp;

/**
 * Created by zhangzhenjun on 2020/6/15.
 */
public interface CamelGameService {

    /**
     * 按id查询活动信息
     * @param actId
     * @return
     */
    AlloResp<BetActInfoResp> getActInfo(long actId);

    /**
     * 获取骆驼游戏数据
     * @param actId
     * @param uid
     * @return
     */
    BetGameRoundInfo getCamelGameRound(long actId, long uid);

    /**
     * 根据游戏id获取游戏信息
     * @param gameId
     * @param uid
     * @return
     */
    AlloResp<BetGameRoundInfoResp> getBetGameRound(long gameId,long uid);

    /**
     * 投注骆驼
     * @param actId
     * @param gameId
     * @param uid
     * @param spiritId
     * @param amount
     * @return
     */
    AlloResp<Long> bet(long actId,long gameId,long uid,long spiritId,int amount);

    /**
     * 分页查询骆驼游戏数据
     * @param actId
     * @param id
     * @param pageSize
     * @return
     */
    AlloResp<Page<BetGameRoundRecord>> pageGameRound(long actId,long id,int pageSize);

    /**
     * 分页查询投注历史
     * @param actId
     * @param id
     * @param uid
     * @param suffix
     * @param pageSize
     * @return
     */
    AlloResp<Page<BetResultInfo>> pageResultInfo(long actId,long id,long uid,String suffix,int pageSize);
}
