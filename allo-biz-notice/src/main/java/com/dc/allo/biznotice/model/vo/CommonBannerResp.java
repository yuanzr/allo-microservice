package com.dc.allo.biznotice.model.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * description: 通用banner响应实体
 *
 * @date: 2020年05月06日 15:45
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonBannerResp implements Serializable {

    private static final long serialVersionUID = 1859832186417841700L;

    private List<CommonBannerVO> banners;

}
