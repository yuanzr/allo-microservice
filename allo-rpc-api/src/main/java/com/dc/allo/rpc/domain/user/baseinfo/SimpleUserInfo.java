package com.dc.allo.rpc.domain.user.baseinfo;


import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * description: SimpleUserInfo
 *
 * @date: 2020年04月17日 11:01
 * @author: qinrenchuan
 */
@Data
@ToString
public class SimpleUserInfo implements Serializable {
    /** uid */
    private Long uid;
    /** 耳伴号 */
    private Long erbanNo;
    /** 昵称 */
    private String nick;
    /** 性别 1.男  2.女 */
    private Byte gender;
    /** 头像 */
    private String avatar;
    /** phone */
    private String phone;
}
