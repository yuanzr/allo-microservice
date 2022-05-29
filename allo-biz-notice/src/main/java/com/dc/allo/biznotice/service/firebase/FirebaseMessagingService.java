package com.dc.allo.biznotice.service.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FirebaseMessagingService
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/15:56
 */
public interface FirebaseMessagingService {

    /**
     * 使用service-account和dataUrl初始化AdminSDK
     */
    FirebaseApp initializeWithServiceAccount() throws IOException;

    /**
     * 向单个设备发送消息
     * @param title 用户在firebase注册的token
     * @param body 用户在firebase注册的token
     * @param registrationToken  用户在firebase注册的token
     * @param data             消息载体
     */
    String sendMsgSingle(String title,String body,String imageUrl, Map<String, String> data,String registrationToken) throws FirebaseMessagingException, IOException;

    /**
     * 向多个设备发送消息,最大500个

     * @param registrationTokens 用户在firebase注册的token
     * @param data              消息的数据载体
     */
    void sendMsgMulticast(String title,String body, String imageUrl,Map<String, String> data, List<String> registrationTokens) throws FirebaseMessagingException , IOException;


    /**
     * 处理批量发送失败的用户
     * @param response
     * @param userRegistrationTokens
     * @param data
     */
    void sendMulticastAndHandleErrors(BatchResponse response,List<String> userRegistrationTokens, Map<String, String> data)  throws FirebaseMessagingException ;


    /**
     * 给多个设备订阅topic
     * @param topic
     * @param userRegistrationTokens
     */
    void subscribeTopic(String topic, List<String> userRegistrationTokens);


    /**
     * 给多个设备退订topic
     * @param topic
     * @param userRegistrationTokens
     */
    void unsubscribeTopic(String topic, List<String> userRegistrationTokens);

    /**
     * 向单个topic发送消息
     * @param topic
     * @param data             消息载体
     */
    String sendMsgTopic(String title,String body,String imageUrl,String topic, Map<String, String> data) throws FirebaseMessagingException;


    /**
     * 向多个topic发送消息
     * @param condition 表达式:stock-GOOG' in topics || 'industry-tech' in topics
     * @param data             消息载体
     */
    void sendMsgMultiTopic(String title,String body,String imageUrl,String condition, Map<String, String> data) throws FirebaseMessagingException;
}
