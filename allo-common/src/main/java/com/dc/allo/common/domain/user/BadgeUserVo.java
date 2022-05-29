package com.dc.allo.common.domain.user;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class BadgeUserVo implements Serializable {
    private Long id;

    private Long uid;

    private Byte badgeType;//勋章类型

    private Byte badgeCode;//勋章编号

    private String badgeName;//勋章名称

    private Integer badgeLevel;//勋章等级

    private Long badgeResId;//勋章资源ID

    private String badgeDefPic;//显示图片

    private String badgePicMax;//显示图片

    private Integer seq;//佩戴的序号

    private Byte used;//是否已经使用

    private Date expireTime;//过期时间

    private Byte status;//勋章状态

    private List<BadgeResVo> badgeResList;
}
