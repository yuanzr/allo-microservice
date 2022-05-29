package com.dc.allo.task.service;

import com.dc.allo.task.domain.entity.CommonTask;
import com.dc.allo.task.domain.vo.CommonTaskVO;
import java.util.List;

/**
 * description: 任务Service
 * date: 2020年05月26日 11:00
 * author: qinrenchuan
 */
public interface CommonTaskService {

    /**
     * 根据任务编号查询任务详情
     * @param code
     * @return com.xchat.common.model.ActivityCommonPrizePackage
     * @author qinrenchuan
     * @date 2020/5/20/0020 17:05
     */
    CommonTaskVO getByCode(Byte code);


    /**
     *  根据活动编号查询任务礼包明细
     * @param code
     * @return
     */
    CommonTaskVO getTaskDetailByCode(Byte code);

    /**
     * 查询任务列表
     * @return java.util.List<com.erban.main.model.ActivityCommonTask>
     * @author qinrenchuan
     * @date 2020/5/21/0021 15:01
     */
    List<CommonTask> queryTaskList();

    /**
     * 修改任务状态
     * @param id
     * @param status {@link com.dc.allo.task.constant.TaskConstant.TaskStatus}
     * @author qinrenchuan
     * @date 2020/6/5/0005 15:53
     */
    int updateTaskStatus(Long id, Byte status);
}
