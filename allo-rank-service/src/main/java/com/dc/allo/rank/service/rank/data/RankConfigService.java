package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rank.domain.rank.config.App;
import com.dc.allo.rank.domain.rank.config.RankConfig;
import com.dc.allo.rpc.domain.page.AdminPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
public interface RankConfigService {

    /**
     * 新增app配置
     * @param name
     * @param appKey
     * @return
     */
    long addApp(String name,String appKey);

    /**
     * 更新app配置
     * @param id
     * @param name
     * @param appKey
     * @return
     */
    long updateApp(long id,String name,String appKey,int enable);

    /**
     * 查询所有app配置
     * @return
     */
    List<App> queryAllApp();


    /**
     * 新增榜单配置
     * @param rankConfig
     * @return
     */
    long addRankConfig(RankConfig rankConfig);

    /**
     * 更新榜单配置
     * @param rankConfig
     * @return
     */
    long upateRankConfig(RankConfig rankConfig);

    AdminPage<RankConfig> pageRankConfig(int pageNum,int pageSize);

    List<RankConfig> queryRankKeys();

    /**
     * 根据appkey和榜单key获取榜单配置
     * @param appKey
     * @param rankKey
     * @return
     * @throws Exception
     */
    RankConfig getRankConfig(String appKey, String rankKey) throws Exception;

    List<RankConfig> getRankConfigListByDataSourceId(Integer dataSourceId, Date date) throws Exception;

    List<RankConfig> getRankConfigListByAppKey(String appKey) throws Exception;

    /**
     * 获取所有有效期榜单
     * @param date
     * @return
     * @throws Exception
     */
    List<RankConfig> queryValidRankConfigs(@Param("date") Date date)throws Exception;
}
