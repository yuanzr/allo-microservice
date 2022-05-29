package com.dc.allo.finance.handler;

import com.dc.allo.common.constants.Constant.App;
import com.dc.allo.common.constants.Constant.RamadanJobType;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.finance.service.VirtualItemService;
import com.dc.allo.rpc.domain.message.ActRamadanPiecesMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ActRamadanItemHandler
 * @Description: 斋月活动碎片处理器
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/4/20/18:55
 */
@Component
@Slf4j
public class ActRamadanItemHandler {

    @Autowired
    private VirtualItemService virtualItemService;

    public void handler(KafkaEvent event){
        if(event != null){
            log.info("ActRamadanItemHandler accept bill event:{}", JsonUtils.toJson(event));
            if(event.getEventType()== KafkaTopic.EventType.DC_ACT_RAMADAN_ITEM){
                ActRamadanPiecesMessage message = JsonUtils.fromJson(event.getData(), ActRamadanPiecesMessage.class);
                log.info("ActRamadanItemHandler accept bill obj:{}", JsonUtils.toJson(message));
                if(message!=null) {
                    //碎片道具
                    try {
                        Integer itemId = 1;
                        Long uid = message.getUid();
                        Byte jobType = message.getJobType();
                        Integer jobCode = message.getJobCode();
                        Long piecesValue = message.getPiecesValue().longValue();
                        virtualItemService.addVirtualItemBill(App.ALLO, uid, itemId, piecesValue, null, jobCode.toString());
                    } catch (Exception e) {
                        log.error("ActRamadanItemHandler error bill message:{},e={}", JsonUtils.toJson(message),e.getMessage());
                    }
                }
            }
        }
    }

}
