package com.dc.allo.rpc.domain.bet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Data
public class BetAwardInfo implements Serializable{
    private int awardTarget;
    private long packageId;
    private String packageName;
    private int price;
    private int realPrice;
    private String url;
}
