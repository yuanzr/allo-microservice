package com.dc.allo.rpc.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
@Data
public class Sign {

    private long id;
    private String app;
    private long taskId;
    private long uid;
    private Date signDate;
    private int signStatus;  //0正常签到；1补签
    private Date signTime;
}
