package com.dc.allo.user.mapper.sysconf;

import com.dc.allo.rpc.domain.sysconf.SysConf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/1.
 */
@Mapper
public interface SysConfMapper {

    @Select({" SELECT config_id, config_name, config_value, name_space FROM sys_conf WHERE config_id=#{configId} and config_status=1 "})
    SysConf getById(@Param("configId") String configId);

    @Select({" SELECT config_id, config_name, config_value, name_space FROM sys_conf WHERE config_status=1 limit 1000 "})
    List<SysConf> queryAll();
}
