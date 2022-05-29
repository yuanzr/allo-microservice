package com.dc.allo.rpc.domain.roomplay;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RoomPkSummary {
    private Long roomUid;

    private Long roomId;

    private Integer winTimes;

    private Integer loseTimes;

    private BigDecimal historyMaxScore;

    private BigDecimal totalGold;

    private Integer onlineMaxPerson;

    private Date createTime;

    private Date updateTime;

    private Integer drawTimes;
}