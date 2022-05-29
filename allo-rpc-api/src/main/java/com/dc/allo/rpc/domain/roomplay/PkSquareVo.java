package com.dc.allo.rpc.domain.roomplay;

import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class PkSquareVo {
    private Double currentCombatPower;
    private Long roomUid;
    private String roomTitle;
    private String roomAvatar;
    //旗鼓相当的对手
    private List<PkSquareOpponentVo> doughtyOpponentList;
    //其他对手
    private List<PkSquareOpponentVo> otherOpponentList;
    private long zone;
}
