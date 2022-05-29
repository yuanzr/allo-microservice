package com.dc.allo.rank.domain.bet;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
public class BetResult implements Serializable {

    //id
    private long id;
    //活动id
    private long actId;
    //游戏id
    private long gameId;
    private long uid;
    //投注精灵id
    private long spiritId;
    //胜方精灵id
    private long winSpiritId;
    //是否胜方（0败方；1胜方）
    private int isWin;
    //此轮总支付额
    private long payTotalAmount;
    //此轮拿回本金（无庄模式为抽成后的数额）
    private long takeBackAmount;
    //奖励总金额（无庄模式为抽成后的数额）
    private long awardAmount;
    //发放奖励礼包实际内容（礼包礼物json串）
    private String packageContent;
    //发奖状态0失败，1成功（所有奖励发放成功才成功），2无奖励
    private int awardStatus;
    //备注
    private String remark;
    //动画数据
    private String animations;
    //创建时间
    private Date ctime;
    //更新时间
    private Date utime;
}
