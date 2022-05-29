package com.dc.allo.rpc.domain.room;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: Room
 *
 * @date: 2020年04月17日 15:24
 * @author: qinrenchuan
 */
@Data
@ToString
public class Room implements Serializable {
    /** 主键UID */
    private Long uid;
    /** roomId */
    private Long roomId;
    /** 进房间密码 */
    private String roomPwd;
    /** 对应的标签ID */
    private Integer tagId;
    /** 标签图片 */
    private String tagPict;
    /** 房间标签 */
    private String roomTag;

    private String badge;
    private String meetingName;
    /** 房间名 */
    private String title;
    /** 房间开闭状态，true为开，false为闭 */
    private Boolean valid;
    /** 房间类型竞拍房1，轻聊房(电台)2,游戏轰趴（坑位）房3 */
    private Byte type;
    /** 1普通房间，2官方房间 */
    private Byte officialRoom;

    private Byte abChannelType;
    /** 悬赏记录ID */
    private Long rewardId;
    /** 悬赏金额，悬赏房专属，做冗余 */
    private BigDecimal rewardMoney;
    /** 服务时长，冗余 */
    private Integer servDura;
    /** 房主离开或者在房间状态 */
    private Byte operatorStatus;
    /** 房间头像 */
    private String avatar;

    private String roomDesc;
    /** 房间背景图 */
    private String backPic;
    /** 开房时间 */
    private Date openTime;
    /** 是否是牌照房1是牌照房，2是个人房，3是新秀房 */
    private Byte isPermitRoom;
    /** 房间实时在线人数，用户做排序，定时任务实时更新 */
    private Integer onlineNum;

    private Date createTime;
    private Date updateTime;
    /** 是否是异常关闭 */
    private Boolean isExceptionClose;
    /** 异常关闭时间 */
    private Date exceptionCloseTime;
    /** 推荐权重 */
    private Long recomSeq;
    /** 配置展示或不展示，1：展示，0：不展示 */
    private Byte canShow;
    /** 默认背景 */
    private String defBackpic;
    /** 是否开启礼物特效 */
    private Boolean hasAnimationEffect;
    /** 房间音质级别 1:低音质 2:高音质 */
    private Byte audioQuality;
    /** 是否有龙珠 */
    private Boolean hasDragonGame;
    /** 是否有ktv权限,1:true,0:false */
    private Boolean hasKtvPriv;
    /** 是否开启了ktv模式,1:true,0:false */
    private Boolean isOpenKtv;
    /** 是否关闭公屏 */
    private Boolean isCloseScreen;
    /** 是否开启排麦模式 */
    private Integer roomModeType;
    /** 房间限制类型 lock-上锁|isFriend-好友可进|isInvite-邀请可进 */
    private String limitType;
    /** 房间介绍 */
    private String introduction;
    /** 是否开启游戏模式 1:true-开启 |0:false-关闭 */
    private Boolean isOpenGame;
    /** 是否开启纯净模式 */
    private Boolean isPureMode;
    /** 开箱子是否有全麦礼物, 1:true,0:false */
    private Boolean hasWholeMicroGift;
    /** 国家ID */
    private Integer countryId;
    /** 国旗图片资源 */
    private String countryPic;
    /** 房间是否设置置顶(0不置顶,1置顶) */
    private Byte roomTop;
    /** 是否优先展示(0否 1是) */
    private Byte priorityShow;
}
