package com.dc.allo.rank.domain.rank.config;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankScoreCalcConfig implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 榜单id
     */
    private Long rankId;

    /**
     * 系数（用来乘以原分值得到最终分值）
     */
    private BigDecimal coefficient;

    /**
     * 生效业务id(用来实现只对指定礼物分值加倍类型的需求)
     */
    private String bizId;

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