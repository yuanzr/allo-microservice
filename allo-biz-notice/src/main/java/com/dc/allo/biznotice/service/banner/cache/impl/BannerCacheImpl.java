package com.dc.allo.biznotice.service.banner.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.model.entity.CommonBannerItem;
import com.dc.allo.biznotice.service.banner.cache.BannerCache;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.RedisKeyUtil.RedisExpire_Time;
import com.dc.allo.common.utils.RedisKeyUtil.RedisKey_Module_Pre;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: BannerCacheImpl
 *
 * @date: 2020年05月08日 19:01
 * @author: qinrenchuan
 */
@Service
public class BannerCacheImpl implements BannerCache {
    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKey_Module_Pre.Finance;
    private static final String bannerKey = "banner";
    /**
     * 从缓存查询
     * @param language
     * @param bannerKey
     * @return java.util.List<com.dc.allo.biznotice.model.entity.CommonBannerItem>
     * @author qinrenchuan
     * @date 2020/5/8/0008 19:00
     */
    @Override
    public List<CommonBannerItem> queryCommonBanners(String language, Byte bannerKey) {
        String cacheKey = RedisKeyUtil.appendCacheKeyByColon(bannerKey, language, bannerKey);
        List<CommonBannerItem> bannerVoList = new ArrayList<>();

        Map<Object, Object> bannerMap = redisUtil.hGetAll(cacheKey);
        if (bannerMap != null && bannerMap.size() > 0) {
            for (Object str : bannerMap.values()) {
                if (str  != null && StringUtils.isNotBlank(str.toString())) {
                    CommonBannerItem bannerItem = JSONObject.parseObject(str.toString(), CommonBannerItem.class);
                    bannerVoList.add(bannerItem);
                }
            }
        }
        return bannerVoList;
    }

    /**
     * 向缓存插入
     * @param language
     * @param bannerKey
     * @param maps
     * @author qinrenchuan
     * @date 2020/5/8/0008 19:13
     */
    @Override
    public void putCommonBanners(String language, Byte bannerKey, Map<String, String> maps) {
        String cacheKey = RedisKeyUtil.appendCacheKeyByColon(bannerKey, language, bannerKey);
        redisUtil.hPutAll(cacheKey, maps, RedisExpire_Time.FiveMinutes);
    }
}
