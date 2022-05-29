package com.dc.allo.roomplay.service.pk.impl;

import com.dc.allo.common.domain.room.RoomGiftSendVo;
import com.dc.allo.roomplay.mapper.pk.RoomGiftMapper;
import com.dc.allo.roomplay.service.pk.RoomGiftService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: RoomGiftServiceImpl
 *
 * @date: 2020年04月30日 10:56
 * @author: qinrenchuan
 */
@Service
public class RoomGiftServiceImpl implements RoomGiftService {

    @Autowired
    private RoomGiftMapper roomGiftMapper;

    /**
     * 统计房间送礼对应的战力值
     * @param roomUids
     * @param startTime
     * @return java.util.List<com.dc.allo.common.domain.room.RoomGiftSendVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 10:56
     */
    @Override
    public List<RoomGiftSendVo> getRoomCombatPowerBySendGift(List<Long> roomUids, Date startTime) {
        return roomGiftMapper.getRoomCombatPowerBySendGift(roomUids, startTime);
    }
}
