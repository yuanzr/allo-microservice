package com.dc.allo.roomplay.service.common.cache;

import com.dc.allo.rpc.domain.sysconf.SysConf;

public interface SysConfCache {
    SysConf getSysConfById(String configId);
}
