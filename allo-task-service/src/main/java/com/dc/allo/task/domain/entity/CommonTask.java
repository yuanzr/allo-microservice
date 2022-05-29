package com.dc.allo.task.domain.entity;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: CommonTask
 *
 * @date: 2020年05月26日 11:08
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonTask {
    private Long id;
    private String name;
    private Byte code;

    private Byte status;
    private Byte levelNum;
    private Date createTime;
    private Date updateTime;
}
