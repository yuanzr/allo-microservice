package com.dc.allo.activity.domain.vo.camel;

import com.dc.allo.rpc.domain.bet.BetGameRoundInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/6/15.
 */
@Data
public class BetGameRoundInfoResp implements Serializable{
    BetGameRoundInfo gameRoundInfo;
}
