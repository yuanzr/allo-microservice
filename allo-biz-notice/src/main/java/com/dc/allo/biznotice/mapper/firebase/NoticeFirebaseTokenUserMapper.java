package com.dc.allo.biznotice.mapper.firebase;


import com.dc.allo.biznotice.model.firebase.NoticeFirebaseTokenUser;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface NoticeFirebaseTokenUserMapper {

    @Insert({"<script>"
            +"INSERT INTO notice_firebase_token_user (uid, register_token,create_time,update_time)"
            +"VALUES(#{uid},#{registerToken},now(),now())"
            +"ON DUPLICATE key UPDATE uid=#{uid},register_token=#{registerToken},update_time=now()"
            +"</script>"})
    int insert(@Param("uid") Long uid,@Param("registerToken") String registerToken);


    @Delete({"<script>"
            +"DELETE FROM notice_firebase_token_user WHERE uid=#{uid} AND register_token=#{registerToken}"
            +"</script>"})
    int delete(@Param("uid") Long uid,@Param("registerToken") String registerToken);


    @Select({"<script>"
            + "SELECT register_token FROM notice_firebase_token_user "
            + "WHERE 1=1 "
            + "<if test='list != null and list.size() > 0'> "
            + " AND uid IN"
            + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
            +   "#{item} "
            + "</foreach>"
            + "</if>"
            + "</script>"})
    List<String> queryToken(List<Long> list);

    @Select({"<script>"
            + "SELECT uid, register_token FROM notice_firebase_token_user "
            + "WHERE uid=#{uid} "
            + "</script>"})
    NoticeFirebaseTokenUser get(@Param("uid") Long uid);
}