package com.dc.allo.rpc.domain.bet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Data
public class BetGameSpiritInfo implements Serializable {
    private long spiritId;
    private String spiritName;
    private String url;
    private String svgaUrl;
    private double odds;
    private int winNum;
    private int lossNum;
    private long totalBetAmount;
    private long userBetAmount;
    private long supportNum;
}
