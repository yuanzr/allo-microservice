package com.dc.allo.biznotice.service.rpc.firebase;

import com.dc.allo.biznotice.service.firebase.FirebaseMsgService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.msg.DcFirebaseMsgService;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: DcFirebaseMsgServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/4/10:41
 */
@Slf4j
@Service
public class DcFirebaseMsgServiceImpl implements DcFirebaseMsgService {
    @Autowired
    private FirebaseMsgService firebaseMsgService;

    @Override
    public AlloResp delayQueueProvider() {
        try {
            log.info("sendPendingMsgToDelayQueue-rpc");
            firebaseMsgService.delayQueueProvider();
           return AlloResp.success();
        } catch (Exception e) {
            log.error("sendPendingMsgToDelayQueue-error:",e);
            return AlloResp.failed(BusiStatus.BUSIERROR);
        }
    }
}
