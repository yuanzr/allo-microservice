package com.dc.allo.rpc.domain.roomplay.pk;

import java.util.Date;

/**
 * description: 预设PK
 *
 * @date: 2020年04月13日 15:09
 * @author: qinrenchuan
 */
public class RoomPkPlan {
    /** id */
    private Long id;
    /** 发起pk房间 */
    private Long roomUid;
    /** 接受pk的房间 */
    private Long targetRoomUid;
    /** 发起pk房间 */
    private Long roomId;
    /** 接受pk的房间 */
    private Long targetRoomId;
    /** pk 时间 */
    private Date pkStartTime;
    /** 持续时长分钟 */
    private Integer durationMinute;

    /** pk 时间 */
    private Date pkEndTime;
    /** 惩罚措施(1浇水 2砸鸡蛋 3棒球打头) */
    private Byte punishment;

    private Date createTime;

    /** 0预约PK未开启，1表示已正常发起PK, 2发起PK异常 */
    private Byte status;


    /** 创建管理员UID */
    private Integer createUid;

    public Long getId() {
        return id;
    }

    public RoomPkPlan setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getRoomUid() {
        return roomUid;
    }

    public RoomPkPlan setRoomUid(Long roomUid) {
        this.roomUid = roomUid;
        return this;
    }

    public Long getTargetRoomUid() {
        return targetRoomUid;
    }

    public RoomPkPlan setTargetRoomUid(Long targetRoomUid) {
        this.targetRoomUid = targetRoomUid;
        return this;
    }

    public Long getRoomId() {
        return roomId;
    }

    public RoomPkPlan setRoomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public Long getTargetRoomId() {
        return targetRoomId;
    }

    public RoomPkPlan setTargetRoomId(Long targetRoomId) {
        this.targetRoomId = targetRoomId;
        return this;
    }

    public Date getPkStartTime() {
        return pkStartTime;
    }

    public RoomPkPlan setPkStartTime(Date pkStartTime) {
        this.pkStartTime = pkStartTime;
        return this;
    }

    public Integer getDurationMinute() {
        return durationMinute;
    }

    public RoomPkPlan setDurationMinute(Integer durationMinute) {
        this.durationMinute = durationMinute;
        return this;
    }

    public Date getPkEndTime() {
        return pkEndTime;
    }

    public RoomPkPlan setPkEndTime(Date pkEndTime) {
        this.pkEndTime = pkEndTime;
        return this;
    }

    public Byte getPunishment() {
        return punishment;
    }

    public RoomPkPlan setPunishment(Byte punishment) {
        this.punishment = punishment;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public RoomPkPlan setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public RoomPkPlan setCreateUid(Integer createUid) {
        this.createUid = createUid;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public RoomPkPlan setStatus(Byte status) {
        this.status = status;
        return this;
    }
}
