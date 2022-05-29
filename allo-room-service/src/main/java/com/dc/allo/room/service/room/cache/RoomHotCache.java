package com.dc.allo.room.service.room.cache;

import com.dc.allo.room.domain.room.RoomHot;

/**
 * description: RoomHotCache
 * date: 2020年04月30日 16:17
 * author: qinrenchuan
 */
public interface RoomHotCache {
    /**
     * 根据roomUid查询热门房间
     * @param roomUid
     * @return com.dc.allo.room.domain.room.RoomHot
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:04
     */
    RoomHot getByRoomUid(Long roomUid);

    /**
     * 设置热门房间缓存
     * @param roomHot
     * @return void
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:27
     */
    void setRoomHotCache(RoomHot roomHot);
}
