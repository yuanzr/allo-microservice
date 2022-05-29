package com.dc.allo.user.mapper.baseinfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: UserLanguageMapper
 * @since 2020/06/30 16:06
 */
@Mapper
public interface UserLanguageMapper {

    /**
     *  根据uid查询用户语言
     * @param uid
     * @return java.lang.String
     * @author tudoutiao
     * @date 16:07 2020/6/30
     **/
    @Select({"select language from country where country_id=(select country_id from users where uid=#{uid}) limit 1"})
    String getUserLanguageByUid(@Param("uid") Long uid);
}
