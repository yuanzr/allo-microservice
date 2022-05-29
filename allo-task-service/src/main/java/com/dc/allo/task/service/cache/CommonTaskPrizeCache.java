package com.dc.allo.task.service.cache;

import com.dc.allo.task.domain.entity.PrizeRankActItem;
import java.util.List;
import java.util.Map;

/**
 * description: CommonTaskPrizeCache
 * date: 2020年05月28日 11:41
 * author: qinrenchuan
 */
public interface CommonTaskPrizeCache {
    /**
     * 根据礼包id列表查询礼包内奖品（缓存五分钟）
     * @param packageIds
     * @return java.util.Map<java.lang.Long,java.util.List<com.dc.allo.task.domain.entity.PrizeRankActItem>>
     * @author qinrenchuan
     * @date 2020/5/28/0028 11:43
     */
    Map<Long, List<PrizeRankActItem>> queryPrizesByPackageIds(List<Long> packageIds);

    /**
     * 存入缓存
     * @param packagePrizeMap
     * @param items
     * @author qinrenchuan
     * @date 2020/5/28/0028 14:24
     */
    void putPackagePrizes(Map<Long, List<PrizeRankActItem>> packagePrizeMap);
}
