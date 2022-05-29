package com.dc.allo.roomplay.service.pk.cache;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.roomplay.service.common.cache.SysConfCache;
import com.dc.allo.rpc.domain.sysconf.SysConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysConfCacheImpl implements SysConfCache {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysConf getSysConfById(String configId) {
        Object sysConfStr = redisUtil.hGet(RedisKeyUtil.RedisKey.sys_conf.name(), configId);
        if (sysConfStr == null) {
           return null;
        }
        return JsonUtils.fromJson(sysConfStr.toString(), SysConf.class);
    }
}
