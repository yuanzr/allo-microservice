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
public class CommonAwardReleaseRecord implements Serializable {
    private long id;
    //用户uid
    private long uid;
    //礼包id
    private int packageId;
    //礼包名（冗余）
    private String packageName;
    //礼包图片（冗余）
    private String packageIcon;
    //唯一序号
    private String seqId;
    //状态 0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足
    private int status;
    //礼包内礼物列表json备份
    private String awardBackUp;
    private Date ctime;
    private Date utime;
}
