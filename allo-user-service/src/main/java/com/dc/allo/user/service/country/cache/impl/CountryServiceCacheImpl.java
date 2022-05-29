package com.dc.allo.user.service.country.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.RedisKeyUtil.RedisExpire_Time;
import com.dc.allo.rpc.domain.country.Country;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.user.service.country.cache.CountryServiceCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CountryServiceCacheImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/22:17
 */
@Service
public class CountryServiceCacheImpl implements CountryServiceCache {

    @Autowired
    private RedisUtil redisUtil;
    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.User;
    /** 用户详情缓存key： 字符串数据结构 */
    private static final String COUNTRY = "country";

    @Override
    public Country getCountry(Integer countryId) {
        Object entityCache = redisUtil.get(
                RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, COUNTRY, countryId));
        if (entityCache != null && StringUtils.isNotBlank(entityCache.toString())) {
            return JSONObject.parseObject(entityCache.toString(), Country.class);
        }
        return null;
    }

    @Override
    public void addCountry(Country country) {
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, COUNTRY, country.getCountryId()),
                JSONObject.toJSONString(country), RedisKeyUtil.RedisExpire_Time.FiveMinutes);
    }
}
