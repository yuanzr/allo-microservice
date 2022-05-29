package com.dc.allo.task.constant;

/**
 * description: TaskConstant
 *
 * @date: 2020年05月26日 11:52
 * @author: qinrenchuan
 */
public class TaskConstant {

    /** 旧项目查询首充礼包URL */
    public static final String queryPrizePackageByPackageIds = "/common/activity/queryPrizePrizePackageByPackageIds";

    /**
     * 任务状态
     */
    public static class TaskStatus {
        /** 1有效 */
        public static final Byte ENABLE = 1;
        /** 0无效 */
        public static final Byte DISABLE = 0;
    }

    /**
     * 签到状态
     */
    public static class SignStatus {
        /** 已签到 */
        public static final int SIGNED = 1;
        /** 未签到 */
        public static final int NOT_SIGNED = 2;
    }

    /** 数字常量： 1 */
    public static int DIGITAL_ONE = 1;
    /** 数字常量： 7 */
    public static int DIGITAL_SEVEN = 7;

    private TaskConstant() {
    }
}
