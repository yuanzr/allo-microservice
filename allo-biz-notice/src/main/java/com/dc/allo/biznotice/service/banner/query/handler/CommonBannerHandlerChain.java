package com.dc.allo.biznotice.service.banner.query.handler;

import com.dc.allo.biznotice.model.vo.CommonBannerResp;
import com.dc.allo.biznotice.model.vo.CommonBannerVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * description: CommonBannerHandlerChain
 *
 * @date: 2020年05月08日 15:31
 * @author: qinrenchuan
 */
@Component
@Scope("prototype")
public class CommonBannerHandlerChain extends AbstractBannerHandler {

    @Override
    public CommonBannerResp getBannerResp() {
        List<Byte> bannerKeys = bannerReq.getBannerKeys();
        Iterator<Byte> iterator = bannerKeys.iterator();

        List<Byte> removeKeys = new ArrayList<>();
        while (iterator.hasNext()) {
            Byte key = iterator.next();
            BannerHandler handler = commonBannerFactory.getHandler(key);
            Boolean flag = (Boolean)handler.execute(bannerReq.getUid());
            if (!flag) {
                removeKeys.add(key);
                iterator.remove();
            }
        }

        CommonBannerResp commonBannerResp = null;
        if (bannerKeys.size() > 0) {
            commonBannerResp = query();
        }

        if (commonBannerResp == null) {
            commonBannerResp = new CommonBannerResp();
            List<CommonBannerVO> commonBanners = new ArrayList<>();
            commonBannerResp.setBanners(commonBanners);
        }

        if (removeKeys.size() > 0) {

            for (Byte removeKey : removeKeys) {
                CommonBannerVO commonBannerVO = new CommonBannerVO();
                commonBannerVO.setKey(removeKey.toString());
                commonBannerVO.setItems(new ArrayList<>());

                commonBannerResp.getBanners().add(commonBannerVO);
            }
        }
        return commonBannerResp;
    }
}
