package com.dc.allo.task.mapper;

import com.dc.allo.task.domain.entity.CommonTaskPrize;
import com.dc.allo.task.domain.vo.CommonTaskPrizeVO;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * description: 任务奖励Mapper
 * date: 2020年05月26日 11:25
 * author: qinrenchuan
 */
@Mapper
public interface CommonTaskPrizeMapper {
    /**
     * 列表查询
     * @param queryVo
     * @return java.util.List<com.dc.allo.task.domain.vo.CommonTaskPrizeVO>
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:29
     */
    @Select({"<script>"
            + " SELECT "
            + " ctp.id, "
            + " ctp.task_id taskId,  "
            + " ctp.task_type_id taskTypeId,  "
            + " ctp.package_id packageId,  "
            + " ctp.level,  "
            + " ctp.create_time createTime,  "
            + " ctp.update_time updateTime,  "
            + " act.code taskCode,  "
            + " ctt.name taskTypeName  "
            + " FROM activity_common_task_prize ctp  "
            + " LEFT JOIN activity_common_task act ON ctp.task_id=act.id "
            + " LEFT JOIN activity_common_task_type ctt ON ctp.task_type_id=ctt.id "
            + "WHERE 1=1 "
            + "<if test=\"taskId != null and taskId > 0\"> AND ctp.task_id=#{taskId} </if>"
            + "<if test=\"taskTypeId != null and taskTypeId > 0\"> AND ctp.task_type_id=#{taskTypeId} </if>"
            + "<if test=\"level != null and level > 0\"> AND ctp.level=#{level} </if>"
            + "</script>"})
    List<CommonTaskPrizeVO> query(CommonTaskPrizeVO queryVo);

    /**
     * 删除任务奖励基础信息
     * @param taskId
     * @author qinrenchuan
     * @date 2020/5/20/0020 18:35
     */
    @Delete({" DELETE FROM activity_common_task_prize WHERE task_id=#{taskId}"})
    void deleteByTaskId(@Param("taskId") Long taskId);

    /**
     * 批量插入
     * @param list
     * @author qinrenchuan
     * @date 2020/5/20/0020 18:44
     */
    @Insert({"<script>"
            + " INSERT INTO activity_common_task_prize(task_id, task_type_id, level)"
            + " VALUES "
            + "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">"
            + "(#{item.taskId}, #{item.taskTypeId}, #{item.level})"
            + "</foreach>"
            + "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertBatch(@Param("list") List<CommonTaskPrizeVO> list);

    /**
     *  设置任务奖励礼包
     * @param taskPrizeId
     * @param prizePackageId
     * @author qinrenchuan
     * @date 2020/5/25/0025 14:57
     */
    @Update({"UPDATE activity_common_task_prize SET package_id=#{prizePackageId} WHERE id=#{taskPrizeId}"})
    void setPrizePackage(@Param("taskPrizeId") Long taskPrizeId, @Param("prizePackageId") Long prizePackageId);

    /**
     * 根据任务code和等级查询奖品
     * @param actCode
     * @param level
     * @return java.util.List<com.dc.allo.task.domain.entity.CommonTaskPrize>
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:19
     */
    @Select({"<script>" +
                "SELECT "
                + " ctp.level,  "
                + " ctp.package_id "
                + "FROM "
                + " activity_common_task_prize ctp "
                + "INNER JOIN activity_common_task act ON ctp.task_id=act.id "
                + "INNER JOIN activity_common_task_type actt ON actt.id=ctp.task_type_id "
                + "WHERE 1=1 "
                + "<if test=\"level != null and level > 0\"> AND ctp.level=#{level} </if>"
                + "<if test=\"actCode != null and actCode > 0\"> AND act.code=#{actCode} </if>"
                + "<if test=\"taskTypeCode != null \"> AND actt.code=#{taskTypeCode} </if>"
            + "</script>"})
    List<CommonTaskPrize> getPackageIdByActTypeAndLevel(
            @Param("actCode") Byte actCode,
            @Param("level") Byte level,
            @Param("taskTypeCode") Byte taskTypeCode);
}
