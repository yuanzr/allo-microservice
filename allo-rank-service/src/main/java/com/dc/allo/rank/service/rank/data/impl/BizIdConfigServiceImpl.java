package com.dc.allo.rank.service.rank.data.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.domain.rank.config.BizIdConfig;
import com.dc.allo.rank.mapper.rank.BizIdMapper;
import com.dc.allo.rank.service.rank.data.BizIdConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/7/20.
 */
@Service
public class BizIdConfigServiceImpl implements BizIdConfigService {

    @Autowired
    BizIdMapper bizIdMapper;

    @Override
    public long addBizIdConfig(long rankId,String bizIds) {
        if(rankId<=0|| StringUtils.isBlank(bizIds)){
            return 0;
        }
        return bizIdMapper.add(rankId,bizIds);
    }

    @Override
    public long updateBizIdConfig(long rankId, String bizIds, long id) {
        if(rankId<=0|| StringUtils.isBlank(bizIds)||id<=0){
            return 0;
        }
        return bizIdMapper.update(rankId,bizIds,id);
    }

    @Override
    @Cached(name = "act-center:rank:bizIdConfigCache.query", key = "#rankId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public BizIdConfig get(long rankId) {
        return bizIdMapper.get(rankId);
    }

    @Override
    public BizIdConfig get4db(long rankId) {
        return bizIdMapper.get(rankId);
    }
}
