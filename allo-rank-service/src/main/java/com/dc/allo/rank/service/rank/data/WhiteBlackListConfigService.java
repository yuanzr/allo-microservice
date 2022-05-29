package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rank.domain.rank.config.RankWhiteBlackList;

/**
 * Created by zhangzhenjun on 2020/5/12.
 */
public interface WhiteBlackListConfigService {

    /**
     * 黑白名单配置
     * @return
     */
    long addWhiteBlackList(RankWhiteBlackList rankWhiteBlackList);
}
