package com.dc.allo.rpc.domain.finance.purse;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * 用户钱包
 * @author qinrenchuan
 * @date 2020/5/8/0008 18:29
 */
@Data@ToString
public class UserPurse {
    private Long uid;

    private BigDecimal chargeGoldNum;

    private BigDecimal nobleGoldNum;

    private BigDecimal goldNum;

    private Double diamondNum;

    private BigDecimal depositNum;

    private Boolean isFirstCharge;

    private Date firstRechargeTime;

    private Date updateTime;

}