package com.dc.allo.user.service.baseinfo.impl;

import com.dc.allo.common.domain.room.RoomMicPositionVo;
import com.dc.allo.user.service.baseinfo.UserMicService;
import com.dc.allo.user.service.baseinfo.cache.UserMicCache;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: UserMicServiceImpl
 *
 * @date: 2020年04月30日 18:42
 * @author: qinrenchuan
 */
@Service
public class UserMicServiceImpl implements UserMicService {

    @Autowired
    private UserMicCache userMicCache;

    /**
     * 取用户信息非实时的麦序列表-UserMicService.getRoomMicListNotRealTime
     * 缓存名格式要用RedisKeyUtil.getKey方法
     * @param roomUid
     * @return java.util.Map<java.lang.String,com.dc.allo.common.domain.room.RoomMicPositionVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 18:43
     */
    @Override
    public Map<String, RoomMicPositionVo> getRoomMicListNotRealTime(Long roomUid) {
        return userMicCache.queryAllRoomMicPositionVosByRoomUid(roomUid);
    }
}
