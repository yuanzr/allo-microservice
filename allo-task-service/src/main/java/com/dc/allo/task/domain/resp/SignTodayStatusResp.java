package com.dc.allo.task.domain.resp;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * description: SignTodayStatusResp
 *
 * @date: 2020年06月03日 19:03
 * @author: qinrenchuan
 */
@Data
@ToString
public class SignTodayStatusResp implements Serializable {

    private static final long serialVersionUID = -6382995003010743399L;
    private Boolean signed;
}
