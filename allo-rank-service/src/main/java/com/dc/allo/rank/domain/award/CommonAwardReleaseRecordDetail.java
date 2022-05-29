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
public class CommonAwardReleaseRecordDetail implements Serializable {
    private long id;
    //用户id
    private long uid;
    //唯一序号
    private String seqId;
    //奖品id
    private int awardId;
    //奖品名字
    private String awardName;
    //奖品图片
    private String awardIcon;
    //发放类型
    private int awardReleaseType;
    //发放id
    private String awardReleaseId;
    //发放数量
    private int awardCount;
    //有效期
    private int awardValidDays;
    //价值
    private int awardPrice;
    //扩展信息
    private String awardExtend;
    //0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足
    private int status;
    //备注
    private String remark;
    private Date ctime;
    private Date utime;
}
