package com.dc.allo.rpc.domain.finance.virtualitem;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
@Data
public class VirtualItemBillVo implements Serializable {
    private long id;
    private long uid;
    private long val;
    private long  createTime;
    private String bizId;
    private String context;
    //冗余字段，虚拟价格可能会改
    private int price;
}
