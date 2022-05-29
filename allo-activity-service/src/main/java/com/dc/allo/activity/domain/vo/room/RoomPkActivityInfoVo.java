package com.dc.allo.activity.domain.vo.room;

import com.dc.allo.common.domain.room.RoomHeatValueTotal;
import lombok.Data;

import java.util.List;

@Data
public class RoomPkActivityInfoVo extends RoomHeatValueTotal {
    private List<RoomPkActivityInfoMissionVo> missionList;
}
