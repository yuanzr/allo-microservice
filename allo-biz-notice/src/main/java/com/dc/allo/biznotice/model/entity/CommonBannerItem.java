package com.dc.allo.biznotice.model.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * description: CommonBannerItem
 *
 * @date: 2020年05月08日 14:27
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonBannerItem implements Serializable {

    private static final long serialVersionUID = -8760440782521863099L;

    private Long bannerId;
    /** banner图片 */
    private String bannerPic;
    /** banner标题 */
    private String bannerTitle;
    /** banner副标题 */
    private String bannerSubTitle;
    /** 点击事件 {@link com.dc.allo.biznotice.constant.Constant.BannerActionType} */
    private Byte actionType;
    private String actionUrl;

    private Integer seqNo;

}
