package com.dc.allo.biznotice.service.msg;


import com.dc.allo.biznotice.model.im.*;

import java.util.List;

public interface RongCloudMessageService {
    /**
     * 一个用户向多个用户发送系统消息（单应用限频100/s，一次最多100人，可找商务调整）
     * 对应 RongCloudErBanNetEaseService 的 sendSysAttachMsg 和 sendBatchSysAttachMsg 方法
     * @param senderUid
     * @param receiverUids
     * @param content
     * @param pushContent
     * @param payload
     * @return
     */
    IMResponse sendSystemMessage(String senderUid, String receiverUids, String content, String pushContent, String payload, String objectName) throws Exception;
    /**
     * 用户向其它用户（们）发送消息，单条消息最大 128k，单限频 6000/m，用户上限 1000 人
     * 对应 RongCloudErBanNetEaseService 的 sendMsg 和 sendBatchMsg 方法
     * @param senderId
     * @param targetUidList
     * @param objectName
     * @param body
     * @param pushContent
     * @param payload
     * @return
     * @throws Exception
     */
    IMResponse sendPrivateMessage(String senderId, List<String> targetUidList, String objectName, String body, String pushContent, String payload) throws Exception;
    /**
     * 用户向其它用户（们）发送状态消息，单条消息最大 128k，单限频 6000/m，用户上限 1000 人
     * 对应 RongCloudErBanNetEaseService 的 sendMsg 和 sendBatchMsg 方法
     * @param senderId
     * @param targetUidList
     * @param objectName
     * @param body
     * @return
     * @throws Exception
     */
    IMResponse sendPrivateStatusMessage(String senderId, List<String> targetUidList, String objectName, String body) throws Exception;

    /**
     * 发送内置图片消息
     * @param jpgBase64 jpg 类型缩略图，格式为 Base64
     */
    IMResponse sendIMGMessage(String senderId, List<String> targetUidList, String jpgBase64, String imgUrl, String pushContent, String payload) throws Exception;
    /**
     * 发送内置图文消息
     */
    IMResponse sendImgTextMessage(SendImgTextMessageParam param) throws Exception;
    /**
     * 发送聊天室消息
     * @param roomId
     * @param senderUid
     * @param content
     * @param objectName
     * @return
     * @throws Exception
     */
    IMResponse sendChatroomMessage(String roomId, String senderUid, String content, String objectName) throws Exception;
}
