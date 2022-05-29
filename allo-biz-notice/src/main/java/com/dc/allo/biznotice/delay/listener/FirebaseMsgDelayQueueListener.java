package com.dc.allo.biznotice.delay.listener;

import com.dc.allo.biznotice.delay.message.FirebaseMessage;
import com.dc.allo.biznotice.model.firebase.NoticeMsgPushRecord;
import com.dc.allo.biznotice.service.firebase.FirebaseMsgService;
import com.dc.allo.common.component.delay.redis.DelayQueueListener;
import com.google.gson.Gson;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: FirebaseMsgDelayQueueListener
 * @Description: 延迟队列的监听
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/4/11:01
 */
@Slf4j
public class FirebaseMsgDelayQueueListener  implements DelayQueueListener<FirebaseMessage> {

    @Autowired
    private FirebaseMsgService firebaseMsgService;

    Gson gson = new Gson();

    @Override
    public void onMessage(FirebaseMessage message) throws Exception {
        log.info("FirebaseMsgDelayQueueListener-start:msg={}", gson.toJson(message));
        if (message == null) {
            return;
        }
        try {
            NoticeMsgPushRecord record = new NoticeMsgPushRecord();
            BeanUtils.copyProperties(message,record);
            if (message.getPushTimeTemp()!=null){
                record.setPushTime(new Date(message.getPushTimeTemp()));
            }
            firebaseMsgService.delayQueueConsumer(record);
        }catch (Exception e){
            log.error("FirebaseMsgDelayQueueListener-error,recordId:{}",gson.toJson(message),e);
        }
        log.info("FirebaseMsgDelayQueueListener-end:{}",gson.toJson(message));
    }
}
