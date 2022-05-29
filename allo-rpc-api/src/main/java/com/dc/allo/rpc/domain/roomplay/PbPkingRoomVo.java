package com.dc.allo.rpc.domain.roomplay;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PbPkingRoomVo {
    private Long  leftRoomUid;
    private String leftRoomAvatar;
    private String leftRoomNick;
    private Double leftTotalGold;
    private Integer leftPersonNum;
    private Double leftComcatPower;
    private Long  rightRoomUid;
    private String rightRoomAvatar;
    private String rightRoomNick;
    private Double rightTotalGold;
    private Integer rightPersonNum;
    private Double rightComcatPower;
}
