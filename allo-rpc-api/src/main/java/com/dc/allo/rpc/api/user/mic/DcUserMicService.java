package com.dc.allo.rpc.api.user.mic;

import com.dc.allo.common.domain.room.RoomMicPositionVo;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.Map;

public interface DcUserMicService {
    /**
     * 获取用户信息非实时的麦序列表-UserMicService.getRoomMicListNotRealTime
     * 缓存名格式要用RedisKeyUtil.getKey方法
     */
    AlloResp<Map<String, RoomMicPositionVo>> getRoomMicListNotRealTime(Long roomUid);
}
