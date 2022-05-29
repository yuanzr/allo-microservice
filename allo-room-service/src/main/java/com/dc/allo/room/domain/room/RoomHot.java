package com.dc.allo.room.domain.room;

import java.util.Date;
import lombok.Data;

/**
 * description: RoomHot
 *
 * @date: 2020年04月30日 16:01
 * @author: qinrenchuan
 */
@Data
public class RoomHot {
    private Long uid;
    private Integer roomSeq;
    private Date startLiveTime;
    private Date endLiveTime;
}
