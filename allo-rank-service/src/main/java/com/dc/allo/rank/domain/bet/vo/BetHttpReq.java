package com.dc.allo.rank.domain.bet.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangzhenjun on 2020/6/22.
 */
@Data
public class BetHttpReq implements Serializable {
    private long uid;
    private BigDecimal goldNum;
    private byte objType;
    private String sign;
}
