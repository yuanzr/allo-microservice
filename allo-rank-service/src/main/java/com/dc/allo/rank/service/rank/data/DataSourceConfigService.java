package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rank.domain.rank.config.RankConfig;
import com.dc.allo.rank.domain.rank.config.RankDatasourceConfig;
import com.dc.allo.rpc.domain.page.AdminPage;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
public interface DataSourceConfigService {

    /**
     * 添加数据源
     * @return
     */
    long addDataSourceConfig(RankDatasourceConfig datasourceConfig);

    long updateDataSourceConfig(RankDatasourceConfig datasourceConfig);

    /**
     * 获取数据源
     * @param appKey
     * @param dataSourceKey
     * @return
     * @throws Exception
     */
    RankDatasourceConfig getDataSource(String appKey, String dataSourceKey) throws Exception;

    /**
     * 后台分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<RankDatasourceConfig> pageDataSource(int pageNum,int pageSize);

    /**
     * 查询所有的id
     * @return
     */
    List<RankDatasourceConfig> queryAllId();
}
