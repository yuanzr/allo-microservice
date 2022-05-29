package com.dc.allo.rank.constants;


import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rank.domain.bet.BetGameRound;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

public interface BetConstants {

    int WAWARD_TARGET_WIN = 1;
    int WAWARD_TARGET_LOSS = 2;
    int WAWARD_TARGET_GRANDPRIZE = 3;

    int SEND_AWARD_SUCCES = 1;
    int SEND_AWARD_FAIL = 2;

    int EXPIRE_TIME_MINUTES = 120;
    int EXPIRE_TIME_HOUR = 3600;
    int EXPIRE_TIME_DAY = 86400;

    String BET_REDIS_DELAY_QUEUE = "bet-redis-delay-event-queue";

    interface GAME_BET_MODEL {
        int ONECE = 0;
        int REPEAT = 1;
    }

    interface GAME_ROUND_STATUS {
        int GAMING = 0;
        int ANIMATIONING = 1;
        int SHOWING = 2;
    }

    interface GAME_EVENT_TYPE {
        int STOP_BET = 1;
        int SETTLE = 2;
        int STOP_GAME = 3;
    }

    interface GAME_RANK_CONFIG {
        String RANK_APP_KEY = "allo";
        String RANK_BET_DAY_KEY = "allo-bet-camel-day";
        String RANK_BET_TOTAL_KEY = "allo-bet-camel-total";
        String DATA_SOURCE_KEY = "bet-ds";
        String DATA_SOURCE_SECRET = "bet-secret";
    }

    interface GAME_ROUND_ERR {
        int NO_ACT = -10;           //活动不在进行中
        int NO_GAME = -20;          //无此游戏
        int NOT_GAMEING = -30;      //游戏不在进行中
        int NOT_SETTLE_TIME = -31;  //不在结算时间
        int NO_WINNER = -40;         //无获胜阵营
        int NO_SPIRITS = -41;       //没有配置游戏精灵
        int SETTING = -50;          //结算中
        int HAD_SETTLE = -60;       //已结算
        int BET_NUM_LIMIT = -70;    //投注次数限制
        int BET_SO_FAST = -80;      //投注太快
        int PAY_USER_ERR = -90;    //投注用户错误
        int PAY_PRICE_ERR = -100;   //投注金额错误
        int PAY_TYPE_ERR = -110;    //投注支付方式错误
        int PAY_ERR = -120;         //支付失败
        int PAY_NOT_ENOUGH = -121;  //支付余额不够
        int ANIMATION_DURATION_ERR = -130;  //动画时间错误
    }

    enum AnimationModelType {
        /**
         * 赛车类
         */
        RACINGCAR(0);


        private int val;

        AnimationModelType(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }

    enum BankerModelType {
        /**
         * 官方
         */
        OFFCIAL(0),
        /**
         * 无庄
         */
        NOBANKER(1);

        private int val;

        BankerModelType(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }

    enum PayType {
        /**
         * 金币
         */
        COINS(1),
        /**
         * 道具
         */
        VIRTUALITEM(2);

        private int val;

        PayType(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }

    interface REDIS_KEY {
        String GAME_ROUND = "bet-game-round";
        String BET_RESULT = "bet-result";
        String BET_UID_STATISTIC_CUR = "bet-uid-statistic-cur";
        String BET_UID_STATISTIC_FINAL = "bet-uid-statistic-final";
        String BET_SPIRIT_STATISTIC_CUR = "bet-spirit-statistic-cur";
        String BET_SPIRIT_SUPPORT_STATISTIC_CUR = "bet-spirit-support-statistic-cur";
        String BET_USER_AWARDS = "bet-user-awards";
        String BET_USER_HOUR_RANK = "bet-user-hour-rank";
        String BET_USER_WIN_COUNT = "bet-user-win-count";
        String BET_USER_WIN_AMOUNT = "bet-user-win-amount";
        String BET_GAME_LOCK = "bet-game-lock";
        String BET_SETTLE_GAME_LOCK = "bet-settle-game-lock";
        String BET_WIN_SPIRIT_INFO = "bet-win-spirit-info";
        String BET_ANIMATIONS = "bet-animation";
        String BET_GAMEROUND_SPIRITS_RANK = "bet-gameround-spirits-rank";
    }

    String ERROR_LOG = "【投注ERROR】--";

    String INFO_LOG = "【投注INFO】--";

    /**
     * 结算锁
     *
     * @param gameId
     * @return
     */
    static String getBetSettleGameLock(long gameId) {
        return RedisKeyUtil.appendCacheKeyByColon(REDIS_KEY.BET_SETTLE_GAME_LOCK, gameId);
    }

    static Map<String, String> objMap2StrMap(Map<Object, Object> objMap) {
        Map<String, String> strMap = null;
        if (MapUtils.isNotEmpty(objMap)) {
            strMap = new HashMap<>(objMap.size());
            Iterator it = objMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
                strMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        return strMap;
    }

    static BetSpiritInfo doLottery(List<BetSpiritInfo> spirits) {
        if (CollectionUtils.isEmpty(spirits)) {
            return null;
        }
        int totalRate = 0;
        for (BetSpiritInfo siprit : spirits) {
            int rate = (int) (siprit.getRate() * 1000);
            totalRate += rate;
        }
        Integer rand = new Random().nextInt(totalRate) + 1;
        for (BetSpiritInfo siprit : spirits) {
            rand -= (int) (siprit.getRate() * 1000);
            if (rand <= 0) {
                return siprit;
            }
        }
        Collections.shuffle(spirits);
        return spirits.get(0);
    }

    static long validGameStatus(BetActInfo actInfo, BetGameRound gameRound) {
        if (gameRound == null) {
            return BetConstants.GAME_ROUND_ERR.NO_GAME;
        }
//        if (gameRound.getGameStatus() != BetConstants.GAME_ROUND_STATUS.ANIMATIONING) {
//            return BetConstants.GAME_ROUND_ERR.NOT_GAMEING;
//        }
        long settleTime = gameRound.getStime().getTime() + actInfo.getGameDuration() * 1000;
        long nowTime = new Date().getTime();
        if (nowTime < settleTime) {
            return BetConstants.GAME_ROUND_ERR.NOT_SETTLE_TIME;
        }
        if (gameRound.getIsSettle() != 0) {
            return BetConstants.GAME_ROUND_ERR.HAD_SETTLE;
        }
        return 0;
    }

}
