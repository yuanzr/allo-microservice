package com.dc.allo.biznotice.controller;

import com.dc.allo.biznotice.service.firebase.FirebaseMessagingService;
import com.dc.allo.biznotice.service.firebase.FirebaseMsgService;
import com.dc.allo.biznotice.service.firebase.NoticeFirebaseTopicUserService;
import com.dc.allo.common.annotation.AutoPbTrans;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import com.erban.main.proto.PbCommon.PbHttpBizReq;
import com.erban.main.proto.PbHttpReq.PbRegisterFCMReq;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: NoticeFirebaseController
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/14:25
 */
@Slf4j
@RestController
@RequestMapping("/firebase")
public class NoticeFirebaseController  extends BaseController {

    @Autowired
    private FirebaseMsgService firebaseMsgService;
    @Autowired
    private NoticeFirebaseTopicUserService noticeFirebaseTopicUserService;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    //绑定注册token
    @RequestMapping("/pb/auth/token/bind")
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,
            clzFullName = "com.erban.main.proto.PbCommon$PbHttpBizResp",
            paramClassName = "com.erban.main.proto.PbHttpReq$PbRegisterFCMReq")
    public AlloResp bindFirebaseTokenPb(HttpServletRequest request) {
        // 公共请求参数
        PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        String uid = pbHttpBizReq.getUid();
        try {
            PbRegisterFCMReq reqParam = PbRegisterFCMReq.parseFrom(pbHttpBizReq.getData());
            String fcmToken = reqParam.getFcmToken();
            log.info("bindFirebaseToken-info,uid={},fcmToken={}",uid,fcmToken);
            if (StringUtils.isBlank(fcmToken)) {
                log.error("bindFirebaseToken-error,uid={},fcmToken={}",uid,fcmToken);
                return AlloResp.failed(BusiStatus.REQUEST_PARAM_ERROR);
            }
            //新增或更新用户token
            firebaseMsgService.bindFirebaseToken(Long.valueOf(uid),fcmToken);
        } catch (Exception e) {
            log.error("bindFirebaseToken-Exception,uid={}",uid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR.value(),e.getMessage(),null);
        }
        return AlloResp.success();
    }

    //绑定注册token
    @RequestMapping("/token/bind")
    public AlloResp bindFirebaseToken(Long uid, String fcmToken) {
        try {
            log.info("bindFirebaseToken-info,uid={},fcmToken={}",uid,fcmToken);
            if (StringUtils.isBlank(fcmToken)) {
                log.error("bindFirebaseToken-error,uid={},fcmToken={}",uid,fcmToken);
                return AlloResp.failed(BusiStatus.REQUEST_PARAM_ERROR);
            }
            setPoxy();
            //新增或更新用户token
            firebaseMsgService.bindFirebaseToken(uid,fcmToken);
        } catch (Exception e) {
            log.error("bindFirebaseToken-Exception,uid={}",uid);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }


    //解绑token
    @RequestMapping("/pb/auth/token/unbind")
    @AutoPbTrans(module = PbCommon.Module.httpModule_VALUE,
            clzFullName = "com.erban.main.proto.PbCommon$PbHttpBizResp",
            paramClassName = "com.erban.main.proto.PbHttpReq$PbUnRegisterFCMReq")
    public AlloResp unbindFirebaseTokenPb(HttpServletRequest request) {
        PbHttpBizReq pbHttpBizReq = getPbHttpBizReq(request);
        String uid = pbHttpBizReq.getUid();
        try {
            PbRegisterFCMReq reqParam = PbRegisterFCMReq.parseFrom(pbHttpBizReq.getData());
            String fcmToken = reqParam.getFcmToken();
            log.info("unbindFirebaseToken-info,uid={},fcmToken={}",uid,fcmToken);
            if (StringUtils.isBlank(fcmToken)) {
                log.error("unbindFirebaseToken-error,uid={},fcmToken={}",uid,fcmToken);
                return AlloResp.failed(BusiStatus.REQUEST_PARAM_ERROR);
            }
            //解绑Token
            firebaseMsgService.unbindFirebaseToken(Long.valueOf(uid),fcmToken);
        } catch (InvalidProtocolBufferException e) {
            log.error("unbindFirebaseToken-Exception,uid={}",uid);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    //解绑token
    @RequestMapping("/token/unbind")
    public AlloResp unbindFirebaseTokenPb(Long uid, String fcmToken) {
        try {
            log.info("unbindFirebaseToken-info,uid={},fcmToken={}",uid,fcmToken);
            if (StringUtils.isBlank(fcmToken)) {
                log.error("unbindFirebaseToken-error,uid={},fcmToken={}",uid,fcmToken);
                return AlloResp.failed(BusiStatus.REQUEST_PARAM_ERROR);
            }
            setPoxy();
            //解绑Token
            firebaseMsgService.unbindFirebaseToken(uid,fcmToken);
        } catch (Exception e) {
            log.error("unbindFirebaseToken-Exception,uid={}",uid);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    //解绑token
    @RequestMapping("/topic/bind")
    public AlloResp bindFirebaseTopic(Long uid,String topic, String fcmToken) {
        try {
            setPoxy();
            //榜单topic
            noticeFirebaseTopicUserService.subscribe(topic,fcmToken);
        } catch (Exception e) {
            log.error("unbindFirebaseToken-Exception,uid={}",uid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    //解绑token
    @RequestMapping("/topic/unbind")
    public AlloResp unbindFirebaseTopic(Long uid,String topic, String fcmToken) {
        try {
            setPoxy();
            //解绑topic
            noticeFirebaseTopicUserService.unsubscribe(topic,fcmToken);
        } catch (Exception e) {
            log.error("unbindFirebaseToken-Exception,uid={}",uid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    //解绑token
    @RequestMapping("/topic/msg/push")
    public AlloResp pushMsgTopicTest(String title,String body, String imageUrl, String topic) {
        try {
            Map<String, String> data = new HashMap<>();
            // "actionType" : "none"/"web"/"native"/"webAlert"
            data.put("actionType","native");
            //"action": ""
            data.put("action","allolike://Jump/Pager/av_room?roomUid=61004339");
            setPoxy();
            firebaseMessagingService.sendMsgTopic(title,body,imageUrl,topic,data);
        } catch (Exception e) {
            log.error("unbindFirebaseTokenPb-Exception",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }



    //解绑token
    @RequestMapping("/msg/push")
    public AlloResp unbindFirebaseTokenPb(String title,String body, String imageUrl, String tokens) {
        try {
            Map<String, String> data = new HashMap<>();
            // "actionType" : "none"/"web"/"native"/"webAlert"
            data.put("actionType","native");
            //"action": ""
            data.put("action","allolike://Jump/Pager/av_room?roomUid=61004339");
            setPoxy();
            firebaseMessagingService.sendMsgMulticast(title,body,imageUrl,data, Arrays.asList(tokens));
        } catch (Exception e) {
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }


    private void setPoxy(){
        //电脑可以翻墙,但是代码可是不可以翻墙的，需要用代码设置代理IP和端口
        //设置Java代理,端口号是代理软件开放的端口，这个很重要
//        System.setProperty("proxyHost", "localhost");
//        System.setProperty("proxyPort", "51724");
    }

}
