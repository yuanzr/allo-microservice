package com.dc.allo.rpc.domain.user.baseinfo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * description: 用户详细信息
 *
 * @date: 2020年04月01日 16:00
 * @author: qinrenchuan
 */
@Data
@ToString
public class UserInfo implements Serializable {
    private Long uid;
    private Long erbanNo;
    /** 是否是耳伴靓号，true为是，false为否，默认否flase */
    private Boolean hasPrettyErbanNo;

    private String phone;
    private Date birth;
    private Byte star;
    private String nick;
    private String email;

    /** 兴趣爱好 */
    private String hobby;
    /** 签名 */
    private String signture;
    /** 用户语音介绍 */
    private String userVoice;
    /** 自我介绍时长 */
    private Integer voiceDura;
    /** 关注数量 */
    private Integer followNum;
    /** 粉丝数量 */
    private Integer fansNum;
    /** 1普通账号，2官方账号，3机器账号， 4工会账户 , 5.客服账户 */
    private Byte defUser;
    /** 财富值（人气值）收到的总礼物金额 */
    private Long fortune;
    /** 下载渠道类型 */
    private Byte channelType;

    private Date lastLoginTime;
    private String lastLoginIp;
    private Byte gender;
    private String avatar;
    private Integer countryId;

    /** 用户的地域,国家 */
    private String region;

    private String userDesc;
    private String alipayAccount;
    private String alipayAccountName;
    private Date createTime;
    private Date updateTime;
    private String wxOpenid;

    /** 被分享的房主id */
    private Long roomUid;
    /** 微信公众号微信openid */
    private String wxPubFansOpenid;

    private Byte wxPubFansGender;

    /** 分享人id */
    private Long shareUid;
    /** 分享的渠道 */
    private Byte shareChannel;
    /** 用户邀请码 */
    private String shareCode;
    /** 操作系统类型 */
    private String os;
    /** 操作系统版本 */
    private String osversion;
    /** app */
    private String app;

    private String imei;

    /** 下载渠道 */
    private String channel;
    /** 推广渠道，linkedme账号 */
    private String linkedmeChannel;
    /** 运营商类型 */
    private String ispType;
    /** 网络类型 */
    private String netType;
    /** 手机型号 */
    private String model;
    /** 设备号 */
    private String deviceId;
    /** 是否新设备，true/false */
    private Boolean newDevice;
    /** app版本号 */
    private String appVersion;
    /** 账号封禁开始时间 */
    private Date accBlockStartTime;
    /** 账号封禁结束时间 */
    private Date accBlockEndTime;
    /** 设备封禁开始时间 */
    private Date deviceBlockStartTime;
    /** 设备封禁结束时间 */
    private Date deviceBlockEndTime;

    private Integer nobleId;
    private String nobleName;

    /** 支付密码 */
    private String paymentPwd;
    /** 真实姓名 */
    private String realName;
    /** 身份证号 */
    private String idCardNum;
}
