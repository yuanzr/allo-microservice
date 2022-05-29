package com.dc.allo.biznotice.service.firebase.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.constant.Constant;
import com.dc.allo.biznotice.constant.Constant.ActionType;
import com.dc.allo.biznotice.constant.Constant.FirebaseTopicRegion;
import com.dc.allo.biznotice.constant.Constant.MsgPushStatusType;
import com.dc.allo.biznotice.constant.Constant.MsgPushType;
import com.dc.allo.biznotice.constant.Constant.MsgToType;
import com.dc.allo.biznotice.constant.Constant.MsgType;
import com.dc.allo.biznotice.model.firebase.NoticeFirebaseTokenUser;
import com.dc.allo.biznotice.model.firebase.NoticeMsgPushRecord;
import com.dc.allo.biznotice.delay.message.FirebaseMessage;
import com.dc.allo.biznotice.service.firebase.FirebaseMessagingService;
import com.dc.allo.biznotice.service.firebase.FirebaseMsgService;
import com.dc.allo.biznotice.service.firebase.NoticeFirebaseTokenUserService;
import com.dc.allo.biznotice.service.firebase.NoticeFirebaseTopicUserService;
import com.dc.allo.biznotice.service.firebase.NoticeMsgPushRecordService;
import com.dc.allo.common.component.delay.redis.DelayMessage;
import com.dc.allo.common.component.delay.redis.RedisDelayQueue;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.proto.AlloResp;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName: FirebaseMsgServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/15:29
 */
@Slf4j
@Service
public class FirebaseMsgServiceImpl implements FirebaseMsgService {

    @Autowired
    private NoticeFirebaseTokenUserService noticeFirebaseTokenUserService;
    @Autowired
    private NoticeFirebaseTopicUserService noticeFirebaseTopicUserService;
    @Autowired
    private NoticeMsgPushRecordService noticeMsgPushRecordService;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    @Autowired
    @Qualifier("fireBaseMsgDelayQueue")
    private RedisDelayQueue redisDelayQueue;
    @Reference
    private DcUserInfoService dcUserInfoService;

    private Gson gson = new Gson();


    @Async
    @Override
    public void bindFirebaseToken(Long uid, String token) {
        NoticeFirebaseTokenUser tokenUser = noticeFirebaseTokenUserService.get(uid);
        Boolean isInsert = true;
        if (tokenUser == null){
            //1.插入token
            noticeFirebaseTokenUserService.saveOrUpdate(uid, token);
        }else {
            isInsert = false;
            Boolean isSameToken = tokenUser.getRegisterToken().equals(token);
            if (!isSameToken){
                //相同不更新,不同更新token
                noticeFirebaseTokenUserService.saveOrUpdate(uid, token);
            }
        }
        //2.订阅语言区topic
        noticeFirebaseTopicUserService.subscribeRegion(uid,token,token,isInsert);
    }

    @Async
    @Override
    public void unbindFirebaseToken(Long uid, String token) {
        //1.直接从DB删除用户绑定的token
        int deleteResult = noticeFirebaseTokenUserService.delete(uid,token);
        if (deleteResult > 0){
            log.info("unbindFirebaseToken:uid={},token={}",uid,token);
            noticeFirebaseTopicUserService.unsubscribeRegion(uid, token);
        }
    }

