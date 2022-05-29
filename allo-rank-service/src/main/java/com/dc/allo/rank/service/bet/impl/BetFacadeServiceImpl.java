package com.dc.allo.rank.service.bet.impl;

import com.dc.allo.common.component.delay.redis.DelayMessage;
import com.dc.allo.common.component.delay.redis.RedisDelayQueue;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.redis.RedisLock;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.*;
import com.dc.allo.rank.domain.bet.vo.*;
import com.dc.allo.rank.redis.message.BetMessage;
import com.dc.allo.rank.service.bet.*;
import com.dc.allo.rank.service.bet.animation.AnimationProcessorProxy;
import com.dc.allo.rank.service.bet.award.AwardProcessorProxy;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rpc.domain.bet.*;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.domain.page.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Service
@Slf4j
public class BetFacadeServiceImpl implements BetFacadeService {

    @Autowired
    BetActConfigService actConfigService;

    @Autowired
    BetGameRoundService gameRoundService;

    @Autowired
    BetResultService betResultService;

    @Autowired
    BetStatisticService betStatisticService;

    @Autowired
    BetDetailService betDetailService;

    @Autowired
    BetSpiritService betSpiritService;

    @Autowired
    BetAwardConfigService betAwardConfigService;

    @Autowired
    @Qualifier("betDelayQueue")
    private RedisDelayQueue betDelayQueue;

    @Autowired
    BetCache betCache;

    @Autowired
    AwardProcessorProxy awardProcessorProxy;

    @Autowired
    AnimationProcessorProxy animationProcessorProxy;

    @Override
    public long addActConf(BetActConfig betActConfig) {
        return actConfigService.add(betActConfig);
    }

    @Override
    public long updateActConf(BetActConfig betActConfig) {
        return actConfigService.update(betActConfig);
    }

    @Override
    public List<BetActConfig> queryAllAct() {
        return actConfigService.queryAllAct();
    }

    @Override
    public BetActInfo getActInfo(long actId) {
        BetActInfo actInfo = actConfigService.getBetActInfo(actId);
        if(actInfo!=null){
            List<BetSpiritInfo> spirits = actInfo.getSpirits();
            if(CollectionUtils.isNotEmpty(spirits)){
                spirits = spirits.stream().sorted(Comparator.comparing(BetSpiritInfo::getSpiritId)).collect(Collectors.toList());
                actInfo.setSpirits(spirits);
            }
        }
        return actInfo;
    }

    @Override
    public long addSpirit(BetSpirit spirit) {
        return betSpiritService.addSpirit(spirit);
    }

    @Override
    public long updateSpirit(BetSpirit spirit) {
        return betSpiritService.updateSpirit(spirit);
    }

    @Override
    public AdminPage<BetSpirit> pageSpirit(int pageNum, int pageSize) {
        return betSpiritService.pageSpirit(pageNum, pageSize);
    }

    @Override
    public long addSpiritConf(BetSpiritConfig spiritConfig) {
        return betSpiritService.addSpiritConfig(spiritConfig);
    }

    @Override
    public long addAwardConf(BetAwardConfig awardConfig) {
        return betAwardConfigService.add(awardConfig);
    }

    @Override
    public long updateAwardConf(BetAwardConfig awardConfig) {
        return betAwardConfigService.update(awardConfig);
    }

    @Override
    public AdminPage<BetAwardConfig> queryAwardConfigs(long actId) {
        AdminPage<BetAwardConfig> page = new AdminPage<>();
        List<BetAwardConfig> awardConfigs = betAwardConfigService.queryAwardConfigsForAdmin(actId);
        page.setRows(awardConfigs);
        page.setTotal(awardConfigs.size());
        return page;
    }

    @Override
    public AdminPage<BetSpiritInfo> querySpiritConfigs(long actId) {
        AdminPage<BetSpiritInfo> adminPage = new AdminPage<>();
        List<BetSpiritInfo> spiritInfos = betSpiritService.querySpiritConfigsForAdmin(actId);
        adminPage.setRows(spiritInfos);
        adminPage.setTotal(spiritInfos.size());
        return adminPage;
    }

