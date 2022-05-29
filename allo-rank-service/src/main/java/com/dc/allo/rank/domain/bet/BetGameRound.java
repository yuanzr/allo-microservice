package com.dc.allo.rank.domain.bet;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
public class BetGameRound implements Serializable {
    //id
    private long id;
    //活动id
    private long actId;
    //参与活动精灵json串
    private String spirits;
    //获胜精灵id
    private long winSpiritId;
    //每个精灵下注总额（json，精灵id和总投注额）
    private String betAmounts;
    //游戏状态     0游戏中；1动画中；2结果展示中
    private int gameStatus;
    //是否已结算（0未结算，1已结算）
    private int isSettle;
    //动画数据
    private String animations;
    //转场动画时长
    private int transAnimationDuration;
    //开始时间
    private Date stime;
    //动画开始时间
    private Date atime;
    //结果展示开始时间
    private Date rtime;
    //结束时间
    private Date etime;
}
