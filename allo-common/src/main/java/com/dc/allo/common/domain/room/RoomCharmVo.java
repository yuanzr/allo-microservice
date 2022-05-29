package com.dc.allo.common.domain.room;

import lombok.Data;

import java.util.List;

/**
 * 房间魅力值相关信息
 */
@Data
public class RoomCharmVo {
    //是否显示礼物值
    private boolean showCalculator;

    private List<RoomCharmDetailVo> giftValueVos;

    private Long currentTime;

    private Long roomUid;
}
