package com.dc.allo.rank.domain.bet.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangzhenjun on 2020/6/22.
 */
@Data
public class BetUserPurse implements Serializable {
    private long uid;
    private BigDecimal goldNum;
    private BigDecimal chargeGoldNum;
    private BigDecimal nobleGoldNum;
    private BigDecimal diamondNum;
    private boolean isFirstCharge;
}
