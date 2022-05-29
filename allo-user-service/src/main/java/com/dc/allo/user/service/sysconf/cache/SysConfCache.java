package com.dc.allo.user.service.sysconf.cache;


import com.dc.allo.rpc.domain.sysconf.SysConf;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/1.
 */
public interface SysConfCache {

    void setSysConf(SysConf config);

    SysConf getSysConf(String sysConfigId);

    String getSysConfValueById(String configId);

    void refreshConfig(List<SysConf> confs);

    List<SysConf> queryConfs(List<String> ids);
}
