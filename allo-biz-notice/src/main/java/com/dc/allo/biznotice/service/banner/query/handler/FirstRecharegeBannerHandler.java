package com.dc.allo.biznotice.service.banner.query.handler;

import com.dc.allo.biznotice.constant.Constant;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.rpc.domain.finance.purse.UserPurse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description: 首充banner handler
 *
 * @date: 2020年05月06日 16:27
 * @author: qinrenchuan
 */
@Component
@Slf4j
public class FirstRecharegeBannerHandler implements BannerHandler {

    @Value("${xchat.web.domain}")
    private String xchatApiDomain;

    @Override
    public Object execute(Long uid) {
       try {
           Map<String, String> map = new HashMap<>();
           map.put("uid", uid.toString());
           UserPurse userPurse = HttpUtils.doGetWithClass(
                   xchatApiDomain + Constant.queryUserPurseUrl,
                   map, UserPurse.class);
           log.info("FirstRecharegeBannerHandler userPurse:{}", userPurse);
           if (userPurse != null && userPurse.getIsFirstCharge() != null) {
               return userPurse.getIsFirstCharge();
           }
       } catch (Exception e) {
           log.error("query userPurse error", e);
       }
        return false;
    }

}
