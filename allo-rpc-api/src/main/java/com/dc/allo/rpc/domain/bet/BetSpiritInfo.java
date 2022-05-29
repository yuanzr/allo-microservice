package com.dc.allo.rpc.domain.bet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Data
public class BetSpiritInfo implements Serializable {

    private long actId;
    private long spiritId;
    private String spiritName;
    private String url;
    private String svgaUrl;
    //获胜概率
    private double rate;
    //固定赔率
    private double odds;
    //获胜次数
    private int winNum;
    //失败次数
    private int lossNum;
    //单场游戏总投注
    private long totalAmount;
    //单场游戏总奖励
    private long totalAwardAmount;
    //单场游戏总盈亏
    private long winAmount;
    private String remark;
}
