package com.dc.allo.common.domain.user;

import java.io.Serializable;
import java.math.BigDecimal;

public class BadgeResVo implements Serializable {
    private Long resId;

    private Byte badgeCode;

    private Byte badgeType;

    private Byte isSale;

    private Byte currencyType;

    private BigDecimal price;

    private Integer day;

    private Byte status;

    private Integer badgeLevel;

    private String badgePicMin;

    private String badgePicMax;

    private String badgeName;

    private String badgeDes;

    private Boolean isOwn;//是否已经拥有
}