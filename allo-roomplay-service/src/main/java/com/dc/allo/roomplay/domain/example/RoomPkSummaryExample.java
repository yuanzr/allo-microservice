package com.dc.allo.roomplay.domain.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomPkSummaryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoomPkSummaryExample() {
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

        public Criteria andRoomIdIsNull() {
            addCriterion("room_id is null");
            return (Criteria) this;
        }

        public Criteria andRoomIdIsNotNull() {
            addCriterion("room_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoomIdEqualTo(Long value) {
            addCriterion("room_id =", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotEqualTo(Long value) {
            addCriterion("room_id <>", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThan(Long value) {
            addCriterion("room_id >", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThanOrEqualTo(Long value) {
            addCriterion("room_id >=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThan(Long value) {
            addCriterion("room_id <", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThanOrEqualTo(Long value) {
            addCriterion("room_id <=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdIn(List<Long> values) {
            addCriterion("room_id in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotIn(List<Long> values) {
            addCriterion("room_id not in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdBetween(Long value1, Long value2) {
            addCriterion("room_id between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotBetween(Long value1, Long value2) {
            addCriterion("room_id not between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andWinTimesIsNull() {
            addCriterion("win_times is null");
            return (Criteria) this;
        }

        public Criteria andWinTimesIsNotNull() {
            addCriterion("win_times is not null");
            return (Criteria) this;
        }

        public Criteria andWinTimesEqualTo(Integer value) {
            addCriterion("win_times =", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesNotEqualTo(Integer value) {
            addCriterion("win_times <>", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesGreaterThan(Integer value) {
            addCriterion("win_times >", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("win_times >=", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesLessThan(Integer value) {
            addCriterion("win_times <", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesLessThanOrEqualTo(Integer value) {
            addCriterion("win_times <=", value, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesIn(List<Integer> values) {
            addCriterion("win_times in", values, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesNotIn(List<Integer> values) {
            addCriterion("win_times not in", values, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesBetween(Integer value1, Integer value2) {
            addCriterion("win_times between", value1, value2, "winTimes");
            return (Criteria) this;
        }

        public Criteria andWinTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("win_times not between", value1, value2, "winTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesIsNull() {
            addCriterion("lose_times is null");
            return (Criteria) this;
        }

        public Criteria andLoseTimesIsNotNull() {
            addCriterion("lose_times is not null");
            return (Criteria) this;
        }

        public Criteria andLoseTimesEqualTo(Integer value) {
            addCriterion("lose_times =", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesNotEqualTo(Integer value) {
            addCriterion("lose_times <>", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesGreaterThan(Integer value) {
            addCriterion("lose_times >", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("lose_times >=", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesLessThan(Integer value) {
            addCriterion("lose_times <", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesLessThanOrEqualTo(Integer value) {
            addCriterion("lose_times <=", value, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesIn(List<Integer> values) {
            addCriterion("lose_times in", values, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesNotIn(List<Integer> values) {
            addCriterion("lose_times not in", values, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesBetween(Integer value1, Integer value2) {
            addCriterion("lose_times between", value1, value2, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andLoseTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("lose_times not between", value1, value2, "loseTimes");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreIsNull() {
            addCriterion("history_max_score is null");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreIsNotNull() {
            addCriterion("history_max_score is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreEqualTo(BigDecimal value) {
            addCriterion("history_max_score =", value, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreNotEqualTo(BigDecimal value) {
            addCriterion("history_max_score <>", value, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreGreaterThan(BigDecimal value) {
            addCriterion("history_max_score >", value, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("history_max_score >=", value, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreLessThan(BigDecimal value) {
            addCriterion("history_max_score <", value, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("history_max_score <=", value, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreIn(List<BigDecimal> values) {
            addCriterion("history_max_score in", values, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreNotIn(List<BigDecimal> values) {
            addCriterion("history_max_score not in", values, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("history_max_score between", value1, value2, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andHistoryMaxScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("history_max_score not between", value1, value2, "historyMaxScore");
            return (Criteria) this;
        }

        public Criteria andTotalGoldIsNull() {
            addCriterion("total_gold is null");
            return (Criteria) this;
        }

        public Criteria andTotalGoldIsNotNull() {
            addCriterion("total_gold is not null");
            return (Criteria) this;
        }

        public Criteria andTotalGoldEqualTo(BigDecimal value) {
            addCriterion("total_gold =", value, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNotEqualTo(BigDecimal value) {
            addCriterion("total_gold <>", value, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldGreaterThan(BigDecimal value) {
            addCriterion("total_gold >", value, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_gold >=", value, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldLessThan(BigDecimal value) {
            addCriterion("total_gold <", value, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_gold <=", value, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldIn(List<BigDecimal> values) {
            addCriterion("total_gold in", values, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNotIn(List<BigDecimal> values) {
            addCriterion("total_gold not in", values, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_gold between", value1, value2, "totalGold");
            return (Criteria) this;
        }

        public Criteria andTotalGoldNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_gold not between", value1, value2, "totalGold");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonIsNull() {
            addCriterion("online_max_person is null");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonIsNotNull() {
            addCriterion("online_max_person is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonEqualTo(Integer value) {
            addCriterion("online_max_person =", value, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonNotEqualTo(Integer value) {
            addCriterion("online_max_person <>", value, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonGreaterThan(Integer value) {
            addCriterion("online_max_person >", value, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonGreaterThanOrEqualTo(Integer value) {
            addCriterion("online_max_person >=", value, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonLessThan(Integer value) {
            addCriterion("online_max_person <", value, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonLessThanOrEqualTo(Integer value) {
            addCriterion("online_max_person <=", value, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonIn(List<Integer> values) {
            addCriterion("online_max_person in", values, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonNotIn(List<Integer> values) {
            addCriterion("online_max_person not in", values, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonBetween(Integer value1, Integer value2) {
            addCriterion("online_max_person between", value1, value2, "onlineMaxPerson");
            return (Criteria) this;
        }

        public Criteria andOnlineMaxPersonNotBetween(Integer value1, Integer value2) {
            addCriterion("online_max_person not between", value1, value2, "onlineMaxPerson");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDrawTimesIsNull() {
            addCriterion("draw_times is null");
            return (Criteria) this;
        }

        public Criteria andDrawTimesIsNotNull() {
            addCriterion("draw_times is not null");
            return (Criteria) this;
        }

        public Criteria andDrawTimesEqualTo(Integer value) {
            addCriterion("draw_times =", value, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesNotEqualTo(Integer value) {
            addCriterion("draw_times <>", value, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesGreaterThan(Integer value) {
            addCriterion("draw_times >", value, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("draw_times >=", value, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesLessThan(Integer value) {
            addCriterion("draw_times <", value, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesLessThanOrEqualTo(Integer value) {
            addCriterion("draw_times <=", value, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesIn(List<Integer> values) {
            addCriterion("draw_times in", values, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesNotIn(List<Integer> values) {
            addCriterion("draw_times not in", values, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesBetween(Integer value1, Integer value2) {
            addCriterion("draw_times between", value1, value2, "drawTimes");
            return (Criteria) this;
        }

        public Criteria andDrawTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("draw_times not between", value1, value2, "drawTimes");
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