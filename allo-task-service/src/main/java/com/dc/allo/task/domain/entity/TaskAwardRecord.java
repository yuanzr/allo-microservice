package com.dc.allo.task.domain.entity;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: TaskAwardRecord
 *
 * @date: 2020年05月27日 17:17
 * @author: qinrenchuan
 */
@Data
@ToString
public class TaskAwardRecord {
    private Long id;
    /** 任务编码 */
    private Byte actType;
    /** 业务ID。签到对应 task_sign_record表的id*/
    private Long businessId;
    private Long actPrizeId;
    private Byte prizeType;
    private Integer actualPrizeId;
    private String prizeName;
    private String prizeImgUrl;
    private Integer prizeValue;
    private Byte timeUnit;
    private Integer timeNum;
    private Date createTime;
}
