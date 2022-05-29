package com.dc.allo.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 房主热度值奖励
 */
@Data
public class RoomOwnerHeatValueAward {
    private Long roomUid;
    private Long uid;
    private BigDecimal goldNum;
    private Integer nobleId;
    //赠送贵族多少天
    private Integer nobleDay;
    //1房主奖励 2贡献榜奖励
    private Byte type;
    private Integer carId;
    //赠送座驾多少天
    private Integer carDay;
    private Integer headwearId;
    //赠送头饰多少天
    private Integer headwearDay;
}
