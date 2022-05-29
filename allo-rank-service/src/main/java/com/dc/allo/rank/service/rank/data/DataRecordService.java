package com.dc.allo.rank.service.rank.data;

import com.dc.allo.rpc.domain.rank.RankDataRecord;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
public interface DataRecordService {
    /**
     * 添加app
     * @param name
     * @param appKey
     * @return
     */
    long addApp(String name,String appKey);

    /**
     * 添加榜单数据
     * @param record
     * @param checkPass
     * @return
     */
    long addRankDataRecord(RankDataRecord record, boolean checkPass);

    /**
     * 创建表
     */
    void createTable();
}
