package com.dc.allo.rpc.api.sysconf;

import com.dc.allo.rpc.domain.sysconf.SysConf;
import com.dc.allo.rpc.proto.AlloResp;

import java.util.List;
import java.util.Map;


/**
 * Created by zhangzhenjun on 2020/5/11.
 */
public interface DcSysConfService {
    /**
     * 根据配置id获取配置值
     * @param configId
     * @return
     */
    AlloResp<String> getSysConfValueById(String configId);

    /**
     * 根据配置id获取配置对象
     * @param sysConfigId
     * @return
     */
    AlloResp<SysConf> getSysConfById(String sysConfigId);

    /**
     * 刷新配置缓存
     * @return
     */
    AlloResp<String> refreshConfig();

    /**
     * 批量查询配置信息（缓存）
     * @param ids
     * @return
     */
    AlloResp<List<SysConf>> queryConfs(List<String> ids);

    /**
     * 键值对方式获取配置信息
     * @param ids
     * @return
     */
    AlloResp<Map<String,String>> queryConfigVals(List<String> ids);
}
