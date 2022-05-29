package com.dc.allo.common.domain.room;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomMicPositionUserVoExpand implements Serializable {
	private RoomMicPositionUserVo userinfo;
	//用户在房间的成员角色
	private Byte role;
}
