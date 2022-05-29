package com.dc.allo.roomplay.domain.vo;

import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import lombok.Data;

@Data
public class RoomPkEventVo {
    //type为1是取消Pk
    private byte type;
    private RoomPkRecord pkRecord;
    private String dataJson;
}
