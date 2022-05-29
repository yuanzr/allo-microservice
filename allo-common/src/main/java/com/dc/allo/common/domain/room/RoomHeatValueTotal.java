package com.dc.allo.common.domain.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomHeatValueTotal {
    private Long roomUid;

    private String roomAvatar;

    private String roomTitle;

    private BigDecimal heatValue;

    private Byte level;
}