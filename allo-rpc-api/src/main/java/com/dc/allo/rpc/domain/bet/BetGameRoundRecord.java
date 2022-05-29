package com.dc.allo.rpc.domain.bet;

import com.dc.allo.rpc.domain.bet.BetAnimationData;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/6/11.
 */
@Data
public class BetGameRoundRecord implements Serializable {
    //id
    private long id;
    //活动id
    private long actId;
    //参与活动精灵json串
    private List<BetSpiritInfo> spirits;
    //获胜精灵id
    private long winSpiritId;
    //游戏状态     0游戏中；1动画中；2结果展示中
    private int gameStatus;
    //动画数据
    private Map<Long,BetAnimationData> animations;
    //开始时间
    private long stime;
    //动画开始时间
    private long atime;
    //结果展示开始时间
    private long rtime;
    //结束时间
    private long etime;

}
