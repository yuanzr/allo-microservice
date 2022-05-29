package com.dc.allo.biznotice.service.firebase;

import com.dc.allo.biznotice.model.firebase.NoticeFirebaseTokenUser;
import java.util.List;

public interface NoticeFirebaseTokenUserService {

    /**
     *  查询一个用户的token
     * @param uid
     * @return
     */
    NoticeFirebaseTokenUser get(Long uid);

    /**
     * 新增/更新注册token
     * @param uid
     * @param token
     * @return
     */
    int saveOrUpdate(Long uid,String token);


    /**
     * 解绑(删除)注册token
     * @param uid
     * @return
     */
    int delete(Long uid,String token);


    /**
     * 批量获取用户token
     * @param uidList
     * @return
     */
    List<String> queryToken(List<Long> uidList);

}
