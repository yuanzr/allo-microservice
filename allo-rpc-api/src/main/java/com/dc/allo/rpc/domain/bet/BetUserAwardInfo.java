package com.dc.allo.rpc.domain.bet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/6/15.
 */
@Data
public class BetUserAwardInfo implements Serializable{
    long packageId;
    long awardId;
    String name;
    String icon;
    int awardCount;
}
