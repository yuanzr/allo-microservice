package com.dc.allo.rank.service.activity.cache.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.domain.activity.Activity;
import com.dc.allo.rank.mapper.activity.ActivityMapper;
import com.dc.allo.rank.service.activity.cache.ActivityCacheService;
import com.dc.allo.rpc.domain.activity.ActivityBgInfo;
import com.dc.allo.rpc.domain.activity.ActivityGiftInfo;
import com.dc.allo.rpc.domain.activity.ActivityRankInfo;
import com.dc.allo.rpc.domain.activity.ActivityRuleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/7/23.
 */
@Service
public class ActivityCacheServiceImpl implements ActivityCacheService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    @Cached(name = "act-center:rank:activityCache.get", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public Activity getActivity(long actId) {
        return activityMapper.getActivity(actId);
    }

    @Override
    @Cached(name = "act-center:rank:activityBgInfoCache.get", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public ActivityBgInfo getActivityBgInfo(long actId) {
        return activityMapper.getActivityBgInfo(actId);
    }

    @Override
    @Cached(name = "act-center:rank:activityRuleInfoCache.get", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public ActivityRuleInfo getActivityRuleInfo(long actId) {
        return activityMapper.getActivityRuleInfo(actId);
    }

    @Override
    @Cached(name = "act-center:rank:activityRankInfoCache.get", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public ActivityRankInfo getActivityRankInfo(long actId) {
        return activityMapper.getActivityRankInfo(actId);
    }

    @Override
    @Cached(name = "act-center:rank:activityGiftInfoCache.query", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<ActivityGiftInfo> queryActivityGiftInfos(long actId) {
        return activityMapper.queryGiftInfos(actId);
    }
}
