package com.dc.allo.rank.domain.rank.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
@Data
public class RankAutoRewardDetail implements Serializable {

    private long id;
    //榜单id
    private long rankId;
    //榜单奖励配置id
    private long awardConfigId;
    //获奖成员类型 1用户；2房间
    private int memberType;
    //获奖成员Id
    private long memberId;
    //发奖结果
    private boolean awardResult;
    //发奖时间
    private Date awardTime;
}
