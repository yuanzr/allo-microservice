package com.dc.allo.task.mapper;

import com.dc.allo.task.domain.entity.TaskAwardRecord;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: TaskAwardRecordMapper
 * date: 2020年05月27日 17:28
 * author: qinrenchuan
 */
@Mapper
public interface TaskAwardRecordMapper {

    /**
     * 批量插入
     * @param list
     * @author qinrenchuan
     * @date 2020/5/20/0020 18:44
     */
    @Insert({"<script>"
            + " INSERT INTO activity_task_award_record(act_code, business_id, prize_id, actual_prize_id, prize_type, prize_img, prize_value, time_unit, time_num)"
            + " VALUES "
            + "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">"
            + "(#{item.actType}, #{item.businessId}, #{item.actPrizeId}, #{item.actualPrizeId}, #{item.prizeType}, #{item.prizeImgUrl}, #{item.prizeValue}, #{item.timeUnit}, #{item.timeNum})"
            + "</foreach>"
            + "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertBatch(@Param("list") List<TaskAwardRecord> list);

    /**
     * 根据业务id查询奖励id列表
     * @param bussinessId
     * @return java.util.List<java.lang.Long>
     * @author qinrenchuan
     * @date 2020/6/1/0001 17:39
     */
    @Select({" SELECT prize_id FROM activity_task_award_record WHERE business_id=#{bussinessId}"})
    List<Long> queryPrizesByBusinessId(@Param("bussinessId") Long bussinessId);
}
