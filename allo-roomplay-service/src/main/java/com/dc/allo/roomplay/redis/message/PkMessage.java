package com.dc.allo.roomplay.redis.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class PkMessage implements Serializable {
    private static final long serialVersionUID = 6454707251727767400L;
    private Byte      pkType;   // 业务类型:4.pk结束 data为pk记录Id
    private Long pkRecordId;
    //发起pk房主Uid
    private Long roomUid;
    //接受Pk房主Uid
    private Long targetRoomUid;
}
