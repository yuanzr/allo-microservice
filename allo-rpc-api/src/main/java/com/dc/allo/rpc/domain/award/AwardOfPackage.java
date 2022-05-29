package com.dc.allo.rpc.domain.award;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Data
@ToString
public class AwardOfPackage implements Serializable {
    private int id;
    private int awardId;
    private int packageId;
    private int actId;
    private String name;
    private String icon;
    private int releaseType;
    private String releaseId;
    private int price;
    private int priceDisplay;
    private Double rate;
    private Double displayRate;
    private int dailyCountLimit;
    private int totalCount;
    private int releaseCount;
    private int awardCount;
    private int validDays;
    private String extend;
    private String remark;
}
