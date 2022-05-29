package com.dc.allo.activity.domain.vo.camel;

import com.dc.allo.rpc.domain.bet.BetAnimationData;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/6/17.
 */
@Data
public class BetCamelGameRecord implements Serializable {
    //参与活动精灵json串
    private List<BetSpiritInfo> spirits;
    //获胜精灵id
    private long winSpiritId;
    //动画数据
    private Map<Long,BetAnimationData> animations;
}
