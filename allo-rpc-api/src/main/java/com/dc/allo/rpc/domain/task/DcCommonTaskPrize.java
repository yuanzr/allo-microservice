package com.dc.allo.rpc.domain.task;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName: DcCommonTaskPrize
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/7/1/15:54
 */
@Data
public class DcCommonTaskPrize implements Serializable{
    private static final long serialVersionUID = -4213537295603471804L;

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
