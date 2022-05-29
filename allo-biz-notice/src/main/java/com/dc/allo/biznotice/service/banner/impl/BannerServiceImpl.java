package com.dc.allo.biznotice.service.banner.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.mapper.banner.BannerMapper;
import com.dc.allo.biznotice.model.entity.CommonBannerItem;
import com.dc.allo.biznotice.model.vo.CommonBannerReq;
import com.dc.allo.biznotice.model.vo.CommonBannerResp;
import com.dc.allo.biznotice.service.banner.BannerService;
import com.dc.allo.biznotice.service.banner.cache.BannerCache;
import com.dc.allo.biznotice.service.banner.query.CommonBannerFactory;
import com.dc.allo.common.utils.RedisKeyUtil.RedisKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: CommonBannerService
 *
 * @date: 2020年05月06日 16:10
 * @author: qinrenchuan
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerCache bannerCache;

    @Autowired
    private CommonBannerFactory commonBannerFactory;

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 通用banner查询
     * @param bannerReq
     * @return com.erban.main.model.CommonBannerResp
     * @author qinrenchuan
     * @date 2020/5/6/0006 16:13
     */
    @Override
    public CommonBannerResp queryCommonBanner(CommonBannerReq bannerReq) {
        return commonBannerFactory.getHandlerChina(bannerReq, this).getBannerResp();
    }

    /**
     * 查询banners
     * @param bannerKeys
     * @author qinrenchuan
     * @date 2020/5/6/0006 18:01
     */
    @Override
    public Map<Byte,List<CommonBannerItem>> queryBanners(String language, List<Byte> bannerKeys) {
        Map<Byte,List<CommonBannerItem>> bannerMap = new HashMap<>(bannerKeys.size());

        for (Byte bannerKey : bannerKeys) {
            List<CommonBannerItem> commonBannerItems = getCommonBannersByKey(language, bannerKey);
            bannerMap.put(bannerKey, commonBannerItems);
        }
        return bannerMap;
    }

    /**
     * 单个实现
     * @param language
     * @param bannerKey
     * @return java.util.List<com.erban.main.model.CommonBannerItem>
     * @author qinrenchuan
     * @date 2020/5/8/0008 14:52
     */
    private List<CommonBannerItem> getCommonBannersByKey(String language, Byte bannerKey) {
        List<CommonBannerItem> bannerVoList = bannerCache.queryCommonBanners(language, bannerKey);
        if (bannerVoList == null || bannerVoList.size() == 0) {
            // 从数据库查询
            bannerVoList = bannerMapper.queryBanners(bannerKey, language);
            if (bannerVoList != null && bannerVoList.size() > 0) {
                Map<String, String> map = new HashMap<>();
                for (CommonBannerItem bannerVo : bannerVoList) {
                    map.put(bannerVo.getBannerId().toString(), JSONObject.toJSONString(bannerVo));
                }
                bannerCache.putCommonBanners(language, bannerKey, map);
            }
        }
        return bannerVoList;
    }
}
