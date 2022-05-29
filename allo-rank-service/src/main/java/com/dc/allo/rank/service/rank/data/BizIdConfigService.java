package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rank.domain.rank.config.BizIdConfig;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/20.
 */
public interface BizIdConfigService {

    long addBizIdConfig(long rankId,String bizIds);

    long updateBizIdConfig(long rankId,String bizIds,long id);

    BizIdConfig get(long rankId);

    BizIdConfig get4db(long rankId);
}
