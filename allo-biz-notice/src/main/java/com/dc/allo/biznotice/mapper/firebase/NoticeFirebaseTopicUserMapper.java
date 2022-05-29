package com.dc.allo.biznotice.mapper.firebase;


import com.dc.allo.biznotice.model.firebase.NoticeFirebaseTopicUser;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface NoticeFirebaseTopicUserMapper {


    @Insert({"<script>"
            +"INSERT INTO notice_firebase_topic_user (uid,topic,create_time,update_time) "
            +"VALUES(#{uid},#{topic},now(),now())"
            +"</script>"})
    int insert(@Param("uid") Long uid,@Param("topic") String topic);


    @Select({"<script>"
            +"SELECT topic FROM notice_firebase_topic_user "
            +"WHERE uid = #{uid} AND topic IN('ar','en','in','tr','all')"
            +"</script>"})
    List<String> selectRegionTopicByUid(@Param("uid") Long uid);


    @Delete({"<script>"
            +"DELETE FROM notice_firebase_topic_user WHERE uid=#{uid} AND topic IN('ar','en','in','tr','all')"
            +"</script>"})
    int deleteAllLang(@Param("uid") Long uid);

}