package com.dc.allo.rank.service.bet.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.BetActConfig;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetAwardInfo;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rank.mapper.bet.BetActConfigMapper;
import com.dc.allo.rank.service.bet.BetActConfigService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Service
@Slf4j
public class BetActConfigServiceImpl implements BetActConfigService {

    @Autowired
    BetActConfigMapper actConfigMapper;

    private LoadingCache<Long, BetActInfo> betActInfoCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(60, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, BetActInfo>() {
                @Override
                public BetActInfo load(Long key) throws Exception {
                    return loadBetActInfo(key);
                }
            });


    @Cached(name = "act-center:bet:actConfigCache.getBetActConfig", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    protected BetActConfig getBetActConfig(long actId){
        return actConfigMapper.get(actId);
    }

    @Cached(name = "act-center:bet:actConfigCache.querySpiritConfigs", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    protected List<BetSpiritInfo> querySpiritConfigs(long actId){
        return actConfigMapper.querySpiritConfigs(actId);
    }

    @Cached(name = "act-center:bet:actConfigCache.queryAwardConfigs", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    protected List<BetAwardInfo> queryAwardConfigs(long actId){
        return actConfigMapper.queryAwardConfigs(actId);
    }

    public BetActInfo loadBetActInfo(long id){
        BetActInfo info = null;
        BetActConfig config = getBetActConfig(id);
        if(config!=null){
            info = new BetActInfo();
            info.setActId(id);
            info.setGameDuration(config.getGameDuration());
            info.setAnimationDuration(config.getAnimationDuration());
            info.setTransAnimationDuration(config.getTransAnimationDuration());
            info.setShowDuration(config.getShowDuration());
            info.setBetModel(config.getBetModel());
            info.setBankerModel(config.getBankerModel());
            info.setWarnScore(config.getWarnScore());
            info.setPayType(config.getPayType());
            info.setStime(config.getStime());
            info.setEtime(config.getEtime());
            info.setJoinSpiritNum(config.getJoinSpiritNum());
            info.setDeductRate(config.getDeductRate());
            info.setAnimationModel(config.getAnimationModel());
            info.setRemark(config.getRemark());

            //设置投注精灵信息
            List<BetSpiritInfo> spiritConfigs = querySpiritConfigs(id);
            info.setSpirits(spiritConfigs);

            String payPrices = config.getPayPrices();
            //投注金额
            if(StringUtils.isNotBlank(payPrices)){
                String[] arr = payPrices.split(",");
                List<Integer> priceList = new ArrayList<>(arr.length);
                for(String a:arr){
                    priceList.add(Integer.valueOf(a));
                }
                info.setPayPrices(priceList);
            }else{//没配置用默认值
                List<Integer> priceList = new ArrayList<>(4);
                priceList.add(500);
                priceList.add(1000);
                priceList.add(2000);
                priceList.add(3000);
                info.setPayPrices(priceList);
            }

            //设置投注奖励信息
            List<BetAwardInfo> awardConfigs = queryAwardConfigs(id);
            if(CollectionUtils.isNotEmpty(awardConfigs)){
                List<BetAwardInfo> winAwardInfos = awardConfigs.stream().filter(betAwardInfo -> BetConstants.WAWARD_TARGET_WIN==betAwardInfo.getAwardTarget()).collect(Collectors.toList());
                List<BetAwardInfo> lossAwardInfos = awardConfigs.stream().filter(betAwardInfo -> BetConstants.WAWARD_TARGET_LOSS==betAwardInfo.getAwardTarget()).collect(Collectors.toList());
                List<BetAwardInfo> grandAwardInfos = awardConfigs.stream().filter(betAwardInfo -> BetConstants.WAWARD_TARGET_GRANDPRIZE==betAwardInfo.getAwardTarget()).collect(Collectors.toList());
                info.setWinAwardInfo(CollectionUtils.isEmpty(winAwardInfos) ? null : winAwardInfos.get(0));
                info.setLossAwardInfo(CollectionUtils.isEmpty(lossAwardInfos ) ? null : lossAwardInfos.get(0));
                info.setGrandAwardInfo(CollectionUtils.isEmpty(grandAwardInfos ) ? null : grandAwardInfos.get(0));
            }
        }
        return info;
    }

    /**
     * 校验
     * @param betActConfig
     * @return
     */
    private boolean valid(BetActConfig betActConfig){
        boolean flag = false;
        if(betActConfig !=null && betActConfig.getStime() !=null && betActConfig.getEtime() !=null && betActConfig.getStime().before(betActConfig.getEtime())){
            flag = true;
        }
        return flag;
    }

    @Override
    public long add(BetActConfig betActConfig) {
        if(!valid(betActConfig)){
            return 0;
        }
        return actConfigMapper.add(betActConfig);
    }

    @Override
    public long update(BetActConfig betActConfig) {
        if(!valid(betActConfig)){
            return 0;
        }
        return actConfigMapper.update(betActConfig);
    }

    @Override
    public List<BetActConfig> queryAllAct() {
        return actConfigMapper.queryAll();
    }

    @Override
    public boolean checkActing(BetActInfo info) {
        if(info==null){
            return false;
        }
        long curTime = System.currentTimeMillis();
        long stime = info.getStime().getTime();
        long etime = info.getEtime().getTime();
        if(curTime>=stime&&curTime<=etime&& CollectionUtils.isNotEmpty(info.getSpirits())
                &&info.getWinAwardInfo()!=null){
            return true;
        }
        return false;
    }

    @Override
    public BetActInfo getBetActInfo(long actId) {
        try {
            return betActInfoCache.get(actId);
        } catch (ExecutionException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
