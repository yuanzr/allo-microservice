package com.dc.allo.rpc.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/15.
 */
@Data
public class Check implements Serializable {
    private String appName;
    private Date checkTime;
}
