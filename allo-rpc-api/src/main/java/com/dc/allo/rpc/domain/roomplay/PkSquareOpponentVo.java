package com.dc.allo.rpc.domain.roomplay;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PkSquareOpponentVo {
    private Long roomUid;
    private String roomAvatar;
    private String roomNick;
    private Byte pkStatus;
    private Double currentCombatPower;
    private Integer todayWinNum;
    private Integer roomOnlineNum;

    //跟查询当前pk广场的房间的绝对值,这个字段不存入缓存,仅用在pk广场使用
    private Double currentOpponentAbs;
}
