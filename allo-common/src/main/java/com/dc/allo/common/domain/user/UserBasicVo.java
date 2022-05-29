package com.dc.allo.common.domain.user;

import lombok.Data;

@Data
public class UserBasicVo {
    private Long uid;

    private Long erbanNo;

    private String avatar;

    private String nick;

    private Boolean isInRoom;

}
