package com.dc.allo.rpc.domain.room;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class RoomMember implements Serializable {
    private static final long serialVersionUID = 1415011120511883871L;
    private Long memRecordId;

    private Long roomUid;

    private Long memberUid;

    private Byte memberStatus;

    private Byte memberRole;

    private Date applyTime;

    private Date joinTime;

    private Date updateTime;
}
