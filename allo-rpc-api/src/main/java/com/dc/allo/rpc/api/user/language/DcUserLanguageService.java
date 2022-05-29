package com.dc.allo.rpc.api.user.language;

import com.dc.allo.rpc.proto.AlloResp;

/**
 * @description: 用户语言接口
 * @author tudoutiao
 * @version v1.0.0
 * @since 2020/06/30 15:46
 */
public interface DcUserLanguageService {

    /**
     * @description 根据uid查询用户的语言
     * @param uid uid
     * @return com.dc.allo.rpc.proto.AlloResp<java.lang.String>
     * @author tudoutiao
     * @date 15:48 2020/6/30
     **/
    AlloResp<String> getUserLanguageByUid(Long uid);
}
