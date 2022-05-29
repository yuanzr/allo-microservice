package com.dc.allo.finance.service;

import com.dc.allo.rpc.domain.finance.BillRecord;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
public interface BillRecordService {
    int addBillRecord(@Param("record") BillRecord record);
}
