package com.dc.allo.roomplay.service.pk.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.roomplay.service.pk.cache.SampleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Service
public class SampleCacheImpl implements SampleCache {

    @Autowired
    private RedisUtil redisUtil;

    private String redisKeyPre = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.RoomPlay);

    private String getRedisKeySample(long uid) {
        return RedisKeyUtil.appendCacheKeyByColon(redisKeyPre,uid);
    }

    int oneHourExpire = 1;

    @Override
    public void setUserCache(long uid) {
        redisUtil.set(getRedisKeySample(uid), uid + ":" + System.currentTimeMillis(), oneHourExpire, TimeUnit.HOURS);
    }

    @Override
    public String getUserCache(long uid) {
        return redisUtil.get(getRedisKeySample(uid));
    }


}
