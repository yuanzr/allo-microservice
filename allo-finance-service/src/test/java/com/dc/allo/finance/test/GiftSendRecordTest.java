package com.dc.allo.finance.test;

import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.finance.Application;
import com.dc.allo.rpc.domain.finance.BillRecord;
import com.dc.allo.rpc.domain.finance.GiftSendRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class GiftSendRecordTest {

    @Autowired
    KafkaSender kafkaSender;

    @Test
    public void giftSendKafka() {
        GiftSendRecord record = new GiftSendRecord();
        record.setUid(1111111111111l);
        record.setReciveUid(22222222222l);
        record.setGiftId(1);
        record.setGiftNum(20);
        record.setCreateTime(new Date());
        kafkaSender.send(KafkaTopic.Finance.DC_GIFT_SEND, KafkaTopic.EventType.GIFT_SEND_EVENT, record);
    }

    @Test
    public void billRecordKafka() {
        BillRecord record = new BillRecord();
        record.setBillId(UUID.randomUUID().toString());
        record.setUid(111111l);
        record.setGoldNum(new BigDecimal(10));
        record.setTargetUid(2222222l);
        kafkaSender.send(KafkaTopic.Finance.DC_BILL_RECORD, KafkaTopic.EventType.BILL_RECORD_ADD_EVENT, record);
    }
}
