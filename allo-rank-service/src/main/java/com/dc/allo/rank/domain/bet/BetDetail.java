package com.dc.allo.rank.domain.bet;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
public class BetDetail implements Serializable {

    //id
    private long id;
    //活动id
    private long actId;
    //游戏id
    private long gameId;
    //精灵id
    private long spiritId;
    private long uid;
    //下注额
    private int payPrice;
    //支付状态
    private int payStatus;
    //支付序号
    private String paySeqId;
    //创建时间
    private Date ctime;
    //更新时间
    private Date utime;
}
