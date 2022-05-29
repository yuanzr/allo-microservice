package com.dc.allo.task.service.impl;

import com.dc.allo.task.domain.entity.CommonTaskType;
import com.dc.allo.task.mapper.CommonTaskTypeMapper;
import com.dc.allo.task.service.CommonTaskTypeService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: CommonTaskServiceImpl
 *
 * @date: 2020年05月26日 11:00
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class CommonTaskTypeServiceImpl implements CommonTaskTypeService {

    @Autowired
    private CommonTaskTypeMapper taskTypeMapper;

    /**
     * 根据任务Id查询任务类型列表
     * @param taskId
     * @return java.util.List<com.dc.allo.task.domain.entity.CommonTaskType>
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:16
     */
    @Override
    public List<CommonTaskType> queryTaskTypesByTaskId(Long taskId) {
        return taskTypeMapper.queryTaskTypesByTaskId(taskId);
    }
}