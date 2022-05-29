package com.dc.allo.room.domain.room;

import lombok.Data;

/**
 * description: RunningRoomVo
 *
 * @date: 2020年04月30日 14:36
 * @author: qinrenchuan
 */
@Data
public class RunningRoomVo {
    private Long uid;
    private int onlineNum;
    private Long roomId;
    private Byte type;
    /** 综合人气 + 流水值 */
    private int calcSumDataIndex;
    /** 计数器，用于指示某段时间内一直没人 */
    private int count;
}
