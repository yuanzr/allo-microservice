package com.dc.allo.roomplay.domain.vo;

import com.alibaba.fastjson.JSON;

/**
 * @author yangziwen
 * @description
 * @date 2018/4/24 12:47
 */
public class RoomRankingVo {

    private Long uid;
    private Long erbanNo;
    private String nick;
    private String avatar;
    private Byte gender;
    private Integer experSeq;
    private String experUrl;
    private Integer charmSeq;
    private String charmUrl;
    private Integer nobleId;
    private String nobleName;
    private String badge;
    private String micDecorate;
    private Long goldAmount;
    private Long ranking;
    private Boolean hide;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getErbanNo() {
        return erbanNo;
    }

    public void setErbanNo(Long erbanNo) {
        this.erbanNo = erbanNo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getExperSeq() {
        return experSeq;
    }

    public void setExperSeq(Integer experSeq) {
        this.experSeq = experSeq;
    }

    public String getExperUrl() {
        return experUrl;
    }

    public void setExperUrl(String experUrl) {
        this.experUrl = experUrl;
    }

    public Integer getCharmSeq() {
        return charmSeq;
    }

    public void setCharmSeq(Integer charmSeq) {
        this.charmSeq = charmSeq;
    }

    public String getCharmUrl() {
        return charmUrl;
    }

    public void setCharmUrl(String charmUrl) {
        this.charmUrl = charmUrl;
    }

    public Integer getNobleId() {
        return nobleId;
    }

    public void setNobleId(Integer nobleId) {
        this.nobleId = nobleId;
    }

    public String getNobleName() {
        return nobleName;
    }

    public void setNobleName(String nobleName) {
        this.nobleName = nobleName;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getMicDecorate() {
        return micDecorate;
    }

    public void setMicDecorate(String micDecorate) {
        this.micDecorate = micDecorate;
    }

    public Long getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(Long goldAmount) {
        this.goldAmount = goldAmount;
    }

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
