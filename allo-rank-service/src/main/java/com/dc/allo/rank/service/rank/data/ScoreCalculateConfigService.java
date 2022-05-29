package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rank.domain.rank.config.RankScoreCalcConfig;

/**
 * Created by zhangzhenjun on 2020/5/12.
 */
public interface ScoreCalculateConfigService {

    /**
     * 新增分值计算配置
     * @param calcConfig
     * @return
     */
    long addScoreCalculateConfig(RankScoreCalcConfig calcConfig);
}