    @Override
    public String messagePush(NoticeMsgPushRecord record) throws FirebaseMessagingException, IOException {
        //1.转化action
        Map<String, String> data = new HashMap<String, String>();
        data.put("actionType",record.getActionType());
        if (ActionType.NATIVE.equals(record.getActionType())){
            String rounte = Constant.ActionMap.get(record.getRouteType().intValue());
            String[] params = record.getAction().split(",");
            String action = MessageFormat.format(rounte, params);
            data.put("action",action);
        }else if (ActionType.WEB.equals(record.getActionType())){
            data.put("action",record.getAction());
        }else if (ActionType.WEB_ALERT.equals(record.getActionType())){

        }else {
            //默认不跳转
            data.put("action",ActionType.NONE);
        }

        //2.判断消息发送对象
        log.info("messagePush:record={}",gson.toJson(record));
        String title    = record.getTitle();
        String body     = record.getBody();
        String imageUrl = record.getImageUrl();
        String messageId = null;
        if (MsgToType.USERS.equals(record.getToObjType())){
            //2.1.1根据ID批量查询出用户uid
            if (StringUtils.isBlank(record.getToErbanNos())){
                throw new DCException(BusiStatus.USERINFONOTEXISTS);
            }
            String[] erbanNoArr = record.getToErbanNos().split(",");
            if (erbanNoArr.length < 1){
                throw new DCException(BusiStatus.USERINFONOTEXISTS);
            }

            List<String> erbanNoStr = Arrays.asList(erbanNoArr);
            List<Long>  erbanNoList = new ArrayList<>();
            CollectionUtils.collect(erbanNoStr, new Transformer() {
                @Override
                public Object transform(Object o) {
                    return Long.valueOf(o.toString());
                }
            }, erbanNoList);
            AlloResp<Map<Long, Long>> mapAlloResp = dcUserInfoService.queryErbanNo2UidMapByErbanNos(erbanNoList);
            List<Long> uidList = new ArrayList(mapAlloResp.getData().values());
            if (CollectionUtils.isEmpty(uidList)){
                throw new DCException(BusiStatus.USERINFONOTEXISTS);
            }
            log.info("messagePush-userList:erbanNoArry={},userList={}",gson.toJson(erbanNoStr),gson.toJson(mapAlloResp));
            //2.1.2 根据uid列表查询出用户注册token
            List<String> tokenList = noticeFirebaseTokenUserService.queryToken(uidList);
            if (CollectionUtils.isEmpty(tokenList)){
                throw new DCException(BusiStatus.NOTICE_MSG_FIREBASE_USER_NOT_REGIST);
            }
            //2.1.3 循环遍历发送广播消息(500一页)
            int limitSize = 500;
            if (tokenList.size()>limitSize){
                int insertRound = tokenList.size() / limitSize;
                for (int i = 1; i <= insertRound+1; i++) {
                    Integer size = tokenList.size();
                    Integer skip = (i - 1) * limitSize;
                    if (skip >= size) {
                        continue;
                    }
                    if (skip + limitSize > size) {
                        List<String> recordList = tokenList.subList(skip, tokenList.size());
                        firebaseMessagingService.sendMsgMulticast(title,body,imageUrl,data,recordList);
                        continue;
                    }
                    List<String> recordList = tokenList.subList(skip, tokenList.size());
                    firebaseMessagingService.sendMsgMulticast(title,body,imageUrl,data,recordList);
                }
            }else {
                firebaseMessagingService.sendMsgMulticast(title,body,imageUrl,data,tokenList);
            }
            messageId ="multi";
        }else if(MsgToType.TOPIC.equals(record.getToObjType())){
             messageId = firebaseMessagingService.sendMsgTopic(title, body, imageUrl, record.getRegion(), data);
        }
        return messageId;
    }

