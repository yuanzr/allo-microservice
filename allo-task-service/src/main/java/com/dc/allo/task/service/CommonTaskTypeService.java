package com.dc.allo.task.service;

import com.dc.allo.task.domain.entity.CommonTaskType;
import java.util.List;

/**
 * description: 任务Service
 * date: 2020年05月26日 11:00
 * author: qinrenchuan
 */
public interface CommonTaskTypeService {

    /**
     * 根据任务Id查询任务类型列表
     * @param taskId
     * @return java.util.List<com.dc.allo.task.domain.entity.CommonTaskType>
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:16
     */
    List<CommonTaskType>  queryTaskTypesByTaskId(Long taskId);
}