    @Override
    public long updateSpiritConfig(BetSpiritConfig spiritConfig) {
        return betSpiritService.updateSpiritConfig(spiritConfig);
    }

    @Override
    public long genGameRound(long actId) {
        long result = BetConstants.GAME_ROUND_ERR.NO_ACT;
        BetActInfo actInfo = actConfigService.getBetActInfo(actId);
        if (actInfo != null) {
            result = gameRoundService.genGameRound(actInfo);
            if (result > 0) {
                BetGameRound gameRound = gameRoundService.getCurBetGameRound(actId);
                long gameId = 0;
                if (gameRound != null) {
                    gameId = gameRound.getId();
                }
                BetGameInfo gameInfo = new BetGameInfo();
                gameInfo.setActId(actId);
                gameInfo.setGameId(gameId);
                long now = System.currentTimeMillis();

                //延迟队列--停止投注
                BetMessage msg = new BetMessage();
                msg.setMsgType(BetConstants.GAME_EVENT_TYPE.STOP_BET);
                msg.setGameInfo(gameInfo);
                //停止投注时间
                long stopBetExcuteTime = now + actInfo.getGameDuration() * 1000;
                DelayMessage<BetMessage> delayMessage = new DelayMessage<>();
                delayMessage.setData(msg);
                delayMessage.setDuplicateKey(UUID.randomUUID().toString());
                delayMessage.setExpireTime(stopBetExcuteTime);
                log.info(BetConstants.INFO_LOG + "DelayQueueJob stopBet enqueue:{}", delayMessage);
                betDelayQueue.enQueue(delayMessage);

                //延迟队列--结算游戏(结束投注后1秒开始结算）
                long settleExcuteTime = stopBetExcuteTime + 1000;
                msg.setMsgType(BetConstants.GAME_EVENT_TYPE.SETTLE);
                delayMessage = new DelayMessage<>();
                delayMessage.setData(msg);
                delayMessage.setDuplicateKey(UUID.randomUUID().toString());
                delayMessage.setExpireTime(settleExcuteTime);
                log.info(BetConstants.INFO_LOG + "DelayQueueJob settleGame enqueue:{}", delayMessage);
                betDelayQueue.enQueue(delayMessage);

                //延迟队列--停止游戏
//                long stopGameExcuteTime = stopBetExcuteTime + actInfo.getAnimationDuration()*1000+100;
//                msg.setMsgType(BetConstants.GAME_EVENT_TYPE.STOP_GAME);
//                delayMessage = new DelayMessage<>();
//                delayMessage.setData(msg);
//                delayMessage.setDuplicateKey(UUID.randomUUID().toString());
//                delayMessage.setExpireTime(stopGameExcuteTime);
//                log.info(BetConstants.INFO_LOG+"DelayQueueJob stopGame enqueue:{}", delayMessage);
//                betDelayQueue.enQueue(delayMessage);

            }
        }
        return result;
    }

