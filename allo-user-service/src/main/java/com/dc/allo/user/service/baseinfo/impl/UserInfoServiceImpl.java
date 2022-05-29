package com.dc.allo.user.service.baseinfo.impl;

import com.dc.allo.rpc.domain.country.Country;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.user.mapper.baseinfo.UserInfoMapper;
import com.dc.allo.user.service.baseinfo.UserInfoService;
import com.dc.allo.user.service.baseinfo.cache.UserInfoCache;
import com.dc.allo.user.service.country.CountryService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: 用户基础信息Service组件
 *
 * @date: 2020年04月01日 15:46
 * @author: qinrenchuan
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoCache userInfoCache;
    @Autowired
    private CountryService countryService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getByUid(Long uid) {
        UserInfo userInfo = userInfoCache.getByUid(uid);
        if (userInfo == null) {
            userInfo = userInfoMapper.getById(uid);
            if (userInfo != null) {
                userInfoCache.addUserInfo(userInfo);
            }
        }

        return userInfo;
    }

    @Override
    public UserInfo getByErbanNo(Long erbanNo) {
        UserInfo userInfo = userInfoCache.getByErbanNo(erbanNo);
        if (userInfo == null) {
            userInfo = userInfoMapper.getByErbanNo(erbanNo);
            if (userInfo != null) {
                userInfoCache.addUserInfo(userInfo);
            }
        }
        return userInfo;
    }

    /**
     * 根据uidList列表获取用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.UserInfo>
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:44
     */
    @Override
    public List<UserInfo> queryByUids(List<Long> uidList) {
        List<UserInfo> userInfos = userInfoCache.queryByUids(uidList);
        if (userInfos.size() == uidList.size()) {
            return userInfos;
        }

        // 如果缓存只有部分数据，那么将从DB查询
        List<Long> cacheQueryUidList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            cacheQueryUidList.add(userInfo.getUid());
        }

        uidList.removeAll(cacheQueryUidList);
        if (uidList.size() > 0) {
            List<UserInfo> userInfoList = userInfoMapper.queryByUids(uidList);
            if (userInfoList != null && userInfoList.size() > 0) {
                userInfoCache.addUserInfoListCache(userInfoList);
                userInfos.addAll(userInfoList);
            }
        }

        return userInfos;
    }

    @Override
    public List<UserInfo> queryByErbanNos(List<String> erbanNosList) {
        List<UserInfo> userInfos = userInfoCache.queryByErbanNos(erbanNosList);
        if (userInfos.size() == erbanNosList.size()) {
            return userInfos;
        }
        // 如果缓存只有部分数据，那么将从DB查询
        List<Long> cacheQueryUidList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            cacheQueryUidList.add(userInfo.getUid());
        }
        erbanNosList.removeAll(cacheQueryUidList);
        if (erbanNosList.size() > 0) {
            List<UserInfo> userInfoList = userInfoMapper.queryByErbanNos(erbanNosList);
            if (userInfoList != null && userInfoList.size() > 0) {
                userInfoCache.addUserInfoErbanNosListCache(userInfoList);
                userInfos.addAll(userInfoList);
            }
        }
        return userInfos;
    }

    @Override
    public UserInfo getRegionByUid(Long uid) {
        UserInfo users = getByUid(uid);
        Integer countryId = users.getCountryId();
        users.setRegion("en");
        if (countryId !=null){
            Country country = countryService.getCountry(countryId);
            users.setRegion(country.getLanguage());
        }
        return users;
    }

    /**
     * 根据耳伴号列表查询与UID的对应关系    erbanNo->Uid
     * @param erbanNoList
     * @return java.util.Map<java.lang.Long,java.lang.Long>
     * @author qinrenchuan
     * @date 2020/6/4/0004 10:21
     */
    @Override
    public Map<Long, Long> queryErbanNo2UidMapByErbanNos(List<Long> erbanNoList) {
        Map<Long, Long> resultMap = userInfoCache.queryErbanNo2UidMapByErbanNos(erbanNoList);

        if (resultMap.size() == erbanNoList.size()) {
            return resultMap;
        }

        // 如果缓存只有部分数据，那么将从DB查询
        Iterator<Long> erbanNOIterator = erbanNoList.iterator();
        while (erbanNOIterator.hasNext()) {
            if (resultMap.get(erbanNOIterator.next()) != null) {
                erbanNOIterator.remove();
            }
        }

        if (erbanNoList.size() > 0) {
            List<SimpleUserInfo> simpleUserInfos = userInfoMapper.querySimpleUsersByErbanNos(erbanNoList);
            if (simpleUserInfos != null && simpleUserInfos.size() > 0) {
                for (SimpleUserInfo simpleUserInfo : simpleUserInfos) {
                    resultMap.put(simpleUserInfo.getErbanNo(), simpleUserInfo.getUid());
                }
                userInfoCache.addSimpleUserInfoListCache(simpleUserInfos);
            }
        }

        return resultMap;
    }

    /**
     * 根据uidList列表获取用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>
     * @author qinrenchuan
     * @date 2020/6/2/0002 17:59
     */
    @Override
    public List<SimpleUserInfo> querySimpleUserList(List<Long> uidList) {
        List<SimpleUserInfo> userInfos = userInfoCache.querySimpleUsersByUids(uidList);
        if (userInfos.size() == uidList.size()) {
            return userInfos;
        }

        // 如果缓存只有部分数据，那么将从DB查询
        List<Long> cacheQueryUidList = new ArrayList<>();
        for (SimpleUserInfo userInfo : userInfos) {
            cacheQueryUidList.add(userInfo.getUid());
        }

        uidList.removeAll(cacheQueryUidList);
        if (uidList.size() > 0) {
            List<SimpleUserInfo> userInfoList = userInfoMapper.querySimpleUsesByUids(uidList);
            if (userInfoList != null && userInfoList.size() > 0) {
                userInfoCache.addSimpleUserInfoListCache(userInfoList);
                userInfos.addAll(userInfoList);
            }
        }

        return userInfos;
    }

}
