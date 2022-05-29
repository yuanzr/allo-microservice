package com.dc.allo.user.service.baseinfo.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.RedisKeyUtil.RedisExpire_Time;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.user.service.baseinfo.cache.UserInfoCache;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: 用户基础信息cache组件
 *
 * @date: 2020年04月01日 15:50
 * @author: qinrenchuan
 */
@Service
public class UserInfoCacheImpl implements UserInfoCache {

    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.User;
    /** 用户详情缓存key： 字符串数据结构 */
    private static final String USERINFO = "userinfo";
    private static final String SIMPLE_USERINFO = "simpleUser";
    /** 用户耳伴号-uid的缓存： hash数据结构  */
    private static final String ERBANNO2UID = "erbanNo2Uid";

    private Gson gson = new Gson();

    @Override
    public UserInfo getByUid(Long uid) {
        Object userInfoCache = redisUtil.get(
                RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, USERINFO, uid));

        if (userInfoCache != null && StringUtils.isNotBlank(userInfoCache.toString())) {
            return JSONObject.parseObject(userInfoCache.toString(), UserInfo.class);
        }
        return null;
    }

    @Override
    public void addUserInfo(UserInfo userInfo) {
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, USERINFO, userInfo.getUid()),
                JSONObject.toJSONString(userInfo), RedisKeyUtil.RedisExpire_Time.OneDay);
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(
                    redisKeyPre, ERBANNO2UID, userInfo.getErbanNo().toString()),
                userInfo.getUid().toString(),
                RedisExpire_Time.OneDay);
    }

    @Override
    public Long getUidByErbanNo(Long erbanNo) {
        if (erbanNo != null) {
            Object uidObj = redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(
                    redisKeyPre, ERBANNO2UID, erbanNo.toString()));
            if (uidObj != null) {
                return Long.valueOf(uidObj.toString());
            }
        }
        return null;
    }

    @Override
    public UserInfo getByErbanNo(Long erbanNo) {
        if (erbanNo != null) {
            Long uid = getUidByErbanNo(erbanNo);
            if (uid != null) {
                return getByUid(uid);
            }
        }
        return null;
    }

    /**
     * 根据uid列表从缓存查询用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.UserInfo>
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:48
     */
    @Override
    public List<UserInfo> queryByUids(List<Long> uidList) {
        List<String> keyList = new ArrayList<>(uidList.size());
        for (Long uid : uidList) {
            keyList.add(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, USERINFO, uid));
        }

        List<UserInfo> resultList = new ArrayList<>();

        List<String> userInfoStrList = redisUtil.multiGet(keyList);
        if (userInfoStrList == null && userInfoStrList.size() == 0) {
            return resultList;
        }

        for (String userInfoStr : userInfoStrList) {
            if (StringUtils.isNotBlank(userInfoStr)) {
                resultList.add(JSONObject.parseObject(userInfoStr, UserInfo.class));
            }
        }
        return resultList;
    }

    /**
     * 将用户列表保存到缓存
     * @param userInfoList
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:58
     */
    @Override
    public void addUserInfoListCache(List<UserInfo> userInfoList) {
        Map<String, String> maps = new HashMap<>();
        Map<String, String> erbanMaps = new HashMap<>();

        for (UserInfo userInfo : userInfoList) {
            maps.put(RedisKeyUtil.appendCacheKeyByColon(
                        redisKeyPre, USERINFO, userInfo.getUid()),
                    JSONObject.toJSONString(userInfo));
            erbanMaps.put(RedisKeyUtil.appendCacheKeyByColon(
                        redisKeyPre, ERBANNO2UID, userInfo.getErbanNo().toString()),
                    userInfo.getUid().toString());
        }
        redisUtil.multiSet(maps, RedisKeyUtil.RedisExpire_Time.OneDay);
        redisUtil.multiSet(erbanMaps, RedisKeyUtil.RedisExpire_Time.OneDay);
    }

    /**
     * 查询简体用户列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:05
     */
    @Override
    public List<SimpleUserInfo> querySimpleUsersByUids(List<Long> uidList) {
        List<String> keyList = new ArrayList<>(uidList.size());
        for (Long uid : uidList) {
            keyList.add(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, SIMPLE_USERINFO, uid));
        }

        List<SimpleUserInfo> resultList = new ArrayList<>();

        List<String> userInfoStrList = redisUtil.multiGet(keyList);
        if (userInfoStrList == null && userInfoStrList.size() == 0) {
            return resultList;
        }

        for (String userInfoStr : userInfoStrList) {
            if (StringUtils.isNotBlank(userInfoStr)) {
                resultList.add(JSONObject.parseObject(userInfoStr, SimpleUserInfo.class));
            }
        }
        return resultList;
    }

    /**
     * 用户(简单)列表保存到缓存
     * @param userInfoList
     * @return void
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:14
     */
    @Override
    public void addSimpleUserInfoListCache(List<SimpleUserInfo> userInfoList) {
        Map<String, String> maps = new HashMap<>();
        Map<String, String> erbanMaps = new HashMap<>();

        for (SimpleUserInfo userInfo : userInfoList) {
            maps.put(RedisKeyUtil.appendCacheKeyByColon(
                    redisKeyPre, SIMPLE_USERINFO, userInfo.getUid()),
                    JSONObject.toJSONString(userInfo));
            erbanMaps.put(RedisKeyUtil.appendCacheKeyByColon(
                        redisKeyPre, ERBANNO2UID, userInfo.getErbanNo().toString()),
                    userInfo.getUid().toString());
        }
        redisUtil.multiSet(maps, RedisKeyUtil.RedisExpire_Time.OneDay);
        redisUtil.multiSet(erbanMaps, RedisKeyUtil.RedisExpire_Time.OneDay);
    }

    @Override
    public List<UserInfo> queryByErbanNos(List<String> erbanNosList) {
        List<String> keyList = new ArrayList<>(erbanNosList.size());
        for (String erbanNo : erbanNosList) {
            keyList.add(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, ERBANNO2UID, erbanNo));
        }
        List<UserInfo> resultList = new ArrayList<>();
        List<String> userInfoStrList = redisUtil.multiGet(keyList);
        if (userInfoStrList == null && userInfoStrList.size() == 0) {
            return resultList;
        }

        for (String userInfoStr : userInfoStrList) {
            if (StringUtils.isNotBlank(userInfoStr)) {
                resultList.add(gson.fromJson(userInfoStr, UserInfo.class));
            }
        }
        return resultList;
    }

    /**
     * 根据耳伴号列表查询与UID的对应关系    erbanNo->Uid
     * @param erbanNos
     * @return java.util.Map<java.lang.Long,java.lang.Long>
     * @author qinrenchuan
     * @date 2020/6/4/0004 10:36
     */
    @Override
    public Map<Long, Long> queryErbanNo2UidMapByErbanNos(List<Long> erbanNos) {
        Map<Long, Long> resultMap = new HashMap<>();

        List<String> keyList = new ArrayList<>(erbanNos.size());
        for (Long erbanNo : erbanNos) {
            keyList.add(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, ERBANNO2UID, erbanNo));
        }

        List<String> uidListStr = redisUtil.multiGet(keyList);
        for (int i = 0; i < erbanNos.size(); i++) {
            String uidStr = uidListStr.get(i);
            if (StringUtils.isNotBlank(uidStr)) {
                resultMap.put(erbanNos.get(i), Long.valueOf(uidStr));
            }
        }

        return resultMap;
    }


    /**
     * 将用户列表保存到缓存 hash-key erbanno
     * @param userInfoList
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:58
     */
    @Override
    public void addUserInfoErbanNosListCache(List<UserInfo> userInfoList) {
        Map<String, String> maps = new HashMap<>();
        Map<String, String> erbanMaps = new HashMap<>();

        for (UserInfo userInfo : userInfoList) {
            maps.put(RedisKeyUtil.appendCacheKeyByColon(
                    redisKeyPre, ERBANNO2UID, userInfo.getUid()),
                    JSONObject.toJSONString(userInfo));
            erbanMaps.put(userInfo.getErbanNo().toString(),
                    userInfo.getUid().toString());
        }
        redisUtil.multiSet(maps, RedisKeyUtil.RedisExpire_Time.FiveMinutes);
        redisUtil.multiSet(erbanMaps, RedisKeyUtil.RedisExpire_Time.FiveMinutes);
    }
}
