package com.dc.allo.rank.service.rank.cache;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
public interface RankCache {

    /**
     * 记录榜单自动发奖
     * @param rankId
     * @param rankKey
     */
    void recordAutoReward(String rankId,String rankKey,long seconds);

    /**
     * 判断榜单是否已经自动发奖
     * @param rankId
     * @param rankKey
     * @return
     */
    boolean alreadyAutoReward(String rankId,String rankKey);

    void delRank(String rankRedisKey);
}
