package com.dc.allo.rank.service.bet.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.domain.bet.BetAwardConfig;
import com.dc.allo.rank.mapper.bet.BetAwardConfigMapper;
import com.dc.allo.rank.service.bet.BetAwardConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@Service
public class BetAwardConfigServiceImpl implements BetAwardConfigService{

    @Autowired
    BetAwardConfigMapper awardConfigMapper;

    private boolean valid(BetAwardConfig awardConfig){
        boolean flag = false;
        if(awardConfig!=null && awardConfig.getActId()>0 && awardConfig.getPackageId()>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public long add(BetAwardConfig awardConfig) {
        if(!valid(awardConfig)) {
            return 0;
        }
        return awardConfigMapper.add(awardConfig);
    }

    @Override
    public long update(BetAwardConfig awardConfig) {
        if(!valid(awardConfig)) {
            return 0;
        }
        return awardConfigMapper.update(awardConfig);
    }

    @Override
    @Cached(name = "act-center:bet:awardConfigCache.queryAwardConfigs", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<BetAwardConfig> queryAwardConfigs(long actId) {
        return awardConfigMapper.queryAwardConfigs(actId);
    }

    @Override
    public List<BetAwardConfig> queryAwardConfigsForAdmin(long actId) {
        return awardConfigMapper.queryAwardConfigs(actId);
    }
}
