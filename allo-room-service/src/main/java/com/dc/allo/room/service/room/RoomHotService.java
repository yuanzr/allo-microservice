package com.dc.allo.room.service.room;

import com.dc.allo.room.domain.room.RoomHot;

/**
 * description: RoomHotService
 *
 * @date: 2020年04月30日 15:24
 * @author: qinrenchuan
 */
public interface RoomHotService {
    /**
     *
     * @param roomUid
     * @return boolean
     * @author qinrenchuan
     * @date 2020/4/30/0030 15:51
     */
    boolean isHotRoomByUid(Long roomUid);

    /**
     * 根据roomUid查询热门房间
     * @param roomUid
     * @return com.dc.allo.room.domain.room.RoomHot
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:04
     */
    RoomHot getByRoomUid(Long roomUid);
}
