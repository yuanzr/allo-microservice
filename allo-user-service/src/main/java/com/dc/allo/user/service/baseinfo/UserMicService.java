package com.dc.allo.user.service.baseinfo;

import com.dc.allo.common.domain.room.RoomMicPositionVo;
import java.util.Map;

/**
 * description: UserMicService
 * date: 2020年04月30日 18:41
 * author: qinrenchuan
 */
public interface UserMicService {

    /**
     * 取用户信息非实时的麦序列表-UserMicService.getRoomMicListNotRealTime
     * 缓存名格式要用RedisKeyUtil.getKey方法
     * @param roomUid
     * @return java.util.Map<java.lang.String,com.dc.allo.common.domain.room.RoomMicPositionVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 18:43
     */
    Map<String, RoomMicPositionVo> getRoomMicListNotRealTime(Long roomUid);
}
