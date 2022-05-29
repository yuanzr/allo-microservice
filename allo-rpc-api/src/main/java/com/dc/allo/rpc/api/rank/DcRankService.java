package com.dc.allo.rpc.api.rank;

import com.dc.allo.rpc.domain.activity.ActivityInfo;
import com.dc.allo.rpc.domain.rank.RankQueryReq;
import com.dc.allo.rpc.domain.rank.RankQueryResp;
import com.dc.allo.rpc.proto.AlloResp;
import org.apache.commons.lang.ObjectUtils;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
public interface DcRankService {

    /**
     * 检查并创建下月分表
     * @return
     */
    AlloResp<Boolean> check2CreatNextMonthTable();

    /**
     * 查询榜单
     * @param req
     * @return
     */
    AlloResp<RankQueryResp> queryRank(RankQueryReq req);

    /**
     * 有效期榜单自动发奖（榜单本身需进行发奖配置，通过礼包组件发放奖励）只能通过定时任务访问
     */
    AlloResp<Boolean> autoReward();

    /**
     * 根据活动id获取活动信息
     * @param actId
     * @return
     */
    AlloResp<ActivityInfo> getAcitivityInfo(long actId);

    /**
     * 删除榜单数据
     * @param rankRedisKey
     * @return
     */
    AlloResp<Boolean> delRankCache(String rankRedisKey);
}
