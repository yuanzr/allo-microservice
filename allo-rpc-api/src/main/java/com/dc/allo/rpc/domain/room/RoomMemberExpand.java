package com.dc.allo.rpc.domain.room;

import com.dc.allo.common.domain.user.BadgeUserVo;
import com.dc.allo.common.domain.user.NobleUsers;
import com.dc.allo.common.domain.user.UserHeadwearVo;
import com.dc.allo.common.domain.user.UserLevelVo;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class RoomMemberExpand {
    private Long erbanNo;

    private String avatar;

    private String nick;

    private Byte   gender;

    private Date birth; //生日

    private Boolean hasPrettyErbanNo;//是否靓号

    private UserLevelVo userLevelVo ;//等级信息

    private UserHeadwearVo userHeadwear;//头饰信息

    private NobleUsers nobleUsers; //贵族信息

    private Integer unReadApply;//未读的申请消息

    private String  msgContent;//发送的消息内容

    private List<BadgeUserVo> badgeUserVo;

    public List<BadgeUserVo> getBadgeUserVo() {
        return badgeUserVo;
    }
}
