package com.dc.allo.user.service.baseinfo.cache;

import com.dc.allo.common.domain.room.RoomMicPositionVo;
import java.util.Map;

/**
 * description: UserMicCache
 * date: 2020年04月30日 18:44
 * author: qinrenchuan
 */
public interface UserMicCache {

    /**
     * 查询房间所有麦序
     * @param uid
     * @return java.util.Map<java.lang.String,com.dc.allo.common.domain.room.RoomMicPositionVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 18:56
     */
    Map<String,RoomMicPositionVo> queryAllRoomMicPositionVosByRoomUid(Long uid);
}