    /**
     * 转对象
     *
     * @param spiritsStr
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    private List<BetGameSpiritInfo> toGameSpiritInfo(String spiritsStr, long actId, long gameId, long uid) {
        List<BetGameSpiritInfo> gameSpiritInfos = null;
        if (StringUtils.isBlank(spiritsStr)) {
            return gameSpiritInfos;
        }
        List<BetSpiritInfo> spirits = JsonUtils.fromJson(spiritsStr, new TypeReference<List<BetSpiritInfo>>() {
        });
        if (CollectionUtils.isEmpty(spirits)) {
            return gameSpiritInfos;
        }
        //每个精灵投注总额
        Map<String, String> spiritAmounts = null;
        try {
            spiritAmounts = getSpiritBetAmount(actId, gameId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //每个精灵支持数
        Map<String, String> spiritSupports = null;
        try {
            spiritSupports = getSpiritSupport(actId, gameId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //个人投注总额
        Map<String, String> userAmounts = null;
        try {
            userAmounts = getUserBetAmount(actId, gameId, uid);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        BetGameSpiritInfo gameSpiritInfo = null;
        gameSpiritInfos = new ArrayList<>(spirits.size());
        for (BetSpiritInfo spirit : spirits) {
            long spiritId = spirit.getSpiritId();
            gameSpiritInfo = new BetGameSpiritInfo();
            gameSpiritInfo.setSpiritId(spiritId);
            gameSpiritInfo.setSpiritName(spirit.getSpiritName());
            gameSpiritInfo.setUrl(spirit.getUrl());
            gameSpiritInfo.setSvgaUrl(spirit.getSvgaUrl());
            gameSpiritInfo.setOdds(spirit.getOdds());
            gameSpiritInfo.setWinNum(spirit.getWinNum());
            gameSpiritInfo.setLossNum(spirit.getLossNum());
            String amount = userAmounts.get(String.valueOf(spiritId));
            gameSpiritInfo.setUserBetAmount(amount == null ? 0 : Long.valueOf(amount));
            amount = spiritAmounts.get(String.valueOf(spiritId));
            gameSpiritInfo.setTotalBetAmount(amount == null ? 0 : Long.valueOf(amount));
            amount = spiritSupports.get(String.valueOf(spiritId));
            gameSpiritInfo.setSupportNum(amount == null ? 0 : Long.valueOf(amount));
            gameSpiritInfos.add(gameSpiritInfo);
        }
        return gameSpiritInfos;
    }

    /**
     * 转对象
     *
     * @param gameRound
     * @param uid
     * @return
     */
    private BetGameRoundInfo toGameInfo(BetGameRound gameRound, long uid) {
        BetGameRoundInfo gameInfo = null;
        if (gameRound != null) {
            long gameId = gameRound.getId();
            long actId = gameRound.getActId();
            BetActInfo actInfo = getActInfo(actId);
            gameInfo = new BetGameRoundInfo();
            gameInfo.setActId(actId);
            gameInfo.setGameId(gameRound.getId());
            gameInfo.setWinSpiritId(gameRound.getWinSpiritId());
            gameInfo.setSpirits(toGameSpiritInfo(gameRound.getSpirits(), actId, gameId, uid));
            gameInfo.setGameStatus(gameRound.getGameStatus());
            gameInfo.setStime(gameRound.getStime().getTime());
            gameInfo.setEtime(gameRound.getEtime().getTime());
            gameInfo.setAtime(gameRound.getAtime().getTime());
            gameInfo.setRtime(gameRound.getRtime().getTime());
            gameInfo.setPayPrices(actInfo.getPayPrices());
            gameInfo.setTransAnimationDuration(gameRound.getTransAnimationDuration());
            gameInfo.getTransAnimationUrl();
            if (gameRound.getGameStatus() == BetConstants.GAME_ROUND_STATUS.ANIMATIONING) {
                gameInfo.setResultInfos(betResultService.getBetResult(actId, gameId, uid));
                gameInfo.setWinSpiritInfo(betDetailService.getWinSpiritInfo(actId, gameId));
                gameInfo.setAnimations(gameRoundService.getAnimations(gameId));
                gameInfo.setResultSpirits(gameRoundService.getGameRoundRank(gameId, gameInfo.getSpirits().size()));
            }
            gameInfo.setSystime(new Date().getTime());
        }
        return gameInfo;
    }

    @Override
    public BetGameRoundInfo getBetGameRound(long gameId, long uid) {
        BetGameRound gameRound = gameRoundService.getBetGameRound(gameId, true);
        return toGameInfo(gameRound, uid);
    }

    @Override
    public BetGameRoundInfo getCurBetGameRound(long actId, long uid) {
        BetGameRound gameRound = gameRoundService.getCurBetGameRound(actId);
        return toGameInfo(gameRound, uid);
    }

