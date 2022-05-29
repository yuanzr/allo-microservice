package com.dc.allo.activity.domain.vo.recharge;

import lombok.Data;

/**
 * description: FirstRechargePrizeVO
 *
 * @date: 2020年05月11日 14:43
 * @author: qinrenchuan
 */
@Data
public class FirstRechargePrizeVO {
    private Long prizeId;
    private String prizeName;
    /** 1 Coins ;  3 Car ;  4 Headwear ; 2 Gift ; 14 Aristocratic ; 15 Badge */
    private Byte prizeType;
    private String prizePic;
    private Integer prizeNum;
    /** 奖品单位: 1.day;2.week;3.month;4.year;5.forever 。为0时为特殊情况。比如当prizeType为1和2时该值为0，表示金币和礼物XXX个，也可以理解为单位为个 */
    private Byte unit;
}
