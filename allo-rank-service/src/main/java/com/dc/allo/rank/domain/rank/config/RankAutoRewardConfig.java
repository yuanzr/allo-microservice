package com.dc.allo.rank.domain.rank.config;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
@Data
public class RankAutoRewardConfig implements Serializable{

    private long id;
    //榜单id
    private long rankId;
    //奖励类型：0 连续 ；1 一次性（总榜）
    private int awardType;
    //发奖礼包id
    private long awardPackageId;
    //开始名次，包含
    private int begin;
    //结束名次，包含
    private int end;
    //发奖门槛
    private long limitScore;
}
