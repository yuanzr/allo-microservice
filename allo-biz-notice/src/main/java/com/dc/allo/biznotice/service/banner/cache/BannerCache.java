package com.dc.allo.biznotice.service.banner.cache;

import com.dc.allo.biznotice.model.entity.CommonBannerItem;
import java.util.List;
import java.util.Map;

/**
 * description: BannerCache
 * date: 2020年05月08日 18:59
 * author: qinrenchuan
 */
public interface BannerCache {
    /**
     * 从缓存查询
     * @param language
     * @param bannerKey
     * @return java.util.List<com.dc.allo.biznotice.model.entity.CommonBannerItem>
     * @author qinrenchuan
     * @date 2020/5/8/0008 19:00
     */
    List<CommonBannerItem> queryCommonBanners(String language, Byte bannerKey);

    /**
     * 向缓存插入
     * @param language
     * @param bannerKey
     * @param maps
     * @author qinrenchuan
     * @date 2020/5/8/0008 19:13
     */
    void putCommonBanners(String language, Byte bannerKey, Map<String, String> maps);
}
