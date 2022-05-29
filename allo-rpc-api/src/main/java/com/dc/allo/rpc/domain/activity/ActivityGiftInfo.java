package com.dc.allo.rpc.domain.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/7/24.
 */
@Data
public class ActivityGiftInfo implements Serializable {

    private long id;
    private long actId;
    private String name;
    private String icon;
    private long cost;
}
