package com.dc.allo.rpc.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
@Data
public class SignContinuous {
    private long id;
    private String app;
    private long taskId;
    private long uid;
    private int cNum;
    private Date preSignDate;
}
