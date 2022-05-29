package com.dc.allo.common.kafka.message;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 礼物消息
 */
@Data
public class GiftMessage implements Serializable {

    private static final long serialVersionUID = -6801361516819484608L;
    private String messId;    // 消息唯一标识
    private Long messTime;    // 消息创建时间
    private Long sendUid;     // 发送者UID
    private Long recvUid;     // 接收者UID
    private Long roomUid;     // 房间主UID
    private Byte roomType;    // 房间类型
    private BigDecimal goldNum;     // 金币数量
    private Double diamondNum;// 钻石数量
    private Integer giftId;   // 礼物标识
    private Integer giftNum;  // 礼物数量
    private Byte sendType; // 赠送类型，如： 1对1送礼物，全麦送礼物
    private String msg; // 全服消息内容
    private Integer giftSource; //礼物来源，1:普通礼物，2:背包礼物
    private BigDecimal crystalNum;  // 水晶数量
    private Boolean commissionSwitch;//能不能获取提成 true为能

    @Override
    public String toString() {
        return "GiftMessage{" +
                "messId='" + messId + '\'' +
                ", sendUid=" + sendUid +
                ", recvUid=" + recvUid +
                ", roomUid=" + roomUid +
                ", roomType=" + roomType +
                ", goldNum=" + goldNum +
                ", diamondNum=" + diamondNum +
                ", giftId=" + giftId +
                ", giftNum=" + giftNum +
                ", sendType=" + sendType +
                ", messTime=" + messTime +
                ", msg=" + msg +
                '}';
    }
}
