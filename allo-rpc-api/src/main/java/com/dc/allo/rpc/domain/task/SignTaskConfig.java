package com.dc.allo.rpc.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
@Data
public class SignTaskConfig {
    private long id;
    private String app;
    private String taskName;
    private int taskType;
    private Date stime;
    private Date etime;
    private Date ctime;
    private Date utime;
}
