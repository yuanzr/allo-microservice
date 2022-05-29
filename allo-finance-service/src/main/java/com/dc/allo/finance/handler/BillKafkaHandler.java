package com.dc.allo.finance.handler;

import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.finance.service.BillRecordService;
import com.dc.allo.rpc.domain.finance.BillRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Component
@Slf4j
public class BillKafkaHandler {

    @Autowired
    BillRecordService billRecordService;

    public void handler(KafkaEvent event){
        if(event != null){
            log.info("kafka accept bill event:{}", JsonUtils.toJson(event));
            if(event.getEventType()== KafkaTopic.EventType.BILL_RECORD_ADD_EVENT){
                BillRecord record = JsonUtils.fromJson(event.getData(),BillRecord.class);
                log.info("kafka accept bill obj:{}", JsonUtils.toJson(record));
                if(record!=null) {
                    billRecordService.addBillRecord(record);
                }
            }
        }
    }
}
