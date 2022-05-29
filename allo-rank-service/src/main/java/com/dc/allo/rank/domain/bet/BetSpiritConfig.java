package com.dc.allo.rank.domain.bet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
public class BetSpiritConfig implements Serializable {
    //id
    private long id;

    //活动id
    private long actId;

    //精灵id
    private long spiritId;

    //获胜概率
    private double rate;

    //固定赔率
    private double odds;

    //获胜次数
    private int winNum;

    //失败次数
    private int lossNum;

    //投注总数
    private long totalAmount;

    //获胜奖金（包括本金）
    private long totalAwardAmount;
}
