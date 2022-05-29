package com.dc.allo.rpc.domain.finance.virtualitem;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
@Data
public class VirtualItemDef implements Serializable {
    private int id;
    private String name;
    private Date ctime;
    private Date utime;
    private int price;
    private long innerFlag;  //是否自动扣金币购买标记
}
