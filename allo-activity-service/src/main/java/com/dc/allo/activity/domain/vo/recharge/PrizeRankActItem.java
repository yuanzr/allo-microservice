package com.dc.allo.activity.domain.vo.recharge;

import lombok.Data;

@Data
public class PrizeRankActItem {
    private Long actPrizeId;
    private Byte prizeType;
    private Integer referenceId;
    private String prizeName;
    private String prizeImgUrl;
    private Integer prizeValue;
    private Byte rankSeq;
    private Byte timeUnit;
    private Integer timeNum;
    private Byte seq;
}