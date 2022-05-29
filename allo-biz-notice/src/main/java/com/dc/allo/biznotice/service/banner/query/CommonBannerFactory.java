package com.dc.allo.biznotice.service.banner.query;

import com.dc.allo.biznotice.constant.Constant.BannerType;
import com.dc.allo.biznotice.model.vo.CommonBannerReq;
import com.dc.allo.biznotice.service.banner.impl.BannerServiceImpl;
import com.dc.allo.biznotice.service.banner.query.handler.BannerHandler;
import com.dc.allo.biznotice.service.banner.query.handler.CommonBannerHandlerChain;
import com.dc.allo.biznotice.service.banner.query.handler.DefaultCommonBannerHandler;
import com.dc.allo.biznotice.service.banner.query.handler.FirstRecharegeBannerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * description: 通用banner查询工厂
 *
 * @date: 2020年05月06日 16:15
 * @author: qinrenchuan
 */
@Component
public class CommonBannerFactory {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private FirstRecharegeBannerHandler firstRecharegeBannerHandler;
    @Autowired
    private DefaultCommonBannerHandler defaultCommonBannerHandler;


    /**
     * 根据bannerType得到相应的Handler
     * @return com.erban.main.service.banner.query.handler.BannerHandler
     * @author qinrenchuan
     * @date 2020/5/6/0006 16:24
     */
    public BannerHandler getHandler(Byte bannerType) {
        BannerHandler bannerHandler;
        // 目前充值页面也按首充逻辑走
        if (BannerType.BANNER_FIRST_RECHARGE == bannerType.byteValue() ||
                BannerType.BANNER_RECHARGE == bannerType.byteValue()) {
            bannerHandler = firstRecharegeBannerHandler;
        } else {
            bannerHandler = defaultCommonBannerHandler;
        }

        return bannerHandler;
    }

    /**
     * HandlerChain
     * @param bannerReq
     * @param commonBannerService
     * @return com.erban.main.service.banner.query.handler.CommonBannerHandlerChain
     * @author qinrenchuan
     * @date 2020/5/8/0008 15:40
     */
    public CommonBannerHandlerChain getHandlerChina(
            CommonBannerReq bannerReq,
            BannerServiceImpl bannerService) {
        CommonBannerHandlerChain commonBannerHandlerChain =
                applicationContext.getBean(CommonBannerHandlerChain.class);
        commonBannerHandlerChain.setBannerReq(bannerReq);
        commonBannerHandlerChain.setBannerService(bannerService);
        commonBannerHandlerChain.setCommonBannerFactory(this);
        return commonBannerHandlerChain;
    }
}
