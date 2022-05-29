package com.dc.allo.biznotice.model.firebase;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NoticeFirebaseTopicUser {
    private Long recordId;

    private Long uid;

    private String topic;

    private Date createTime;

    private Date updateTime;
}