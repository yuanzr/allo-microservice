package com.dc.allo.activity.domain.vo.camel;

import com.dc.allo.rpc.domain.bet.BetActInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/6/23.
 */
@Data
public class BetActInfoResp implements Serializable {
    private BetActInfo actInfo;
}
