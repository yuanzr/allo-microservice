package com.dc.allo.rpc.domain.country;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: Country
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/28/21:31
 */
@Data
@ToString
public class Country {

    private Long id;

    private Integer countryId;

    private String countryName;

    private String countryPic;

    private Integer regionNo;

    private String language;

    private Boolean isShow;

    private Boolean isTop;

    private Boolean isHot;

    private Integer seq;

    private Date createTime;

    private Date updateTime;

    private Byte priorityShow;

    private String abbreviation;

    private Integer priorityShowSeq;
}
