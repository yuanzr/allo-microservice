package com.dc.allo.rpc.domain.rank;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankQueryReq implements Serializable {
    private String appKey;
    private String rankKey;
    private String divideKey;
    private Integer timeOffset;
    private Integer page;
    private Integer rows;
    private Boolean withOne;
    private String oneId;
    private Boolean withRelation;
    private Integer relationPage;
    private Integer relationRows;
    private boolean needBizInfo = false;
}
