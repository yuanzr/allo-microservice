package com.dc.allo.rank.service.rank.data.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.common.redis.RedisLock;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.domain.rank.Rank;
import com.dc.allo.rank.domain.rank.config.RankAutoRewardConfig;
import com.dc.allo.rank.domain.rank.config.RankConfig;
import com.dc.allo.rank.domain.rank.config.RankDatasourceConfig;
import com.dc.allo.rank.mapper.rank.AutoRewardMapper;
import com.dc.allo.rank.service.rank.RankManager;
import com.dc.allo.rank.service.award.AwardService;
import com.dc.allo.rank.service.rank.cache.RankCache;
import com.dc.allo.rank.service.rank.data.AutoRewardService;
import com.dc.allo.rank.service.rank.data.RankConfigService;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.domain.rank.RankDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
@Service
@Slf4j
public class AutoRewardServiceImpl implements AutoRewardService {

    @Autowired
    AutoRewardMapper autoRewardMapper;

    @Autowired
    RankManager rankManager;

    @Autowired
    RankCache rankCache;

    @Autowired
    RankConfigService rankConfigService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AwardService awardService;

    @Override
    public long addAutoRewardConfig(RankAutoRewardConfig autoRewardConfig) {
        if (autoRewardConfig == null || autoRewardConfig.getAwardPackageId() <= 0 || autoRewardConfig.getRankId() <= 0) {
            return 0;
        }
        return autoRewardMapper.addConfig(autoRewardConfig);
    }

    @Override
    public long updateAutoRewardConfig(RankAutoRewardConfig autoRewardConfig) {
        if (autoRewardConfig == null || autoRewardConfig.getId()<=0) {
            return 0;
        }
        return autoRewardMapper.updateConfig(autoRewardConfig);
    }

    @Override
    public AdminPage<RankAutoRewardConfig> pageAutoRewardConfig(int pageNum, int pageSize) {
        AdminPage<RankAutoRewardConfig> page = new AdminPage<>();
        long total = autoRewardMapper.countConfig();
        List<RankAutoRewardConfig> confs = autoRewardMapper.pageConfig((pageNum - 1) * pageSize,pageSize);
        page.setTotal(total);
        page.setRows(confs);
        return page;
    }

    @Override
    @Cached(name = "act-center:rank:autoRewardCache.queryAwardConfigs", key = "#rankId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<RankAutoRewardConfig> queryAwardConfigs(long rankId) {
        return autoRewardMapper.queryByRankId(rankId);
    }


    private long addAutoRewardDetail(long rankId, String rankKey, long awardConfigId, int memberType, String memberId, boolean awardResult) {
        if (rankId <= 0 || awardConfigId <= 0) {
            return 0;
        }
        return autoRewardMapper.addDetail(rankId, rankKey, awardConfigId, memberType, memberId, awardResult);
    }

