package com.dc.allo.user.service.baseinfo.impl;

import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.user.mapper.baseinfo.UserLanguageMapper;
import com.dc.allo.user.service.baseinfo.UserLanguageService;
import com.dc.allo.user.service.baseinfo.cache.UserLanguageCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: UserLanguageServiceImpl
 * @since 2020/06/30 15:52
 */
@Service
public class UserLanguageServiceImpl implements UserLanguageService {

    @Autowired
    private UserLanguageCache userLanguageCache;

    @Autowired
    private UserLanguageMapper userLanguageMapper;

    /**
     * @description 根据uid查询用户语言
     * @param uid
     * @return java.lang.String 用户语言
     * @author tudoutiao
     * @date 15:51 2020/6/30
     **/
    @Override
    public String getUserLanguageByUid(Long uid) {
        String userLangeuage = userLanguageCache.getLanguageByUid(uid);
        if (!StringUtils.isEmpty(userLangeuage)) {
            return userLangeuage;
        }

        userLangeuage = userLanguageMapper.getUserLanguageByUid(uid);
        if (StringUtils.isEmpty(userLangeuage)) {
            userLangeuage = "en";
        }
        userLanguageCache.setUserLanguage(uid, userLangeuage);

        return userLangeuage;
    }
}
