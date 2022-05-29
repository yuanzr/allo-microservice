package com.dc.allo.common.domain.room;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * mybatis generate 
 * @author Administrator 
 * 表名 room_gameroomserveice
 */
@Data
public class RoomGame implements Serializable {
    /**
     * room_game
     */
    private static final long serialVersionUID = 1L;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 房主uid
     */
    private Long uid;

    /**
     * 游戏发起人
     */
    private Long startUid;

    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 游戏名
     */
    private String gameName;

    /**
     * 游戏状态 1-无状态 | 2-选择中 | 3-准备中 | 4-已准备 | 5-游戏中
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}