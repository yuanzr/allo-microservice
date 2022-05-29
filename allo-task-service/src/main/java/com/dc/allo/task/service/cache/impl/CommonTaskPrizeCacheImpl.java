package com.dc.allo.task.service.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.RedisKeyUtil.RedisExpire_Time;
import com.dc.allo.task.domain.entity.PrizeRankActItem;
import com.dc.allo.task.service.cache.CommonTaskPrizeCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: CommonTaskPrizeCacheImpl
 *
 * @date: 2020年05月28日 11:42
 * @author: qinrenchuan
 */
@Service
public class CommonTaskPrizeCacheImpl implements CommonTaskPrizeCache {

    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.Task;
    /** 礼包奖品 */
    private static final String PACKAGE_PRIZE = "packagePrize";

    /**
     * 根据礼包id列表查询礼包内奖品（缓存五分钟）
     * @param packageIds
     * @return java.util.Map<java.lang.Long,java.util.List<com.dc.allo.task.domain.entity.PrizeRankActItem>>
     * @author qinrenchuan
     * @date 2020/5/28/0028 11:43
     */
    @Override
    public Map<Long, List<PrizeRankActItem>> queryPrizesByPackageIds(List<Long> packageIds) {
        Map<Long, List<PrizeRankActItem>> resultMap = new HashMap<>();

        List<String> keys = new ArrayList<>();
        for (Long packageId : packageIds) {
            String key = RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, PACKAGE_PRIZE, packageId);
            if (!keys.contains(key)) {
                keys.add(key);
            }
        }
        List<String> resutlList = redisUtil.multiGet(keys);
        for (int i = 0; i < keys.size(); i++) {
            String value = resutlList.get(i);
            if (StringUtils.isNotBlank(value)) {
                List<PrizeRankActItem> items = JSONObject.parseArray(value, PrizeRankActItem.class);
                resultMap.put(packageIds.get(i), items);
            }
        }

        return resultMap;
    }

    /**
     * 存入缓存
     * @param packagePrizeMap
     * @param items
     * @author qinrenchuan
     * @date 2020/5/28/0028 14:24
     */
    @Override
    public void putPackagePrizes(Map<Long, List<PrizeRankActItem>> packagePrizeMap) {
        if (packagePrizeMap == null || packagePrizeMap.size() == 0) {
            return;
        }

        Map<String, String> chacheMap = new HashMap<>(packagePrizeMap.size());

        Set<Entry<Long, List<PrizeRankActItem>>> entries = packagePrizeMap.entrySet();
        for (Entry<Long, List<PrizeRankActItem>> entry : entries) {
            String key = RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, PACKAGE_PRIZE, entry.getKey());
            List<PrizeRankActItem> items = entry.getValue();
            chacheMap.put(key, JSONObject.toJSONString(items));
        }

        redisUtil.multiSet(chacheMap, RedisExpire_Time.FiveMinutes);
    }
}
