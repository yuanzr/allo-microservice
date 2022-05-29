package com.dc.allo.common.domain.room;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomMicPositionVo implements Serializable{
    private Integer position;
    private Integer posiStat;
    private Integer micStat;
    private Integer micType;
    //麦序的过期时间(上次心跳的时间+设定的心跳过期时间)
    //麦序没用户上麦的时候，expire为空,但是有可能被锁麦或者被静音
    private Long expireTime;
    //存在mongodb的麦序心跳id
    private String id;
    private RoomMicPositionUserVoExpand chatroomMember;
}
