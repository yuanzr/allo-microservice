package com.dc.allo.rank.mapper.rank;

import com.dc.allo.rank.domain.rank.config.BizIdConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/20.
 */
@Mapper
public interface BizIdMapper {

    @Insert("insert into act_center_rank_biz_id (rank_id,biz_ids) values (#{rankId},#{bizIds})")
    long add(@Param("rankId") long rankId,@Param("bizIds") String bizIds);

    @Update("update act_center_rank_biz_id set rank_id = #{rankId},biz_ids = #{bizIds} where id = #{id}")
    long update(@Param("rankId") long rankId,@Param("bizIds") String bizIds,@Param("id") long id);

    @Select("select * from act_center_rank_biz_id where rank_id = #{rankId} limit 1")
    BizIdConfig get(@Param("rankId") long rankId);
}
