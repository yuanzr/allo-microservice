package com.dc.allo.rpc.domain.bet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@ToString
@Getter
@Setter
public class BetWinSpiritInfo implements Serializable {
    private long spiritId;
    private int supportNum;
    private long totalAmount;
    private String luckUserNick;
    private String luckUserLogo;
    private long luckAmount;
}
