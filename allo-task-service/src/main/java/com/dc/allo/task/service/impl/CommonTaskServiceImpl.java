package com.dc.allo.task.service.impl;

import com.dc.allo.task.domain.entity.CommonTask;
import com.dc.allo.task.domain.entity.CommonTaskType;
import com.dc.allo.task.domain.vo.CommonTaskVO;
import com.dc.allo.task.mapper.CommonTaskMapper;
import com.dc.allo.task.service.CommonTaskService;
import com.dc.allo.task.service.CommonTaskTypeService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: CommonTaskServiceImpl
 *
 * @date: 2020年05月26日 11:00
 * @author: qnrenchuan
 */
@Service
@Slf4j
public class CommonTaskServiceImpl implements CommonTaskService {

    @Autowired
    private CommonTaskTypeService taskTypeService;

    @Autowired
    private CommonTaskMapper taskMapper;
    @Autowired
    private CommonTaskTypeService commonTaskTypeService;

    /**
     * 根据任务编号查询任务详情
     * @param code
     * @return com.xchat.common.model.ActivityCommonPrizePackage
     * @author qinrenchuan
     * @date 2020/5/20/0020 17:05
     */
    @Override
    public CommonTaskVO getByCode(Byte code) {
        CommonTaskVO commonTaskVO = taskMapper.getByCode(code);
        // 任务类型查询
        if (commonTaskVO != null) {
            List<CommonTaskType> activityCommonTaskTypes =
                    taskTypeService.queryTaskTypesByTaskId(commonTaskVO.getId());
            commonTaskVO.setTaskTypes(activityCommonTaskTypes);
        }

        return commonTaskVO;
    }

    @Override
    public CommonTaskVO getTaskDetailByCode(Byte code) {
        CommonTaskVO commonTaskVO = taskMapper.getByCode(code);
        return commonTaskVO;
    }


    /**
     * 查询任务列表
     * @return java.util.List<com.erban.main.model.ActivityCommonTask>
     * @author qinrenchuan
     * @date 2020/5/21/0021 15:01
     */
    @Override
    public List<CommonTask> queryTaskList() {
        return taskMapper.queryTaskList();
    }

    /**
     * 修改任务状态
     * @param id
     * @param status {@link com.dc.allo.task.constant.TaskConstant.TaskStatus}
     * @author qinrenchuan
     * @date 2020/6/5/0005 15:53
     */
    @Override
    public int updateTaskStatus(Long id, Byte status) {
        return taskMapper.updateTaskStatus(id, status);
    }
}