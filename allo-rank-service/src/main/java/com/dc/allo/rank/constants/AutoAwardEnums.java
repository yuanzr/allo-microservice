package com.dc.allo.rank.constants;

/**
 * Created by zhangzhenjun on 2020/5/15.
 */
public interface AutoAwardEnums {

    /**
     * 发放类别
     */
    enum AwardType {
        /**
         * 连续性
         */
        CONTINUE(0),
        /**
         * 一次性
         */
        ONCE(1);

        private int val;

        AwardType(int val) {
            this.val = val;
        }

        public int val() {
            return this.val;
        }
    }
}
