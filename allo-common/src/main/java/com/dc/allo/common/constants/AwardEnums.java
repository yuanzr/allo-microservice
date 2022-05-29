package com.dc.allo.common.constants;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
public interface AwardEnums {

    /**
     * 购买货币类型
     */
    enum PurchaseType{
        COINS(1),
        CAMEL_ITEM(2);
        private int val;

        PurchaseType(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }

    /**
     * 发放状态
     */
    enum ReleaseStatus {
        /**
         * 初始化
         */
        INIT(0),
        /**
         * 成功
         */
        SUCCESS(1),
        /**
         * 失败
         */
        FAIL(-1),
        /**
         * 重试成功
         */
        RETRY_SUCCESS(2),
        /**
         * 重试失败
         */
        RETRY_FAIL(-2),
        /**
         * 支付成功
         */
        PAY_SUCCESS(3),
        /**
         * 支付失败
         */
        PAY_FAIL(-3),
        /**
         * 库存不足
         */
        STOCK_NOT_ENOUGH(-99);

        private int val;

        ReleaseStatus(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }

    /**
     * 发放类别
     */
    enum ReleaseType {
        /**
         * 礼物
         */
        GIFT(1),
        /**
         * 头像框
         */
        AVATAR(2),
        /**
         * 座驾
         */
        CAR(3),
        /**
         * 勋章
         */
        BADGE(4),
        /**
         * 金币
         */
        COINS(5),
        /**
         * 贵族
         */
        NOBLE(6),
        /**
         * 背景
         */
        BACKGROUND(7),
        /**
         * 虚拟道具
         */
        VIRTUALITEM(8),
        /**
         * 实物
         */
        REAL_SUBJECT(999),
        /**
         * 空奖励
         */
        EMPTY(-1);

        private int val;

        ReleaseType(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }
}
