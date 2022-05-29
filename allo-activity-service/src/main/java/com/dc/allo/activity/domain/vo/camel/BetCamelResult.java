package com.dc.allo.activity.domain.vo.camel;

import com.dc.allo.rpc.domain.bet.BetUserAwardInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/6/18.
 */
@Data
public class BetCamelResult implements Serializable {
    private long spiritId;
    private long payTotalAmount;
    private long awardAmount;
    private boolean isWin;
    private BetUserAwardInfo awardInfo;
}