    /**
     * ??????????????????
     *
     * @throws Exception
     */
    @Override
    public void autoRewardTask() throws Exception {
        String lockKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Rank, "lock:autoReward");
        String lockValue = UUID.randomUUID().toString();
        boolean lockSuccess = RedisLock.lock(stringRedisTemplate, lockKey, lockValue, 10);
        if (lockSuccess) {
            StopWatch st = new StopWatch("autoRewardTask");
            st.start(Constant.Rank.INFO + "????????????????????????????????????");
            try {
                List<RankConfig> rankConfs = rankConfigService.queryValidRankConfigs(new Date());
                Rank rank = null;
                for (RankConfig rankConf : rankConfs) {
                    rank = rankManager.getRankById(rankConf.getAppKey(), rankConf.getRankKey());
                    if (rank == null) {
                        continue;
                    }
                    List<RankAutoRewardConfig> awardConfs = queryAwardConfigs(rank.getId());
                    if (CollectionUtils.isNotEmpty(awardConfs)) {
                        log.info(Constant.Rank.INFO + "???????????? name:" + rank.getName() + "??????????????? ???????????? awardConfs:" + JsonUtils.toJson(awardConfs));
                        executeAutoReward(rank, awardConfs);
                    }
                }
            } catch (Exception e) {
                log.error(Constant.Rank.ERROR + "??????????????????", e);
            }
            st.stop();
            log.info(st.prettyPrint());
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param rank
     * @param awardConfs
     */
    private void executeAutoReward(Rank rank, List<RankAutoRewardConfig> awardConfs) {
        try {
            switch (rank.getTimeType()) {
                case HOUR:
                    executeTimeRankAutoReward(rank, awardConfs);
                    break;
                case DAY:
                    executeTimeRankAutoReward(rank, awardConfs);
                    break;
                case WEEK:
                    executeTimeRankAutoReward(rank, awardConfs);
                    break;
                case MONTH:
                    executeTimeRankAutoReward(rank, awardConfs);
                    break;
                case YEAR:
                    executeTimeRankAutoReward(rank, awardConfs);
                    break;
                case UNLIMITED:
                    executeTotalRankAutoReward(rank, awardConfs);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "????????????????????????????????? ?????? name:" + rank.getName() + " ???????????? configs:" + awardConfs, e);
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param rank
     * @param configs
     * @throws Exception
     */
    private void executeTimeRankAutoReward(Rank rank, List<RankAutoRewardConfig> configs) throws Exception {
        //???????????????????????????????????????
        if (rankCache.alreadyAutoReward(String.valueOf(rank.getId()), rank.getLatestRankKey(1, null))) {
            log.info(Constant.Rank.INFO + "?????? name:" + rank.getName() + "latestRankKey:" + rank.getLatestRankKey(1, null) + " ?????????????????????????????????????????????");
            return;
        }
        try {
            sendPrize(rank, configs);
        } finally {
            rankCache.recordAutoReward(String.valueOf(rank.getId()), rank.getLatestRankKey(1, null), rank.getExpireTime() + RedisKeyUtil.RedisExpire_Time.OneMonth);
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param rank
     * @param configs
     * @throws Exception
     */
    private void executeTotalRankAutoReward(Rank rank, List<RankAutoRewardConfig> configs) throws Exception {
        //?????????????????????????????????
        if (!rank.isEnded()) {
            log.info(Constant.Rank.INFO + "?????? name:" + rank.getName() + "???????????? ???????????? ?????????????????????");
            return;
        }
        //????????????????????????
        if (rankCache.alreadyAutoReward(String.valueOf(rank.getId()), rank.getCurrentRankKey())) {
            log.info(Constant.Rank.INFO + "?????? name:" + rank.getName() + "latestRankKey:" + rank.getCurrentRankKey() + " ?????????????????????????????????????????????");
            return;
        }
        try {
            sendPrize(rank, configs);
        } finally {
            rankCache.recordAutoReward(String.valueOf(rank.getId()), rank.getCurrentRankKey(), rank.getExpireTime() + RedisKeyUtil.RedisExpire_Time.OneMonth);
        }
    }

    /**
     * ????????????
     *
     * @param rank
     * @param configs
     * @throws Exception
     */
    private void sendPrize(Rank rank, List<RankAutoRewardConfig> configs) throws Exception {
        for (RankAutoRewardConfig config : configs) {
            int rankBegin = config.getBegin();
            int rankEnd = config.getEnd();
            //?????????????????????????????????????????????uid??????
            List<RankDetail> rankList = rank.queryRangeRankList(String.valueOf(rank.getId()), 1, rankBegin, rankEnd, null);
            if (CollectionUtils.isNotEmpty(rankList) && config != null) {
                for (RankDetail detail : rankList) {
                    long memberId = Long.valueOf(detail.getId());
                    double score = detail.getScore();
                    //??????????????????
                    if (score < config.getLimitScore()) {
                        //????????????????????????break
                        break;
                    }
                    try {
                        ReleasePkgResult result = awardService.releaseAwardPackage(memberId, config.getAwardPackageId());
                        //??????????????????
                        addAutoRewardDetail(rank.getId(), rank.getLatestRankKey(1, null), config.getId(), detail.getRankIdType(), detail.getId(), result.isResult());
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

}
