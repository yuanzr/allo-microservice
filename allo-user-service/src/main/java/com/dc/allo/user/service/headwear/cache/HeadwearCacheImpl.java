package com.dc.allo.user.service.headwear.cache;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.domain.user.headwear.Headwear;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: HeadwearCacheImpl
 *
 * @date: 2020年04月07日 17:02
 * @author: qinrenchuan
 */
@Service
public class HeadwearCacheImpl implements HeadwearCache {

    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.User;
    /** 用户详情缓存key： 字符串数据结构 */
    private static final String HEADWEAR = "headwear";

    /**
     * 根据id查询
     * @param id
     * @return com.dc.allo.rpc.domain.user.headwear.Headwear
     * @author qinrenchuan
     * @date 2020/4/7/0007 17:20
     */
    @Override
    public Headwear getById(Long id) {
        Object headwearCache = redisUtil.get(
                RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, HEADWEAR, id));

        if (headwearCache != null && StringUtils.isNotBlank(headwearCache.toString())) {
            return JSONObject.parseObject(headwearCache.toString(), Headwear.class);
        }
        return null;
    }
}
