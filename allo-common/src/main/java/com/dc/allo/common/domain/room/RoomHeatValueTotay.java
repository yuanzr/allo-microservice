package com.dc.allo.common.domain.room;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RoomHeatValueTotay implements Serializable {
    private Long id;

    private Long roomUid;

    private String roomAvatar;

    private String roomTitle;

    private BigDecimal heatValue;

    private String mvpAvatar;

    private String mvpNick;

    private Long mvpUid;

    private Byte day;
}