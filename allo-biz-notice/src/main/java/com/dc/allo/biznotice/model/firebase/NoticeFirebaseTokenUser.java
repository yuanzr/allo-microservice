package com.dc.allo.biznotice.model.firebase;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NoticeFirebaseTokenUser {
    private Long uid;

    private String registerToken;

    private Date createTime;

    private Date updateTime;

}