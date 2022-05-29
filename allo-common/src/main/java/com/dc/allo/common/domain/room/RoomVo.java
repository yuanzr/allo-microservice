package com.dc.allo.common.domain.room;


import com.alibaba.fastjson.JSON;
import com.dc.allo.common.domain.user.UserBasicVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liuguofu on 2017/5/21.
 */
@Data
public class RoomVo implements Comparable<RoomVo>, Serializable {

    private Long uid;

    private Byte officeUser;// 1官方人员 其他非官方人员 用于判断是否有竞拍权限

    private Long roomId;

    private String title;

    private Byte type;

    private String meetingName;

    private Boolean valid;

    private Byte operatorStatus;

    private String avatar;

    private String roomDesc;

    private String backPic;

    private Date openTime;

    private Integer onlineNum;

    private Integer seqNo;

    private Byte abChannelType;

    private Byte gender;

    private String nick;

    private Long erbanNo;

    private String roomPwd;

    private String roomSafePwd;

    private String roomTag;

    private Byte officialRoom;

    private int calcSumDataIndex; //综合人气+流水值

    private Integer tagId;

    private String tagPict;

    private Long recomSeq;

    private String badge;

    private Byte isPermitRoom;

    private Double score;

    private byte isRecom;  // 是否皇帝推荐
    private int count;  //计数器

    private Boolean hasAnimationEffect;

    private Byte audioQuality;

    private Boolean isCloseScreen;

    private Boolean hasDragonGame;

    private Boolean hasKTVPriv;

    private Boolean isOpenKTV;

    private Boolean isOpenGame;

    private String singingMusicName; //正在演唱的歌曲名

    private Integer roomModeType;

    private String introduction;

    private String limitType; //房间限制类型

    private RoomGame roomGame;

    private Integer topicRoomSeqNo;//话题房间表序号

    private String skillTag;//101认证技能小图标

    private String fontColor;//房间标签字体颜色

    private Integer countryId;

    private String  countryPic;

    private String  countryName;

    private Integer realOnlineNum;//真实在线人数 仅用于HomeTask的refreshHome方法

    /* 三个标签选优先级最高的放此字段展示
     * 在三个标签循环设值时，tagLink不为空说明已经设置了比当前标签还高优先级的标签的值，则不设当前标签的值
     */
    private String tagLink;
    private String officialTagLink;//官方房标签

    /* 客户端要求这三个标签用一个字段展示，由服务端确定展示的优先级
    private String nobleTagLink;//贵族标签
    private String coupleTagLink;// CP房标签
    private String detailOrWeekTagLink;//日榜或周榜标签*/

    private String gameTagLink;   //游戏标签
    private List<UserBasicVo> memberList;
    //房主
    private UserBasicVo owner;
    //当前用户在房间成员属于什么角色
    private Byte role;
    //房间日榜第一名的头像
    private String dayRankFirstUser;
    private Integer memberNum;//会员数
    //用户拥有的各种类最高级别勋章照片
    private List<String> badgePic;

    //房间麦位魅力值信息
    private RoomCharmVo roomCharmVo;
    private String roomPkingUrl;
    //纯净模式
    private Boolean isPureMode;

    @Override
    public int compareTo(RoomVo roomVo) {

        int calcSumDataIndexVo = roomVo.calcSumDataIndex;
        int calcSumDataIndexThis = this.calcSumDataIndex;

        if (calcSumDataIndexThis < calcSumDataIndexVo) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
