package com.dc.allo.roomplay.mapper.pk;

import com.dc.allo.common.domain.room.RoomGiftSendVo;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * description: RoomGiftMapper
 * date: 2020年04月30日 10:59
 * author: qinrenchuan
 */
@Mapper
public interface RoomGiftMapper {

    @Select({"<script>"
            +"SELECT "
                + "         room_uid roomUid, "
                + "         sum(total_gold_num) totalGoldNum "
            + "      FROM "
                + "         gift_send_record "
            + "      WHERE "
                + "         room_uid in "
                + "              <foreach item='roomUid' index='index' collection='roomUids' open='(' separator=',' close=')'> "
                + "                #{roomUid} "
                + "              </foreach> "
                + "           AND create_time >=#{startTime} "
                + "         GROUP BY room_uid"
    + "</script>"})
    List<RoomGiftSendVo> getRoomCombatPowerBySendGift(
            @Param("roomUids") List<Long> roomUids,
            @Param("startTime") Date startTime);
}
