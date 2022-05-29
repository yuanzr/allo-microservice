package com.dc.allo.rpc.api.user.baseinfo;

import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;

import java.util.List;
import java.util.Map;

/**
 * description: DcUserInfoService
 * date: 2020年04月02日 17:22
 * author: qinrenchuan
 */
public interface DcUserInfoService {
    /**
     * 根据Uid查询用户信息
     * @param uid
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    AlloResp<UserInfo> getByUid(Long uid);

    /**
     * 根据耳伴号查询用户信息
     * @param erbanNo
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:02
     */
    AlloResp<UserInfo> getByErbanNo(Long erbanNo);

    /**
     * 根据Uid查询用户简单信息
     * @param uid
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    AlloResp<SimpleUserInfo> getSimpleUserInfoByUid(Long uid);

    /**
     * 根据耳伴号查询用户简单信息
     * @param erbanNo
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:02
     */
    AlloResp<SimpleUserInfo> getSimpleUserInfoByErbanNo(Long erbanNo);

    /**
     * 根据uidList列表获取用户列表
     * @param uidList
     * @return
     */
    AlloResp<List<UserInfo>> getUserList(List<Long> uidList);

    /**
     * 根据uidList列表获取用户列表
     * @param uidList
     * @return
     */
    AlloResp<List<SimpleUserInfo>> getSimpleUserList(List<Long> uidList);


    /**
     * 根据ErbanNoList列表获取用户列表
     * @param erbanNoList
     * @return
     */
    @Deprecated
    AlloResp<List<UserInfo>> getUserByErbanNoList(List<String> erbanNoList);

    /**
     * 根据耳伴号列表查询与UID的对应关系    erbanNo->Uid
     * @param erbanNoList
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.Map<java.lang.Long,java.lang.Long>>
     * @author qinrenchuan
     * @date 2020/6/4/0004 10:18
     */
    AlloResp<Map<Long, Long>> queryErbanNo2UidMapByErbanNos(List<Long> erbanNoList);

    /**
     * 根据Uid查询缓存的用户信息(将region替换成国家所属的语言区)
     * @param uid
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    AlloResp<UserInfo> getRegionByUid(Long uid);
    /**
     * 根据uidList列表获取用户key-value对(uid->SimpleUserInfo)
     * @param uidList
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.Map<java.lang.Long,com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:22
     */
    AlloResp<Map<Long, SimpleUserInfo>> getSimpleUsersMap(List<Long> uidList);

}
