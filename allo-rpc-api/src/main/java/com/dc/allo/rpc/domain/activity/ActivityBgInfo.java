package com.dc.allo.rpc.domain.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/7/14.
 * 入库
 */
@Data
public class ActivityBgInfo implements Serializable {

    private long id;
    //活动id
    private long actId;
    //多语言背景url列表
    private String bgUrls;
    //背景色
    private String bgColor;
}
