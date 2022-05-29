package com.dc.allo.activity.service;

import com.dc.allo.activity.domain.vo.recharge.PrizeRankActItem;
import java.util.List;

/**
 * description: 首充
 * date: 2020年05月11日 14:55
 * author: qinrenchuan
 */
public interface FirstRechargeService {
    /**
     * 首充奖品查询
     * @param actType
     * @author qinrenchuan
     * @date 2020/5/11/0011 14:59
     */
    List<PrizeRankActItem> queryFirstRechargePrize(Byte actType);
}
