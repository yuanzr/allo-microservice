package com.dc.allo.common.domain.user;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class UserHeadwearVo implements Comparable<UserHeadwearVo>, Serializable {
    private Long uid;
    private Integer headwearId;

    private String headwearName;

    private Boolean used;

    private Date buyTime;

    private Date expireTime;

    private Integer expireDays;

    private Byte status;

    private String pic;

    private String effect;

    private BigDecimal price;

    private BigDecimal renewPrice;

    private Integer days;

    private Byte comeFrom;

    private Boolean isSale;

    private Byte labelType;
    private Boolean nobleLimit;
    private String limitDesc;

    private BigDecimal originalPrice;

    private String redirectLink;
    private Boolean goldSale;//是否支持金币购买

    private Boolean radishSale;//是否支持萝卜购买

    private BigDecimal radishPrice; //萝卜购买价格

    private BigDecimal radishRenewPrice;//萝卜续费价格

    private BigDecimal radishOriginalPrice;//萝卜原价

    private String priceStr;//价格描述字符串，客户端直接显示
    private String name;
    private Boolean enableStatus;
    private Integer nobleId; //贵族等级
    private Boolean free;    //是否免费
    private Integer currencyType;
    private String webp;

    @Override
    public int compareTo(UserHeadwearVo o) {
        if(o.getStatus()>this.getStatus()){
            return -1;
        }else if(o.getStatus()<this.getStatus()){
            return 1;
        }else {
            if(o.getEnableStatus()){
                return 1;
            }else if(!o.getEnableStatus()){
                return -1;
            }
            return 0;
        }
    }
}
