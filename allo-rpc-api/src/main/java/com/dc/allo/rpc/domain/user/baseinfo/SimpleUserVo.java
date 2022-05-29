package com.dc.allo.rpc.domain.user.baseinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SimpleUserVo {
    /**
     * 用户uid
     */
    private Long uid;
    /**
     * 兔兔号
     */
    private Long erbanNo;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 性别 1.男  2.女
     */
    private Byte gender;

    private Double gold;
}
