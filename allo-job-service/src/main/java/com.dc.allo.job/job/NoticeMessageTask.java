package com.dc.allo.job.job;

import com.dc.allo.rpc.api.msg.DcFirebaseMsgService;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: NoticeMessageTask
 * @Description: 消息提醒
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/4/8:58
 */
@Slf4j
@Component
public class NoticeMessageTask {

    @Reference
    private DcFirebaseMsgService dcFirebaseMsgService;

    /**
     * 投递延迟发送的消息
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void addMessageIntoDelayQueue() {
        log.info("addMessageIntoDelayQueue start==============");
        try {
            AlloResp alloResp = dcFirebaseMsgService.delayQueueProvider();
            log.info("addMessageIntoDelayQueue.code:{}", alloResp.getCode());
        } catch (Exception e) {
            log.error("addMessageIntoDelayQueue error", e);
        }
        log.info("addMessageIntoDelayQueue end=============");
    }
}
