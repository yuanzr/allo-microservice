package com.dc.allo.roomplay.domain.vo;


import java.math.BigDecimal;

public class RoomPkWinVo {
    private Long roomUid;
    private Long erbanNo;
    private String roomAvatar;
    private String roomTitle;
    //战力值
    private BigDecimal combatPower;
    //胜场
    private Integer winNum;

    public Long getRoomUid() {
        return roomUid;
    }

    public void setRoomUid(Long roomUid) {
        this.roomUid = roomUid;
    }

    public Long getErbanNo() {
        return erbanNo;
    }

    public void setErbanNo(Long erbanNo) {
        this.erbanNo = erbanNo;
    }

    public String getRoomAvatar() {
        return roomAvatar;
    }

    public void setRoomAvatar(String roomAvatar) {
        this.roomAvatar = roomAvatar;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public BigDecimal getCombatPower() {
        return combatPower;
    }

    public void setCombatPower(BigDecimal combatPower) {
        this.combatPower = combatPower;
    }

    public Integer getWinNum() {
        return winNum;
    }

    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
    }
}
