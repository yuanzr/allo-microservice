package com.dc.allo.user.service.baseinfo.cache;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: UserLanguageCache
 * @since 2020/06/30 15:56
 */
public interface UserLanguageCache {
    /**
     * @description 根据用户uid查询语言
     * @param uid
     * @return java.lang.String
     * @author tudoutiao
     * @date 16:03 2020/6/30
     **/
    String getLanguageByUid(Long uid);

    /**
     * @description 设置用户语言
     * @param uid
     * @param language
     * @author tudoutiao
     * @date 16:04 2020/6/30
     **/
    void setUserLanguage(Long uid, String language);
}
