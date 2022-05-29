package com.dc.allo.biznotice.controller;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.constant.Constant.MsgPushType;
import com.dc.allo.biznotice.constant.Constant.MsgToType;
import com.dc.allo.biznotice.model.firebase.NoticeMsgPushRecord;
import com.dc.allo.biznotice.service.firebase.FirebaseMsgService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: NoticeFirebaseAdminController
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/2/1:25
 */
@Slf4j
@RestController
@RequestMapping("/admin/firebase")
public class NoticeFirebaseAdminController {

    @Autowired
    private FirebaseMsgService firebaseMsgService;

    private Gson gson  = new Gson();

    /**
     * 测试推送
     * @param record
     * @return
     */
    @RequestMapping("/push/test")
    public AlloResp pushMsgTest(NoticeMsgPushRecord record) {
        String json = gson.toJson(record);
        try {
            log.info("pushMsgTest-info,,record={}",json);
            setPoxy();
            record.setPushType(MsgPushType.IMMEDIATELY);
            record.setToObjType(MsgToType.USERS);
            firebaseMsgService.messagePush(record);
        } catch (Exception e) {
            log.error("pushMsgTest-Exception,record={}",json,e);
            return AlloResp.failed(BusiStatus.SERVERERROR.value(),e.getMessage(), null);
        }
        return AlloResp.success();
    }



    /**
     * 测试推送
     * @param record
     * @return
     */
    @RequestMapping("/push/save")
    public AlloResp messagePushAdmin(NoticeMsgPushRecord record) {
        String json = gson.toJson(record);
        try {
            log.info("messagePushAdmin-info,,record={}",json);
            setPoxy();
            firebaseMsgService.messagePushAdmin(record);
        } catch (Exception e) {
            log.error("messagePushAdmin-Exception,record={}",json,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
        return AlloResp.success();
    }

    /**
     * 分页条件查询
     * @param startTimeStr  开始时间
     * @param endTimeStr   结束时间
     * @param page      页码
     * @param pageSize  页面大小
     * @return
     */
    @RequestMapping("/queryPage")
    public AlloResp queryPage(Long startTimeStr,Long endTimeStr,
                    @RequestParam(required = false, defaultValue = "1") Integer page,
                    @RequestParam(required = false, defaultValue = "20")Integer pageSize) {
        try {
            //Date starTime,Date endTime,Integer page,Integer PageSize
            Date starTime = null;
            Date endTime = null;
            if (startTimeStr!=null){
                starTime = new Date(startTimeStr);
            }
            if (endTimeStr!=null){
                endTime = new Date(endTimeStr);
            }
            JSONObject data = firebaseMsgService.queryPage(starTime, endTime, page, pageSize);
            return AlloResp.success(data);
        } catch (Exception e) {
            log.error("queryPage-Exception",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }


    /**
     * 扫描待发送的消息
     * @return
     */
    @RequestMapping("/sendDelayQueue")
    public AlloResp sendDelayQueue() {
        try {
            log.info("sendDelayQueue-delayQueueProvider");
            firebaseMsgService.delayQueueProvider();
            return AlloResp.success();
        } catch (Exception e) {
            log.error("sendDelayQueue-Exception",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    private void setPoxy(){
        //电脑可以翻墙,但是代码可是不可以翻墙的，需要用代码设置代理IP和端口
        //设置Java代理,端口号是代理软件开放的端口
//        System.setProperty("proxyHost", "localhost");
//        System.setProperty("proxyPort", "51724");
    }


}
