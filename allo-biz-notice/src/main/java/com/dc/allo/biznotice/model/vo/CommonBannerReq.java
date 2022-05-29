package com.dc.allo.biznotice.model.vo;

import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * description: CommonBannerReq
 *
 * @date: 2020年05月06日 15:50
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonBannerReq {
    /** uid */
    private Long uid;
    /** os操作系统类型： ios / android */
    private String os;
    /** 语言 */
    private String language;
    /** 通用banner位置  {@link com.dc.allo.biznotice.constant.Constant.BannerType}
     *  多个用英文逗号隔开
     */
    private List<Byte> bannerKeys;


}
