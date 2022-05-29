package com.dc.allo.rank.domain.rank.config;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankWhiteBlackList implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 生效域（1app全局域 2指定榜单域 全局域优先级高于榜单域）
     */
    private Integer scope;

    /**
     * appid
     */
    private Integer appId;

    /**
     * 榜单id（app全局域时该字段值为null）
     */
    private Long rankId;

    /**
     * 白名单or黑名单（同一个rank_data_id不能同时存在于白名单和黑名单）
     */
    private Integer whiteBlack;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;

    /**
     * 名单id（根据榜单的方向取recvId或者sendId来判断是否存在于该列表，存在才记录(白名单)or不记录(黑名单)）
     */
    private String rankDataId;


}