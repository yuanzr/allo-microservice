package com.dc.allo.task.constant;


import com.dc.allo.common.constants.BaseBusiStatus;

/**
 * description: TaskBusiStatus
 *
 * @date: 2020年05月26日 14:15
 * @author: qinrenchuan
 */
public enum TaskBusiStatus implements BaseBusiStatus {
    /** 没找到相关任务 */
    TASK_NOT_FOUND(10001, "task not found."),
    /** admin初始化任务奖励失败： 任务状态不正确 */
    TASK_PRIZE_INIT_FAIL(10002, "任务状态为有效，初始化任务奖励数据以后将清空之前数据，故请先将该任务置为无效。"),
    ;


    private Integer code;
    private String msg;

    TaskBusiStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
