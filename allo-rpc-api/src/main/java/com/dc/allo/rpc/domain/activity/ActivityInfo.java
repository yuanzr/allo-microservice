package com.dc.allo.rpc.domain.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/16.
 */
@Data
public class ActivityInfo implements Serializable {
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

    private ActivityBgInfo activityBgInfo;

    private ActivityRuleInfo activityRuleInfo;

    private ActivityRankInfo activityRankInfo;

    private List<ActivityGiftInfo> activityGiftInfos;
}
