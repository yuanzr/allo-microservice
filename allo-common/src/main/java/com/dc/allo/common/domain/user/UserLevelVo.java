package com.dc.allo.common.domain.user;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class UserLevelVo implements Serializable {
    private Long experAmount;
    private Long charmAmount;
    private String experUrl;
    private String charmUrl;
    private String experLevelName;
    private String charmLevelName;
    private String experLevelGrp;
    private String charmLevelGrp;
    private int experLevelSeq;
    private int charmLevelSeq;

    private Byte levelOnline;
    private BigDecimal levelOnlineAmount;
    private String levelOnlineUrl;
}
