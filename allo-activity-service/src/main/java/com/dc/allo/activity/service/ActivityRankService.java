package com.dc.allo.activity.service;

import com.dc.allo.rpc.domain.rank.RankQueryReq;
import com.dc.allo.rpc.domain.rank.RankQueryResp;
import com.dc.allo.rpc.proto.AlloResp;

/**
 * Created by zhangzhenjun on 2020/6/19.
 */
public interface ActivityRankService {
    /**
     * 查询榜单
     * @param req
     * @return
     */
    AlloResp<RankQueryResp> queryRank(RankQueryReq req);

    AlloResp<Boolean> delRankCache(String key,String rankKey);
}
