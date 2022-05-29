package com.dc.allo.rank.domain.activity;

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
public class Activity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * appid
     */
    private Integer appId;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动描述
     */
    private String remark;

    /**
     * 活动类型：1榜单、2抽奖等
     */
    private Integer type;

    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stime;

    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date etime;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;

}