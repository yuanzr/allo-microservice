package com.dc.allo.task.service.impl;

import com.dc.allo.task.domain.entity.TaskAwardRecord;
import com.dc.allo.task.mapper.TaskAwardRecordMapper;
import com.dc.allo.task.service.TaskAwardRecordService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: TaskAwardRecordServiceImpl
 *
 * @date: 2020年05月27日 17:35
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class TaskAwardRecordServiceImpl implements TaskAwardRecordService {

    @Autowired
    private TaskAwardRecordMapper taskAwardRecordMapper;

    /**
     * 批量插入
     * @param list
     * @return int
     * @author qinrenchuan
     * @date 2020/5/27/0027 17:35
     */
    @Override
    public int insertBatch(List<TaskAwardRecord> list) {
        return taskAwardRecordMapper.insertBatch(list);
    }

    /**
     * 根据业务id查询奖励id列表
     * @param bussinessId
     * @return java.util.List<java.lang.Long>
     * @author qinrenchuan
     * @date 2020/6/1/0001 17:36
     */
    @Override
    public List<Long> queryPrizesByBusinessId(Long bussinessId) {
        return taskAwardRecordMapper.queryPrizesByBusinessId(bussinessId);
    }
}
