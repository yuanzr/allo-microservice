package com.dc.allo.room.service.room.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.room.domain.room.RoomHot;
import com.dc.allo.room.service.room.cache.RoomHotCache;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: RoomHotCacheImpl
 *
 * @date: 2020年04月30日 16:17
 * @author: qinrenchuan
 */
@Service
public class RoomHotCacheImpl implements RoomHotCache {
    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.Room;
    /** 热门房间key */
    private static final String roomHotKey = "room_hot";

    /**
     * 根据roomUid查询热门房间
     * @param roomUid
     * @return com.dc.allo.room.domain.room.RoomHot
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:04
     */
    @Override
    public RoomHot getByRoomUid(Long roomUid) {
        Object roomHotObj = redisUtil.hGet(RedisKeyUtil.getKey(roomHotKey), roomUid.toString());
        if (roomHotObj == null || StringUtils.isBlank(roomHotObj.toString())) {
            return null;
        }

        return new Gson().fromJson(roomHotObj.toString(), RoomHot.class);
    }

    /**
     * 设置热门房间缓存
     * @param roomHot
     * @return void
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:27
     */
    @Override
    public void setRoomHotCache(RoomHot roomHot) {
        redisUtil.hPut(RedisKeyUtil.getKey(roomHotKey),
                roomHot.getUid().toString(),
                new Gson().toJson(roomHot));
    }
}
