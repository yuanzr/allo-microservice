package com.dc.allo.biznotice.service.firebase;

import java.util.List;


public interface NoticeFirebaseTopicUserService {

    /**
     * 订阅语言区topic
     * @param uid
     * @return
     */
    void subscribeRegion(Long uid ,String newtoken,String oldToken,Boolean isInsert);

    /**
     * 订阅topic
     * @param topic
     * @return
     */
    int subscribe(String topic,String token);

    /**
     * 订阅topic
     * @param uid
     * @param topic
     * @return
     */
    int subscribe(Long uid,String topic,String token);

    /**
     * 退订语言区topic
     * @param uid
     * @return
     */
    int unsubscribeRegion(Long uid,String token);

    /**
     * 退订语言区topic
     * @param uid
     * @return
     */
    int unsubscribeRegion(Long uid,String token,List<String> topicList);

    /**
     * 退订topic
     * @param topic
     * @return
     */
    int unsubscribe(String topic,String token);

}
