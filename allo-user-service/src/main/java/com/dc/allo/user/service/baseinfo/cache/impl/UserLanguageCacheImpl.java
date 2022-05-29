package com.dc.allo.user.service.baseinfo.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.RedisKeyUtil.RedisExpire_Time;
import com.dc.allo.common.utils.StringUtils;
import com.dc.allo.user.service.baseinfo.cache.UserLanguageCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: UserLanguageCacheImpl
 * @since 2020/06/30 15:57
 */
@Service
public class UserLanguageCacheImpl implements UserLanguageCache {

    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.User;
    /** 语言key */
    private static final String USER_LANG = "userLang";

    /**
     * @description 根据用户uid查询语言
     * @param uid
     * @return java.lang.String
     * @author tudoutiao
     * @date 16:03 2020/6/30
     **/
    @Override
    public String getLanguageByUid(Long uid) {
        String userLanguage = redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(
                redisKeyPre, USER_LANG, String.valueOf(uid)));
        if (!StringUtils.isEmpty(userLanguage)) {
            return userLanguage;
        }
        return null;
    }

    /**
     * @description 设置用户语言
     * @param uid
     * @param language
     * @author tudoutiao
     * @date 16:04 2020/6/30
     **/
    @Override
    public void setUserLanguage(Long uid, String language) {
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, USER_LANG, String.valueOf(uid)),
                language,
                RedisExpire_Time.OneDay);
    }
}
