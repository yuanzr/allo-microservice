package com.dc.allo.biznotice.delay.message;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: FirebaseMessage
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/4/9:58
 */
@ToString
@Data
public class FirebaseMessage implements Serializable {

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

    private Long pushTimeTemp;
    /** 事件类型 {@link com.dc.allo.biznotice.constant.Constant.ActionType} */
    private String actionType;
    /** 原生路由  */
    private Byte routeType;

    private String action;
    /** 推送状态 {@link com.dc.allo.biznotice.constant.Constant.MsgPushStatusType} */
    private Byte pushStatus;
}
