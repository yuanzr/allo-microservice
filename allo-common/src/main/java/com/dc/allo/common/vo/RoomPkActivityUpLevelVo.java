package com.dc.allo.common.vo;

import lombok.Data;

@Data
public class RoomPkActivityUpLevelVo {
    private Long roomUid;
    private Byte level;
    private boolean upLevel;
}
