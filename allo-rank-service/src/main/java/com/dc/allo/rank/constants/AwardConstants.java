package com.dc.allo.rank.constants;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
public interface AwardConstants {
    String INFO_LOG = "---礼包组件INFO---";
    String WARN_LOG = "---礼包组件WARN---";
    String ERR_LOG = "---礼包组件ERROR---";

    String PACKAGE_LOCK_KEY_PREFIX = "common-award-package-lock:";
    String AWARD_LOCK_KEY_PREFIX = "common-award-lock:";

    int TURNOVER_API_RETRY_COUNT = 3;
    /**
     * 礼物发放个数最大限制1w个
     */
    int  GIFT_RELEASE_COUNT_LIMIT = 10000;
    /**
     * 金币发放个数最大限制100w
     */
    int  COINS_RELEASE_COUNT_LIMIT = 1000000;

}

