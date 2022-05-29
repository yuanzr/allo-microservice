package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rank.domain.rank.config.RankAutoRewardConfig;
import com.dc.allo.rpc.domain.page.AdminPage;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
public interface AutoRewardService {

    /**
     * 新增榜单奖励配置
     * @param autoRewardConfig
     * @return
     */
    long addAutoRewardConfig(RankAutoRewardConfig autoRewardConfig);

    /**
     * 更新榜单奖励配置
     * @param autoRewardConfig
     * @return
     */
    long updateAutoRewardConfig(RankAutoRewardConfig autoRewardConfig);

    AdminPage<RankAutoRewardConfig> pageAutoRewardConfig(int pageNum,int pageSize);

    /**
     * 根据榜单id查询奖励配置信息
     * @param rankId
     * @return
     */
    List<RankAutoRewardConfig> queryAwardConfigs(long rankId);

    /**
     * 有效期榜单自动发奖（榜单本身需进行发奖配置，通过礼包组件发放奖励）
     */
    void autoRewardTask()throws Exception;


}
