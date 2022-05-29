package com.dc.allo.biznotice.mapper.firebase;

import com.dc.allo.biznotice.model.firebase.NoticeMsgPushRecord;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName: NoticeMsgPushRecordMapper
 * @Description: Firebase消息记录
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/1/16:39
 */
public interface NoticeMsgPushRecordMapper {


    @Insert({"<script>"
            +"INSERT INTO notice_msg_push_record(message_id, msg_type, image_url, title, body, to_obj_type,"
            + "to_erban_nos, region, push_type, push_time, action_type,route_type, action, push_status, admin_name,create_time) "
            + "VALUES(#{messageId}, #{msgType}, #{imageUrl},#{title}, #{body}, #{toObjType}, #{toErbanNos},"
            + "#{region},#{pushType}, #{pushTime}, #{actionType},#{routeType},#{action}, #{pushStatus}, #{adminName}, #{createTime})"
            +"</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "recordId", keyColumn = "record_id")
    int insert(NoticeMsgPushRecord record);


    @Select({"<script>"
            + "SELECT  record_id, message_id, msg_type, image_url, title, body, to_obj_type, to_erban_nos, region, "
            + "push_type, push_time, action_type,route_type, action, push_status, admin_name, create_time "
            + "FROM notice_msg_push_record "
            + "WHERE record_id = #{recordId}"
            +"</script>"})
    NoticeMsgPushRecord get(@Param("recordId")Long recordId);

    @Select({"<script>"
            + "SELECT  record_id, message_id, msg_type, image_url, title, body, to_obj_type,to_erban_nos, region, "
            + "push_type, push_time, action_type,route_type, action, push_status, admin_name, create_time "
            + "FROM notice_msg_push_record "
            + "WHERE 1=1 "
            + "<if test='starTime != null' > "
            + "AND push_time &gt;= #{starTime} "
            + "</if> "
            + "<if test='endTime != null' > "
            + "AND push_time &lt;= #{endTime} "
            + " </if> "
            + "ORDER BY record_id DESC "
            + "LIMIT #{page},#{pageSize}"
            +"</script>"})
    List<NoticeMsgPushRecord> queryPage(@Param("starTime") Date starTime,@Param("endTime") Date endTime,@Param("page")Integer page,@Param("pageSize")Integer pageSize);

    /**
     * 查询即将发送的消息
     * @param expireTime
     * @return
     */
    @Select({"<script>"
            + "SELECT  record_id, message_id, msg_type, image_url, title, body, to_obj_type,to_erban_nos, region, "
            + "push_type, push_time, action_type,route_type, action, push_status, admin_name, create_time "
            + "FROM notice_msg_push_record "
            + "WHERE push_type=2 AND push_status=1 AND push_time BETWEEN #{currentTime} AND #{expireTime} "
            +"</script>"})
    List<NoticeMsgPushRecord> queryPendingRecord(@Param("currentTime")Date currentTime ,@Param("expireTime") Date expireTime);

    @Select({"<script>"
            + "SELECT  COUNT(1)"
            + "FROM notice_msg_push_record "
            + "WHERE 1=1 "
            + "<if test='starTime != null' > "
            + "AND push_time &gt;= #{starTime} "
            + "</if> "
            + "<if test='endTime != null' > "
            + "AND push_time &lt;= #{endTime} "
            + " </if> "
            +"</script>"})
    int queryPageCount(@Param("starTime") Date starTime,@Param("endTime") Date endTime);




    @Update({"<script>"
            + "UPDATE notice_msg_push_record "
            + "SET  msg_type = #{msgType}, image_url = #{imageUrl}, title = #{title},body = #{body}, to_obj_type = #{toObjType},"
            + "     to_erban_nos = #{toErbanNos}, region = #{region},  push_type = #{pushType}, push_time = #{pushTime},"
            + "     action_type = #{actionType}, route_type = #{routeType}, action = #{action} "
            + "WHERE record_id = #{recordId}"
            +"</script>"})
    int update(NoticeMsgPushRecord record);


    @Update({"<script>"
            + "UPDATE  notice_msg_push_record "
            + "SET push_status = #{pushStatus},message_id=#{messageId} "
            + "WHERE record_id = #{recordId}"
            +"</script>"})
    int updateStatus(@Param("recordId") Long recordId,@Param("pushStatus") Byte pushStatus,@Param("messageId") String messageId);

    @Update({"<script>"
            + "UPDATE  notice_msg_push_record "
            + "SET push_status = #{pushStatus} "
            + "WHERE 1=1 "
            + "<if test='list != null and list.size() > 0'> "
            + " AND record_id IN"
            + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
            +   "#{item} "
            + "</foreach>"
            + "</if>"
            + "</script>"})
    int updateStatusBatch(@Param("list")List<Long> list,@Param("pushStatus") Byte pushStatus);
}
