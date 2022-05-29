package com.dc.allo.activity.constant;

/**
 * description: Constant
 *
 * @date: 2020年05月11日 14:52
 * @author: qinrenchuan
 */
public class Constant {

    /** 旧项目查询首充礼包URL */
    public static final String queryFirstRechargePrizes = "/common/activity/prizePackage";

    /**
     * 榜单活动类型
     */
    public static class RankActType {
        public static final byte CHARGE          = 1;//充值
        public static final byte CP              = 2;//CP
        public static final byte BADGE_RECEIVER  = 3;//周榜勋章收礼
        public static final byte BADGE_SENDER    = 4;//周榜勋章送礼
        public static final byte USER_INVITE     = 5;//User Invite
        public static final byte RAMADAN         = 6;//斋月活动
        public static final byte FIRST_RECHARGE  = 7;//首充
        public static final byte SIGN_IN         = 8;//签到
        public static final byte NEW_USER_GUIDE_SIGN  = 9;//新用户引导首次充值
        public static final byte CHARGE_SUMMER   = 10;//夏日充值

    }

    /**
     * 活动榜单日期类型
     */
     public static class ActRankDateType {
        public static final Byte day = 1;
        public static final Byte week = 2;
        public static final Byte month = 3;
        public static final Byte total = 4;
        public static final Byte halfHour = 5;
    }

    /**
     * 活动榜单排序
     */
    public static class ActRankingSeq {
        public static final Byte TOP1 = 1;
        public static final Byte TOP2 = 2;
        public static final Byte TOP3 = 3;
        public static final Byte TOP4 = 4;
        public static final Byte TOP5 = 5;
    }

    /**
     * 夏日充值活动任务状态
     */
    public static class ChargeSummerStatus {
        public static final String UNFINISH = "0";//未完成
        public static final String PENGDING = "1";//已完成,待领取
        public static final String FINISHED = "2";//已经领取
    }



    private Constant() {
    }
}
