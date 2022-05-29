package com.dc.allo.common.domain.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
public class CarportVo implements Comparable<CarportVo>, Serializable {

    private Long uid;
    private Integer id;
    private String name;
    private String pic;      // 预览图
    private String effect;   // 动效
    private BigDecimal price;      // 正常购买
    private BigDecimal renewPrice; // 续费价格
    private Byte using;
    private Boolean isGive; // 是否赠送标识
    private Byte status;        // 1下架，2过期，3有效
    private Long updateTime;    // 更新时间
    private Date expireTime;    // 失效时间
    private Integer expireDate; // 剩余多少天
    private Integer days;       // 购买的商品天数
    private Boolean isSale; //是否可售
    private Byte labelType; //限定类型类型
    private String limitDesc; //限定描述
    private BigDecimal originalPrice; //原价
    private String redirectLink; //链接
    private Boolean free; //是否免费

    private Boolean goldSale;//是否支持金币购买

    private Boolean radishSale;//是否支持萝卜购买

    private BigDecimal radishPrice; //萝卜购买价格

    private BigDecimal radishRenewPrice;//萝卜续费价格

    private BigDecimal radishOriginalPrice;//萝卜原价

    private String priceStr;//价格描述字符串，客户端直接显示
    private Boolean enableStatus;

    private Integer nobleId;

    private Integer currencyType;

    private Byte activityCarType;

    private Byte showUserAvatar;
    //活动座驾用户的头像
    private List<String> activityCarImgs;

    public Byte getActivityCarType() {
        return activityCarType;
    }

    public void setActivityCarType(Byte activityCarType) {
        this.activityCarType = activityCarType;
    }

    public List<String> getActivityCarImgs() {
        return activityCarImgs;
    }

    public void setActivityCarImgs(List<String> activityCarImgs) {
        this.activityCarImgs = activityCarImgs;
    }

    public Byte getShowUserAvatar() {
        return showUserAvatar;
    }

    public void setShowUserAvatar(Byte showUserAvatar) {
        this.showUserAvatar = showUserAvatar;
    }

    public Integer getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(Integer currencyType) {
        this.currencyType = currencyType;
    }

    public Boolean getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Boolean enableStatus) {
        this.enableStatus = enableStatus;
    }
    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public Boolean getGoldSale() {
        return goldSale;
    }

    public void setGoldSale(Boolean goldSale) {
        this.goldSale = goldSale;
    }

    public Boolean getRadishSale() {
        return radishSale;
    }

    public void setRadishSale(Boolean radishSale) {
        this.radishSale = radishSale;
    }

    public BigDecimal getRadishPrice() {
        return radishPrice;
    }

    public void setRadishPrice(BigDecimal radishPrice) {
        this.radishPrice = radishPrice;
    }

    public BigDecimal getRadishRenewPrice() {
        return radishRenewPrice;
    }

    public void setRadishRenewPrice(BigDecimal radishRenewPrice) {
        this.radishRenewPrice = radishRenewPrice;
    }

    public BigDecimal getRadishOriginalPrice() {
        return radishOriginalPrice;
    }

    public void setRadishOriginalPrice(BigDecimal radishOriginalPrice) {
        this.radishOriginalPrice = radishOriginalPrice;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Boolean getGive() {
        return isGive;
    }

    public void setGive(Boolean give) {
        isGive = give;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getRenewPrice() {
        return renewPrice;
    }

    public void setRenewPrice(BigDecimal renewPrice) {
        this.renewPrice = renewPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Byte getUsing() {
        return using;
    }

    public void setUsing(Byte using) {
        this.using = using;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Integer expireDate) {
        this.expireDate = expireDate;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsGive() {
        return isGive;
    }

    public void setIsGive(Boolean isGive) {
        this.isGive = isGive;
    }

    public Boolean getIsSale() {
        return isSale;
    }

    public void setIsSale(Boolean isSale) {
        this.isSale = isSale;
    }

    public Byte getLabelType() {
        return labelType;
    }

    public void setLabelType(Byte labelType) {
        this.labelType = labelType;
    }

    public String getLimitDesc() {
        return limitDesc;
    }

    public void setLimitDesc(String limitDesc) {
        this.limitDesc = limitDesc;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }

    public Integer getNobleId() {
        return nobleId;
    }

    public void setNobleId(Integer nobleId) {
        this.nobleId = nobleId;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    @Override
    public int compareTo(CarportVo obj) {
        if (obj.getStatus() > this.getStatus()) {
            return 1;
        } else if (obj.getStatus() < this.getStatus()) {
            return -1;
        } else {
            if (obj.getUpdateTime() > this.getUpdateTime()) {
                return 1;
            } else if (obj.getUpdateTime() < this.getUpdateTime()) {
                return -1;
            }
            return 0;
        }
    }
}
