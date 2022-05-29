package com.dc.allo.finance.service.impl;

import com.dc.allo.finance.mapper.BillRecordMapper;
import com.dc.allo.finance.service.BillRecordService;
import com.dc.allo.rpc.domain.finance.BillRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Service
public class BillRecordServiceImpl implements BillRecordService {

    @Autowired
    BillRecordMapper billRecordMapper;

    boolean validate(BillRecord record){
        boolean flag = false;
        if(record != null && StringUtils.isNotBlank(record.getBillId()) && record.getUid() > 0 && record.getGoldNum() != null ){
            flag = true;
        }
        return flag;
    }

    @Override
    public int addBillRecord(BillRecord record) {
        if(validate(record)){
            return billRecordMapper.addBillRecord(record);
        }
        return 0;
    }
}
