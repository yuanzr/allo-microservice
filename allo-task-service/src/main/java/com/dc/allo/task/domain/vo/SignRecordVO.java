package com.dc.allo.task.domain.vo;

import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * description: SignRecordVO
 *
 * @date: 2020年05月26日 17:50
 * @author: qinrenchuan
 */
@Data
@ToString
public class SignRecordVO {
    /** 今天是否已经签到 */
    private Boolean todaySigned;
    /** 记录 */
    private List<SignRecordPrizeVO> signRecordPrizeVOS;
}
