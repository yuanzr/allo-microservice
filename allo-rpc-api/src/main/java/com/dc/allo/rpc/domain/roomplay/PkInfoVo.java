package com.dc.allo.rpc.domain.roomplay;

import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserVo;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class PkInfoVo {
    private Long leftRoomUid;

    private Long rightRoomUid;

    private String leftRoomTitle;

    private String rightRoomTitle;

    private Date pkStartTime;

    private Date pkEndTime;

    private Byte punishment;

    private Double leftTotalGold;

    private Short leftTotalPerson;

    private Double rightTotalGold;

    private Short rightTotalPerson;
    private List<SimpleUserVo> leftUserVoList;
    private List<SimpleUserVo> rightUserVoList;

    private Long recordId;
    //左边Pk的武器样子
    private String leftPkArms;
    //右边Pk的武器样子
    private String rightPkArms;

    private String leftRoomAvatar;

    private String rightRoomAvatar;
    //左边房间战力值
    private Double leftRoomCombatPower;
    private Byte pkStatus;
}
