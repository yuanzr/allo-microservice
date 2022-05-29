package com.dc.allo.rank.redis.message;

import com.dc.allo.rank.domain.bet.vo.BetGameInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Data
public class BetMessage implements Serializable {
    private int msgType;
    BetGameInfo gameInfo;
}
