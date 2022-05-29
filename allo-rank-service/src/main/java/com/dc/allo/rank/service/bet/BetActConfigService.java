package com.dc.allo.rank.service.bet;

import com.dc.allo.rank.domain.bet.BetActConfig;
import com.dc.allo.rpc.domain.bet.BetActInfo;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
public interface BetActConfigService {

    /**
     * 增加投注活动（后台用）
     * @param betActConfig
     * @return
     */
    long add(BetActConfig betActConfig);

    /**
     * 更新投注活动配置（后台用）
     * @param betActConfig
     * @return
     */
    long update(BetActConfig betActConfig);

    /**
     * 查全量活动（后台用）
     * @return
     */
    List<BetActConfig> queryAllAct();

    /**
     * 校验是否活动进行中
     */
    boolean checkActing(BetActInfo info);

    /**
     * 根据活动id获取活动信息
     * @param actId
     * @return
     */
    BetActInfo getBetActInfo(long actId);
}
