package com.dc.allo.rank.service.rank.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.service.rank.cache.RankCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
@Service
public class RankCacheImpl implements RankCache {

    @Autowired
    RedisUtil redisUtil;

    private String getAutoRewardKey(String rankId){
        return RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Rank,"auto-reward",rankId);
    }

    public void recordAutoReward(String rankId,String rankKey,long seconds){
        redisUtil.hPut(getAutoRewardKey(rankId),rankKey, TimeUtils.toStr(new Date(),TimeUtils.YEAR2HOUR_NOLINE));
        redisUtil.expire(getAutoRewardKey(rankId),seconds);
    }

    public boolean alreadyAutoReward(String rankId,String rankKey){
        return redisUtil.hExists(getAutoRewardKey(rankId),rankKey);
    }

    @Override
    public void delRank(String rankRedisKey) {
        if(StringUtils.isNotBlank(rankRedisKey)) {
            redisUtil.delete(rankRedisKey);
        }
    }
}
