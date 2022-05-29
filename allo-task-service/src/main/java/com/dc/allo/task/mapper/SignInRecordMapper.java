package com.dc.allo.task.mapper;

import com.dc.allo.task.domain.entity.SignInRecord;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: 签到记录Mapper
 * date: 2020年05月27日 11:21
 * author: qinrenchuan
 */
@Mapper
public interface SignInRecordMapper {

    /**
     * 根据UID查询最近一次签到记录
     * @param uid
     * @return com.dc.allo.task.domain.entity.SignInRecord
     * @author qinrenchuan
     * @date 2020/5/27/0027 11:25
     */
    @Select({" SELECT id,uid, sign_time signTime,times FROM task_sign_record WHERE uid=#{uid} ORDER BY id DESC LIMIT #{count}"})
    List<SignInRecord> getLatestSignInRecordByUid(@Param("uid") Long uid, @Param("count") Integer count);

    /**
     * 保存签到记录
     * @param signInRecord
     * @return void
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:59
     */
    @Insert({"INSERT INTO task_sign_record(uid, times) VALUES(#{uid}, #{times})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int save(SignInRecord signInRecord);

    /**
     * 列表查询
     * @param uid
     * @return java.util.List<com.dc.allo.task.domain.vo.SignRecordQueryVO>
     * @author qinrenchuan
     * @date 2020/5/28/0028 15:57
     */
    @Select({"<script> "
            + " SELECT "
                + " id, "
                + " uid, "
                + " sign_time signTime, "
                + " times "
            + " FROM task_sign_record "
            + " WHERE 1=1 "
            + "<if test=\"uid != null and uid > 0\"> AND uid=#{uid} </if>"
            + " ORDER BY id DESC "
            + "</script>  "})
    List<SignInRecord> listByUid(@Param("uid") Long uid);
}
