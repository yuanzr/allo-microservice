package com.dc.allo.task.domain.vo;

import com.dc.allo.task.domain.entity.CommonTaskPrize;
import com.dc.allo.task.domain.entity.PrizeRankActItem;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * description: CommonTaskPrizeVO
 *
 * @date: 2020年05月26日 11:26
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonTaskPrizeVO extends CommonTaskPrize {
    private String prizePackageName;
    private Byte   taskCode;
    private String taskTypeName;
    private String jobStatus;
    private List<PrizeRankActItem> prizeList;
}
