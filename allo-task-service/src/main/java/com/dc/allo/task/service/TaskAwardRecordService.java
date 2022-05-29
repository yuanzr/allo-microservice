package com.dc.allo.task.service;

import com.dc.allo.task.domain.entity.TaskAwardRecord;
import java.util.List;

/**
 * description: TaskAwardRecordService
 * date: 2020年05月27日 17:34
 * author: qinrenchuan
 */
public interface TaskAwardRecordService {

    /**
     * 批量插入
     * @param list
     * @return int
     * @author qinrenchuan
     * @date 2020/5/27/0027 17:35
     */
    int insertBatch(List<TaskAwardRecord> list);
    
    /**
     * 根据业务id查询奖励id列表
     * @param bussinessId
     * @return java.util.List<java.lang.Long>
     * @author qinrenchuan
     * @date 2020/6/1/0001 17:36
     */
    List<Long> queryPrizesByBusinessId(Long bussinessId);
}
