package com.dc.allo.rank.service.bet;

import com.dc.allo.rank.domain.bet.BetAwardConfig;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
public interface BetAwardConfigService {
    /**
     * 添加奖励配置
     * @param awardConfig
     * @return
     */
    long add(BetAwardConfig awardConfig);

    /**
     * 更新奖励配置
     * @param awardConfig
     * @return
     */
    long update(BetAwardConfig awardConfig);

    /**
     * 查询奖励配置
     * @param actId
     * @return
     */
    List<BetAwardConfig> queryAwardConfigs(long actId);

    /**
     * 查询奖励配置
     * @param actId
     * @return
     */
    List<BetAwardConfig> queryAwardConfigsForAdmin(long actId);
}
