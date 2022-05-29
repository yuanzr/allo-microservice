package com.dc.allo.rpc.domain.roomplay;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PbPkingRoomListVo {
    private List<PbPkingRoomVo> pkingRoomVoList;
    private Long zone;
}
