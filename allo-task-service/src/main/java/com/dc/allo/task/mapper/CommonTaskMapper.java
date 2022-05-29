package com.dc.allo.task.mapper;

import com.dc.allo.task.domain.entity.CommonTask;
import com.dc.allo.task.domain.vo.CommonTaskVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * description: CommonTaskMapper
 * date: 2020年05月26日 11:04
 * author: qinrenchuan
 */
@Mapper
public interface CommonTaskMapper {

    /**
     * 根据code查询任务
     * @param code
     * @return com.dc.allo.task.domain.vo.CommonTaskVO
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:31
     */
    @Select({
            " SELECT "
                    + " id, name, code, status, level_num levelNum, create_time createTime, update_time updateTime  "
                    + " FROM activity_common_task "
                    + " WHERE code=#{code} "
    })
    CommonTaskVO getByCode(@Param("code") Byte code);

    /**
     * 查询任务列表
     * @return java.util.List<com.dc.allo.task.domain.entity.CommonTask>
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:31
     */
    @Select({
            " SELECT "
                    + " id, name, code, status, level_num levelNum  "
                    + " FROM activity_common_task "
    })
    List<CommonTask> queryTaskList();

    /**
     * 修改任务状态
     * @param id
     * @param status {@link com.dc.allo.task.constant.TaskConstant.TaskStatus}
     * @author qinrenchuan
     * @date 2020/6/5/0005 15:53
     */
    @Update({"UPDATE activity_common_task SET status=#{status} WHERE id=#{id} "})
    int updateTaskStatus(@Param("id") Long id, @Param("status") Byte status);
}
