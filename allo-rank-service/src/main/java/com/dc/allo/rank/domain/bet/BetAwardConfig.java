package com.dc.allo.rank.domain.bet;

import lombok.Data;
import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
public class BetAwardConfig implements Serializable {

    //id
    private long id;

    //活动id
    private long actId;

    //奖品id
    private long packageId;

    //领取目标（2败方；1胜方；3大奖得主）
    private int awardTarget;
}
