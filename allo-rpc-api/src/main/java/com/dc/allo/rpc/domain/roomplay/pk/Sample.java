package com.dc.allo.rpc.domain.roomplay.pk;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Data
public class Sample implements Serializable {

    private long id;
    private String username;
    private String password;
    private String headimg;
    private String email;
    private String roleStr;
    private String createTime;
    private String phone;
    private String qq;
}
