package com.dc.allo.common.domain.user;

import java.io.Serializable;

/**
 * @ClassName: NobleUsersExpand
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2019/08/09 14:04
 */
public class NobleUsersExpand implements Serializable {

    private String avatar;

    private Long expireDays;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(Long expireDays) {
        this.expireDays = expireDays;
    }
}
