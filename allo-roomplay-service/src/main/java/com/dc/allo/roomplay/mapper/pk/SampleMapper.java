package com.dc.allo.roomplay.mapper.pk;

import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Mapper
public interface SampleMapper {

    @Insert("insert into admin_user (username, password,headimg, email,roleStr,createTime,phone,qq) " +
            "VALUES(#{sample.username}, #{sample.password},#{sample.headimg},#{sample.email}," +
            "#{sample.roleStr},now(),#{sample.phone},#{sample.qq})")
    @Options(useGeneratedKeys = true, keyProperty = "sample.id")
    long addAdminUser(@Param(value = "sample") Sample sample);

    @Select("select * from admin_user  limit 100")
    List<Sample> queryAdmins();

    @Update("update admin_user set phone=#{phone} where id=#{id} ")
    long updateAdminPhone(@Param(value = "id") long id, @Param(value = "phone") String phone);

    @SelectProvider(type = SampleMapperProvider.class, method = "pageAdminsSql")
    List<Sample> pageAdmins(@Param("offset") long offset, @Param("pageSize") int pageSize);

    class SampleMapperProvider {
        public String pageAdminsSql(@Param("offset") long offset, @Param("pageSize") int pageSize) {
            StringBuilder sqlBuilder = new StringBuilder("select * from admin_user where 1=1 ");

            sqlBuilder.append(" limit " + offset + "," + pageSize);
            return sqlBuilder.toString();
        }
    }
}
