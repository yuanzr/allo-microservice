package com.dc.allo.user.service.rpc.baseinfo;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.user.language.DcUserLanguageService;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.user.service.baseinfo.UserLanguageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: DcUserLanguageServiceImpl
 * @since 2020/06/30 15:49
 */
@Service
@Slf4j
public class DcUserLanguageServiceImpl implements DcUserLanguageService {

    @Autowired
    private UserLanguageService userLanguageService;

    /**
     * @description 根据uid查询用户的语言
     * @param uid uid
     * @return com.dc.allo.rpc.proto.AlloResp<java.lang.String>
     * @author tudoutiao
     * @date 15:48 2020/6/30
     **/
    @Override
    public AlloResp<String> getUserLanguageByUid(Long uid) {
        if (uid == null || uid < 0) {
            log.error("getUserLanguageByUid parameror uid:{} ", uid);
            return AlloResp.failed(BusiStatus.PARAMERROR);
        }

        try {
            String userLanguage = userLanguageService.getUserLanguageByUid(uid);
            return AlloResp.success(userLanguage);
        } catch (Exception e) {
            log.error("getUserLanguageByUid error ", e);
            return AlloResp.failed(BusiStatus.BUSIERROR);
        }
    }
}