    @Override
    public void messagePushAdmin(NoticeMsgPushRecord record) throws FirebaseMessagingException, IOException {
        if (MsgType.TEXT.equals(record.getMsgType())){
            record.setImageUrl("");
        }
        if (MsgToType.USERS.equals(record.getToObjType())){
            record.setRegion("");
        }
        if (MsgToType.TOPIC.equals(record.getToObjType())){
            record.setToErbanNos("");
        }
        if (MsgPushType.IMMEDIATELY.equals(record.getPushType())){
            //立即推送
            //1.1推送消息
            String messgeId = messagePush(record);
            record.setMessageId(messgeId);
            record.setPushStatus(MsgPushStatusType.PUSHED);
            record.setPushTime(new Date());
            //1.2插入记录
            noticeMsgPushRecordService.saveOrUpdate(record);
        }else if (MsgPushType.TIMING.equals(record.getPushType())){
            //定时推送
            //2.1判断是否满足立即投递条件
            Date currentTime = Calendar.getInstance().getTime();
            Date futureTime = DateTimeUtil.addMinute(currentTime, 5);
            if (record.getPushTime().getTime() >= currentTime.getTime()
                    && record.getPushTime().getTime() <= futureTime.getTime()){
                //2.2.1 保存记录-状态(已投递)
                record.setPushStatus(MsgPushStatusType.PEDNING);
                //2.2.2只插入记录
                noticeMsgPushRecordService.saveOrUpdate(record);
                //2.2.3 投递延迟消息
                sendDelayMessage(record);
            }else{
                //2.3 状态(未推送)
                record.setPushStatus(MsgPushStatusType.UNPUSH);
                noticeMsgPushRecordService.saveOrUpdate(record);
            }

        }
    }

    @Override
    public JSONObject queryPage(Date starTime, Date endTime, Integer page, Integer pageSize) {
        List<NoticeMsgPushRecord> list = noticeMsgPushRecordService.queryPage(starTime, endTime, page, pageSize);
        int count = noticeMsgPushRecordService.queryPageCount(starTime, endTime);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("rows", list);
        return jsonObject;
    }

    @Override
    public void delayQueueProvider() {
        //1.查询出待发送消息
        List<NoticeMsgPushRecord> pendMsgList = noticeMsgPushRecordService.queryPendingRecord();
        log.info("sendPendingMsgToDelayQueue-list:size={}",pendMsgList.size());
        //2.发送消息
        if (CollectionUtils.isEmpty(pendMsgList)){
            return;
        }
        ArrayList<Long> sendMsgList = new ArrayList<>();
        pendMsgList.forEach(msg->{
            Long recordId = sendDelayMessage(msg);
            if (recordId != null){
                sendMsgList.add(recordId);
            }
        });
        //3.更新成功投递延迟消息的记录为已投递
        if (CollectionUtils.isEmpty(sendMsgList)){
            log.error("sendPendingMsgToDelayQueue-send:pendsize={}",pendMsgList.size());
            return;
        }
        noticeMsgPushRecordService.updateStatusBatch(sendMsgList,MsgPushStatusType.PEDNING);
    }

    @Override
    public void delayQueueConsumer(NoticeMsgPushRecord record) throws FirebaseMessagingException, IOException {
        String json = gson.toJson(record);
        //1.发送firebase消息
        log.info("delayQueueConsumer-push:record={}",json);
        String messgeId = messagePush(record);
        record.setMessageId(messgeId);
        //2.更新消息状态
        log.info("delayQueueConsumer-update:record={}",json);
        noticeMsgPushRecordService.updateStatus(record.getRecordId(), MsgPushStatusType.PUSHED,messgeId);
    }

    /**
     * 发送延迟队列消息
     * @param record
     * @return
     */
    private Long sendDelayMessage(NoticeMsgPushRecord record) {
        FirebaseMessage message = new FirebaseMessage();
        BeanUtils.copyProperties(record,message);
        if (record.getPushTime()==null){
           return null;
        }
        message.setPushTimeTemp(record.getPushTime().getTime());
        //封装消息
        DelayMessage<FirebaseMessage> delayMessage = new DelayMessage<>();
        delayMessage.setData(message);
        delayMessage.setDuplicateKey(UUID.randomUUID().toString());
        //时间差
        delayMessage.setExpireTime(record.getPushTime().getTime());
        try {
            log.info("sendDelayMessage,delayMessage={}",gson.toJson(delayMessage));
            redisDelayQueue.enQueue(delayMessage);
            return record.getRecordId();
        } catch (Exception e) {
            log.error("sendDelayMessage,record={}",gson.toJson(record));
            return null;
        }
    }


}
