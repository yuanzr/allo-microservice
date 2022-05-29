package com.dc.allo.common.domain.room;


import com.dc.allo.common.domain.user.*;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class RoomMicPositionUserVo implements Serializable {
	private Long uid;

	private Long erbanNo;

	private Byte star;

	private String nick;

	private Byte gender;

	private Byte roleType;

	private String avatar;

	private NobleUsers nobleUsers;

	private UserLevelVo userLevelVo;

	private CarportVo carport;

	private UserHeadwearVo userHeadwear;

	private List<BadgeUserVo> badgeUserVo;

}
