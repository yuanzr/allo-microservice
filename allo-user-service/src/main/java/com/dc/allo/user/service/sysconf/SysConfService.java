package com.dc.allo.user.service.sysconf;

import com.dc.allo.rpc.domain.sysconf.SysConf;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/4/1.
 */
public interface SysConfService {

    SysConf getSysConf(String sysConfigId);

    String getSysConfValueById(String configId);

    void refreshConfig();

    List<SysConf> queryConfs(List<String> ids);

    Map<String,String> queryConfigVals(List<String> ids);
}
