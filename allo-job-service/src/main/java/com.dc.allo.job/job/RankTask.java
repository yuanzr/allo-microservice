package com.dc.allo.job.job;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.redis.RedisLock;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rpc.api.rank.DcAwardService;
import com.dc.allo.rpc.api.rank.DcBetService;
import com.dc.allo.rpc.api.rank.DcRankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhangzhenjun on 2020/12/29.
 */
@Slf4j
@Component
public class RankTask {
    @Reference
    DcBetService dcBetService;

    @Reference
    DcRankService dcRankService;

    @Reference
    DcAwardService dcAwardService;

    @Autowired
    RedisUtil redisUtil;

    @Scheduled(cron = "0 */2 * * * ?")
    public void genCamelGame(){
        log.info("RankTask genCamelGame start============== time:{}",new Date());
        try{
            String lockKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Rank, "lock:genCamelGame");
            String lockValue = UUID.randomUUID().toString();
            boolean lockSuccess = RedisLock.lock(redisUtil.getStringRedisTemplate(), lockKey, lockValue, 10);
            if (lockSuccess) {
                dcBetService.genGameRound(1);
            }
        }catch (Exception e){
            log.error("RankTask genCamelGame err============== time:{}",new Date(),e);
        }
        log.info("RankTask genCamelGame end============== time:{}", new Date());
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void creatRankTable(){
        log.info("RankTask creatRankTable start============== time:{}",new Date());
        try{
            String lockKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Rank, "lock:creatRankTable");
            String lockValue = UUID.randomUUID().toString();
            boolean lockSuccess = RedisLock.lock(redisUtil.getStringRedisTemplate(), lockKey, lockValue, 10);
            if (lockSuccess) {
                dcRankService.check2CreatNextMonthTable();
                dcBetService.check2CreatNextMonthTable();
                dcAwardService.check2CreatNextMonthTable();
            }
        }catch (Exception e){
            log.error("RankTask creatRankTable err============== time:{}",new Date(),e);
        }
        log.info("RankTask creatRankTable end============== time:{}", new Date());
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void autoReward(){
        log.info("RankTask autoReward start============== time:{}",new Date());
        try{
            String lockKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Rank, "lock:autoRewardTask");
            String lockValue = UUID.randomUUID().toString();
            boolean lockSuccess = RedisLock.lock(redisUtil.getStringRedisTemplate(), lockKey, lockValue, 10);
            if (lockSuccess) {
                dcRankService.autoReward();
            }
        }catch (Exception e){
            log.error("RankTask autoReward err============== time:{}", new Date(), e);
        }
        log.info("RankTask autoReward end============== time:{}", new Date());
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void betStatistic(){
        log.info("RankTask betStatistic start============== time:{}",new Date());
        try{
            String lockKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Rank, "lock:betStatistic");
            String lockValue = UUID.randomUUID().toString();
            boolean lockSuccess = RedisLock.lock(redisUtil.getStringRedisTemplate(), lockKey, lockValue, 10);
            if (lockSuccess) {
                dcBetService.betStatistic();
            }
        }catch (Exception e){
            log.error("RankTask betStatistic err============== time:{}",new Date(),e);
        }
        log.info("RankTask betStatistic end============== time:{}",new Date());
    }
}
