package com.dc.allo.biznotice.service.banner;

import com.dc.allo.biznotice.model.entity.CommonBannerItem;
import com.dc.allo.biznotice.model.vo.CommonBannerReq;
import com.dc.allo.biznotice.model.vo.CommonBannerResp;
import java.util.List;
import java.util.Map;

/**
 * description: BannerService
 * date: 2020年05月08日 18:20
 * author: qinrenchuan
 */
public interface BannerService {
    /**
     * 通用banner查询
     * @param bannerReq
     * @return com.erban.main.model.CommonBannerResp
     * @author qinrenchuan
     * @date 2020/5/6/0006 16:13
     */
    CommonBannerResp queryCommonBanner(CommonBannerReq bannerReq);

    /**
     * 根据语言和bannerkey  查询banners
     * @param bannerKeys
     * @author qinrenchuan
     * @date 2020/5/6/0006 18:01
     */
    Map<Byte,List<CommonBannerItem>> queryBanners(String language, List<Byte> bannerKeys);
}
