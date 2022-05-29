package com.dc.allo.user.service.sysconf.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rpc.domain.sysconf.SysConf;
import com.dc.allo.user.service.sysconf.cache.SysConfCache;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/4/1.
 */
@Service
public class SysConfCacheImpl implements SysConfCache {

    @Autowired
    RedisUtil redisUtil;

    public String getSysConfigKey(String sysConfigId) {
        return RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.SysConfig, sysConfigId);
    }

    @Override
    public void setSysConf(SysConf config) {
        if (config != null && StringUtils.isNoneBlank(config.getConfigId()) && StringUtils.isNoneBlank(config.getConfigValue())) {
            redisUtil.set(getSysConfigKey(config.getConfigId()), JsonUtils.toJson(config), RedisKeyUtil.RedisExpire_Time.OneMinute);
        }
    }

    @Override
    public SysConf getSysConf(String sysConfigId) {
        SysConf config = null;
        String json = redisUtil.get(getSysConfigKey(sysConfigId));
        if (StringUtils.isNoneBlank(json)) {
            config = JsonUtils.fromJson(json, SysConf.class);
        }
        return config;
    }

    @Override
    public String getSysConfValueById(String configId) {
        return redisUtil.get(getSysConfigKey(configId));
    }

    public void refreshConfig(List<SysConf> confs){
        if(CollectionUtils.isNotEmpty(confs)){
            Map<String,String> datas = new HashMap<>(confs.size());
            for(SysConf conf:confs){
                datas.put(getSysConfigKey(conf.getConfigId()),JsonUtils.toJson(conf));
            }
            redisUtil.multiSet(datas,RedisKeyUtil.RedisExpire_Time.OneWeek);
        }
    }

    @Override
    public List<SysConf> queryConfs(List<String> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return null;
        }
        List<String> keys = new ArrayList<>(ids.size());
        List<SysConf> confs = new ArrayList<>(ids.size());
        for(String id:ids){
            keys.add(getSysConfigKey(id));
        }
        List<String> jsons = redisUtil.multiGet(keys);
        for (String json:jsons){
            confs.add(JsonUtils.fromJson(json,SysConf.class));
        }
        return confs;
    }
}
