package com.dc.allo.rank.domain.bet;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
public class BetActConfig implements Serializable{
    //id
    private long id;
    //活动名称
    private String name;
    //每轮时长
    private int gameDuration;
    //转场动画时间，默认3秒
    private int transAnimationDuration;
    //动画时间
    private int animationDuration;
    //结果展示时长
    private int showDuration;
    //开始时间
    private Date stime;
    //结束时间
    private Date etime;
    //做庄模式（0官方；1无庄）
    private int bankerModel;
    //投注模式（0单次；1多次）
    private int betModel;
    //动画模式 (0赛车类；）
    private int animationModel;
    //告警分值，官方做庄有效，单局亏损超过分值，触发结算结果重选（默认0，表示不告警）
    private long warnScore;
    //抽成比例，无庄模式有效，默认0，表示不抽成
    private double deductRate;
    //支付方式（1 金币）
    private int payType;
    //随机上场精灵数量
    private int joinSpiritNum;
    //投注金额配置，逗号分割
    private String payPrices;
    //备注
    private String remark;
}
