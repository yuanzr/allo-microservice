package com.dc.allo.biznotice.service.banner.query.handler;

import org.springframework.stereotype.Component;

/**
 * description: DefaultCommonBannerHandler
 *
 * @date: 2020年05月06日 16:19
 * @author: qinrenchuan
 */
@Component
public class DefaultCommonBannerHandler implements BannerHandler {

    @Override
    public Object execute(Long uid) {
        return true;
    }
}
