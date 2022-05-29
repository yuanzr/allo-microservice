package com.dc.allo.rank.domain.bet.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/6/22.
 */
@Data
public class BetHttpResp implements Serializable {
    private int code;
    private String message;
    private BetUserPurse data;
}
