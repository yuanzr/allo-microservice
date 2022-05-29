package com.dc.allo.biznotice.controller;

import com.dc.allo.biznotice.model.vo.CommonBannerReq;
import com.dc.allo.biznotice.model.vo.CommonBannerResp;
import com.dc.allo.biznotice.service.banner.impl.BannerServiceImpl;
import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbCommon.PbHttpBizReq;
import com.erban.main.proto.PbHttpReq.PbCommonBannerReq;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: BannerController
 *
 * @date: 2020年05月08日 17:48
 * @author: qinrenchuan
 */
@Slf4j
@RestController
@RequestMapping("/banner")
public class BannerController extends BaseController {

    @Autowired
    private BannerServiceImpl bannerService;

    /**
     * 通用Banner 查询
     * @param request
     * @return com.xchat.common.result.BusiResult
     * @author qinrenchuan
     * @date 2020/5/6/0006 15:39
     */
    //@RequestMapping(value = "/pb/banner/list", method = RequestMethod.POST)
    @RequestMapping(value = "/pb/auth/list", method = RequestMethod.POST)
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,
            clzFullName = "com.erban.main.proto.PbHttpResp$PbCommonBannerResp",
            paramClassName = "com.erban.main.proto.PbHttpReq$PbCommonBannerReq")
    public AlloResp queryCommonBanner(HttpServletRequest request) {
        try {
            // 公共请求参数
            PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);

            PbCommonBannerReq commonBannerReq = PbCommonBannerReq.parseFrom(pbHttpBizReq.getData());
            if (StringUtils.isBlank(commonBannerReq.getBannerKeys())) {
                log.error("queryCommonBanner failed, bannerKeys:", commonBannerReq.getBannerKeys());
                return AlloResp.failed(BusiStatus.REQUEST_PARAM_ERROR);
            }

            // 封装一个查询实体
            CommonBannerReq bannerReq = new CommonBannerReq();
            bannerReq.setUid(Long.valueOf(pbHttpBizReq.getUid()));
            bannerReq.setOs(pbHttpBizReq.getOs());
            bannerReq.setLanguage(getLocalLanguage());

            List<Byte> bannerKeys = new ArrayList<>();
            String[] bannerKeyArr = commonBannerReq.getBannerKeys().split(",");
            for (String bannerKey : bannerKeyArr) {
                bannerKeys.add(Byte.valueOf(bannerKey));
            }
            bannerReq.setBannerKeys(bannerKeys);
            log.info("queryCommonBanner req: {}", bannerReq);

            CommonBannerResp commonBannerResp = bannerService.queryCommonBanner(bannerReq);
            log.info("queryCommonBanner resp: {}", commonBannerResp);

            return AlloResp.success(commonBannerResp);

        } catch (Exception e) {
            log.error("saveFeedback failed", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
