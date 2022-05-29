package com.dc.allo.rank.service.rank;

import com.dc.allo.rank.domain.rank.Rank;
import com.dc.allo.rank.domain.rank.config.*;
import com.dc.allo.rank.service.rank.cache.RankCache;
import com.dc.allo.rank.service.rank.data.AutoRewardService;
import com.dc.allo.rank.service.rank.data.BizIdConfigService;
import com.dc.allo.rank.service.rank.data.DataSourceConfigService;
import com.dc.allo.rank.service.rank.data.RankConfigService;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rpc.domain.page.AdminPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Slf4j
@Component
public class RankManager {

    @Autowired
    RankConfigService rankConfigService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    DataSourceConfigService dataSourceConfigService;

    @Autowired
    AutoRewardService autoRewardService;

    @Autowired
    BizIdConfigService bizIdConfigService;

    @Autowired
    RankCache rankCache;

    /**
     * 新增app
     * @param name
     * @param appKey
     * @return
     */
    public long addApp(String name,String appKey){
        return rankConfigService.addApp(name,appKey);
    }

    /**
     * 更新app
     * @param id
     * @param name
     * @param appKey
     * @return
     */
    public long updateApp(long id,String name,String appKey,int enable){
        return rankConfigService.updateApp(id, name, appKey, enable);
    }

    /**
     * 查询所有app
     * @return
     */
    public List<App> queryAllApp(){
        return rankConfigService.queryAllApp();
    }

    /**
     * 添加榜单配置
     * @param rankConfig
     * @return
     */
    public long addRankConfig(RankConfig rankConfig){
        return rankConfigService.addRankConfig(rankConfig);
    }

    public long updateRankConfig(RankConfig rankConfig){
        return rankConfigService.upateRankConfig(rankConfig);
    }

    public AdminPage<RankConfig> pageRankConfig(int pageNum,int pageSize){
        return rankConfigService.pageRankConfig(pageNum, pageSize);
    }

    public List<RankConfig> queryRankKeys(){
        return rankConfigService.queryRankKeys();
    }
    /**
     * 添加数据源配置
     * @param datasourceConfig
     * @return
     */
    public long addDataSourceConfig(RankDatasourceConfig datasourceConfig){
        return dataSourceConfigService.addDataSourceConfig(datasourceConfig);
    }

    public long updateDataSourceConfig(RankDatasourceConfig datasourceConfig){
        return dataSourceConfigService.updateDataSourceConfig(datasourceConfig);
    }

    public AdminPage<RankDatasourceConfig> pageDataSourceConfig(int pageNum,int pageSize){
        return dataSourceConfigService.pageDataSource(pageNum, pageSize);
    }

    public List<RankDatasourceConfig> queryAllId(){
        return dataSourceConfigService.queryAllId();
    }

    public long addAutoRewardConfig(RankAutoRewardConfig config){
        return autoRewardService.addAutoRewardConfig(config);
    }

    public long updateAutoRewardConfig(RankAutoRewardConfig config){
        return autoRewardService.updateAutoRewardConfig(config);
    }

    public AdminPage<RankAutoRewardConfig> pageAutoRewardConfig(int pageNum,int pageSize){
        return autoRewardService.pageAutoRewardConfig(pageNum, pageSize);
    }

    public long addBizIdConfig(long rankId,String bizIds){
        return bizIdConfigService.addBizIdConfig(rankId, bizIds);
    }

    public long updateBizIdConfig(long rankId,String bizIds,long id){
        return bizIdConfigService.updateBizIdConfig(rankId, bizIds, id);
    }

    public BizIdConfig getBizIds(long rankId){
        return bizIdConfigService.get(rankId);
    }

    public BizIdConfig getBizIds4db(long rankId){
        return bizIdConfigService.get4db(rankId);
    }

    public void delRankCache(String rankRedisKey){
        rankCache.delRank(rankRedisKey);
    }

    /**
     * 从缓存获取数据源对应的榜单对象列表
     *
     * @param datasourceConfig
     * @return
     */
    public List<Rank> getRankListBelongToDataSource(RankDatasourceConfig datasourceConfig) throws Exception {
        List<Rank> rankList = new ArrayList<>();
        try {
            List<RankConfig> rankConfigs = rankConfigService.getRankConfigListByDataSourceId(datasourceConfig.getId(), new Date());
            if (CollectionUtils.isNotEmpty(rankConfigs)) {
                for (RankConfig rankConfig : rankConfigs) {
                    rankList.add(buildVo(rankConfig));
                }
            }
        } catch (ExecutionException e) {
            log.error(Constant.Rank.ERROR + "数据源id对应榜单实例列表获取失败 datasourceConfig:{}", datasourceConfig, e);
        }
        return rankList;
    }

    /**
     * 从缓存获取榜单对象列表
     *
     * @param appKey
     * @param rankKey
     * @return
     */
    public Rank getRankById(String appKey, String rankKey) {
        try {
            RankConfig rankConfig = rankConfigService.getRankConfig(appKey, rankKey);
            if (rankConfig != null) {
                return buildVo(rankConfig);
            }
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "榜单实例获取失败 appKey:{} rankKey:{}", appKey, rankKey, e);
        }
        return Rank.NULL;
    }


    /**
     * 构建榜单对象
     *
     * @param rankConfig
     * @return
     */
    private Rank buildVo(RankConfig rankConfig) {
        if (rankConfig == null) {
            return null;
        }
        Rank rank = Rank.builder()
                .stringTemplate(stringRedisTemplate)
                .id(rankConfig.getId())
                .name(rankConfig.getName())
                .timeType(Constant.Rank.RankTimeType.of(rankConfig.getTimeType()))
                .dataSourceId(rankConfig.getDataSourceId())
                .direction(Constant.Rank.RankDirection.of(rankConfig.getDirection()))
                .startTime(rankConfig.getStartTime())
                .endTime(rankConfig.getEndTime())
                .expireUnit(Constant.Rank.RankExpireTimeUnit.of(rankConfig.getExpireUnit()))
                .expireValue(rankConfig.getExpireValue())
                .genRelation(rankConfig.getGenRelation())
                .key(rankConfig.getRankKey())
                .memberType(Constant.Rank.RankMemberType.of(rankConfig.getMemberType()))
                .roomTypeId(rankConfig.getRoomTypeId())
                .divideByBizId(rankConfig.isDivideByBizId())
                .calcType(Constant.Rank.RankCalcType.of(rankConfig.getCalcType()))
                .specifyRoomType(rankConfig.isSpecifyRoomType())
                .build();
        return rank;
    }

}
