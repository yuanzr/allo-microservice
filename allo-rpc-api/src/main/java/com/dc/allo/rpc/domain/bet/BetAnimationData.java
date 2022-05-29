package com.dc.allo.rpc.domain.bet;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/6/17.
 */
@Data
public class BetAnimationData implements Serializable {
    List<BetRacingCarAnimationData> racingCarAnimations;
}
