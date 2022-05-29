package com.dc.allo.biznotice.service.firebase.impl;

import com.dc.allo.biznotice.service.firebase.FirebaseMessagingService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.ApnsFcmOptions;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Message.Builder;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName: FirebaseAppImpl
 * @Description: 初始化SDK
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/21/18:18
 *
 *
 * 自定义字段
 * "actionType" : "none"/"web"/"native"/"webAlert"
 * "action": ""
 * "click_action":"MainActivity"
 */
@Slf4j
@Service
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService {


    private String serviceAccountJson;

    private String databaseUrl;

    private  FirebaseApp firebaseApp;

    private Gson gson = new Gson();

    private  Map<String, FirebaseApp> firebaseAppMap = new ConcurrentHashMap<>();

    public String getServiceAccountJson() {
        return serviceAccountJson;
    }
    @Value("${firebase.adminsdk.service-account}")
    public void setServiceAccountJson(String serviceAccountJson) {
        this.serviceAccountJson = serviceAccountJson;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }
    @Value("${firebase.adminsdk.database}")
    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }


    @Override
    public FirebaseApp initializeWithServiceAccount(){
        InputStream serviceAccount = this.getClass().getResourceAsStream(this.serviceAccountJson);
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(databaseUrl)
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
            firebaseAppMap.put("Allo",firebaseApp);
            return firebaseApp;
        } catch (IOException e) {
            log.error("initializeWithServiceAccount-error:", e);
        }
        return null;
    }

    @Override
    public String sendMsgSingle(String title,String body,String imageUrl, Map<String, String> data,String registrationToken) throws FirebaseMessagingException {
        firebaseAppMap.get("Allo");
        if (firebaseApp == null) {
            firebaseApp =  initializeWithServiceAccount();
        }
        //1.封装通知消息的内容:titel/body/image
        Notification.Builder noticeBuilder = Notification.builder()
                .setTitle(title)
                .setBody(body);
        if (StringUtils.isNotBlank(imageUrl)){
            noticeBuilder.setImage(imageUrl);
        }
        //2.封装消息体:token/提醒内容
        Builder builder = Message.builder()
                .setToken(registrationToken)
                .setNotification(noticeBuilder.build());

        for (String key : data.keySet()) {
            builder.putData(key, data.get(key));
        }
        Message message = builder.build();
        log.info("sendMsgSingle={},message: " + gson.toJson(message));
        //3.发送消息
        String response = FirebaseMessaging.getInstance(firebaseApp).send(message);
        log.info("sendMsgSingle-Successfully:messageId={} ", response);
        return response;
    }

    @Override
    public void sendMsgMulticast(String title,String body,String imageUrl,Map<String, String> data, List<String> registrationTokens) throws FirebaseMessagingException{
        firebaseAppMap.get("Allo");
        if (firebaseApp == null) {
            firebaseApp =  initializeWithServiceAccount();
        }
        //1.封装通知消息的内容:titel/body/image
        Notification.Builder noticeBuilder = Notification.builder()
                .setTitle(title)
                .setBody(body);
        if (StringUtils.isNotBlank(imageUrl)){
            noticeBuilder.setImage(imageUrl);
        }
        //2.封装消息体:多个token/提醒内容
        MulticastMessage.Builder builder = MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setNotification(noticeBuilder.build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder().setClickAction("MainActivity").build())
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                                .setAps(Aps.builder().setBadge(0).build())
                                .putHeader("mutable-content","1").build());
        for (String key : data.keySet()) {
            builder.putData(key, data.get(key));
        }
        MulticastMessage message = builder.build();
        log.info("sendMsgMulticast,message={} ",gson.toJson(message));
        //3.发送消息
        BatchResponse response = FirebaseMessaging.getInstance(firebaseApp).sendMulticast(message);
        log.info("sendMsgSingle-Successfully:totalCount={},successCount={} ", registrationTokens.size(),response.getSuccessCount());
        if (response.getFailureCount() > 0) {
            log.warn("sendMsgSingle-hander:totalCount={},successCount={} ", registrationTokens.size(),response.getSuccessCount());
            log.info("sendMsgSingle-hander=:Responses={}", gson.toJson(response.getResponses()));
            sendMulticastAndHandleErrors(response, registrationTokens, data);
        }
    }

    @Override
    public void  sendMulticastAndHandleErrors(BatchResponse response,List<String> userRegistrationTokens, Map<String, String> data)  throws FirebaseMessagingException {
        //暂时只重试3次
        int limit = 3;
        List<String> failedTokens = new ArrayList<>();
        List<SendResponse> responses = response.getResponses();
        for (int j = 0; j < limit; j++) {
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    failedTokens.add(userRegistrationTokens.get(i));
                }
            }
            firebaseAppMap.get("Allo");
            if (firebaseApp == null) {
                firebaseApp =  initializeWithServiceAccount();
            }
            MulticastMessage.Builder builder = MulticastMessage.builder().addAllTokens(failedTokens);
            for (String key : data.keySet()) {
                builder.putData(key, data.get(key));
            }
            MulticastMessage message = builder.build();
            BatchResponse responseSub = FirebaseMessaging.getInstance(firebaseApp).sendMulticast(message);
            if (responseSub.getFailureCount() < 1) {
                return;
            }
            log.info("sendMulticastAndHandleErrors-failures:{}", failedTokens);
            log.info("sendMulticastAndHandleErrors-Responses:{}", gson.toJson(responseSub.getResponses()));
            responses = responseSub.getResponses();
            failedTokens.clear();
        }
    }



    @Override
    public void subscribeTopic(String topic, List<String> userRegistrationTokens){
        firebaseAppMap.get("Allo");
        if (firebaseApp == null) {
            firebaseApp =  initializeWithServiceAccount();
        }
        log.info("subscribeTopic-info:topic={},tokenList={}",topic,userRegistrationTokens);
        TopicManagementResponse response = null;
        try {
            response = FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(userRegistrationTokens, topic);
        } catch (FirebaseMessagingException e) {
            log.error("subscribeTopic-error={}",response.getSuccessCount(),e);
            throw new DCException(BusiStatus.NOTICE_MSG_FIREBASE_SUB_ERROE);
        }
        log.info("subscribeTopic-success={}",response.getSuccessCount());
    }

    @Override
    public void unsubscribeTopic(String topic, List<String> userRegistrationTokens){
        firebaseAppMap.get("Allo");
        if (firebaseApp == null) {
            firebaseApp =  initializeWithServiceAccount();
        }
        log.info("subscribeTopic-info:topic={},tokenList={}",topic,userRegistrationTokens);
        TopicManagementResponse response = null;
        try {
            response = FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(
                    userRegistrationTokens, topic);
        } catch (FirebaseMessagingException e) {
            log.error("unsubscribeTopic-error={}",response.getSuccessCount(),e);
            throw new DCException(BusiStatus.NOTICE_MSG_FIREBASE_UNSUB_ERROE);
        }
        if (response.getSuccessCount() == 0 ){
            log.info("unsubscribeTopic-success={},error={}",response.getSuccessCount(),response.getErrors());
        }else{
            log.info("unsubscribeTopic-success={}",response.getSuccessCount());
        }
    }


    @Override
    public String sendMsgTopic(String title,String body,String imageUrl,String topic, Map<String, String> data) throws FirebaseMessagingException{
        log.info("sendMsgTopic-info:topic={},title={},body={},imageUrl={},",topic,title,body,imageUrl,gson.toJson(data));
        // [START send_to_topic]
        firebaseAppMap.get("Allo");
        if (firebaseApp == null) {
            firebaseApp =  initializeWithServiceAccount();
        }
        //1.封装通知消息的内容:titel/body/image
        Notification.Builder noticeBuilder = Notification.builder()
                .setTitle(title)
                .setBody(body);
        if (StringUtils.isNotBlank(imageUrl)){
            noticeBuilder.setImage(imageUrl);
        }
        // The topic name can be optionally prefixed with "/topics/".
        Builder builder = Message.builder().setTopic(topic)
                            .setNotification(noticeBuilder.build())
                            .setAndroidConfig(AndroidConfig.builder()
                                        .setNotification(AndroidNotification.builder().setClickAction("MainActivity").build())
                                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder().setBadge(0).build())
                        .putHeader("mutable-content","1").build());
        for (String key : data.keySet()) {
            builder.putData(key, data.get(key));
        }
        Message message = builder.build();
        log.info("sendMsgTopic={},message={}",topic, gson.toJson(message));
        //3.发送消息-topic
        String  messageId = FirebaseMessaging.getInstance(firebaseApp).send(message);
        log.info("sendMsgTopic-Successfully:messageId={} ", messageId);
        return messageId;
    }

    @Override
    public void sendMsgMultiTopic(String title,String body,String imageUrl,String condition, Map<String, String> data) throws FirebaseMessagingException{
        firebaseAppMap.get("Allo");
        if (firebaseApp == null) {
            firebaseApp =  initializeWithServiceAccount();
        }
        //1.封装通知消息的内容:titel/body/image
        Notification.Builder noticeBuilder = Notification.builder()
                .setTitle(title)
                .setBody(body);
        if (StringUtils.isNotBlank(imageUrl)){
            noticeBuilder.setImage(imageUrl);
        }
        Builder builder = Message.builder().setCondition(condition).setNotification(noticeBuilder.build());
        for (String key : data.keySet()) {
            builder.putData(key, data.get(key));
        }
        Message message = builder.build();
        log.info("sendMsgMultiTopic={},message={}",condition, gson.toJson(message));
        String response = FirebaseMessaging.getInstance(firebaseApp).send(message);
        log.info("sendMsgMultiTopic-Successfully:messageId={} ", response);
    }



    /**
     * 组装Android设备消息
     * @return
     */
    public Message androidMessage(String title ,String body ,String icon ,String color ) {
        // [START android_message]
        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000) // 1 hour in milliseconds
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                                .setTitle("$GOOG up 1.43% on the day")
                                .setBody("$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
                                .setIcon("stock_ticker_update")
                                .setColor("#f45342")
                                .build())
                        .build())
                .build();
        // [END android_message]
        return message;
    }


    /**
     * 组装IOS设备消息
     * @return
     */
    public Message apnsMessage() {
        // [START apns_message]
        Message message = Message.builder()
                .setApnsConfig(ApnsConfig.builder()
                        .putHeader("apns-priority", "10")
                        .setAps(Aps.builder()
                                .setAlert(ApsAlert.builder()
                                        .setTitle("$GOOG up 1.43% on the day")
                                        .setBody("$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
                                        .build())
                                .setBadge(42)
                                .build())
                        .build())
                .setTopic("industry-tech")
                .build();
        // [END apns_message]
        return message;
    }



    /**
     * 组装全平台IOS和android消息
     * @return
     */
    public Message allPlatformsMessage(String token) {
        // [START multi_platforms_message]
        String title = "GOOG up 1.43% on the day";
        String body  = "GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.";
        Message message = Message.builder()
                //指定发送目标
                .setToken(token)
                //设置提醒
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .setImage("https://b.bdstatic.com/searchbox/icms/searchbox/img/lianghuibanner.jpg")
                        .build())
                //设置andriod设备
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setImage("https://b.bdstatic.com/searchbox/icms/searchbox/img/lianghuibanner.jpg")
                                .setColor("#f45342")
                                .build())
                        .build())
                //设置IOS设备
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setBadge(42)
                                .build())
                        .setFcmOptions(ApnsFcmOptions.builder()
                                .setImage("https://b.bdstatic.com/searchbox/icms/searchbox/img/lianghuibanner.jpg")
                                .build())
                        .build())
                //设置标题
                .setTopic("industry-tech")
                .build();
        // [END multi_platforms_message]
        return message;
    }

}
