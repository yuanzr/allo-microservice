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
public class RankScoreNoticeConfig implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 榜单id
     */
    private Long rankId;

    /**
     * 分值列表(多个用逗号分隔，达到这些分值时触发分值提醒事件)
     */
    private String scoreList;

    /**
     * 分值步长（每达到步长分值时触发分值提醒）
     */
    private Integer scoreStep;

    /**
     * 生效开始时间
     */
    private Date startTime;

    /**
     * 生效结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;


}