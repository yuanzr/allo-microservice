package com.dc.allo.rank.mapper.rank;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Mapper
public interface RankDataRecordMapper {

    @Select("select count(1)as cnt from information_schema.TABLES where table_name = #{tableName}")
    int existTable(@Param("tableName") String tableName);

    @Insert("insert into ${map.tableName} (app_key, data_source_key, data_source_secret,send_id, recv_id, score,count,room_id, room_type, biz_id,`timestamp`, check_pass) " +
            "values (#{map.bean.appKey,jdbcType=VARCHAR}, #{map.bean.dataSourceKey,jdbcType=VARCHAR}, #{map.bean.dataSourceSecret,jdbcType=VARCHAR},\n" +
            "        #{map.bean.sendId,jdbcType=VARCHAR}, #{map.bean.recvId,jdbcType=VARCHAR}, #{map.bean.score,jdbcType=DECIMAL},#{map.bean.count,jdbcType=INTEGER},\n" +
            "        #{map.bean.roomId,jdbcType=VARCHAR}, #{map.bean.roomType,jdbcType=INTEGER}, #{map.bean.bizId,jdbcType=VARCHAR},\n" +
            "        #{map.bean.timestamp,jdbcType=BIGINT}, #{map.bean.checkPass,jdbcType=INTEGER})")
    long insertAndReturnId(@Param("map") Map<String, Object> paramMap);

    @Insert("create table ${tableName} like act_center_rank_data_record")
    void createNewTable(@Param("tableName") String tableName);
}
