package com.dc.allo.rpc.domain.award;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: AwardOfPackageMessage
 * @since 2020/06/24 14:29
 */
@Data
@ToString
public class AwardOfPackageMessage implements Serializable {
    private Long uid;
    private int releaseType;
    private String releaseId;
    private int awardCount;
    private int validDays;
    private String seqId;
}