    @Override
    public void check2CreateBetDetailTable() {
        betDetailService.check2CreateNextMonthTable();
    }

    @Override
    public void check2CreateBetResultTable() {
        betResultService.check2CreateNextMonthTable();
    }

    /**
     * 查游戏状态能否下注
     *
     * @return
     */
    private long canBet(BetDetail betDetail, BetActInfo actInfo) {
        if (betDetail == null) {
            return BusiStatus.PARAMERROR.value();
        }
        long gameId = betDetail.getGameId();
        if (actInfo == null) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        int betModel = actInfo.getBetModel();
        //单次需检查是否重复投注
        if (betModel == BetConstants.GAME_BET_MODEL.ONECE) {
            if (betCache.hasBet(betDetail.getGameId(), betDetail.getUid())) {
                return BetConstants.GAME_ROUND_ERR.BET_NUM_LIMIT;
            }
        }
        BetGameRound gameRound = gameRoundService.getBetGameRound(gameId, true);
        if (gameRound == null) {
            return BetConstants.GAME_ROUND_ERR.NO_GAME;
        }
        if (betDetail.getGameId() != gameRound.getId()) {
            return BetConstants.GAME_ROUND_ERR.SETTING;
        }
        long gameDuration = actInfo.getGameDuration() * 1000;
        long stime = gameRound.getStime().getTime();
        long now = new Date().getTime();
        if (now >= (stime + gameDuration)) {
            return BetConstants.GAME_ROUND_ERR.HAD_SETTLE;
        }
        return 1;
    }

    @Override
    public long bet(long actId, long gameId, long uid, long spiritId, int amount) {
        String lockKey = betCache.getBetLockKey(gameId, uid);
        String lockValue = UUID.randomUUID().toString();
        StringRedisTemplate stringRedisTemplate = betCache.getRedisUtil().getStringRedisTemplate();
        boolean islock = RedisLock.lock(stringRedisTemplate, lockKey, lockValue, 5);
        try {
            if (islock) {
                BetActInfo actInfo = actConfigService.getBetActInfo(actId);
                if (actInfo == null) {
                    return BetConstants.GAME_ROUND_ERR.NO_ACT;
                }
                BetDetail detail = new BetDetail();
                detail.setActId(actId);
                detail.setGameId(gameId);
                detail.setUid(uid);
                detail.setSpiritId(spiritId);
                detail.setPayPrice(amount);
                detail.setPaySeqId(UUID.randomUUID().toString());
                long canBet = canBet(detail, actInfo);
                if (canBet != 1) {
                    return canBet;
                }
                return betDetailService.bet(detail, actInfo.getPayType());
            } else {
                return BetConstants.GAME_ROUND_ERR.BET_SO_FAST;
            }
        } finally {
            if (islock) {
                RedisLock.unlock(stringRedisTemplate, lockKey, lockValue);
            }
        }
    }

    @Override
    public long stopBet(long gameId) {
        return gameRoundService.stopGameBet(gameId);
    }

    @Override
    public void settleCurGame(long actId) {
        BetGameRound gameRound = gameRoundService.getCurBetGameRound(actId);
        if (gameRound != null) {
            settleGameRound(actId, gameRound.getId());
        }
    }

    @Override
    public void settleGame(long actId, long gameId) {
        BetGameRound gameRound = gameRoundService.getBetGameRound(gameId, true);
        if (gameRound != null) {
            settleGameRound(actId, gameRound.getId());
        }
    }

    private void stopGameRound(long actId, long gameId) {
        BetActInfo actInfo = actConfigService.getBetActInfo(actId);
        long result = gameRoundService.stopGameRound(actInfo, gameId);
        log.info(BetConstants.INFO_LOG + "stopGameRound game gameId=" + gameId + ", result =" + result);
    }

