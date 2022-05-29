package com.dc.allo.task.test;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rpc.domain.task.SignContinuous;
import com.dc.allo.rpc.domain.task.SignTaskConfig;
import com.dc.allo.task.Application;
import com.dc.allo.task.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/14.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class SignTest {

    @Autowired
    SignService signService;

    @Test
    public void testAddConf(){
        Date now = new Date();
        SignTaskConfig config = new SignTaskConfig();
        config.setApp("allo");
        config.setTaskName("斋月签到活动");
        config.setTaskType(0);
        config.setStime(now);
        config.setEtime(TimeUtils.offsetDay(now,2));
        config.setStime(now);
        signService.addSignConf(config);
    }

    @Test
    public void testSign(){
        signService.addSign("allo",123,1,"2020-04-13",0);
        SignContinuous continuous = signService.getSignContinuous("allo",1,123,true);
        log.info("###################"+continuous.toString());
    }
}
