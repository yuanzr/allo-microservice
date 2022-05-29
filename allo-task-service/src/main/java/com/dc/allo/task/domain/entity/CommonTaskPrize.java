package com.dc.allo.task.domain.entity;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: CommonTaskPrize
 *
 * @date: 2020年05月26日 11:27
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonTaskPrize {
    private Long id;
    /** 任务ID */
    private Long taskId;
    /** 任务TYPE ID */
    private Long taskTypeId;
    /** 任务等级 */
    private Byte level;
    /** 礼包id */
    private Long packageId;
    private Date createTime;
    private Date updateTime;
}
