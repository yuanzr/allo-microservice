package com.dc.allo.task.service.cache;

import com.dc.allo.rpc.domain.task.Sign;
import com.dc.allo.rpc.domain.task.SignContinuous;
import com.dc.allo.rpc.domain.task.SignTaskConfig;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
public interface SignCache {

    void setSignTaskConfig(SignTaskConfig config);

    SignTaskConfig getSignTaskConfig(long taskId);

    void setSignContinuous(SignContinuous continuous);

    SignContinuous getSignContinuous(String app,long taskId,long uid);

    void setSign(Sign sign);

    Sign getSign(String app,long taskId,long uid,String date);

    boolean newRegisterUser(Long uid);
}
