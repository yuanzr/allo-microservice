package com.dc.allo.roomplay.domain.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomHeatValueTotalExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoomHeatValueTotalExample() {
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

        public Criteria andRoomAvatarIsNull() {
            addCriterion("room_avatar is null");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarIsNotNull() {
            addCriterion("room_avatar is not null");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarEqualTo(String value) {
            addCriterion("room_avatar =", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarNotEqualTo(String value) {
            addCriterion("room_avatar <>", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarGreaterThan(String value) {
            addCriterion("room_avatar >", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("room_avatar >=", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarLessThan(String value) {
            addCriterion("room_avatar <", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarLessThanOrEqualTo(String value) {
            addCriterion("room_avatar <=", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarLike(String value) {
            addCriterion("room_avatar like", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarNotLike(String value) {
            addCriterion("room_avatar not like", value, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarIn(List<String> values) {
            addCriterion("room_avatar in", values, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarNotIn(List<String> values) {
            addCriterion("room_avatar not in", values, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarBetween(String value1, String value2) {
            addCriterion("room_avatar between", value1, value2, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomAvatarNotBetween(String value1, String value2) {
            addCriterion("room_avatar not between", value1, value2, "roomAvatar");
            return (Criteria) this;
        }

        public Criteria andRoomTitleIsNull() {
            addCriterion("room_title is null");
            return (Criteria) this;
        }

        public Criteria andRoomTitleIsNotNull() {
            addCriterion("room_title is not null");
            return (Criteria) this;
        }

        public Criteria andRoomTitleEqualTo(String value) {
            addCriterion("room_title =", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleNotEqualTo(String value) {
            addCriterion("room_title <>", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleGreaterThan(String value) {
            addCriterion("room_title >", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleGreaterThanOrEqualTo(String value) {
            addCriterion("room_title >=", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleLessThan(String value) {
            addCriterion("room_title <", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleLessThanOrEqualTo(String value) {
            addCriterion("room_title <=", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleLike(String value) {
            addCriterion("room_title like", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleNotLike(String value) {
            addCriterion("room_title not like", value, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleIn(List<String> values) {
            addCriterion("room_title in", values, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleNotIn(List<String> values) {
            addCriterion("room_title not in", values, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleBetween(String value1, String value2) {
            addCriterion("room_title between", value1, value2, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andRoomTitleNotBetween(String value1, String value2) {
            addCriterion("room_title not between", value1, value2, "roomTitle");
            return (Criteria) this;
        }

        public Criteria andHeatValueIsNull() {
            addCriterion("heat_value is null");
            return (Criteria) this;
        }

        public Criteria andHeatValueIsNotNull() {
            addCriterion("heat_value is not null");
            return (Criteria) this;
        }

        public Criteria andHeatValueEqualTo(BigDecimal value) {
            addCriterion("heat_value =", value, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueNotEqualTo(BigDecimal value) {
            addCriterion("heat_value <>", value, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueGreaterThan(BigDecimal value) {
            addCriterion("heat_value >", value, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("heat_value >=", value, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueLessThan(BigDecimal value) {
            addCriterion("heat_value <", value, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("heat_value <=", value, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueIn(List<BigDecimal> values) {
            addCriterion("heat_value in", values, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueNotIn(List<BigDecimal> values) {
            addCriterion("heat_value not in", values, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("heat_value between", value1, value2, "heatValue");
            return (Criteria) this;
        }

        public Criteria andHeatValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("heat_value not between", value1, value2, "heatValue");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Byte value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Byte value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Byte value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Byte value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Byte value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Byte> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Byte> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Byte value1, Byte value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("level not between", value1, value2, "level");
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