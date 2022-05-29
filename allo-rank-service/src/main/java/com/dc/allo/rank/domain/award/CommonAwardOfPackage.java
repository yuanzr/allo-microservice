package com.dc.allo.rank.domain.award;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Data
public class CommonAwardOfPackage implements Serializable {
    private int id;
    private long actId;
    //礼包id
    private int packageId;
    //奖励id
    private int awardId;
    //概率
    private double rate;
    //展示概率
    private double displayRate;
    //每日中奖上限
    private int dailyCountLimit;
    //总库存，-1无限
    private int totalCount;
    //已发放数量
    private int releaseCount;
    //奖励数量
    private int awardCount;
    //有效时间
    private int validDays;
    //是否启用
    private boolean enable;
    private String remark;
    private Date ctime;
    private Date utime;
}
