package com.dc.allo.user.mapper.country;

import com.dc.allo.rpc.domain.country.Country;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName: CountryMapper
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/21:27
 */
@Mapper
public interface CountryMapper {

    @Select({"<script>"
            +"SELECT id, country_id, country_name, country_pic, region_no, language, is_show, is_top,"
            +"is_hot, seq, create_time, update_time, priority_show, abbreviation, priority_show_seq "
            +"FROM country WHERE country_id = #{countryId}"
            +"</script>"})
    Country getByCountryId(@Param("countryId") Integer countryId);




}
