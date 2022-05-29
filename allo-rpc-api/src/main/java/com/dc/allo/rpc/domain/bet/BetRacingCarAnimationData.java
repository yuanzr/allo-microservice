package com.dc.allo.rpc.domain.bet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/6/16.
 */
@Data
public class BetRacingCarAnimationData implements Serializable {
    //时间点
    int time;
    //离起点距离
    int distance;
}
