package com.dc.allo.common.domain.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomGiftSendVo {
    private Long roomUid;
    private BigDecimal totalGoldNum;
}
