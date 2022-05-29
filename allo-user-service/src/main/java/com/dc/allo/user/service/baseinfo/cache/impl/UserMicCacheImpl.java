package com.dc.allo.user.service.baseinfo.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.domain.room.RoomMicPositionVo;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.user.service.baseinfo.cache.UserMicCache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: UserMicCacheImpl
 *
 * @date: 2020年04月30日 18:44
 * @author: qinrenchuan
 */
@Service
public class UserMicCacheImpl implements UserMicCache {

    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.User;
    /** mic_posit */
    private static final String micPosit = "mic_posit";


    /**
     * 查询房间所有麦序
     * @param roomUid
     * @return java.util.List<com.dc.allo.common.domain.room.RoomMicPositionVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 18:47
     */
    @Override
    public Map<String,RoomMicPositionVo> queryAllRoomMicPositionVosByRoomUid(Long roomUid) {
        Map<String,RoomMicPositionVo> roomMicPositionVoMap = new HashMap<String,RoomMicPositionVo>();

        Map<Object, Object> objectMap = redisUtil.hGetAll(RedisKeyUtil.getKey(micPosit, roomUid.toString()));
        if (objectMap != null && objectMap.size() > 0) {
            Set<Entry<Object, Object>> entries = objectMap.entrySet();

            RoomMicPositionVo roomMicPositionVo = null;
            Gson gson = new Gson();
            for (Entry<Object, Object> entry : entries) {
                Object key = entry.getKey();
                Object value = entry.getValue();

                if (value != null && StringUtils.isNotBlank(value.toString())) {
                    roomMicPositionVo = gson.fromJson(value.toString(), new TypeToken<RoomMicPositionVo>(){}.getType());
                    if(roomMicPositionVo != null
                            && roomMicPositionVo.getExpireTime() != null
                            &&  roomMicPositionVo.getExpireTime() < System.currentTimeMillis()){
                        continue;
                    }
                    roomMicPositionVoMap.put(key.toString(), roomMicPositionVo);
                }
            }
        }
        return roomMicPositionVoMap;
    }
}