    @Override
    public void stopGame(long actId, long gameId) {
        BetGameRound gameRound = gameRoundService.getBetGameRound(gameId, true);
        if (gameRound != null) {
            stopGameRound(actId, gameRound.getId());
        }
    }

    @Override
    public Map<String, String> getUserBetAmount(long actId, long gameId, long uid) {
        Map<String, String> userAmounts = betDetailService.getUserAmount(actId, gameId, uid);
        return userAmounts == null ? new HashMap<>(1) : userAmounts;
    }

    @Override
    public Map<String, String> getSpiritBetAmount(long actId, long gameId) {
        Map<String, String> spiritAmount = betDetailService.getSpiritAmount(actId, gameId);
        return spiritAmount == null ? new HashMap<>(1) : spiritAmount;
    }

    @Override
    public Map<String, String> getSpiritSupport(long actId, long gameId) {
        Map<String, String> spiritSupport = betDetailService.getSpiritSupport(actId, gameId);
        return spiritSupport == null ? new HashMap<>(1) : spiritSupport;
    }

    @Override
    public Page<BetResultInfo> pageBetResult(long actId, long uid, String month, long id, int pageSize) {
        Page<BetResultInfo> page = new Page();
        Page<BetResult> resultPage = betResultService.pageBetResult(actId, uid, month, id, pageSize);
        page.setHasMore(resultPage.isHasMore());
        page.setPageSize(resultPage.getPageSize());
        page.setId(resultPage.getId());
        page.setSuffix(resultPage.getSuffix());
        List<BetResult> results = resultPage.getRows();
        if (CollectionUtils.isNotEmpty(results)) {
            BetResultInfo info = null;
            List<BetResultInfo> infos = new ArrayList<>(results.size());
            for (BetResult result : results) {
                info = new BetResultInfo();
                info.setAwardAmount(result.getAwardAmount());
                info.setIsWin(result.getIsWin());
                info.setPayTotalAmount(result.getPayTotalAmount());
                info.setSpiritId(result.getSpiritId());
                info.setWinSpiritId(result.getWinSpiritId());
                info.setCtime(result.getCtime().getTime());
                info.setAnimations(result.getAnimations());
                String awards = result.getPackageContent();
                if (StringUtils.isNotBlank(awards)&&!"{}".equals(awards)) {
                    List<AwardOfPackage> pkgContents = JsonUtils.fromJson(awards, new TypeReference<List<AwardOfPackage>>() {});
                    info.setUserAwards(toAwardInfos(pkgContents));
                }
                infos.add(info);
            }
            page.setRows(infos);
        }
        return page;
    }

    private List<BetUserAwardInfo> toAwardInfos(List<AwardOfPackage> pkgs) {
        List<BetUserAwardInfo> awardInfos = null;
        if (CollectionUtils.isNotEmpty(pkgs)) {
            awardInfos = new ArrayList<>(pkgs.size());
            BetUserAwardInfo awardInfo = null;
            for (AwardOfPackage pkg : pkgs) {
                awardInfo = new BetUserAwardInfo();
                awardInfo.setName(pkg.getName());
                awardInfo.setAwardCount(pkg.getAwardCount());
                awardInfo.setPackageId(pkg.getPackageId());
                awardInfo.setAwardId(pkg.getAwardId());
                awardInfo.setIcon(pkg.getIcon());
                awardInfos.add(awardInfo);
            }
        }
        return awardInfos;
    }

    @Override
    public long betStatistic() {
        long result = 0;
        Date yesterday = TimeUtils.offsetDay(new Date(), -1);
        String date = TimeUtils.toStr(yesterday, TimeUtils.PATTERN_FORMAT_DATE);
        List<Long> actIds = betResultService.queryActIds(date);
        if (CollectionUtils.isNotEmpty(actIds)) {
            for (Long actId : actIds) {
                BetStatistic statistic = betResultService.statistic(actId, date);
                long winPayAmounts = betResultService.statisticWinPay(actId, date);
                statistic.setTotalWinAmounts(winPayAmounts);
                result += betStatisticService.add(statistic);
            }
        }
        return result;
    }

