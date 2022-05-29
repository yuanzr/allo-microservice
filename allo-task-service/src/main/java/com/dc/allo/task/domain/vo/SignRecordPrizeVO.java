package com.dc.allo.task.domain.vo;

import com.dc.allo.task.domain.entity.PrizeRankActItem;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * description: SignRecordPrizeVO
 *
 * @date: 2020年05月27日 16:20
 * @author: qinrenchuan
 */
@Data
@ToString
public class SignRecordPrizeVO {
    private List<PrizeRankActItem> prizeRankActItems;
    private List<TaskPrizeDetailVO> prizeVOS;
    private Boolean signed;
}
