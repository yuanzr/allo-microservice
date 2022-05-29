package com.dc.allo.rpc.domain.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/7/14.
 * 入库
 */
@Data
public class ActivityRuleInfo implements Serializable {
    private long id;
    //活动id
    private long actId;
    //悬浮窗位置；0不动；1跟随屏幕滑动
    private int styleType;
    //跳转类型：0 h5；
    private int jumpType;
    //跳转地址
    private String jumpUrl;
    //悬浮窗背景url
    private String bgUrl;
//    //文案描述
//    private String remark;
}
