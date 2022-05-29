package com.dc.allo.rank.constants;

/**
 * Created by zhangzhenjun on 2020/3/21.
 */
public interface Constant {
    String CACHE_PREFIX = "act_center_cache:";
    int ONE_HOUR = 60 * 60;
    int ONE_DAY = 60 * 60 * 24;

    interface RankDataStream {
        String DATA_STREAM_HANDLE_POOL = "RankDataStream";
    }

    interface DataSource {
        String INFO = "---榜单数据源INFO---";
        String WARN = "---榜单数据源WARN---";
        String ERROR = "---榜单数据源ERROR---";
        String DATA_SOURCE_CONFIG_CACHE_KEY = CACHE_PREFIX + "rank_data_source_config";
        /**
         * 数据源config缓存失效时间 7天
         */
        int DATA_SOURCE_CONFIG_CACHE_EXPIRE_TIME = 7 * ONE_DAY;
    }

    interface ProcessChain {
        String INFO = "---榜单数据处理链INFO---";
        String WARN = "---榜单数据处理链WARN---";
        String ERROR = "---榜单数据处理链ERROR---";
    }

    interface RankDataRecordConstant {
        String INFO = "---榜单数据落库INFO---";
        String WARN = "---榜单数据落库WARN---";
        String ERROR = "---榜单数据落库ERROR---";

        String TABLE_NAME_FORMAT = "act_center_rank_data_record_%s_%s";
    }

    interface Rank {
        String INFO = "---榜单INFO---";
        String WARN = "---榜单WARN---";
        String ERROR = "---榜单ERROR---";

        String RANK_CONFIG_WITH_DATASOURCE_CACHE_KEY = CACHE_PREFIX + "rank_config_with_datasource";

        String RANK_CONFIG_CACHE_KEY = CACHE_PREFIX + "rank_config";
        /**
         * 数据源config缓存失效时间 7天
         */
        int RANK_CONFIG_CACHE_EXPIRE_TIME = 7 * ONE_DAY;

        /**
         * 榜单类型
         */
        enum RankType {
            /**
             * 礼物流水榜
             */
            GIFT(1),
            /**
             * 普通榜
             */
            NORMAL(2);
            private int val;

            RankType(int val) {
                this.val = val;
            }

            public int val() {
                return this.val;
            }

            public static RankType of(Integer val) {
                if (val == null) {
                    return null;
                }
                for (RankType type : RankType.values()) {
                    if (type.val() == val) {
                        return type;
                    }
                }
                return null;
            }
        }

        /**
         * 榜单id类型
         */
        enum RankIdType {
            /**
             * 用户
             */
            USER(1),
            /**
             * 频道
             */
            ROOM(2);
            private int val;

            RankIdType(int val) {
                this.val = val;
            }

            public int val() {
                return this.val;
            }

            public static RankIdType of(Integer val) {
                if (val == null) {
                    return null;
                }
                for (RankIdType type : RankIdType.values()) {
                    if (type.val() == val) {
                        return type;
                    }
                }
                return null;
            }
        }

        /**
         * 榜单时间类型
         */
        enum RankTimeType {
            /**
             * 小时榜
             */
            HOUR(1),
            /**
             * 日榜
             */
            DAY(2),
            /**
             * 周榜
             */
            WEEK(3),
            /**
             * 月榜
             */
            MONTH(4),
            /**
             * 年榜
             */
            YEAR(5),
            /**
             * 不限时榜
             */
            UNLIMITED(99);
            private int val;

            RankTimeType(int val) {
                this.val = val;
            }

            public int val() {
                return this.val;
            }

            public static RankTimeType of(Integer val) {
                if (val == null) {
                    return null;
                }
                for (RankTimeType type : RankTimeType.values()) {
                    if (type.val() == val) {
                        return type;
                    }
                }
                return null;
            }
        }

        /**
         * 榜单累计类型
         */
        enum RankCalcType {
            /**
             * 按分值累计
             */
            SCORE(1),
            /**
             * 按次累计
             */
            COUNT(2);
            private int val;

            RankCalcType(int val) {
                this.val = val;
            }

            public int val() {
                return this.val;
            }

            public static RankCalcType of(Integer val) {
                if (val == null) {
                    return null;
                }
                for (RankCalcType type : RankCalcType.values()) {
                    if (type.val() == val) {
                        return type;
                    }
                }
                return null;
            }
        }

        /**
         * 榜单成员类型
         */
        enum RankMemberType {
            /**
             * 用户
             */
            USER(1),
            /**
             * 所有频道
             */
            ROOM(2);
            private int val;

            RankMemberType(int val) {
                this.val = val;
            }

            public int val() {
                return this.val;
            }

            public static RankMemberType of(Integer val) {
                if (val == null) {
                    return null;
                }
                for (RankMemberType type : RankMemberType.values()) {
                    if (type.val() == val) {
                        return type;
                    }
                }
                return null;
            }
        }

        /**
         * 榜单方向
         */
        enum RankDirection {
            /**
             * 无方向
             */
            NONE(0),
            /**
             * 接收方向
             */
            GET(1),
            /**
             * 发送方向
             */
            SEND(2);
            private int val;

            RankDirection(int val) {
                this.val = val;
            }

            public int val() {
                return this.val;
            }

            public static RankDirection of(Integer val) {
                if (val == null) {
                    return null;
                }
                for (RankDirection type : RankDirection.values()) {
                    if (type.val() == val) {
                        return type;
                    }
                }
                return null;
            }
        }

        /**
         * 榜单过期时间单位
         */
        enum RankExpireTimeUnit {
            /**
             * 秒
             */
            SECOND(1),
            /**
             * 分
             */
            MINUTE(2),
            /**
             * 时
             */
            HOUR(3),
            /**
             * 日
             */
            DAY(4),
            /**
             * 周
             */
            WEEK(5),
            /**
             * 月
             */
            MONTH(6),
            /**
             * 年
             */
            YEAR(7);
            private int val;

            RankExpireTimeUnit(int val) {
                this.val = val;
            }

            public int val() {
                return this.val;
            }

            public static RankExpireTimeUnit of(Integer val) {
                if (val == null) {
                    return null;
                }
                for (RankExpireTimeUnit type : RankExpireTimeUnit.values()) {
                    if (type.val() == val) {
                        return type;
                    }
                }
                return null;
            }
        }
    }

    interface RankQuery {
        String INFO = "---榜单查询INFO---";
        String WARN = "---榜单查询WARN---";
        String ERROR = "---榜单查询ERROR---";

        int MAX_ROWS = 500;
    }
}
