package com.dc.allo.rank.domain.award;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Data
@ToString
public class CommonAward implements Serializable {
    private int id;
    //奖励名称
    private String name;
    //奖励图片
    private String icon;
    //奖励发放类型 1礼物 2头像框 3座驾 4勋章 5金币 6贵族 7背景 8虚拟道具 999实物 -1空奖励
    private int releaseType;
    //奖励发放对应id
    private String releaseId;
    //价值
    private int price;
    //显示价值
    private int priceDisplay;
    //扩展信息
    private String extend;
    private Date ctime;
    private Date utime;
}
