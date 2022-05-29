package com.dc.allo.task.mapper;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rpc.domain.task.Sign;
import com.dc.allo.rpc.domain.task.SignContinuous;
import com.dc.allo.rpc.domain.task.SignTaskConfig;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
@Mapper
public interface SignMapper {

    @Insert("REPLACE into sign_task_conf (app,task_name,task_type,stime,etime,ctime) values (#{config.app},#{config.taskName},#{config.taskType},#{config.stime},#{config.etime},now())")
    long addSignConf(@Param("config")SignTaskConfig config);

    @Select("select * from sign_task_conf where id = #{id}")
    SignTaskConfig getSignConf(@Param("id")long id);

    @Insert("REPLACE into sign_continuous (app,task_id,uid,c_num,pre_sign_date) values (#{continuous.app},#{continuous.taskId},#{continuous.uid},#{continuous.cNum},#{continuous.preSignDate})")
    long addSignContinuous(@Param("continuous")SignContinuous continuous);

    @Select("select * from sign_continuous where uid = #{uid} and app = #{app} and task_id = #{taskId}")
    SignContinuous getSignContinuous(@Param("app")String app,@Param("taskId") long taskId,@Param("uid") long uid);

    @Update("update sign_continuous set c_num = #{cNum}, pre_sign_date = now() where uid = #{uid} and app = #{app} and task_id = #{taskId}")
    long updateSignContinuous(@Param("app") String app,@Param("uid")long uid,@Param("taskId") long taskId,@Param("cNum")int cNum);

    @InsertProvider(type = SignMapperProvider.class,method = "addSign")
    long addSign(@Param("app") String app, @Param("uid") long uid, @Param("taskId") int taskId, @Param("signDate") String signDate, @Param("signStatus") int signStatus);

    @SelectProvider(type = SignMapperProvider.class,method = "getSign")
    Sign getSign(@Param("app") String app, @Param("uid") long uid, @Param("taskId") int taskId, @Param("signDate") String signDate);

    class SignMapperProvider {
        public String addSign(@Param("app") String app, @Param("uid") long uid, @Param("taskId") int taskId, @Param("signDate") String signDate, @Param("signStatus") int signStatus) {
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("insert INTO sign_").append(uid % 100);
            sqlBuf.append(" (app,uid,task_id,sign_date,sign_status) VALUES " +
                    "(#{app},#{uid},#{taskId},#{signDate},#{signStatus})");
            return sqlBuf.toString();
        }

        public String getSign(@Param("app") String app, @Param("uid") long uid, @Param("taskId") int taskId, @Param("signDate") String signDate){
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("select * from sign_").append(uid % 100);
            sqlBuf.append(" where uid = #{uid} and task_id = #{taskId} and app = #{app} and sign_date = #{signDate}");
            return sqlBuf.toString();
        }
    }
}
