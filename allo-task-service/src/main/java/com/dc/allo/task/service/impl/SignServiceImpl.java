package com.dc.allo.task.service.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.redis.RedisLock;
import com.dc.allo.common.redis.RedisLockExcutor;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rpc.domain.task.Sign;
import com.dc.allo.rpc.domain.task.SignContinuous;
import com.dc.allo.rpc.domain.task.SignTaskConfig;
import com.dc.allo.task.mapper.SignMapper;
import com.dc.allo.task.service.SignService;
import com.dc.allo.task.service.cache.SignCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
@Service
@Slf4j
public class SignServiceImpl implements SignService {

    @Autowired
    SignMapper signMapper;

    @Autowired
    SignCache signCache;

    @Autowired
    RedisTemplate<String, String> stringTemplate;

    private boolean validConf(SignTaskConfig config) {
        boolean flag = false;
        if (config != null && StringUtils.isNotBlank(config.getApp())
                && StringUtils.isNotBlank(config.getTaskName())) {
            if (config.getTaskType() == 0) {
                if (config.getStime() != null && config.getEtime() != null) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public long addSignConf(SignTaskConfig config) {
        if (validConf(config)) {
            return signMapper.addSignConf(config);
        }
        return 0;
    }

    private SignTaskConfig getSignConfig(long taskId) {
        SignTaskConfig config = signCache.getSignTaskConfig(taskId);
        if (config != null) {
            //任务已无效
            if (!checkSignConfig(config)) {
                config = null;
            }
            return config;
        }
        config = signMapper.getSignConf(taskId);
        if (config != null) {
            if (checkSignConfig(config)) {
                signCache.setSignTaskConfig(config);
                return config;
            }
        }
        return null;
    }

    /**
     * 检查任务是否有效
     *
     * @param config
     * @return
     */
    private boolean checkSignConfig(SignTaskConfig config) {
        boolean flag = false;
        if (validConf(config)) {
            if (config.getTaskType() == 0) {
                flag = true;
            } else if (config.getTaskType() == 1) {
                Date stime = config.getStime();
                Date etime = config.getEtime();
                if (TimeUtils.isBetween(stime, etime)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 是否断签
     *
     * @param continuous
     * @return
     */
    private boolean isContinuous(SignContinuous continuous) {
        boolean flag = true;
        if (continuous != null) {
            Date preSign = continuous.getPreSignDate();
            try {
                Date now = TimeUtils.fromStrDate(TimeUtils.toStr(new Date(), TimeUtils.PATTERN_FORMAT_DATE));
                //已断签,日期间隔大于1天
                if (TimeUtils.daysBetween(preSign, now) > 1) {
                    flag = false;
                }
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            flag = false;
        }
        return flag;
    }


    @Override
    public SignContinuous getSignContinuous(String app, long taskId, long uid, boolean useCache) {
        SignContinuous continuous = null;
        if (useCache) {
            continuous = signCache.getSignContinuous(app, taskId, uid);
            if (continuous != null) {
                if (!isContinuous(continuous)) {
                    continuous.setCNum(0);
                }
                return continuous;
            }
        }
        continuous = signMapper.getSignContinuous(app, taskId, uid);
        if (continuous != null) {
            if (!isContinuous(continuous)) {
                continuous.setCNum(0);
            }
            signCache.setSignContinuous(continuous);
        }
        return continuous;
    }

    @Override
    @Transactional
    public long addSign(String app, long uid, int taskId, String signDate, int signStatus) {
        String logMsg = "app:" + app + ", uid:" + uid + ", taskId:" + taskId + ", signDate:" + signDate + ", signStatus:" + signStatus;
        SignTaskConfig conf = getSignConfig(taskId);
        if (conf == null) {
            throw new DCException(BusiStatus.PARAMERROR.value(), "参数错误，app不存在此签到活动。" + logMsg);
        }
        long result = 0;
        String lockKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Task, "lock:addSign" , uid , taskId);
        String lockValue = UUID.randomUUID().toString();
        boolean islocked = RedisLock.lock(stringTemplate, lockKey, lockValue, 3);
        try (HintManager hintManager = HintManager.getInstance()){
            if(islocked){
                //指定只从主库读写
                hintManager.setMasterRouteOnly();
                //是否已签
                Sign sign = getSign(app, uid, taskId, signDate);
                if (sign != null) {
                    throw new DCException(BusiStatus.PARAMERROR.value(), "已签到 " + logMsg);
                }
                result = signMapper.addSign(app, uid, taskId, signDate, signStatus);
                //签到成功，增加连签
                if (result > 0) {
                    SignContinuous continuous = getSignContinuous(app, taskId, uid, false);
                    int cNum = 1;
                    if (continuous != null) {
                        if (isContinuous(continuous)) {
                            cNum = continuous.getCNum() + 1;
                        }
                        signMapper.updateSignContinuous(app, uid, taskId, cNum);
                    } else {
                        continuous = new SignContinuous();
                        continuous.setApp(app);
                        continuous.setCNum(cNum);
                        continuous.setTaskId(taskId);
                        continuous.setUid(uid);
                        continuous.setPreSignDate(new Date());
                        signMapper.addSignContinuous(continuous);
                    }
                    signCache.setSignContinuous(continuous);
                } else {
                    log.info("sign failed..." + logMsg);
                }
            }
        } catch (Exception e) {
            log.error("sign err..." + logMsg, e);
            throw new RuntimeException(e);
        }finally {
            if(islocked) {
                RedisLock.unlock(stringTemplate, lockKey, lockValue);
            }
        }
        return result;
    }

    @Override
    public Sign getSign(String app, long uid, int taskId, String signDate) {
        Sign sign = signCache.getSign(app, uid, taskId, signDate);
        if (sign != null) {
            return sign;
        }

        sign = signMapper.getSign(app, uid, taskId, signDate);
        if (sign != null) {
            signCache.setSign(sign);
        }

        return sign;
    }
}
