package com.dc.allo.task.domain.entity;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: CommonTaskType
 *
 * @date: 2020年05月26日 11:09
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonTaskType {
    private Long id;
    private Long taskId;
    private String name;
    private Date createTime;
    private Date updateTime;
}
