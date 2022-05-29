package com.dc.allo.rpc.domain.bet;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/6/16.
 */
@Data
public class BetAnimationInfo implements Serializable {
    int animationModelType;
    Map<Long,BetAnimationData> animations;
}
