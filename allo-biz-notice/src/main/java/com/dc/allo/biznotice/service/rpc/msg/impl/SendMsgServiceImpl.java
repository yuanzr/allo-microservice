package com.dc.allo.biznotice.service.rpc.msg.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.model.im.IMResponse;
import com.dc.allo.biznotice.service.msg.RongCloudMessageService;
import com.dc.allo.biznotice.service.msg.impl.RongCloudBaseService;
import com.dc.allo.common.constants.Attach;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.RongCloudConstant;
import com.dc.allo.common.constants.UUIDUitl;
import com.dc.allo.rpc.api.msg.SendMsgService;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@Slf4j
public class SendMsgServiceImpl extends RongCloudBaseService implements SendMsgService {
    @Autowired
    private RongCloudMessageService messageService;

    @Override
    public AlloResp<Integer> sendSendChatRoomMsg(Long roomUid, String fromAccid, int first, int second, Object data) throws Exception {
        return sendSendChatRoomMsg(roomUid,fromAccid,first,second,data, RongCloudConstant.RongCloundMessageObjectName.ROOM_TEXT);
    }

    @Override
    public AlloResp<Integer> sendSendChatRoomMsg(Long roomUid, String fromAccid, String attachStr, String objectName) throws Exception {
        AlloResp alloResp = new AlloResp();
        IMResponse response = this.messageService.sendChatroomMessage(String.valueOf(roomUid), fromAccid, attachStr, objectName);
        if(response == null || response.getCode()!=200){
            alloResp.setCode(response == null ? BusiStatus.SERVERERROR.value() : response.getCode());
            alloResp.setMessage("failed_to_send_chat_room_message");
            return alloResp;
        }
        alloResp.setCode(BusiStatus.SUCCESS.value());
        alloResp.setMessage(BusiStatus.SUCCESS.getReasonPhrase());
        alloResp.setData(1);
        return alloResp;
    }

    @Override
    public AlloResp<Integer> sendSendChatRoomMsg(Long roomUid, String fromAccid, int first, int second, Object data, String objectName) throws Exception {
        AlloResp alloResp = new AlloResp();

        Attach attach = new Attach();
        attach.setFirst(first);
        attach.setSecond(second);
        attach.setData(data);
        String attachStr = JSONObject.toJSONString(attach);
        String msgId = UUIDUitl.get();

        IMResponse response = this.messageService.sendChatroomMessage(String.valueOf(roomUid), fromAccid, attachStr, objectName);
        if (response == null || response.getCode() != 200) {
            alloResp.setCode(response == null ? BusiStatus.SERVERERROR.value() : response.getCode());
            alloResp.setMessage("failed_to_send_chat_room_message");
            return alloResp;
        }
        alloResp.setCode(BusiStatus.SUCCESS.value());
        alloResp.setMessage(BusiStatus.SUCCESS.getReasonPhrase());
        alloResp.setData(1);
        return alloResp;
    }

    @Override
    public AlloResp<Integer> sendPrivateMessage(String senderId, List<String> targetUidList, String objectName, String body, String pushContent, String payload) throws Exception {
        AlloResp alloResp = new AlloResp();
        IMResponse response = this.messageService.sendPrivateMessage(senderId,targetUidList,objectName,body,pushContent,payload);
        if (response == null || response.getCode() != 200) {
            alloResp.setCode(response == null ? BusiStatus.SERVERERROR.value() : response.getCode());
            return alloResp;
        }
        alloResp.setCode(BusiStatus.SUCCESS.value());
        alloResp.setMessage(BusiStatus.SUCCESS.getReasonPhrase());
        alloResp.setData(1);
        return alloResp;
    }

    @Override
    public AlloResp<Integer> sendPrivateStatusMessage(String senderId, List<String> targetUidList, String objectName, String body) throws Exception {
        AlloResp alloResp = new AlloResp();
        IMResponse response = this.messageService.sendPrivateStatusMessage(senderId,targetUidList,objectName,body);
        if (response == null || response.getCode() != 200) {
            alloResp.setCode(response == null ? BusiStatus.SERVERERROR.value() : response.getCode());
            return alloResp;
        }
        alloResp.setCode(BusiStatus.SUCCESS.value());
        alloResp.setMessage(BusiStatus.SUCCESS.getReasonPhrase());
        alloResp.setData(1);
        return alloResp;
    }
}
