package com.dc.allo.roomplay.redis.listener;

import com.dc.allo.common.component.delay.redis.DelayQueueListener;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.roomplay.redis.message.PkMessage;
import com.dc.allo.roomplay.service.pk.RoomPkService;
import groovy.transform.TailRecursive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangzhenjun on 2020/3/20.
 */
@Service
@Slf4j
public class PkDelayQueueListener implements DelayQueueListener<PkMessage> {

    @Autowired
    private RoomPkService roomPkService;
    @Override
    @Transactional
    public void onMessage(PkMessage data) throws Exception {
        log.info("pk receive msg from redis delay queue, msg: {}", JsonUtils.toJson(data));
        if (data == null) {
            return;
        }
        try {
            roomPkService.handleRoomPkMsg(data);
        }catch (Exception e){
            log.info("handler pk end msg Fail,recordId:{}",data.getPkRecordId(),e);
        }
        log.info("handler pk end,recordId:{}",data.getPkRecordId());
    }
}
