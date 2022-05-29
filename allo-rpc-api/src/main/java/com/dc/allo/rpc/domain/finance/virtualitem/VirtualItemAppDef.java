package com.dc.allo.rpc.domain.finance.virtualitem;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
@Data
public class VirtualItemAppDef implements Serializable{
    private int id;
    private String app;
    private int itemId;
    private Date ctime;
}