    @Override
    public AdminPage<BetStatistic> pageBetStatistic(long actId, String date, long pageNum, int pageSize) throws ParseException {
        Date queryDate = null;
        if (StringUtils.isNotBlank(date)) {
            queryDate = TimeUtils.fromStrDate(date);
        }

        AdminPage<BetStatistic> page = betStatisticService.page(actId, queryDate, pageNum, pageSize);
        return page;
    }

    @Override
    public BetGameRoundRecord getGameRoundInfo(long gameId) {
        return gameRoundService.get(gameId);
    }

    @Override
    public AdminPage<BetGameRoundRecord> pageGameRound(long actId, String date, int pageNum, int pageSize) {
        long offset = 0;
        if (pageSize <= 0) {
            pageSize = 10;
        }
        if (pageNum > 0) {
            offset = (pageNum - 1) * pageSize;
        }
        if (StringUtils.isBlank(date)) {
            date = TimeUtils.toStr(TimeUtils.offsetDay(new Date(), -10));
        }
        AdminPage<BetGameRound> page = gameRoundService.page(actId, date, offset, pageSize);
        AdminPage<BetGameRoundRecord> infoPage = new AdminPage<>();
        List<BetGameRound> gameRounds = page.getRows();
        if (CollectionUtils.isNotEmpty(gameRounds)) {
            List<BetGameRoundRecord> gameRoundInfos = new ArrayList<>(gameRounds.size());
            for (BetGameRound gameRound : gameRounds) {
                gameRoundInfos.add(gameRoundService.assembleGameInfo(gameRound));
            }
            infoPage.setRows(gameRoundInfos);
            infoPage.setTotal(page.getTotal());
        }
        return infoPage;
    }

    @Override
    public Page<BetGameRoundRecord> pageGameRound(long actId, long id, int pageSize) {
        return gameRoundService.pageGameRound(actId, id, pageSize);
    }

    @Override
    public long updateResultAnimations(long gameId, String animations) {
        return betResultService.batchUpdateAnimations(gameId,animations);
    }

    /**
     * 结算游戏并发奖
     */
    private void settleGameRound(long actId, long gameId) {
        BetActInfo actInfo = actConfigService.getBetActInfo(actId);
        long winer = gameRoundService.settleGameRound(actInfo, gameId);
        log.info(BetConstants.INFO_LOG + "settle game gameId=" + gameId + ", winer =" + winer);
        //结算成功，计算奖励并发放
        if (winer > 0) {
            //更新胜率
            updateSpiritWinRate(actId, gameId, winer);
            //生成动画
            try {
                animationProcessorProxy.genAnimation(actInfo, gameId, winer);
            } catch (Exception e) {
                log.error(BetConstants.ERROR_LOG + " settle game animation err... gameId:{} winner:{}", gameId, winer, e);
            }
            //发奖
            try {
                awardProcessorProxy.award(actInfo, gameId, winer);
            } catch (Exception e) {
                log.error(BetConstants.ERROR_LOG + " settle game award err... gameId:{} winner:{}", gameId, winer, e);
            }
        }
    }

    private void updateSpiritWinRate(long actId, long gameId, long winer) {
        if (gameId <= 0) {
            return;
        }
        BetGameRound gameRound = gameRoundService.getCurBetGameRound(actId);
        List<BetSpiritInfo> spirits = JsonUtils.fromJson(gameRound.getSpirits(), new TypeReference<List<BetSpiritInfo>>() {
        });
        if (CollectionUtils.isEmpty(spirits)) {
            return;
        }
        for (BetSpiritInfo spirit : spirits) {
            if (spirit.getSpiritId() == winer) {
                betSpiritService.updateSpiritConfigWinNum(winer, actId);
            } else {
                betSpiritService.updateSpiritConfigLossNum(spirit.getSpiritId(), actId);
            }
        }
    }

}
