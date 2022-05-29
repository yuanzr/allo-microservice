package com.dc.allo.rank.mapper.rank;

import com.dc.allo.rank.domain.rank.config.RankDatasourceConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Mapper
public interface DataSourceMapper {

    @Select("select * from act_center_rank_datasource_config where app_key=#{appKey} and data_source_key=#{dataSourceKey}")
    RankDatasourceConfig getDataSource(@Param("appKey") String appKey, @Param("dataSourceKey") String dataSourceKey);

    @Insert("insert into act_center_rank_datasource_config (app_id, app_key, name, data_source_key, secret,type)" +
            "values(#{datasourceConfig.appId},#{datasourceConfig.appKey},#{datasourceConfig.name},#{datasourceConfig.dataSourceKey},#{datasourceConfig.secret},#{datasourceConfig.type})")
    long add(@Param("datasourceConfig") RankDatasourceConfig datasourceConfig);

    @UpdateProvider(value = DataSourceSqlProvider.class, method = "update")
    long update(@Param("datasourceConfig") RankDatasourceConfig datasourceConfig);

    @Select("select id,name,data_source_key from act_center_rank_datasource_config limit 500")
    List<RankDatasourceConfig> queryAllId();

    @Select("select count(*) from act_center_rank_datasource_config")
    long count();

    @Select("select * from act_center_rank_datasource_config limit #{offset}, #{pageSize}")
    List<RankDatasourceConfig> page(@Param("offset") long offset, @Param("pageSize") int pageSize);

    class DataSourceSqlProvider {
        public String update(@Param("datasourceConfig") RankDatasourceConfig datasourceConfig) {
            StringBuffer sqlBuf = new StringBuffer("update act_center_rank_datasource_config set utime = now() ");
            if (StringUtils.isNotBlank(datasourceConfig.getName())) {
                sqlBuf.append(" ,name = #{datasourceConfig.name} ");
            }
            if (StringUtils.isNotBlank(datasourceConfig.getAppKey())) {
                sqlBuf.append(" ,app_key = #{datasourceConfig.appKey}");
            }
            if (datasourceConfig.getAppId() != null && datasourceConfig.getAppId() > 0) {
                sqlBuf.append(" ,app_id = #{datasourceConfig.appId}");
            }
            if (StringUtils.isNotBlank(datasourceConfig.getDataSourceKey())) {
                sqlBuf.append(" ,data_source_key = #{datasourceConfig.dataSourceKey}");
            }
            if (StringUtils.isNotBlank(datasourceConfig.getSecret())) {
                sqlBuf.append(" ,secret = #{datasourceConfig.secret}");
            }
            if (datasourceConfig.getType() != null && datasourceConfig.getType() > 0) {
                sqlBuf.append(" ,type = #{datasourceConfig.type}");
            }
            sqlBuf.append(" where id = #{datasourceConfig.id}");
            return sqlBuf.toString();
        }
    }
}
