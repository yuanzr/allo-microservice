package com.dc.allo.rpc.domain.user.headwear;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: Headwear
 *
 * @date: 2020年04月07日 17:14
 * @author: qinrenchuan
 */
@Data
@ToString
public class Headwear implements Serializable {
    private Integer headwearId;
    private String name;
    /** 预览图链接 */
    private String pic;
    /** 动效图链接 */
    private String effect;
    /** 购买价格 */
    private BigDecimal price;
    /** 续费价格 */
    private BigDecimal renewPrice;
    /** 是否启用，0否，1是 */
    private Boolean enable;
    /** 排序，从大到小 */
    private Integer seq;
    /** 有效天数 */
    private Integer days;
    /** 是否贵族限定 */
    private Boolean nobleLimit;
    /** 是否怪兽限定 */
    private Boolean monsterLimit;
    /** 是否周星榜限定 */
    private Boolean weekStarLimit;
    /** 活动限定 */
    private Boolean activityLimit;
    /** 限定描述 */
    private String limitDesc;
    private Date createTime;
    private Date updateTime;
    /** 是否可售卖 */
    private Boolean isSale;
    /** 0、无   1、新品   2、折扣  3、限定   4、专属  5钻石  6银币 */
    private Byte labelType;
    /** 原价 */
    private BigDecimal originalPrice;
    /** 跳转链接 */
    private String redirectLink;
    /** 是否支持金币购买 */
    private Boolean goldSale;
    /** 是否支持萝卜购买 */
    private Boolean radishSale;
    /** 萝卜首次价格 */
    private BigDecimal radishPrice;
    /** 萝卜续费价格 */
    private BigDecimal radishRenewPrice;
    /** 萝卜原价 */
    private BigDecimal radishOriginalPrice;
    /** 货币类型:0金币购买1钻石购买2银币购买 */
    private Integer currencyType;
    /** 贵族等级;默认0 */
    private Integer nobleId;
    /** webp资源路径 */
    private String webp;
}
