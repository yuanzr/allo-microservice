package com.dc.allo.rank.service.bet.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rpc.domain.bet.BetAnimationInfo;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.bet.BetWinSpiritInfo;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Service
public class BetCacheImpl implements BetCache{

    @Autowired
    RedisUtil redisUtil;

    @Override
    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Override
    public String getBetLockKey(long gameId, long uid) {
        return RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_SETTLE_GAME_LOCK);
    }

    @Override
    public void setCurGameRound(BetGameRound gameRound) {
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.GAME_ROUND,gameRound.getActId()), JsonUtils.toJson(gameRound), RedisKeyUtil.RedisExpire_Time.OneHour);
    }

    @Override
    public BetGameRound getBetCurGameRound(long actId) {
        BetGameRound betGameRound = null;
        String json = redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.GAME_ROUND,actId));
        if(StringUtils.isNotBlank(json)){
            betGameRound = JsonUtils.fromJson(json,BetGameRound.class);
        }
        return betGameRound;
    }

    @Override
    public void setBetGameRound(BetGameRound gameRound) {
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.GAME_ROUND,gameRound.getId()),JsonUtils.toJson(gameRound),RedisKeyUtil.RedisExpire_Time.OneHour);
    }

    @Override
    public BetGameRound getBetGameRound(long gameId) {
        BetGameRound gameRound = null;
        String json = redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.GAME_ROUND,gameId));
        if(StringUtils.isNotBlank(json)){
            gameRound = JsonUtils.fromJson(json,BetGameRound.class);
        }
        return gameRound;
    }

    @Override
    public long incrBet(long gameId, long uid) {
        Long num = redisUtil.incrBy(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.GAME_ROUND, "hasbet", gameId, uid), 1);
        redisUtil.expire(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.GAME_ROUND, "hasbet", gameId, uid), RedisKeyUtil.RedisExpire_Time.OneHour);
        return num==null?0:num;
    }

    @Override
    public boolean hasBet(long gameId, long uid) {
        return redisUtil.hasKey(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.GAME_ROUND, "hasbet", gameId, uid));
    }

    /**
     * 是否结算中
     * @param gameId
     * @return
     */
    @Override
    public boolean isSettling(long gameId){
        return redisUtil.hasKey(BetConstants.getBetSettleGameLock(gameId));
    }

    /**
     * 统计投注额
     * @param actId
     * @param gameId
     * @param uid
     * @param spiritId
     * @param price
     */
    @Override
    public void statisticUserCurAmount(long actId,long gameId,long uid,long spiritId,long price) {
        String userKey = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_UID_STATISTIC_CUR,actId,gameId,uid);
        String spiritKey = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_SPIRIT_STATISTIC_CUR, actId, gameId);
        String supportKey = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_SPIRIT_SUPPORT_STATISTIC_CUR,actId,gameId);
        redisUtil.getStringRedisTemplate().executePipelined((RedisCallback<Object>) connection -> {
            connection.hashCommands().hIncrBy(userKey.getBytes(), String.valueOf(spiritId).getBytes(), price);
            connection.hashCommands().hIncrBy(spiritKey.getBytes(), String.valueOf(spiritId).getBytes(), price);
            connection.hashCommands().hIncrBy(supportKey.getBytes(),String.valueOf(spiritId).getBytes(),1);
            connection.keyCommands().expire(userKey.getBytes(), BetConstants.EXPIRE_TIME_HOUR);
            connection.keyCommands().expire(spiritKey.getBytes(), BetConstants.EXPIRE_TIME_HOUR);
            connection.keyCommands().expire(supportKey.getBytes(),BetConstants.EXPIRE_TIME_HOUR);
            return null;
        });
    }

    @Override
    public void statisticUserFinalAmount(long actId, long gameId, long uid, long spiritId, long price) {
        String key = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_UID_STATISTIC_FINAL,actId,gameId,uid);
        redisUtil.hIncrBy(key, String.valueOf(spiritId), price);
        redisUtil.expire(key, BetConstants.EXPIRE_TIME_HOUR, TimeUnit.SECONDS);
    }

    @Override
    public void statisticSpiritAmounts(long actId,long gameId,Map<String,String> spiritAmounts){
        String spiritKey = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_SPIRIT_STATISTIC_CUR, actId, gameId);
        redisUtil.hPutAll(spiritKey, spiritAmounts, BetConstants.EXPIRE_TIME_HOUR);
    }

    @Override
    public void statisticUserFinalAmounts(long actId,long gameId,long uid,Map<String,String> userAmounts){
        String key = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_UID_STATISTIC_FINAL,actId,gameId,uid);
        redisUtil.hPutAll(key,userAmounts,BetConstants.EXPIRE_TIME_HOUR);
    }

    /**
     * 获取每轮游戏结束时用户最终投注额
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    private Map<String,String> getStatisticUserAmount(long actId, long gameId, long uid){
        String key = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_UID_STATISTIC_FINAL,actId,gameId,uid);
        return BetConstants.objMap2StrMap(redisUtil.hGetAll(key));
    }

    @Override
    public Map<String, String> getUserAmount(long actId, long gameId, long uid) {
        return getStatisticUserAmount(actId, gameId, uid);
    }

    @Override
    public void setWinSpiritInfo(long actId, long gameId, BetWinSpiritInfo winSpiritInfo) {
        if(actId<=0||gameId<=0||winSpiritInfo==null){
            return;
        }
        String key = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_WIN_SPIRIT_INFO,actId,gameId);
        redisUtil.set(key, JsonUtils.toJson(winSpiritInfo), BetConstants.EXPIRE_TIME_HOUR, TimeUnit.SECONDS);
    }

    @Override
    public BetWinSpiritInfo getWinSpiritInfo(long actId, long gameId) {
        BetWinSpiritInfo winSpiritInfo = null;
        String key = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_WIN_SPIRIT_INFO,actId,gameId);
        String json = redisUtil.get(key);
        if(StringUtils.isNotBlank(json)){
            winSpiritInfo = JsonUtils.fromJson(json,BetWinSpiritInfo.class);
        }
        return winSpiritInfo;
    }

    /**
     * 获取用户某场游戏每个精灵投注额
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    private Map<String,String> getStatisticUserCurAmount(long actId, long gameId, long uid) {
        String key = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_UID_STATISTIC_CUR, actId, gameId, uid);
        return BetConstants.objMap2StrMap(redisUtil.hGetAll(key));
    }

    /**
     * 获取某轮游戏每个精灵的投注总额
     * @param actId
     * @param gameId
     * @return
     */
    @Override
    public Map<String, String> getSpiritAmount(long actId, long gameId){
        String spiritKey = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_SPIRIT_STATISTIC_CUR,actId,gameId);
        return BetConstants.objMap2StrMap(redisUtil.hGetAll(spiritKey));
    }

    @Override
    public Map<String, String> getSpiritSupport(long actId, long gameId) {
        String supportKey = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_SPIRIT_SUPPORT_STATISTIC_CUR,actId,gameId);
        return BetConstants.objMap2StrMap(redisUtil.hGetAll(supportKey));
    }

    @Override
    public void setBetResult(long actId, long gameId, long uid, List<BetResultInfo> results) {
        if(CollectionUtils.isEmpty(results)){
            return;
        }
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_RESULT,actId,gameId,uid),JsonUtils.toJson(results),BetConstants.EXPIRE_TIME_HOUR,TimeUnit.SECONDS);;

    }

    @Override
    public List<BetResultInfo> queryBetResult(long actId, long gameId, long uid) {
        List<BetResultInfo> results = null;
        String jsons = redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_RESULT,actId,gameId,uid));
        if (StringUtils.isNotBlank(jsons)){
            results = JsonUtils.fromJson(jsons, new TypeReference<List<BetResultInfo>>() {});
        }
        return results;
    }

    @Override
    public void setGameRoundAnimation(long gameId, String animations) {
        if(StringUtils.isNotBlank(animations)){
            redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_ANIMATIONS,gameId),animations,BetConstants.EXPIRE_TIME_DAY);
        }
    }

    @Override
    public BetAnimationInfo getGameRoundAnimation(long gameId) {
        BetAnimationInfo animationInfo = null;
        String json = redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_ANIMATIONS,gameId));
        if(StringUtils.isNotBlank(json)){
            animationInfo = JsonUtils.fromJson(json,BetAnimationInfo.class);
        }
        return animationInfo;
    }

    @Override
    public void setGameRoundRank(long gameId,long spiritId,int finalTime){
        String key = RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_GAMEROUND_SPIRITS_RANK, gameId);
        redisUtil.zAdd(key,String.valueOf(spiritId),finalTime);
        redisUtil.expire(key,BetConstants.EXPIRE_TIME_DAY);
    }

    @Override
    public List<Long> getGameRoundRank(long gameId,int size){
        Set<String> jsons = redisUtil.zRange(RedisKeyUtil.appendCacheKeyByColon(BetConstants.REDIS_KEY.BET_GAMEROUND_SPIRITS_RANK, gameId), 0, size);
        if(CollectionUtils.isNotEmpty(jsons)){
            return jsons.stream().map(json->Long.valueOf(json)).collect(Collectors.toList());
        }
        return null;
    }
}
