package com.dc.allo.rpc.domain.finance;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Data
public class GiftSendRecord implements Serializable{
    private long sendRecordId;

    private long uid;

    private long reciveUid;

    private Byte reciveType;

    private Byte sendEnv;

    private long roomUid;

    private Byte roomType;

    private Integer giftId;

    private Integer giftNum;

    private Byte giftType;

    private Boolean playEffect;

    private BigDecimal totalGoldNum;

    private Double totalDiamondNum;

    private Date createTime;

    private Integer giftSource;

    private BigDecimal totalCrystalNum;
}
