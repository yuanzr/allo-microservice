package com.dc.allo.user.service.sysconf.impl;

import com.dc.allo.rpc.domain.sysconf.SysConf;
import com.dc.allo.user.mapper.sysconf.SysConfMapper;
import com.dc.allo.user.service.sysconf.SysConfService;
import com.dc.allo.user.service.sysconf.cache.SysConfCache;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/4/1.
 */
@Service
public class SysConfServiceImpl implements SysConfService {

    @Autowired
    SysConfCache sysConfCache;

    @Autowired
    SysConfMapper sysConfMapper;

    @Override
    public SysConf getSysConf(String sysConfigId) {
        SysConf config = sysConfCache.getSysConf(sysConfigId);
        if (config != null) {
            return config;
        }
        config = sysConfMapper.getById(sysConfigId);
        sysConfCache.setSysConf(config);
        return config;
    }

    @Override
    public String getSysConfValueById(String configId) {
        SysConf conf = getSysConf(configId);
        if(conf != null){
            return conf.getConfigValue();
        }
        return null;
    }

    @Override
    public void refreshConfig() {
        List<SysConf> confs = sysConfMapper.queryAll();
        sysConfCache.refreshConfig(confs);
    }

    @Override
    public List<SysConf> queryConfs(List<String> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return null;
        }
        return sysConfCache.queryConfs(ids);
    }

    @Override
    public Map<String, String> queryConfigVals(List<String> ids) {
        List<SysConf> confs = queryConfs(ids);
        if(CollectionUtils.isEmpty(confs)){
            return new HashMap<>(1);
        }
        Map<String,String> map = new HashMap<>(confs.size());
        for(SysConf conf : confs){
            if (conf != null) {
                map.put(conf.getConfigId(),conf.getConfigValue());
            }
        }
        return map;
    }

}
