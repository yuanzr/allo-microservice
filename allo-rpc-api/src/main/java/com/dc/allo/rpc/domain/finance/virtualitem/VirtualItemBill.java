package com.dc.allo.rpc.domain.finance.virtualitem;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
@Data
public class VirtualItemBill implements Serializable {

    private long id;
    private long uid;
    private String app;
    private int itemId;
    private long val;
    private Date ctime;
    private String bizId;
    private String context;
    private int price;  //冗余字段，虚拟价格可能会改
}
