package com.dc.allo.rank.mapper.rank;

import com.dc.allo.rank.domain.rank.config.App;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Mapper
public interface AppMapper {

    @Select("select * from act_center_app")
    List<App> queryAllApp();

    @Insert("insert into act_center_app (name,app_key,enable) values (#{name},#{appKey},1)")
    long add(@Param("name") String name,@Param("appKey") String appKey);

    @Update("update act_center_app set name = #{name},app_key = #{appKey}, enable = #{enable}  where id = #{id}")
    long update(@Param("id") long id,@Param("name") String name,@Param("appKey") String appKey,@Param("enable") int enable);
}
