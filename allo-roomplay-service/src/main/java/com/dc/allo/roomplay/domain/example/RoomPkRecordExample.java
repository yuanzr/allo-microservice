package com.dc.allo.roomplay.domain.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomPkRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoomPkRecordExample() {
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

        public Criteria andTargetRoomUidIsNull() {
            addCriterion("target_room_uid is null");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidIsNotNull() {
            addCriterion("target_room_uid is not null");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidEqualTo(Long value) {
            addCriterion("target_room_uid =", value, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidNotEqualTo(Long value) {
            addCriterion("target_room_uid <>", value, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidGreaterThan(Long value) {
            addCriterion("target_room_uid >", value, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidGreaterThanOrEqualTo(Long value) {
            addCriterion("target_room_uid >=", value, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidLessThan(Long value) {
            addCriterion("target_room_uid <", value, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidLessThanOrEqualTo(Long value) {
            addCriterion("target_room_uid <=", value, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidIn(List<Long> values) {
            addCriterion("target_room_uid in", values, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidNotIn(List<Long> values) {
            addCriterion("target_room_uid not in", values, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidBetween(Long value1, Long value2) {
            addCriterion("target_room_uid between", value1, value2, "targetRoomUid");
            return (Criteria) this;
        }

        public Criteria andTargetRoomUidNotBetween(Long value1, Long value2) {
            addCriterion("target_room_uid not between", value1, value2, "targetRoomUid");
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

        public Criteria andTargetRoomTitleIsNull() {
            addCriterion("target_room_title is null");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleIsNotNull() {
            addCriterion("target_room_title is not null");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleEqualTo(String value) {
            addCriterion("target_room_title =", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleNotEqualTo(String value) {
            addCriterion("target_room_title <>", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleGreaterThan(String value) {
            addCriterion("target_room_title >", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleGreaterThanOrEqualTo(String value) {
            addCriterion("target_room_title >=", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleLessThan(String value) {
            addCriterion("target_room_title <", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleLessThanOrEqualTo(String value) {
            addCriterion("target_room_title <=", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleLike(String value) {
            addCriterion("target_room_title like", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleNotLike(String value) {
            addCriterion("target_room_title not like", value, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleIn(List<String> values) {
            addCriterion("target_room_title in", values, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleNotIn(List<String> values) {
            addCriterion("target_room_title not in", values, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleBetween(String value1, String value2) {
            addCriterion("target_room_title between", value1, value2, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andTargetRoomTitleNotBetween(String value1, String value2) {
            addCriterion("target_room_title not between", value1, value2, "targetRoomTitle");
            return (Criteria) this;
        }

        public Criteria andPkStatusIsNull() {
            addCriterion("pk_status is null");
            return (Criteria) this;
        }

        public Criteria andPkStatusIsNotNull() {
            addCriterion("pk_status is not null");
            return (Criteria) this;
        }

        public Criteria andPkStatusEqualTo(Byte value) {
            addCriterion("pk_status =", value, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusNotEqualTo(Byte value) {
            addCriterion("pk_status <>", value, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusGreaterThan(Byte value) {
            addCriterion("pk_status >", value, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("pk_status >=", value, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusLessThan(Byte value) {
            addCriterion("pk_status <", value, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusLessThanOrEqualTo(Byte value) {
            addCriterion("pk_status <=", value, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusIn(List<Byte> values) {
            addCriterion("pk_status in", values, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusNotIn(List<Byte> values) {
            addCriterion("pk_status not in", values, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusBetween(Byte value1, Byte value2) {
            addCriterion("pk_status between", value1, value2, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("pk_status not between", value1, value2, "pkStatus");
            return (Criteria) this;
        }

        public Criteria andPkResultIsNull() {
            addCriterion("pk_result is null");
            return (Criteria) this;
        }

        public Criteria andPkResultIsNotNull() {
            addCriterion("pk_result is not null");
            return (Criteria) this;
        }

        public Criteria andPkResultEqualTo(Byte value) {
            addCriterion("pk_result =", value, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultNotEqualTo(Byte value) {
            addCriterion("pk_result <>", value, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultGreaterThan(Byte value) {
            addCriterion("pk_result >", value, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultGreaterThanOrEqualTo(Byte value) {
            addCriterion("pk_result >=", value, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultLessThan(Byte value) {
            addCriterion("pk_result <", value, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultLessThanOrEqualTo(Byte value) {
            addCriterion("pk_result <=", value, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultIn(List<Byte> values) {
            addCriterion("pk_result in", values, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultNotIn(List<Byte> values) {
            addCriterion("pk_result not in", values, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultBetween(Byte value1, Byte value2) {
            addCriterion("pk_result between", value1, value2, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkResultNotBetween(Byte value1, Byte value2) {
            addCriterion("pk_result not between", value1, value2, "pkResult");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeIsNull() {
            addCriterion("pk_start_time is null");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeIsNotNull() {
            addCriterion("pk_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeEqualTo(Date value) {
            addCriterion("pk_start_time =", value, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeNotEqualTo(Date value) {
            addCriterion("pk_start_time <>", value, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeGreaterThan(Date value) {
            addCriterion("pk_start_time >", value, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pk_start_time >=", value, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeLessThan(Date value) {
            addCriterion("pk_start_time <", value, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("pk_start_time <=", value, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeIn(List<Date> values) {
            addCriterion("pk_start_time in", values, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeNotIn(List<Date> values) {
            addCriterion("pk_start_time not in", values, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeBetween(Date value1, Date value2) {
            addCriterion("pk_start_time between", value1, value2, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("pk_start_time not between", value1, value2, "pkStartTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeIsNull() {
            addCriterion("pk_end_time is null");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeIsNotNull() {
            addCriterion("pk_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeEqualTo(Date value) {
            addCriterion("pk_end_time =", value, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeNotEqualTo(Date value) {
            addCriterion("pk_end_time <>", value, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeGreaterThan(Date value) {
            addCriterion("pk_end_time >", value, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pk_end_time >=", value, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeLessThan(Date value) {
            addCriterion("pk_end_time <", value, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("pk_end_time <=", value, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeIn(List<Date> values) {
            addCriterion("pk_end_time in", values, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeNotIn(List<Date> values) {
            addCriterion("pk_end_time not in", values, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeBetween(Date value1, Date value2) {
            addCriterion("pk_end_time between", value1, value2, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPkEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("pk_end_time not between", value1, value2, "pkEndTime");
            return (Criteria) this;
        }

        public Criteria andPunishmentIsNull() {
            addCriterion("punishment is null");
            return (Criteria) this;
        }

        public Criteria andPunishmentIsNotNull() {
            addCriterion("punishment is not null");
            return (Criteria) this;
        }

        public Criteria andPunishmentEqualTo(String value) {
            addCriterion("punishment =", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentNotEqualTo(String value) {
            addCriterion("punishment <>", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentGreaterThan(String value) {
            addCriterion("punishment >", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentGreaterThanOrEqualTo(String value) {
            addCriterion("punishment >=", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentLessThan(String value) {
            addCriterion("punishment <", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentLessThanOrEqualTo(String value) {
            addCriterion("punishment <=", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentLike(String value) {
            addCriterion("punishment like", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentNotLike(String value) {
            addCriterion("punishment not like", value, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentIn(List<String> values) {
            addCriterion("punishment in", values, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentNotIn(List<String> values) {
            addCriterion("punishment not in", values, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentBetween(String value1, String value2) {
            addCriterion("punishment between", value1, value2, "punishment");
            return (Criteria) this;
        }

        public Criteria andPunishmentNotBetween(String value1, String value2) {
            addCriterion("punishment not between", value1, value2, "punishment");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldIsNull() {
            addCriterion("room_total_gold is null");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldIsNotNull() {
            addCriterion("room_total_gold is not null");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldEqualTo(BigDecimal value) {
            addCriterion("room_total_gold =", value, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldNotEqualTo(BigDecimal value) {
            addCriterion("room_total_gold <>", value, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldGreaterThan(BigDecimal value) {
            addCriterion("room_total_gold >", value, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("room_total_gold >=", value, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldLessThan(BigDecimal value) {
            addCriterion("room_total_gold <", value, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldLessThanOrEqualTo(BigDecimal value) {
            addCriterion("room_total_gold <=", value, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldIn(List<BigDecimal> values) {
            addCriterion("room_total_gold in", values, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldNotIn(List<BigDecimal> values) {
            addCriterion("room_total_gold not in", values, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("room_total_gold between", value1, value2, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalGoldNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("room_total_gold not between", value1, value2, "roomTotalGold");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonIsNull() {
            addCriterion("room_total_person is null");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonIsNotNull() {
            addCriterion("room_total_person is not null");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonEqualTo(Short value) {
            addCriterion("room_total_person =", value, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonNotEqualTo(Short value) {
            addCriterion("room_total_person <>", value, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonGreaterThan(Short value) {
            addCriterion("room_total_person >", value, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonGreaterThanOrEqualTo(Short value) {
            addCriterion("room_total_person >=", value, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonLessThan(Short value) {
            addCriterion("room_total_person <", value, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonLessThanOrEqualTo(Short value) {
            addCriterion("room_total_person <=", value, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonIn(List<Short> values) {
            addCriterion("room_total_person in", values, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonNotIn(List<Short> values) {
            addCriterion("room_total_person not in", values, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonBetween(Short value1, Short value2) {
            addCriterion("room_total_person between", value1, value2, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andRoomTotalPersonNotBetween(Short value1, Short value2) {
            addCriterion("room_total_person not between", value1, value2, "roomTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldIsNull() {
            addCriterion("target_total_gold is null");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldIsNotNull() {
            addCriterion("target_total_gold is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldEqualTo(BigDecimal value) {
            addCriterion("target_total_gold =", value, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldNotEqualTo(BigDecimal value) {
            addCriterion("target_total_gold <>", value, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldGreaterThan(BigDecimal value) {
            addCriterion("target_total_gold >", value, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("target_total_gold >=", value, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldLessThan(BigDecimal value) {
            addCriterion("target_total_gold <", value, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldLessThanOrEqualTo(BigDecimal value) {
            addCriterion("target_total_gold <=", value, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldIn(List<BigDecimal> values) {
            addCriterion("target_total_gold in", values, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldNotIn(List<BigDecimal> values) {
            addCriterion("target_total_gold not in", values, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("target_total_gold between", value1, value2, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalGoldNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("target_total_gold not between", value1, value2, "targetTotalGold");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonIsNull() {
            addCriterion("target_total_person is null");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonIsNotNull() {
            addCriterion("target_total_person is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonEqualTo(Short value) {
            addCriterion("target_total_person =", value, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonNotEqualTo(Short value) {
            addCriterion("target_total_person <>", value, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonGreaterThan(Short value) {
            addCriterion("target_total_person >", value, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonGreaterThanOrEqualTo(Short value) {
            addCriterion("target_total_person >=", value, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonLessThan(Short value) {
            addCriterion("target_total_person <", value, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonLessThanOrEqualTo(Short value) {
            addCriterion("target_total_person <=", value, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonIn(List<Short> values) {
            addCriterion("target_total_person in", values, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonNotIn(List<Short> values) {
            addCriterion("target_total_person not in", values, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonBetween(Short value1, Short value2) {
            addCriterion("target_total_person between", value1, value2, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andTargetTotalPersonNotBetween(Short value1, Short value2) {
            addCriterion("target_total_person not between", value1, value2, "targetTotalPerson");
            return (Criteria) this;
        }

        public Criteria andSponsorUidIsNull() {
            addCriterion("sponsor_uid is null");
            return (Criteria) this;
        }

        public Criteria andSponsorUidIsNotNull() {
            addCriterion("sponsor_uid is not null");
            return (Criteria) this;
        }

        public Criteria andSponsorUidEqualTo(Long value) {
            addCriterion("sponsor_uid =", value, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidNotEqualTo(Long value) {
            addCriterion("sponsor_uid <>", value, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidGreaterThan(Long value) {
            addCriterion("sponsor_uid >", value, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidGreaterThanOrEqualTo(Long value) {
            addCriterion("sponsor_uid >=", value, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidLessThan(Long value) {
            addCriterion("sponsor_uid <", value, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidLessThanOrEqualTo(Long value) {
            addCriterion("sponsor_uid <=", value, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidIn(List<Long> values) {
            addCriterion("sponsor_uid in", values, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidNotIn(List<Long> values) {
            addCriterion("sponsor_uid not in", values, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidBetween(Long value1, Long value2) {
            addCriterion("sponsor_uid between", value1, value2, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andSponsorUidNotBetween(Long value1, Long value2) {
            addCriterion("sponsor_uid not between", value1, value2, "sponsorUid");
            return (Criteria) this;
        }

        public Criteria andWinUidIsNull() {
            addCriterion("win_uid is null");
            return (Criteria) this;
        }

        public Criteria andWinUidIsNotNull() {
            addCriterion("win_uid is not null");
            return (Criteria) this;
        }

        public Criteria andWinUidEqualTo(Long value) {
            addCriterion("win_uid =", value, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidNotEqualTo(Long value) {
            addCriterion("win_uid <>", value, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidGreaterThan(Long value) {
            addCriterion("win_uid >", value, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidGreaterThanOrEqualTo(Long value) {
            addCriterion("win_uid >=", value, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidLessThan(Long value) {
            addCriterion("win_uid <", value, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidLessThanOrEqualTo(Long value) {
            addCriterion("win_uid <=", value, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidIn(List<Long> values) {
            addCriterion("win_uid in", values, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidNotIn(List<Long> values) {
            addCriterion("win_uid not in", values, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidBetween(Long value1, Long value2) {
            addCriterion("win_uid between", value1, value2, "winUid");
            return (Criteria) this;
        }

        public Criteria andWinUidNotBetween(Long value1, Long value2) {
            addCriterion("win_uid not between", value1, value2, "winUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidIsNull() {
            addCriterion("lose_uid is null");
            return (Criteria) this;
        }

        public Criteria andLoseUidIsNotNull() {
            addCriterion("lose_uid is not null");
            return (Criteria) this;
        }

        public Criteria andLoseUidEqualTo(Long value) {
            addCriterion("lose_uid =", value, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidNotEqualTo(Long value) {
            addCriterion("lose_uid <>", value, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidGreaterThan(Long value) {
            addCriterion("lose_uid >", value, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidGreaterThanOrEqualTo(Long value) {
            addCriterion("lose_uid >=", value, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidLessThan(Long value) {
            addCriterion("lose_uid <", value, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidLessThanOrEqualTo(Long value) {
            addCriterion("lose_uid <=", value, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidIn(List<Long> values) {
            addCriterion("lose_uid in", values, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidNotIn(List<Long> values) {
            addCriterion("lose_uid not in", values, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidBetween(Long value1, Long value2) {
            addCriterion("lose_uid between", value1, value2, "loseUid");
            return (Criteria) this;
        }

        public Criteria andLoseUidNotBetween(Long value1, Long value2) {
            addCriterion("lose_uid not between", value1, value2, "loseUid");
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

        public Criteria andTargetRoomAvatarIsNull() {
            addCriterion("target_room_avatar is null");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarIsNotNull() {
            addCriterion("target_room_avatar is not null");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarEqualTo(String value) {
            addCriterion("target_room_avatar =", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarNotEqualTo(String value) {
            addCriterion("target_room_avatar <>", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarGreaterThan(String value) {
            addCriterion("target_room_avatar >", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("target_room_avatar >=", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarLessThan(String value) {
            addCriterion("target_room_avatar <", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarLessThanOrEqualTo(String value) {
            addCriterion("target_room_avatar <=", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarLike(String value) {
            addCriterion("target_room_avatar like", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarNotLike(String value) {
            addCriterion("target_room_avatar not like", value, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarIn(List<String> values) {
            addCriterion("target_room_avatar in", values, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarNotIn(List<String> values) {
            addCriterion("target_room_avatar not in", values, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarBetween(String value1, String value2) {
            addCriterion("target_room_avatar between", value1, value2, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andTargetRoomAvatarNotBetween(String value1, String value2) {
            addCriterion("target_room_avatar not between", value1, value2, "targetRoomAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidIsNull() {
            addCriterion("win_mvp_uid is null");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidIsNotNull() {
            addCriterion("win_mvp_uid is not null");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidEqualTo(Long value) {
            addCriterion("win_mvp_uid =", value, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidNotEqualTo(Long value) {
            addCriterion("win_mvp_uid <>", value, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidGreaterThan(Long value) {
            addCriterion("win_mvp_uid >", value, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidGreaterThanOrEqualTo(Long value) {
            addCriterion("win_mvp_uid >=", value, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidLessThan(Long value) {
            addCriterion("win_mvp_uid <", value, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidLessThanOrEqualTo(Long value) {
            addCriterion("win_mvp_uid <=", value, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidIn(List<Long> values) {
            addCriterion("win_mvp_uid in", values, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidNotIn(List<Long> values) {
            addCriterion("win_mvp_uid not in", values, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidBetween(Long value1, Long value2) {
            addCriterion("win_mvp_uid between", value1, value2, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpUidNotBetween(Long value1, Long value2) {
            addCriterion("win_mvp_uid not between", value1, value2, "winMvpUid");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarIsNull() {
            addCriterion("win_mvp_avatar is null");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarIsNotNull() {
            addCriterion("win_mvp_avatar is not null");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarEqualTo(String value) {
            addCriterion("win_mvp_avatar =", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarNotEqualTo(String value) {
            addCriterion("win_mvp_avatar <>", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarGreaterThan(String value) {
            addCriterion("win_mvp_avatar >", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("win_mvp_avatar >=", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarLessThan(String value) {
            addCriterion("win_mvp_avatar <", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarLessThanOrEqualTo(String value) {
            addCriterion("win_mvp_avatar <=", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarLike(String value) {
            addCriterion("win_mvp_avatar like", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarNotLike(String value) {
            addCriterion("win_mvp_avatar not like", value, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarIn(List<String> values) {
            addCriterion("win_mvp_avatar in", values, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarNotIn(List<String> values) {
            addCriterion("win_mvp_avatar not in", values, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarBetween(String value1, String value2) {
            addCriterion("win_mvp_avatar between", value1, value2, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinMvpAvatarNotBetween(String value1, String value2) {
            addCriterion("win_mvp_avatar not between", value1, value2, "winMvpAvatar");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerIsNull() {
            addCriterion("win_combat_power is null");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerIsNotNull() {
            addCriterion("win_combat_power is not null");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerEqualTo(BigDecimal value) {
            addCriterion("win_combat_power =", value, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerNotEqualTo(BigDecimal value) {
            addCriterion("win_combat_power <>", value, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerGreaterThan(BigDecimal value) {
            addCriterion("win_combat_power >", value, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("win_combat_power >=", value, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerLessThan(BigDecimal value) {
            addCriterion("win_combat_power <", value, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerLessThanOrEqualTo(BigDecimal value) {
            addCriterion("win_combat_power <=", value, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerIn(List<BigDecimal> values) {
            addCriterion("win_combat_power in", values, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerNotIn(List<BigDecimal> values) {
            addCriterion("win_combat_power not in", values, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_combat_power between", value1, value2, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andWinCombatPowerNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_combat_power not between", value1, value2, "winCombatPower");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeIsNull() {
            addCriterion("invite_invalid_time is null");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeIsNotNull() {
            addCriterion("invite_invalid_time is not null");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeEqualTo(Short value) {
            addCriterion("invite_invalid_time =", value, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeNotEqualTo(Short value) {
            addCriterion("invite_invalid_time <>", value, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeGreaterThan(Short value) {
            addCriterion("invite_invalid_time >", value, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeGreaterThanOrEqualTo(Short value) {
            addCriterion("invite_invalid_time >=", value, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeLessThan(Short value) {
            addCriterion("invite_invalid_time <", value, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeLessThanOrEqualTo(Short value) {
            addCriterion("invite_invalid_time <=", value, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeIn(List<Short> values) {
            addCriterion("invite_invalid_time in", values, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeNotIn(List<Short> values) {
            addCriterion("invite_invalid_time not in", values, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeBetween(Short value1, Short value2) {
            addCriterion("invite_invalid_time between", value1, value2, "inviteInvalidTime");
            return (Criteria) this;
        }

        public Criteria andInviteInvalidTimeNotBetween(Short value1, Short value2) {
            addCriterion("invite_invalid_time not between", value1, value2, "inviteInvalidTime");
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