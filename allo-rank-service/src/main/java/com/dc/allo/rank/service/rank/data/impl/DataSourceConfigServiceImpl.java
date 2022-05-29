package com.dc.allo.rank.service.rank.data.impl;


import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.domain.rank.config.RankConfig;
import com.dc.allo.rank.domain.rank.config.RankDatasourceConfig;
import com.dc.allo.rank.mapper.rank.DataSourceMapper;
import com.dc.allo.rank.service.rank.data.DataSourceConfigService;
import com.dc.allo.rpc.domain.page.AdminPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Service
public class DataSourceConfigServiceImpl implements DataSourceConfigService {
    @Autowired
    DataSourceMapper dataSourceMapper;


    @Override
    public long addDataSourceConfig(RankDatasourceConfig datasourceConfig) {
        if(datasourceConfig == null || datasourceConfig.getAppId()<=0 || StringUtils.isBlank(datasourceConfig.getAppKey())
                || StringUtils.isBlank(datasourceConfig.getDataSourceKey())){
            return 0;
        }
        return dataSourceMapper.add(datasourceConfig);
    }

    @Override
    public long updateDataSourceConfig(RankDatasourceConfig datasourceConfig) {
        if(datasourceConfig == null || datasourceConfig.getId()<=0 ){
            return 0;
        }
        return dataSourceMapper.update(datasourceConfig);
    }

    @Override
    @Cached(name = "act-center:rank:dataSourceConfigCache.getDataSource", key = "#appKey+\"_\"+#dataSourceKey", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public RankDatasourceConfig getDataSource(String appKey, String dataSourceKey) throws Exception {
        return dataSourceMapper.getDataSource(appKey, dataSourceKey);
    }

    @Override
    public AdminPage<RankDatasourceConfig> pageDataSource(int pageNum, int pageSize) {
        AdminPage<RankDatasourceConfig> page = new AdminPage<>();
        long total = dataSourceMapper.count();
        List<RankDatasourceConfig> confs = dataSourceMapper.page((pageNum - 1) * pageSize,pageSize);
        page.setTotal(total);
        page.setRows(confs);
        return page;
    }

    @Override
    public List<RankDatasourceConfig> queryAllId() {
        return dataSourceMapper.queryAllId();
    }
}
