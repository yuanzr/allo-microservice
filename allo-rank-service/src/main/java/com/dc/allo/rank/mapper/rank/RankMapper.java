package com.dc.allo.rank.mapper.rank;

import com.dc.allo.rank.domain.rank.config.RankConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Mapper
public interface RankMapper {
    @Insert(" insert into act_center_rank_config (app_key,name,rank_key,data_source_id,time_type,calc_type,divide_by_biz_id," +
            "member_type,specify_room_type,room_type_id,direction,gen_relation,start_time,end_time,expire_unit,expire_value) " +
            "values(#{rankConfig.appKey},#{rankConfig.name},#{rankConfig.rankKey},#{rankConfig.dataSourceId},#{rankConfig.timeType}" +
            ",#{rankConfig.calcType},#{rankConfig.divideByBizId},#{rankConfig.memberType},#{rankConfig.specifyRoomType}" +
            ",#{rankConfig.roomTypeId},#{rankConfig.direction},#{rankConfig.genRelation},#{rankConfig.startTime}" +
            ",#{rankConfig.endTime},#{rankConfig.expireUnit},#{rankConfig.expireValue})")
    long addRankConfig(@Param("rankConfig") RankConfig rankConfig);

    @UpdateProvider(value = RankSqlProvider.class ,method = "updateRankConf")
    long updateRankConfig(@Param("rankConfig") RankConfig rankConfig);

    @Select("select * from act_center_rank_config limit #{offset},#{pageSize}")
    List<RankConfig> pageRankConf(@Param("offset")long offset,@Param("pageSize")int pageSize);

    @Select("select id,name,rank_key,app_key from act_center_rank_config where now()>start_time and now()<end_time")
    List<RankConfig> queryRankKeys();

    @Select("select count(*) from act_center_rank_config")
    long countRankConf();

    @Select("select * from act_center_rank_config where app_key=#{appKey} and rank_key=#{rankKey}")
    RankConfig getRankConfig(@Param("appKey") String appKey, @Param("rankKey") String rankKey);

    @Select("select * from act_center_rank_config where data_source_id=#{dataSourceId} and #{date} between start_time and end_time")
    List<RankConfig> getRankConfigByDataSourceId(@Param("dataSourceId") Integer dataSourceId, @Param("date") Date date);

    @Select("select * from act_center_rank_config where app_key=#{appKey}")
    List<RankConfig> getRankConfigByAppKey(@Param("appKey") String appKey);

    //结束时间放大25小时，方便判断最后的连续榜单或者总榜
    @Select("select * from act_center_rank_config where  #{date} between start_time and date_add(end_time,interval 25 hour)")
    List<RankConfig> queryValidRankConfigs(@Param("date") Date date);

    class RankSqlProvider{

        public String updateRankConf(@Param("rankConfig") RankConfig rankConfig){
            StringBuffer sqlBuf = new StringBuffer("update act_center_rank_config set utime = now() ");
            if(StringUtils.isNotBlank(rankConfig.getName())){
                sqlBuf.append(" ,name=#{rankConfig.name} ");
            }
            if(StringUtils.isNotBlank(rankConfig.getRankKey())){
                sqlBuf.append(" ,rank_key=#{rankConfig.rankKey}");
            }
            if(rankConfig.getDataSourceId()>0){
                sqlBuf.append(" ,data_source_id=#{rankConfig.dataSourceId}");
            }
            if(rankConfig.getTimeType()>0){
                sqlBuf.append(" ,time_type=#{rankConfig.timeType}");
            }
            if(rankConfig.getCalcType() >0){
                sqlBuf.append(" ,calc_type=#{rankConfig.calcType}");
            }
            if(rankConfig.getMemberType() >0){
                sqlBuf.append(" ,member_type=#{rankConfig.memberType}");
            }
            if(rankConfig.getDirection()>=0){
                sqlBuf.append(" ,direction=#{rankConfig.direction}");
            }
            if(rankConfig.getStartTime()!=null){
                sqlBuf.append(" ,start_time=#{rankConfig.startTime}");
            }
            if(rankConfig.getEndTime()!=null){
                sqlBuf.append(" ,end_time=#{rankConfig.endTime}");
            }
            if(rankConfig.getExpireUnit()>0){
                sqlBuf.append(" ,expire_unit=#{rankConfig.expireUnit}");
            }
            if(rankConfig.getExpireValue()>0){
                sqlBuf.append(" ,expire_value=#{rankConfig.expireValue}");
            }
            sqlBuf.append(" ,gen_relation=#{rankConfig.genRelation} where id = #{rankConfig.id}");
            return  sqlBuf.toString();
        }
    }
}
