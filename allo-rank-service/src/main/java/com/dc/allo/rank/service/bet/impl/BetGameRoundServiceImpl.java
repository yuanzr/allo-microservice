package com.dc.allo.rank.service.bet.impl;

import com.dc.allo.common.redis.RedisLock;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundRecord;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rank.mapper.bet.BetGameRoundMapper;
import com.dc.allo.rank.service.bet.BetActConfigService;
import com.dc.allo.rank.service.bet.BetDetailService;
import com.dc.allo.rank.service.bet.BetGameRoundService;
import com.dc.allo.rank.service.bet.BetResultService;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.dc.allo.rank.service.bet.lottery.LotteryProcessorProxy;
import com.dc.allo.rpc.domain.bet.BetAnimationData;
import com.dc.allo.rpc.domain.bet.BetAnimationInfo;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.domain.page.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Service
public class BetGameRoundServiceImpl implements BetGameRoundService {

    @Autowired
    BetActConfigService betActConfigService;

    @Autowired
    BetGameRoundMapper betGameRoundMapper;

    @Autowired
    BetCache betCache;

    @Autowired
    RedisTemplate<String, String> stringTemplate;

    @Autowired
    BetDetailService betDetailService;

    @Autowired
    BetResultService betResultService;

    @Autowired
    LotteryProcessorProxy lotteryProcessorProxy;

    private boolean valid(BetGameRound gameRound) {
        boolean flag = false;
        if (gameRound != null && gameRound.getActId() > 0
                && StringUtils.isNotBlank(gameRound.getSpirits())
                && gameRound.getStime() != null
                && gameRound.getEtime() != null) {
            flag = true;
        }
        return flag;
    }

    private long addGameRound(BetGameRound gameRound) {
        if (!valid(gameRound)) {
            return 0;
        }
        return betGameRoundMapper.add(gameRound);
    }

    @Override
    public long genGameRound(BetActInfo info) {
        if (!betActConfigService.checkActing(info)) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        BetGameRound gameRound = new BetGameRound();
        gameRound.setActId(info.getActId());
        List<BetSpiritInfo> spirits = info.getSpirits();
        if (CollectionUtils.isNotEmpty(spirits)) {
            if (info.getJoinSpiritNum() >= spirits.size()) {
                gameRound.setSpirits(JsonUtils.toJson(spirits));
            } else {
                Collections.shuffle(spirits);
                String json = JsonUtils.toJson(spirits.subList(0, info.getJoinSpiritNum()));
                if (StringUtils.isBlank(json)) {
                    return BetConstants.GAME_ROUND_ERR.NO_SPIRITS;
                }
                gameRound.setSpirits(json);
            }
        } else {
            return BetConstants.GAME_ROUND_ERR.NO_SPIRITS;
        }
        Date stime = new Date();
        long now = stime.getTime();
        long gameDuration = info.getGameDuration() * 1000;
        long transAnimationDuration = info.getTransAnimationDuration() * 1000;
        long animationDuration = info.getAnimationDuration() * 1000;
        long showDuration = info.getShowDuration() * 1000;
        Date atime = new Date(now + gameDuration);
        Date rtime = new Date(now + gameDuration + transAnimationDuration + animationDuration);
        Date etime = new Date(now + gameDuration + transAnimationDuration + animationDuration + showDuration);
        gameRound.setTransAnimationDuration(info.getTransAnimationDuration());
        gameRound.setStime(stime);
        gameRound.setEtime(etime);
        gameRound.setAtime(atime);
        gameRound.setRtime(rtime);
        long result = addGameRound(gameRound);
        if (result > 0) {
            betCache.setCurGameRound(gameRound);
            betCache.setBetGameRound(gameRound);
        }
        return result;
    }

    @Override
    public BetGameRound getBetGameRound(long gameId, boolean useCache) {
        BetGameRound gameRound = null;
        if (useCache) {
            gameRound = betCache.getBetGameRound(gameId);
            if (gameRound != null) {
                return gameRound;
            }
        }
        gameRound = betGameRoundMapper.get(gameId);
        if (gameRound != null) {
            betCache.setBetGameRound(gameRound);
        }
        return gameRound;
    }

    @Override
    public BetGameRound getCurBetGameRound(long actId) {
        return betCache.getBetCurGameRound(actId);
    }

    @Override
    public long stopCurGameBet(long actId) {
        BetGameRound gameRound = getCurBetGameRound(actId);
        return stopGameBet(gameRound.getId());
    }

