package com.dc.allo.rpc.domain.roomplay;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class RoomPkRecord {
    private Long id;

    private Long roomUid;

    private Long targetRoomUid;

    private String roomTitle;

    private String targetRoomTitle;

    private Byte pkStatus;

    private Byte pkResult;

    private Date pkStartTime;

    private Date pkEndTime;

    private String punishment;

    private BigDecimal roomTotalGold;

    private Short roomTotalPerson;

    private BigDecimal targetTotalGold;

    private Short targetTotalPerson;

    private Long sponsorUid;

    private Long winUid;

    private Long loseUid;

    private Date createTime;

    private String roomAvatar;

    private String targetRoomAvatar;

    private Long winMvpUid;

    private String winMvpAvatar;

    private BigDecimal winCombatPower;

    private Short inviteInvalidTime;
}