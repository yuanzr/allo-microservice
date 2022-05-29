package com.dc.allo.rpc.domain.finance;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Data
public class BillRecord implements Serializable{
    private String billId;

    private Long uid;

    private Long targetUid;

    private Long roomUid;

    private Byte billStatus;

    private String objId;

    private Byte objType;

    private Integer giftId;

    private Integer giftNum;

    private Double diamondNum;

    private BigDecimal goldNum;

    private BigDecimal money;

    private Date createTime;

    private Date updateTime;

    private BigDecimal coinsBefore;

    private BigDecimal coinsAfter;

    private BigDecimal diamondBefore;

    private BigDecimal diamondAfter;

    private String remark;

}
