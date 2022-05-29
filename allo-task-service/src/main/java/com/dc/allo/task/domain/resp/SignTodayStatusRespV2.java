package com.dc.allo.task.domain.resp;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * description: SignTodayStatusResp
 *
 * @date: 2020年06月03日 19:03
 * @author: qinrenchuan
 */
@Data
@ToString
public class SignTodayStatusRespV2 implements Serializable {

    //private Boolean signed;
    //private Long uid;
    private Integer signed;
}
