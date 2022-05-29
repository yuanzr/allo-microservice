package com.dc.allo.job.job;

import com.dc.allo.rpc.api.sysconf.DcSysConfService;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */

@Slf4j
@Component
public class SysconfTask {

    @Reference
    DcSysConfService sysConfService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void refreshConfig() {
        log.info("sysconfRefresh start============== time:{}",new Date());
        try {
            sysConfService.refreshConfig();
        } catch (Exception e) {
            log.error("sysconfRefresh error", e);
        }
        log.info("sysconfRefresh end============== time:{}", new Date());
    }
}
