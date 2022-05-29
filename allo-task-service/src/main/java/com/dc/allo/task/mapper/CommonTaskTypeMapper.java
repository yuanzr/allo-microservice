package com.dc.allo.task.mapper;

import com.dc.allo.task.domain.entity.CommonTaskType;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: CommonTaskTypeMapper
 * date: 2020年05月26日 11:10
 * author: qinrenchuan
 */
@Mapper
public interface CommonTaskTypeMapper {

    /**
     * 根据任务id查询任务类型
     * @param taskId
     * @return java.util.List<com.dc.allo.task.domain.entity.CommonTaskType>
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:31
     */
    @Select({" SELECT id, task_id taskId, name FROM activity_common_task_type WHERE task_id=#{taskId}"})
    List<CommonTaskType> queryTaskTypesByTaskId(@Param("taskId") Long taskId);

}
