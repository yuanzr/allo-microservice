package com.dc.allo.biznotice.service.msg.impl;

import com.alibaba.fastjson.JSON;
import com.dc.allo.biznotice.model.im.*;
import com.dc.allo.biznotice.model.im.RongCloudCustomerMessage;
import com.dc.allo.biznotice.service.msg.RongCloudMessageService;
import com.dc.allo.common.constants.RongCloudConstant;
import com.google.gson.Gson;
import io.rong.models.message.ChatroomMessage;
import io.rong.models.message.PrivateMessage;
import io.rong.models.message.PrivateStatusMessage;
import io.rong.models.message.SystemMessage;
import io.rong.models.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RongCloudMessageServiceImpl extends RongCloudBaseService implements RongCloudMessageService {
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
    @Override
    public IMResponse sendSystemMessage(String senderUid, String receiverUids, String content, String pushContent, String payload, String objectName) throws Exception {
        log.info("sendSystemMessage(), senderUid={}, receiverUids={}, content={}, pushContent={}, payload={}, objectName={}", senderUid, receiverUids,
                content, pushContent, payload, objectName);

        SystemMessage message = new SystemMessage().setSenderId(senderUid).setTargetId(StringUtils.split(receiverUids)).setObjectName(objectName)
                .setPushContent(pushContent).setPushData(payload).setContent(new RongCloudCustomerMessage(objectName, content));
        ResponseResult result = getRongCloudClient().message.system.send(message);

        log.info("result={}", JSON.toJSONString(result));
        return getResponse(JSON.toJSONString(result));
    }

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
    @Override
    public IMResponse sendPrivateMessage(String senderId, List<String> targetUidList, String objectName, String body, String pushContent, String payload) throws Exception {
        log.info("sendPrivateMessage(), senderId={}, targetUidList={}, objectName={}, body={}, pushContent={}, payload={}", senderId, targetUidList,
                objectName, body, pushContent, payload);

        PrivateMessage message = new PrivateMessage().setSenderId(senderId).setTargetId(targetUidList.toArray(new String[targetUidList.size()]))
                .setObjectName(objectName).setContent(new RongCloudCustomerMessage(objectName, body)).setPushContent(pushContent).setPushData(payload);
        ResponseResult result = getRongCloudClient().message.msgPrivate.send(message);
        log.info("result={}", JSON.toJSONString(result));
        return result == null ? null : getResponse(result.toString());
    }

    /**
     * 用户向其它用户（们）发送状态消息，单条消息最大 128k，单限频 6000/m，用户上限 1000 人
     * 对应 RongCloudErBanNetEaseService 的 sendMsg 和 sendBatchMsg 方法
     * @param senderId
     * @param targetUidList
     * @param objectName
     * @return
     * @throws Exception
     */
    @Override
    public IMResponse sendPrivateStatusMessage(String senderId, List<String> targetUidList, String objectName, String body) throws Exception {
        log.info("sendPrivateMessage(), senderId={}, targetUidList={}, objectName={}, body={}", senderId, targetUidList,objectName, body);

        PrivateStatusMessage message = new PrivateStatusMessage().setSenderId(senderId).setTargetId(targetUidList.toArray(new String[targetUidList.size()]))
                .setObjectName(objectName).setContent(new RongCloudCustomerMessage(objectName, body));
        ResponseResult result = getRongCloudClient().message.msgPrivate.sendStatusMessage(message);
        log.info("result={}", JSON.toJSONString(result));
        return result == null ? null : getResponse(result.toString());
    }

    /**
     * 发送内置图片消息
     * @param jpgBase64 jpg 类型缩略图，格式为 Base64
     */
    @Override
    public IMResponse sendIMGMessage(String senderId, List<String> targetUidList, String jpgBase64, String imgUrl, String pushContent, String payload) throws Exception {
        log.info("sendIMGMessage, senderId={}, targetUidList={}, imgUrl={}, jpgBase64={}", senderId, targetUidList, imgUrl,jpgBase64);

        String objectName= RongCloudConstant.RongCloundMessageObjectName.IMG;
        PrivateMessage message = new PrivateMessage().setSenderId(senderId).setTargetId(targetUidList.toArray(new String[targetUidList.size()]))
                .setObjectName(objectName).setContent(new RongCloudCustomerIMGMessage(objectName, jpgBase64, imgUrl)).setPushContent(pushContent).setPushData(payload);
        ResponseResult result = getRongCloudClient().message.msgPrivate.send(message);

        log.info("result={}", JSON.toJSONString(result));
        return result == null ? null : getResponse(result.toString());
    }

    /**
     * 发送内置图文消息
     */
    @Override
    public IMResponse sendImgTextMessage(SendImgTextMessageParam param) throws Exception {
        log.info("sendImgTextMessage, param:{}",new Gson().toJson(param));

        String objectName=RongCloudConstant.RongCloundMessageObjectName.IMG_TEXT;
        RongCloudCustomerImgTextMessage imgtextMessage = new RongCloudCustomerImgTextMessage(objectName,param);
        PrivateMessage message = new PrivateMessage().setSenderId(param.getSenderId()).setTargetId(param.getTargetUidList().toArray(new String[param.getTargetUidList().size()]))
                .setObjectName(objectName).setContent(imgtextMessage).setPushContent(param.getPushContent()).setPushData(param.getPayload());
        ResponseResult result = getRongCloudClient().message.msgPrivate.send(message);

        log.info("result={}", JSON.toJSONString(result));
        return getResponse(JSON.toJSONString(result));
    }

    /**
     * 发送聊天室消息
     * @param roomId
     * @param senderUid
     * @param content
     * @param objectName
     * @return
     * @throws Exception
     */
    @Override
    public IMResponse sendChatroomMessage(String roomId, String senderUid, String content, String objectName) throws Exception {
        log.info("sendChatroomMessage(), roomId={}, senderUid={}, content={}, objectName={}", roomId, senderUid, content, objectName);
        ChatroomMessage message = new ChatroomMessage();
        message.setSenderId(senderUid);
        message.setObjectName(objectName);
        message.setTargetId(new String[]{roomId});
        message.setContent(new RongCloudCustomerMessage(objectName, content));
        ResponseResult result = getRongCloudClient().message.chatroom.send(message);
        log.info("result={}", JSON.toJSONString(result));
        return getResponse(JSON.toJSONString(result));
    }
}
