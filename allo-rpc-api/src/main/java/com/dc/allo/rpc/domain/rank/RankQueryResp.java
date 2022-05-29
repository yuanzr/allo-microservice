package com.dc.allo.rpc.domain.rank;

import com.dc.allo.rpc.proto.AlloResp;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankQueryResp extends AlloResp<RankQueryResp> implements Serializable {
    private Long rankId;
    private String rankKey;
    private String rankName;
    private Date rankStartTime;
    private Date rankEndTime;
    private RankDetail oneRank;
    private List<RankDetail> rankList;
}
