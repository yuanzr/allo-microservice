package com.dc.allo.task.domain.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class PrizeRankActItem implements Serializable {
    private static final long serialVersionUID = 4192301508802823606L;
    private Long actPrizeId;
    private Byte prizeType;
    private Integer referenceId;
    private String prizeName;
    private String prizeImgUrl;
    private Integer prizeValue;
    private Byte timeUnit;
    private Integer timeNum;
    /** 获奖uid */
    private Long uid;
}