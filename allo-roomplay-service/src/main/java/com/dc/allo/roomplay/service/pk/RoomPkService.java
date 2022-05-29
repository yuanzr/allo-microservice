package com.dc.allo.roomplay.service.pk;

import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.common.kafka.message.GiftMessage;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.common.vo.RoomPkActivityUpLevelVo;
import com.dc.allo.roomplay.domain.vo.RoomPkWinVo;
import com.dc.allo.roomplay.redis.message.PkMessage;
import com.dc.allo.rpc.domain.roomplay.*;
import com.erban.main.proto.PbHttpReq;
import com.erban.main.proto.PbPush;

import java.util.List;
import java.util.Map;

public interface RoomPkService {
    PkInfoVo getPkInfo(Long roomUid) throws Exception;

    PbPkingRoomListVo getRoomPkingList(Long uid, PbHttpReq.PbRoomPkingListReq req);

    void sendWantPkMsg(Long roomUid, PbPush.PbUserWantPkPush invitePush);

    PkSquareVo getPkSquareData(PbHttpReq.PbPkSquareReq pbPkSquareReq);

    void checkChoosePkTimeInterval(Long uid,Long roomUid);

    RoomPkRecord chooseRoomPk(Long currentUid, Long roomUid, Long targetRoomUid, Byte chooseRoomPkUserType) throws Exception ;

    void sendChooseRoomPkMsg(Long targetRoomUid,RoomPkRecord roomPkRecord) throws Exception ;

    void setChoosePkTime(Long uid,Long roomUid);

    RoomPkRecord targetChooseAgree(PbHttpReq.PbPkTargetRoomChooseResultReq resultReq) throws Exception ;

    RoomPkRecord targetChooseReject(PbHttpReq.PbPkTargetRoomChooseResultReq resultReq) throws Exception ;

    void targetRoomChooseResultPush(RoomPkRecord record);

    List<RoomPkWinVo> getRoomPkMonthRank();

    List<RoomPkRecord> getRoomPkRecordList(Long roomUid, Byte type, Integer page, Integer pageSize);

    void cancelRoomPk(Long roomUid);

    void cancelRoomPk(RoomPkRecord record);

    PkInfoVo getPkInfoVo(Long roomUid, RoomPkRecord roomPkRecord, boolean isSendMsg) throws Exception;

    void handleGiftMessage(GiftMessage giftMessage) throws Exception;
    void handleRoomPkMsg(PkMessage pkMessage) throws Exception;
    void cancelExpireRoomPkInvite() throws Exception;
    void handlerExpireRoomPking() throws Exception;
    void updateTodayHeatValueRank() throws Exception;
    void setPkSquareAllRoom(Map<String, Double> uidAndScoreMap, List<RoomVo> roomList, List<Long> roomUids);
    void handlerHeatUpLevel(RoomPkActivityUpLevelVo levelVo);
    void handlerPkActivityRoomAward(List<RoomHeatValueTotay> list);
    boolean checkRoomPkActivityDate();
}
