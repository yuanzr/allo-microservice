package com.dc.allo.biznotice.service.firebase.impl;

import com.dc.allo.biznotice.constant.Constant.FirebaseTopicRegion;
import com.dc.allo.biznotice.mapper.firebase.NoticeFirebaseTopicUserMapper;
import com.dc.allo.biznotice.service.firebase.FirebaseMessagingService;
import com.dc.allo.biznotice.service.firebase.NoticeFirebaseTopicUserService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: NoticeFirebaseTopicUserServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/26/12:09
 */
@Slf4j
@Service
public class NoticeFirebaseTopicUserServiceImpl implements NoticeFirebaseTopicUserService {

    @Reference
    private DcUserInfoService dcUserInfoService;

    @Autowired
    private NoticeFirebaseTopicUserMapper noticeFirebaseTopicUserMapper;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void subscribeRegion(Long uid ,String newtoken,String oldToken,Boolean isInsert) {
        //1.查询用户所属语言区
        AlloResp<UserInfo> result = dcUserInfoService.getRegionByUid(uid);
        if(!result.isSuccess() || result.getData() == null){
            //异常,用户不存在
            log.error("subscribeRegion-error-user:uid={},newtoken={},oldToken={},isInsert={}",
                    uid , newtoken, oldToken, isInsert);
            throw new DCException(BusiStatus.USERINFONOTEXISTS);
        }
        String region = result.getData().getRegion();
        //2.新增/更新
        if (isInsert){
            //2.1 订阅region和all
            subscribe(uid,region,newtoken);
            subscribe(uid, FirebaseTopicRegion.ALL,newtoken);
        }else{
            //2.2.1 查询当前所订阅的语言区
            List<String> regionTopicList = noticeFirebaseTopicUserMapper.selectRegionTopicByUid(uid);
            //2.2.2 判断是否需要更新语言区topic
            if (regionTopicList.contains(region) && newtoken.equals(oldToken)){
                //token和region一致,不更新
                return;
            }
            //2.2.3 使用旧的token退订DB/firebase
            unsubscribeRegion(uid,oldToken,regionTopicList);
            //2.2.4 使用新token在DB/firebase 重新订阅region和all
            subscribe(uid,region,newtoken);
            subscribe(uid,FirebaseTopicRegion.ALL,newtoken);
        }
    }


    @Override
    public int subscribe(Long uid, String region,String token) {
        String topic = "tr".equals(region)?"en":region;
        //1.DB插入
        noticeFirebaseTopicUserMapper.insert(uid,topic);
        //2.在firebase的订阅
        firebaseMessagingService.subscribeTopic(topic, Arrays.asList(token));
        return 200;
    }

    @Override
    public int subscribe(String region,String token) {
        String topic = "tr".equals(region)?"en":region;
        //2.在firebase的订阅
        firebaseMessagingService.subscribeTopic(topic, Arrays.asList(token));
        return 200;
    }

    @Override
    public int unsubscribeRegion(Long uid,String token) {
        List<String> topicList =noticeFirebaseTopicUserMapper.selectRegionTopicByUid(uid);
        unsubscribeRegion(uid,token,topicList);
        return 0;
    }

    @Override
    public int unsubscribeRegion(Long uid,String token,List<String> topicList) {
        if (CollectionUtils.isNotEmpty(topicList)){
            topicList.forEach(topic->{
                unsubscribe(topic,token);
            });
            noticeFirebaseTopicUserMapper.deleteAllLang(uid);
        }
        return 0;
    }

    @Override
    public int unsubscribe(String topic,String token) {
        //取消在firebase的订阅
        firebaseMessagingService.unsubscribeTopic(topic, Arrays.asList(token));
        return 0;
    }
}
