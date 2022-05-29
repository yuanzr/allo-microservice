package com.dc.allo.user.service.rpc.sysconf;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.sysconf.DcSysConfService;
import com.dc.allo.rpc.domain.sysconf.SysConf;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.user.service.sysconf.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/4/1.
 */
@Service
@Slf4j
public class DcSysConServiceImpl implements DcSysConfService {

    @Autowired
    private SysConfService sysConfService;


    @Override
    public AlloResp<String> getSysConfValueById(String configId) {
        if (StringUtils.isBlank(configId)) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            String configStr = sysConfService.getSysConfValueById(configId);
            return AlloResp.success(configStr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    @Override
    public AlloResp<SysConf> getSysConfById(String sysConfigId) {
        if (StringUtils.isBlank(sysConfigId)) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        SysConf config = null;
        try {
            config = sysConfService.getSysConf(sysConfigId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(config);
    }

    @Override
    public AlloResp<String> refreshConfig() {
        try{
            sysConfService.refreshConfig();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success("ok");
    }

    @Override
    public AlloResp<List<SysConf>> queryConfs(List<String> ids) {
        List<SysConf> confs = null;
        try{
            confs = sysConfService.queryConfs(ids);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(confs);
    }

    @Override
    public AlloResp<Map<String, String>> queryConfigVals(List<String> ids) {
        Map<String,String> map = null;
        try{
            map = sysConfService.queryConfigVals(ids);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(map);
    }

}
