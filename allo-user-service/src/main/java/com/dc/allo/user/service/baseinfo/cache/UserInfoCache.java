package com.dc.allo.user.service.baseinfo.cache;

import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import java.util.List;
import java.util.Map;

/**
 * description: UserInfoCache
 * date: 2020年04月01日 15:49
 * author: qinrenchuan
 */
public interface UserInfoCache {

    /**
     * 根据Uid查询缓存的用户信息
     * @param uid
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    UserInfo getByUid(Long uid);

    /**
     * 向缓存存入userInfo数据
     * @param userInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:55
     */
    void addUserInfo(UserInfo userInfo);

    /**
     * 根据耳伴号查询Uid
     * @param erbanNo
     * @return java.lang.Long
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:01
     */
    Long getUidByErbanNo(Long erbanNo);

    /**
     * 根据耳伴号查询用户信息
     * @param erbanNo
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:02
     */
    UserInfo getByErbanNo(Long erbanNo);

    /**
     * 根据uid列表从缓存查询用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.UserInfo>
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:48
     */
    List<UserInfo> queryByUids(List<Long> uidList);

    /**
     * 将用户列表保存到缓存-Uid
     * @param userInfoList
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:58
     */
    void addUserInfoListCache(List<UserInfo> userInfoList);

    /**
     * 根据erbanNosList列表从缓存查询用户列表
     * @param erbanNosList
     * @return
     */
    @Deprecated
    List<UserInfo> queryByErbanNos(List<String> erbanNosList);

    /**
     * 根据耳伴号列表查询与UID的对应关系    erbanNo->Uid
     * @param erbanNos
     * @return java.util.Map<java.lang.Long,java.lang.Long>
     * @author qinrenchuan
     * @date 2020/6/4/0004 10:36
     */
    Map<Long, Long> queryErbanNo2UidMapByErbanNos(List<Long> erbanNos);

    /**
     * 将用户列表保存到缓存-erbanNo
     * @param userInfoList
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:58
     */
    void addUserInfoErbanNosListCache(List<UserInfo> userInfoList);

    /**
     * 查询简体用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:05
     */
    List<SimpleUserInfo> querySimpleUsersByUids(List<Long> uidList);

    /**
     * 用户(简单)列表保存到缓存
     * @param userInfoList
     * @return void
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:14
     */
    void addSimpleUserInfoListCache(List<SimpleUserInfo> userInfoList);
}
