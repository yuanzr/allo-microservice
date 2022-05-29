package com.dc.allo.biznotice.model.firebase;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NoticeFirebaseTopic {
    private Integer topicId;

    private String topic;

    private String desc;

    private Date createTime;

    private Date updateTime;
}