package com.dc.allo.user.service.baseinfo;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: UserLanguageService
 * @since 2020/06/30 15:50
 */
public interface UserLanguageService {
    /**
     * @description 根据uid查询用户语言
     * @param uid
     * @return java.lang.String 用户语言
     * @author tudoutiao
     * @date 15:51 2020/6/30
     **/
    String getUserLanguageByUid(Long uid);
}
