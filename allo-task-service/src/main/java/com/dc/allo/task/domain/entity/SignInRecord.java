package com.dc.allo.task.domain.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: 签到记录
 *
 * @date: 2020年05月26日 17:40
 * @author: qinrenchuan
 */
@Data
@ToString
public class SignInRecord implements Serializable {
    private static final long serialVersionUID = -5508469367047615156L;
    private Long id;
    private Long uid;
    private Date signTime;
    /** 本次签到的次数 */
    private Integer times;
}
