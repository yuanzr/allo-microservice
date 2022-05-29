package com.dc.allo.common.vo;

import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import lombok.Data;

import java.util.List;

@Data
public class RoomActivityRankVo {
    //剩余时间单位秒
    private Integer remainTime;
    private List<RoomHeatValueTotay> roomHeatValueTotayList;
}
