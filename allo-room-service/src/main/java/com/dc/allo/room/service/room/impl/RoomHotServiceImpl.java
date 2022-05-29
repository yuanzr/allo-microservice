package com.dc.allo.room.service.room.impl;

import com.dc.allo.room.domain.room.RoomHot;
import com.dc.allo.room.mapper.room.RoomHotMapper;
import com.dc.allo.room.service.room.RoomHotService;
import com.dc.allo.room.service.room.cache.RoomHotCache;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: RoomHotServiceImpl
 *
 * @date: 2020年04月30日 15:50
 * @author: qinrenchuan
 */

@Service
public class RoomHotServiceImpl implements RoomHotService {

    @Autowired
    private RoomHotCache roomHotCache;

    @Autowired
    private RoomHotMapper roomHotMapper;

    /**
     * 根据roomUid查询热门房间
     * @param roomUid
     * @return com.dc.allo.room.domain.room.RoomHot
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:04
     */
    @Override
    public RoomHot getByRoomUid(Long roomUid) {
        RoomHot roomHot = roomHotCache.getByRoomUid(roomUid);
        if (roomHot == null) {
            roomHot = roomHotMapper.getByRoomUid(roomUid);
            if (roomHot != null) {
                roomHotCache.setRoomHotCache(roomHot);
            }
        }
        return roomHot;
    }

    /**
     *
     * @param roomUid
     * @return boolean
     * @author qinrenchuan
     * @date 2020/4/30/0030 15:51
     */
    @Override
    public boolean isHotRoomByUid(Long roomUid) {
        RoomHot roomHot = getByRoomUid(roomUid);
        if (roomHot == null) {
            return false;
        }
        long startTime = formateDate(roomHot.getStartLiveTime());
        long endTime = formateDate(roomHot.getEndLiveTime());
        long nowTime = formateDate(new Date());
        if (startTime > nowTime || endTime < nowTime) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 日期转换
     * @param date
     * @return long
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:32
     */
    private long formateDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String format = sdf.format(date);
        try {
            return sdf.parse(format).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
