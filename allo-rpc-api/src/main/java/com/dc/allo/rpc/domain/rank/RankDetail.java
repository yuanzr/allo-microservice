package com.dc.allo.rpc.domain.rank;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankDetail implements Serializable {
    /**
     * 榜单成员id
     */
    private String id;
    /**
     * 榜单成员type 1：用户 2：房间
     */
    private int rankIdType;
    /**
     * 排名
     */
    private long rank;
    /**
     * 分值
     */
    private double score;

    /**
     * 业务展示id
     */
    private String bizId;

    /**
     * 业务展示名称
     */
    private String name;

    /**
     * 业务展示图像
     */
    private String avatar;
    /**
     * 关联榜单列表
     */
    private List<RankDetail> relationRankList;
}