    @Override
    public long stopGameBet(long gameId) {
        BetGameRound gameRound = getBetGameRound(gameId, true);
        if (gameRound == null) {
            return BetConstants.GAME_ROUND_ERR.NO_GAME;
        }
        if (gameRound.getGameStatus() != BetConstants.GAME_ROUND_STATUS.GAMING) {
            return BetConstants.GAME_ROUND_ERR.NOT_GAMEING;
        }
        long result = betGameRoundMapper.stopBet(gameRound.getId());
        if (result > 0) {
            gameRound.setGameStatus(BetConstants.GAME_ROUND_STATUS.ANIMATIONING);
            betCache.setCurGameRound(gameRound);
            betCache.setBetGameRound(gameRound);
        }
        return result;
    }


    @Override
    public long settleGameRound(BetActInfo actInfo, long gameId) {
        if (actInfo == null) {
            return BetConstants.GAME_ROUND_ERR.NO_ACT;
        }
        long result = 0;

        String lockKey = BetConstants.getBetSettleGameLock(gameId);
        String lockValue = UUID.randomUUID().toString();
        boolean lock = RedisLock.lock(stringTemplate, lockKey, lockValue, 10);
        try {
            if (!lock) {
                return BetConstants.GAME_ROUND_ERR.SETTING;
            }
            //停止游戏，确定胜方
            long winer = lotteryProcessorProxy.lottery(actInfo, gameId);
            if (winer <= 0) {
                return winer;
            }
            //更新数据库状态为已结算
            result = betGameRoundMapper.settleGameRound(gameId, winer);
            if (result > 0) {
                return winer;
            }
        } finally {
            if (lock) {
                RedisLock.unlock(stringTemplate, BetConstants.REDIS_KEY.BET_SETTLE_GAME_LOCK, lockValue);
            }
        }
        return result;
    }

    private boolean canStopGameRound(BetActInfo actInfo, BetGameRound gameRound) {
        boolean flag = false;
        if (actInfo == null || gameRound == null) {
            return flag;
        }
        long stopTime = gameRound.getStime().getTime() + actInfo.getGameDuration() * 1000 + actInfo.getAnimationDuration();
        long nowTime = new Date().getTime();
        long overTime = gameRound.getEtime().getTime();
        if (nowTime >= stopTime) {
            flag = true;
        }
        return flag;
    }

    @Override
    public long stopGameRound(BetActInfo actInfo, long gameId) {
        BetGameRound gameRound = betGameRoundMapper.get(gameId);
        if (gameRound == null) {
            return BetConstants.GAME_ROUND_ERR.NO_GAME;
        }
        if (gameRound.getGameStatus() != BetConstants.GAME_ROUND_STATUS.ANIMATIONING) {
            return BetConstants.GAME_ROUND_ERR.NOT_GAMEING;
        }
        if (!canStopGameRound(actInfo, gameRound)) {
            return BetConstants.GAME_ROUND_ERR.NOT_GAMEING;
        }
        gameRound.setGameStatus(BetConstants.GAME_ROUND_STATUS.SHOWING);
        betCache.setCurGameRound(gameRound);
        betCache.setBetGameRound(gameRound);
        return betGameRoundMapper.stopGame(gameId);
    }

    @Override
    public long updateBetAmount(long actId, long gameId) {
        List<BetSpiritAmount> amounts = betResultService.statisticSpiritByGameId(actId, gameId);
        if (CollectionUtils.isNotEmpty(amounts)) {
            return betGameRoundMapper.updateBetAmount(gameId, JsonUtils.toJson(amounts));
        }
        return 0;
    }

    @Override
    public long updateAnimations(long gameId, String animations) {
        betCache.setGameRoundAnimation(gameId, animations);
        return betGameRoundMapper.updateBetAnimations(gameId, animations);
    }

    @Override
    public Map<Long, BetAnimationData> getAnimations(long gameId) {
        BetAnimationInfo animationInfo = betCache.getGameRoundAnimation(gameId);
        if (animationInfo != null) {
            return animationInfo.getAnimations();
        }
        String animations = betGameRoundMapper.getAnimations(gameId);
        if (StringUtils.isNotBlank(animations)) {
            animationInfo = JsonUtils.fromJson(animations, BetAnimationInfo.class);
            if (animationInfo != null) {
                betCache.setGameRoundAnimation(gameId, animations);
                return animationInfo.getAnimations();
            }
        }
        return null;
    }

