package com.dc.allo.common.domain.room;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class RoomHeatValuePrizeRecord {
    private Long id;

    private Long roomUid;

    private String uid;

    private Byte type;

    private BigDecimal goldNum;

    private String mvpAvatar;

    private String mvpNick;

    private BigDecimal totalGoldNum;

    private Byte rate;

    private String userReward;

    private Date createTime;
}