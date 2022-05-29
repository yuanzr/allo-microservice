package com.dc.allo.finance.service.impl;

import com.dc.allo.finance.mapper.GiftSendRecordMapper;
import com.dc.allo.finance.service.GiftSendRecordService;
import com.dc.allo.rpc.domain.finance.GiftSendRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Service
public class GiftSendRecordServiceImpl implements GiftSendRecordService {

    @Autowired
    GiftSendRecordMapper giftSendRecordMapper;

    public boolean validate(GiftSendRecord record){
        boolean flag = false;
        if(record != null && record.getUid() > 0 && record.getReciveUid() > 0 && record.getGiftId() >0 && record.getGiftNum() > 0 ){
            flag = true;
        }
        return flag;
    }

    @Override
    public int addGiftSendRecord(GiftSendRecord record) {
        if(validate(record)){
            return giftSendRecordMapper.addGiftSendRecord(record);
        }
        return 0;
    }
}
