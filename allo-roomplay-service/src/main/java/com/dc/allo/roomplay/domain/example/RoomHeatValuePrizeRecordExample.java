package com.dc.allo.roomplay.domain.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomHeatValuePrizeRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoomHeatValuePrizeRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRoomUidIsNull() {
            addCriterion("room_uid is null");
            return (Criteria) this;
        }

        public Criteria andRoomUidIsNotNull() {
            addCriterion("room_uid is not null");
            return (Criteria) this;
        }

        public Criteria andRoomUidEqualTo(Long value) {
            addCriterion("room_uid =", value, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidNotEqualTo(Long value) {
            addCriterion("room_uid <>", value, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidGreaterThan(Long value) {
            addCriterion("room_uid >", value, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidGreaterThanOrEqualTo(Long value) {
            addCriterion("room_uid >=", value, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidLessThan(Long value) {
            addCriterion("room_uid <", value, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidLessThanOrEqualTo(Long value) {
            addCriterion("room_uid <=", value, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidIn(List<Long> values) {
            addCriterion("room_uid in", values, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidNotIn(List<Long> values) {
            addCriterion("room_uid not in", values, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidBetween(Long value1, Long value2) {
            addCriterion("room_uid between", value1, value2, "roomUid");
            return (Criteria) this;
        }

        public Criteria andRoomUidNotBetween(Long value1, Long value2) {
            addCriterion("room_uid not between", value1, value2, "roomUid");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(String value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(String value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(String value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(String value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(String value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(String value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(String value) {
            addCriterion("uid like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(String value) {
            addCriterion("uid not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<String> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<String> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(String value1, String value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(String value1, String value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andGoldNumIsNull() {
            addCriterion("gold_num is null");
            return (Criteria) this;
        }

        public Criteria andGoldNumIsNotNull() {
            addCriterion("gold_num is not null");
            return (Criteria) this;
        }

        public Criteria andGoldNumEqualTo(BigDecimal value) {
            addCriterion("gold_num =", value, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumNotEqualTo(BigDecimal value) {
            addCriterion("gold_num <>", value, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumGreaterThan(BigDecimal value) {
            addCriterion("gold_num >", value, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gold_num >=", value, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumLessThan(BigDecimal value) {
            addCriterion("gold_num <", value, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gold_num <=", value, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumIn(List<BigDecimal> values) {
            addCriterion("gold_num in", values, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumNotIn(List<BigDecimal> values) {
            addCriterion("gold_num not in", values, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gold_num between", value1, value2, "goldNum");
            return (Criteria) this;
        }

        public Criteria andGoldNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gold_num not between", value1, value2, "goldNum");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarIsNull() {
            addCriterion("mvp_avatar is null");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarIsNotNull() {
            addCriterion("mvp_avatar is not null");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarEqualTo(String value) {
            addCriterion("mvp_avatar =", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarNotEqualTo(String value) {
            addCriterion("mvp_avatar <>", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarGreaterThan(String value) {
            addCriterion("mvp_avatar >", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("mvp_avatar >=", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarLessThan(String value) {
            addCriterion("mvp_avatar <", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarLessThanOrEqualTo(String value) {
            addCriterion("mvp_avatar <=", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarLike(String value) {
            addCriterion("mvp_avatar like", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarNotLike(String value) {
            addCriterion("mvp_avatar not like", value, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarIn(List<String> values) {
            addCriterion("mvp_avatar in", values, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarNotIn(List<String> values) {
            addCriterion("mvp_avatar not in", values, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarBetween(String value1, String value2) {
            addCriterion("mvp_avatar between", value1, value2, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpAvatarNotBetween(String value1, String value2) {
            addCriterion("mvp_avatar not between", value1, value2, "mvpAvatar");
            return (Criteria) this;
        }

        public Criteria andMvpNickIsNull() {
            addCriterion("mvp_nick is null");
            return (Criteria) this;
        }

        public Criteria andMvpNickIsNotNull() {
            addCriterion("mvp_nick is not null");
            return (Criteria) this;
        }

        public Criteria andMvpNickEqualTo(String value) {
            addCriterion("mvp_nick =", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickNotEqualTo(String value) {
            addCriterion("mvp_nick <>", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickGreaterThan(String value) {
            addCriterion("mvp_nick >", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickGreaterThanOrEqualTo(String value) {
            addCriterion("mvp_nick >=", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickLessThan(String value) {
            addCriterion("mvp_nick <", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickLessThanOrEqualTo(String value) {
            addCriterion("mvp_nick <=", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickLike(String value) {
            addCriterion("mvp_nick like", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickNotLike(String value) {
            addCriterion("mvp_nick not like", value, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickIn(List<String> values) {
            addCriterion("mvp_nick in", values, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickNotIn(List<String> values) {
            addCriterion("mvp_nick not in", values, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickBetween(String value1, String value2) {
            addCriterion("mvp_nick between", value1, value2, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andMvpNickNotBetween(String value1, String value2) {
            addCriterion("mvp_nick not between", value1, value2, "mvpNick");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumIsNull() {
            addCriterion("total_gold_num is null");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumIsNotNull() {
            addCriterion("total_gold_num is not null");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumEqualTo(BigDecimal value) {
            addCriterion("total_gold_num =", value, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumNotEqualTo(BigDecimal value) {
            addCriterion("total_gold_num <>", value, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumGreaterThan(BigDecimal value) {
            addCriterion("total_gold_num >", value, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_gold_num >=", value, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumLessThan(BigDecimal value) {
            addCriterion("total_gold_num <", value, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_gold_num <=", value, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumIn(List<BigDecimal> values) {
            addCriterion("total_gold_num in", values, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumNotIn(List<BigDecimal> values) {
            addCriterion("total_gold_num not in", values, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_gold_num between", value1, value2, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_gold_num not between", value1, value2, "totalGoldNum");
            return (Criteria) this;
        }

        public Criteria andRateIsNull() {
            addCriterion("rate is null");
            return (Criteria) this;
        }

        public Criteria andRateIsNotNull() {
            addCriterion("rate is not null");
            return (Criteria) this;
        }

        public Criteria andRateEqualTo(Byte value) {
            addCriterion("rate =", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotEqualTo(Byte value) {
            addCriterion("rate <>", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThan(Byte value) {
            addCriterion("rate >", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThanOrEqualTo(Byte value) {
            addCriterion("rate >=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThan(Byte value) {
            addCriterion("rate <", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThanOrEqualTo(Byte value) {
            addCriterion("rate <=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateIn(List<Byte> values) {
            addCriterion("rate in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotIn(List<Byte> values) {
            addCriterion("rate not in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateBetween(Byte value1, Byte value2) {
            addCriterion("rate between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotBetween(Byte value1, Byte value2) {
            addCriterion("rate not between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andUserRewardIsNull() {
            addCriterion("user_reward is null");
            return (Criteria) this;
        }

        public Criteria andUserRewardIsNotNull() {
            addCriterion("user_reward is not null");
            return (Criteria) this;
        }

        public Criteria andUserRewardEqualTo(String value) {
            addCriterion("user_reward =", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardNotEqualTo(String value) {
            addCriterion("user_reward <>", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardGreaterThan(String value) {
            addCriterion("user_reward >", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardGreaterThanOrEqualTo(String value) {
            addCriterion("user_reward >=", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardLessThan(String value) {
            addCriterion("user_reward <", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardLessThanOrEqualTo(String value) {
            addCriterion("user_reward <=", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardLike(String value) {
            addCriterion("user_reward like", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardNotLike(String value) {
            addCriterion("user_reward not like", value, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardIn(List<String> values) {
            addCriterion("user_reward in", values, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardNotIn(List<String> values) {
            addCriterion("user_reward not in", values, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardBetween(String value1, String value2) {
            addCriterion("user_reward between", value1, value2, "userReward");
            return (Criteria) this;
        }

        public Criteria andUserRewardNotBetween(String value1, String value2) {
            addCriterion("user_reward not between", value1, value2, "userReward");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}