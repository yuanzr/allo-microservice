package com.dc.allo.rank.domain.rank;

import com.dc.allo.rpc.domain.rank.RankDataRecord;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Builder
@Data
@ToString
public class RankRecordContext {
    /**
     * 榜单实例对象
     */
    private Rank rank;
    /**
     * 榜单数据对象
     */
    private RankDataRecord record;
    /**
     * 本次累计完成后的分值（DataRecordProcessor处理类进行赋值，ScoreNoticeProcessor处理类使用）
     */
    private Double scoreAfterRecord;
}
