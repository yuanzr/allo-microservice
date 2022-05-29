package com.dc.allo.rank.domain.award;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Data
@ToString
public class CommonAwardPackage implements Serializable {
    private int id;
    //礼包名称
    private String name;
    //礼包图片
    private String icon;
    //是否需要概率
    private boolean needRate;
    //是否需要购买
    private boolean needPurchase;
    //购买类型 see com.dc.allo.common.constants.AwardEnums.PurchaseType
    private int purchaseType;
    //价格
    private int price;
    //总库存
    private int totalCount;
    //已发放数量
    private int releaseCount;
    //每日限额
    private int dayLimitCount;
    //是否启用
    private boolean enable;
    private Date ctime;
    private Date utime;
}
