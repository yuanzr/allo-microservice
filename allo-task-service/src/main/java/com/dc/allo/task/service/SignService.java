package com.dc.allo.task.service;

import com.dc.allo.rpc.domain.task.Sign;
import com.dc.allo.rpc.domain.task.SignContinuous;
import com.dc.allo.rpc.domain.task.SignTaskConfig;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
public interface SignService {

    /**
     * 添加签到配置信息
     * @param config
     * @return
     */
    long addSignConf(SignTaskConfig config);

    /**
     * 针对某个任务签到
     * @param app
     * @param uid
     * @param taskId
     * @param signDate
     * @param signStatus
     * @return
     */
    long addSign( String app,  long uid,  int taskId,  String signDate, int signStatus);

    /**
     * 获取某天签到信息
     * @param app
     * @param uid
     * @param taskId
     * @param SignDate
     * @return
     */
    Sign getSign(String app,long uid,int taskId,String SignDate);

    /**
     * 获取连签记录
     * @param app
     * @param taskId
     * @param uid
     * @param useCache
     * @return
     */
    SignContinuous getSignContinuous(String app,long taskId,long uid,boolean useCache);
}
