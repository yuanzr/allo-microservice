package com.dc.allo.user.service.baseinfo;

import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/3/22.
 */
public interface UserInfoService {
    /**
     * 根据Uid查询缓存的用户信息
     * @param uid
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    UserInfo getByUid(Long uid);

    /**
     * 根据耳伴号查询用户信息
     * @param erbanNo
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:02
     */
    UserInfo getByErbanNo(Long erbanNo);

    /**
     * 根据uidList列表获取用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.UserInfo>
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:44
     */
    List<UserInfo> queryByUids(List<Long> uidList);

    /**
     * 根据erbanNosList列表获取用户列表
     * @param erbanNosList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.UserInfo>
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:44
     */
    List<UserInfo> queryByErbanNos(List<String> erbanNosList);


    /**
     * 根据Uid查询缓存的用户信息(将region替换成国家所属的语言区)
     * @param uid
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    UserInfo getRegionByUid(Long uid);

    /**
     * 根据耳伴号列表查询与UID的对应关系    erbanNo->Uid
     * @param erbanNoList
     * @return java.util.Map<java.lang.Long,java.lang.Long>
     * @author qinrenchuan
     * @date 2020/6/4/0004 10:21
     */
    Map<Long, Long> queryErbanNo2UidMapByErbanNos(List<Long> erbanNoList);

    /**
     * 根据uidList列表获取用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>
     * @author qinrenchuan
     * @date 2020/6/2/0002 17:59
     */
    List<SimpleUserInfo> querySimpleUserList(List<Long> uidList);
}
