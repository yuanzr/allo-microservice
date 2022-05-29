package com.dc.allo.biznotice.service.banner.query.handler;

import com.dc.allo.biznotice.model.entity.CommonBannerItem;
import com.dc.allo.biznotice.model.vo.CommonBannerReq;
import com.dc.allo.biznotice.model.vo.CommonBannerResp;
import com.dc.allo.biznotice.model.vo.CommonBannerVO;
import com.dc.allo.biznotice.service.banner.BannerService;
import com.dc.allo.biznotice.service.banner.query.CommonBannerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * description: AbstractBannerHandler
 *
 * @date: 2020年05月06日 16:35
 * @author: qinrenchuan
 */
public abstract class AbstractBannerHandler implements BannerHandler {
    /** 通用banner请求实体 */
    protected CommonBannerReq bannerReq;
    protected BannerService bannerService;
    protected CommonBannerFactory commonBannerFactory;

    public void setBannerReq(CommonBannerReq bannerReq) {
        this.bannerReq = bannerReq;
    }

    public AbstractBannerHandler setBannerService(BannerService bannerService) {
        this.bannerService = bannerService;
        return this;
    }

    public AbstractBannerHandler setCommonBannerFactory(CommonBannerFactory commonBannerFactory) {
        this.commonBannerFactory = commonBannerFactory;
        return this;
    }

    /**
     * 查询
     * @return com.erban.main.model.CommonBannerResp
     * @author qinrenchuan
     * @date 2020/5/6/0006 16:50
     */
    public CommonBannerResp query() {
        CommonBannerResp commonBannerResp = new CommonBannerResp();
        List<CommonBannerVO> banners = new ArrayList<>();
        commonBannerResp.setBanners(banners);

        Map<Byte, List<CommonBannerItem>> commonBannerItemMap = bannerService.queryBanners(
                bannerReq.getLanguage(), bannerReq.getBannerKeys());
        Set<Entry<Byte, List<CommonBannerItem>>> entries = commonBannerItemMap.entrySet();
        for (Entry<Byte, List<CommonBannerItem>> entry : entries) {
            Byte key = entry.getKey();
            List<CommonBannerItem> value = entry.getValue();
            CommonBannerVO commonBannerVO = new CommonBannerVO();
            commonBannerVO.setKey(key.toString());
            commonBannerVO.setItems(value);
            banners.add(commonBannerVO);
        }
        return commonBannerResp;
    }


    @Override
    public Object execute(Long uid) {
        return null;
    }

    public abstract CommonBannerResp getBannerResp();
}
