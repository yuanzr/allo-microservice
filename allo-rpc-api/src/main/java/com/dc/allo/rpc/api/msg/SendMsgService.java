package com.dc.allo.rpc.api.msg;

import com.dc.allo.rpc.proto.AlloResp;

import java.util.List;


public interface SendMsgService {
    AlloResp<Integer> sendSendChatRoomMsg(Long roomUid, String fromAccid, int first, int second, Object data) throws Exception;
    AlloResp<Integer> sendSendChatRoomMsg(Long roomUid, String fromAccid, String attachStr, String objectName) throws Exception;
    AlloResp<Integer> sendSendChatRoomMsg(Long roomUid, String fromAccid, int first, int second, Object data, String objectName) throws Exception;
    AlloResp<Integer> sendPrivateMessage(String senderId, List<String> targetUidList, String objectName, String body, String pushContent, String payload) throws Exception;
    AlloResp<Integer> sendPrivateStatusMessage(String senderId, List<String> targetUidList, String objectName, String body) throws Exception;
}
