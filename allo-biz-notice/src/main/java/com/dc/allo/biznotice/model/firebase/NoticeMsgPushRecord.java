package com.dc.allo.biznotice.model.firebase;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ToString
public class NoticeMsgPushRecord {
    private Long recordId;

    private String messageId;

    private Byte msgType;

    private String imageUrl;

    private String title;

    private String body;
    /** 消息发送对象 {@link com.dc.allo.biznotice.constant.Constant.MsgToType} */
    private Byte toObjType;

    private String toErbanNos;

    private String region;
    /** 推送时间 {@link com.dc.allo.biznotice.constant.Constant.MsgPushType} */
    private Byte pushType;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pushTime;
    /** 事件类型 {@link com.dc.allo.biznotice.constant.Constant.ActionType} */
    private String actionType;
    /** 原生路由  */
    private Byte routeType;

    private String action;
    /** 推送状态 {@link com.dc.allo.biznotice.constant.Constant.MsgPushStatusType} */
    private Byte pushStatus;

    private String adminName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}