    @Override
    public BetGameRoundRecord get(long gameId) {
        BetGameRound game = getBetGameRound(gameId, true);
        return assembleGameInfo(game);
    }

    public BetGameRoundRecord assembleGameInfo(BetGameRound game) {
        BetGameRoundRecord info = new BetGameRoundRecord();
        if (game != null) {
            info.setActId(game.getActId());
            info.setId(game.getId());
            info.setAtime(game.getAtime().getTime());
            info.setEtime(game.getEtime().getTime());
            info.setRtime(game.getRtime().getTime());
            info.setStime(game.getStime().getTime());
            info.setGameStatus(game.getGameStatus());
            info.setWinSpiritId(game.getWinSpiritId());

            String spirits = game.getSpirits();
            if (StringUtils.isBlank(spirits)) {
                return info;
            }
            List<BetSpiritInfo> spiritInfos = JsonUtils.fromJson(spirits, new TypeReference<List<BetSpiritInfo>>() {
            });
            if (CollectionUtils.isEmpty(spiritInfos)) {
                return info;
            }

            String amounts = game.getBetAmounts();
            if (StringUtils.isNotBlank(amounts)) {
                List<BetSpiritAmount> spiritAmounts = JsonUtils.fromJson(amounts, new TypeReference<List<BetSpiritAmount>>() {
                });
                if (CollectionUtils.isNotEmpty(spiritAmounts)) {
                    long totalAmount = 0;
                    Map<Long, BetSpiritAmount> map = new HashMap<>(spiritAmounts.size());
                    for (BetSpiritAmount spiritAmount : spiritAmounts) {
                        map.put(spiritAmount.getSpiritId(), spiritAmount);
                        totalAmount += spiritAmount.getTotalAmount();
                    }
                    BetSpiritAmount amount = null;
                    for (BetSpiritInfo spiritInfo : spiritInfos) {
                        amount = map.get(spiritInfo.getSpiritId());
                        if (amount != null) {
                            spiritInfo.setTotalAmount(amount.getTotalAmount());
                            spiritInfo.setTotalAwardAmount(amount.getTotalAwardAmount());
                            spiritInfo.setWinAmount(amount.getTotalAwardAmount() > 0 ? -amount.getTotalAwardAmount() : amount.getTotalAmount());
                        }
                    }
                }
            }
            info.setSpirits(spiritInfos);

            String animations = game.getAnimations();
            if (StringUtils.isNotBlank(animations)) {
                BetAnimationInfo animationInfo = JsonUtils.fromJson(animations, BetAnimationInfo.class);
                if (animationInfo != null) {
                    info.setAnimations(animationInfo.getAnimations());
//                    switch (animationInfo.getAnimationModelType()) {
//                        case BetConstants.AnimationModelType.RACINGCAR.val():
//                            info.setAnimations(animationInfo.getAnimations());
//                            break;
//                        default:break;
//                    }
                }
            }
        }
        return info;
    }

    @Override
    public AdminPage<BetGameRound> page(long actId, String date, long offset, int pageSize) {
        AdminPage<BetGameRound> page = new AdminPage<>();
        page.setTotal(betGameRoundMapper.count(actId, date));
        page.setRows(betGameRoundMapper.page(actId, date, offset, pageSize));
        return page;
    }

    @Override
    public Page<BetGameRoundRecord> pageGameRound(long actId, long id, int pageSize) {
        Page<BetGameRoundRecord> page = new Page<>();
        List<BetGameRound> gameRounds = betGameRoundMapper.pageGameRound(actId, id, pageSize + 1);
        if (CollectionUtils.isNotEmpty(gameRounds)) {
            int size = gameRounds.size();
            if (size > pageSize) {
                gameRounds = gameRounds.subList(0, size - 2);
                page.setHasMore(true);
            } else {
                page.setHasMore(false);
            }
            List<BetGameRoundRecord> gameRoundInfos = new ArrayList<>(gameRounds.size());
            for (BetGameRound gameRound : gameRounds) {
                gameRoundInfos.add(assembleGameInfo(gameRound));
            }
            page.setRows(gameRoundInfos);
            long lastId = gameRounds.get(gameRounds.size() - 1).getId();
            page.setId(lastId);
        } else {
            page.setHasMore(false);
        }
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public void setGameRoundRank(long gameId, long spiritId, int score) {
        betCache.setGameRoundRank(gameId,spiritId,score);
    }

    @Override
    public List<Long> getGameRoundRank(long gameId, int size) {
        return betCache.getGameRoundRank(gameId,size);
    }
}
