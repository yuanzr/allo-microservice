package com.dc.allo.rank.domain.rank.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankConfig implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * appKey
     */
    private String appKey;

    /**
     * 榜单名
     */
    private String name;

    /**
     * 榜单key(用于生成榜单redis key 不能重复)
     */
    private String rankKey;

    /**
     * 数据来源（只支持单选）
     */
    private Integer dataSourceId;

    /**
     * 榜单时间类型 1小时、2日、3周、4月、5年、99不限时
     */
    private Integer timeType;

    /**
     * 计算方式（1按分值累计、2按次累计）
     */
    private Integer calcType;

    /**
     * 指定记录业务id（只记录该业务id的榜单数据流水，可以用来实现周星榜类型的榜单）
     */
    private boolean divideByBizId;

    /**
     * 成员类型(1用户，2房间)
     */
    private Integer memberType;

    /**
     * 是否限定频道分类
     */
    private boolean specifyRoomType;

    /**
     * 频道分类id
     */
    private Integer roomTypeId;

    /**
     * 方向(0无方向 1接收方的榜单 2发送方的榜单  房间榜不存在方向的概念)
     */
    private Integer direction;

    /**
     * 是否生成关联榜单（对于用户榜：关联榜单是每个人的反方向子榜，对于频道榜：关联榜单是频道内送礼and收礼榜）
     */
    private Boolean genRelation;

    /**
     * 榜单记录开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 榜单记录结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 过期时间单位
     */
    private Integer expireUnit;

    /**
     * 过期时间值
     */
    private Integer expireValue;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;

}