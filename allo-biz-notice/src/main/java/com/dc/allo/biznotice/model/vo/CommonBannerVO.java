package com.dc.allo.biznotice.model.vo;

import com.dc.allo.biznotice.model.entity.CommonBannerItem;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * description: CommonBannerVO
 *
 * @date: 2020年05月08日 14:24
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonBannerVO implements Serializable {


    private static final long serialVersionUID = -7050212617491989816L;

    /** 每个banner的banner 类型 {@link com.dc.allo.biznotice.constant.Constant.BannerType} */
    private String key;

    private List<CommonBannerItem> items;
}
