package com.dc.allo.rank.service.rank.data.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.domain.award.CommonAward;
import com.dc.allo.rank.domain.rank.config.App;
import com.dc.allo.rank.domain.rank.config.RankConfig;
import com.dc.allo.rank.mapper.rank.AppMapper;
import com.dc.allo.rank.mapper.rank.RankMapper;
import com.dc.allo.rank.service.rank.data.RankConfigService;
import com.dc.allo.rpc.domain.page.AdminPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Service
public class RankConfigServiceImpl implements RankConfigService {

    @Autowired
    RankMapper rankMapper;

    @Autowired
    AppMapper appMapper;

    @Override
    public long addApp(String name, String appKey) {
        if(StringUtils.isBlank(name)||StringUtils.isBlank(appKey)){
            return 0;
        }
        return appMapper.add(name,appKey);
    }

    @Override
    public long updateApp(long id, String name, String appKey,int enable) {
        if(id<=0||StringUtils.isBlank(name)||StringUtils.isBlank(appKey)){
            return 0;
        }
        return appMapper.update(id,name,appKey,enable);
    }

    @Override
    public List<App> queryAllApp() {
        return appMapper.queryAllApp();
    }

    @Override
    public long addRankConfig(RankConfig rankConfig) {
        if(rankConfig == null || StringUtils.isBlank(rankConfig.getAppKey()) || StringUtils.isBlank(rankConfig.getName()) || StringUtils.isBlank(rankConfig.getRankKey())
                || rankConfig.getDataSourceId()<=0) {
            return 0;
        }
        return rankMapper.addRankConfig(rankConfig);
    }

    @Override
    public long upateRankConfig(RankConfig rankConfig) {
        if(rankConfig == null || rankConfig.getId()<=0) {
            return 0;
        }
        return rankMapper.updateRankConfig(rankConfig);
    }

    @Override
    public AdminPage<RankConfig> pageRankConfig(int pageNum, int pageSize) {
        AdminPage<RankConfig> page = new AdminPage<>();
        long total = rankMapper.countRankConf();
        List<RankConfig> confs = rankMapper.pageRankConf((pageNum - 1) * pageSize,pageSize);
        page.setTotal(total);
        page.setRows(confs);
        return page;
    }

    @Override
    public List<RankConfig> queryRankKeys() {
        return rankMapper.queryRankKeys();
    }

    @Override
    @Cached(name = "act-center:rank:rankConfigCache.getRankConfig", key = "#appKey+\"_\"+#rankKey", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public RankConfig getRankConfig(String appKey, String rankKey) throws Exception {
        return rankMapper.getRankConfig(appKey, rankKey);
    }

    @Override
    @Cached(name = "act-center:rank:rankConfigCache.getRankConfigListByDataSourceId", key = "#dataSourceId+\"_\"+#date", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<RankConfig> getRankConfigListByDataSourceId(Integer dataSourceId, Date date) throws Exception {
        return rankMapper.getRankConfigByDataSourceId(dataSourceId, date);
    }

    @Override
    @Cached(name = "act-center:rank:rankConfigCache.getRankConfigListByAppKey", key = "#appKey", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<RankConfig> getRankConfigListByAppKey(String appKey) throws Exception {
        return rankMapper.getRankConfigByAppKey(appKey);
    }

    @Override
    @Cached(name = "act-center:rank:rankConfigCache.queryValidRankConfigs", key = "#date", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<RankConfig> queryValidRankConfigs(@Param("date") Date date) throws Exception {
        return rankMapper.queryValidRankConfigs(date);
    }


}
