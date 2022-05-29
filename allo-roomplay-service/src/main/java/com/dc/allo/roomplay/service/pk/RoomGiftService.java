package com.dc.allo.roomplay.service.pk;

import com.dc.allo.common.domain.room.RoomGiftSendVo;
import java.util.Date;
import java.util.List;

/**
 * description: RoomGiftService
 * date: 2020年04月30日 10:55
 * author: qinrenchuan
 */
public interface RoomGiftService {
    /**
     * 统计房间送礼对应的战力值.  注意： gift_send_record分表了，暂时放在PK里面查询
     * @param roomUids
     * @param startTime
     * @return java.util.List<com.dc.allo.common.domain.room.RoomGiftSendVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 10:56
     */
    List<RoomGiftSendVo> getRoomCombatPowerBySendGift(List<Long> roomUids, Date startTime);
}
