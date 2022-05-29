package com.dc.allo.task.service.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rpc.domain.task.Sign;
import com.dc.allo.rpc.domain.task.SignContinuous;
import com.dc.allo.rpc.domain.task.SignTaskConfig;
import com.dc.allo.task.service.cache.SignCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
@Service
public class SignCacheImpl implements SignCache {

    @Autowired
    RedisUtil redisUtil;

    private String getConfigKey(long taskId){
        return RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Task,"sign-config",taskId);
    }

    private String getContinuousKey(String app,long taskId,long uid){
        return RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Task,"sign-continuous",app,taskId,uid);
    }

    private String getSignKey(String app,long taskId,long uid,String date){
        return RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Task,"sign",app,taskId,uid,date);
    }

    @Override
    public void setSignTaskConfig(SignTaskConfig config) {
        if(config !=null && StringUtils.isNotBlank(config.getApp()) && config.getId()>0) {
            redisUtil.set(getConfigKey(config.getId()), JsonUtils.toJson(config), RedisKeyUtil.RedisExpire_Time.OneDay);
        }
    }

    @Override
    public SignTaskConfig getSignTaskConfig(long taskId) {
        SignTaskConfig config = null;
        String json = redisUtil.get(getConfigKey(taskId));
        if(StringUtils.isNotBlank(json)){
            config = JsonUtils.fromJson(json,SignTaskConfig.class);
        }
        return config;
    }

    @Override
    public void setSignContinuous(SignContinuous continuous) {
        if(continuous!=null&&StringUtils.isNotBlank(continuous.getApp())
                &&continuous.getPreSignDate()!=null&&continuous.getTaskId()>0
                &&continuous.getUid()>0){
            redisUtil.set(getContinuousKey(continuous.getApp(),continuous.getTaskId(),continuous.getUid()),JsonUtils.toJson(continuous),RedisKeyUtil.RedisExpire_Time.OneHour);
        }
    }

    @Override
    public SignContinuous getSignContinuous(String app,long taskId, long uid) {
        SignContinuous continuous = null;
        String json = redisUtil.get(getContinuousKey(app,taskId,uid));
        if(StringUtils.isNotBlank(json)){
            continuous = JsonUtils.fromJson(json,SignContinuous.class);
        }
        return continuous;
    }

    @Override
    public void setSign(Sign sign) {
        if(sign!=null&&StringUtils.isNotBlank(sign.getApp())
                &&sign.getTaskId()>0&&sign.getUid()>0){
            redisUtil.set(getSignKey(sign.getApp(),sign.getTaskId(),sign.getUid(), TimeUtils.toStr(sign.getSignDate(), TimeUtils.PATTERN_FORMAT_DATE)),JsonUtils.toJson(sign), RedisKeyUtil.RedisExpire_Time.OneDay);
        }
    }

    @Override
    public Sign getSign(String app, long taskId, long uid,String date) {
        Sign sign = null;
        String json = redisUtil.get(getSignKey(app,taskId,uid,date));
        if(StringUtils.isNotBlank(json)){
            sign = JsonUtils.fromJson(json,Sign.class);
        }
        return sign;
    }

    @Override
    public boolean newRegisterUser(Long uid) {
        Object newUserObj = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.new_user.name()),uid.toString());
        if(newUserObj == null){
            return false;
        }
        return newUserObj.toString().equals("1") ? true : false;
    }
}
