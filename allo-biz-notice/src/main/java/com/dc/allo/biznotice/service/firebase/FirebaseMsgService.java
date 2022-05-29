package com.dc.allo.biznotice.service.firebase;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.model.firebase.NoticeMsgPushRecord;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: FirebaseMsgService
 * @Description: Fire消息服务-接入admin-SDK
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/26/12:12
 */
public interface FirebaseMsgService {

    /**
     * 绑定用户的firebasetoken
     *
     * @param token 只要不卸载客户端重装
     */
    void bindFirebaseToken(Long uid, String token);

    /**
     * 解绑用户的firebasetoken
     */
    void unbindFirebaseToken(Long uid, String token);

    /**
     * 推送消息
     *
     * @return mesasgeId
     */
    String messagePush(NoticeMsgPushRecord record) throws FirebaseMessagingException, IOException;

    /**
     * admin后台推送消息
     */
    void messagePushAdmin(NoticeMsgPushRecord record) throws FirebaseMessagingException, IOException;

    /**
     * admin分页条件查询
     *
     * @param starTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param pageSize 页面大小
     */
    JSONObject queryPage(Date starTime, Date endTime, Integer page, Integer pageSize);

    /**
     * 投递待发送的消息到延迟队列中
     */
    void delayQueueProvider();

    /**
     * 处理监听器中的消息
     * @param record
     */
    void delayQueueConsumer(NoticeMsgPushRecord record) throws FirebaseMessagingException, IOException;

}
