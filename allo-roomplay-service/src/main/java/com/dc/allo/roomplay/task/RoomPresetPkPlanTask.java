package com.dc.allo.roomplay.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * description: RoomPresetPkPlanTask
 *
 * @date: 2020年04月15日 15:44
 * @author: qinrenchuan
 */
@Component
@Slf4j
public class RoomPresetPkPlanTask {
    /**
     * 定时任务
     * @author qinrenchuan
     * @date 2020/4/15/0015 15:59
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void executePresetPkPlan() {

    }
}
