package com.dc.allo.rank.handler;

import com.dc.allo.common.component.chain.ChainManager;
import com.dc.allo.rank.domain.rank.RankDataSource;
import com.dc.allo.rank.service.rank.DataSourceManager;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/7.
 */
@Component
@Slf4j
public class RankDataStreamHandler {
    @Autowired
    DataSourceManager dataSourceManager;
    @Autowired
    ChainManager chainManager;

    /**
     * 处理榜单数据流
     *
     * @param rankDataRecord
     */
    public void handleRankDtaStream(RankDataRecord rankDataRecord) {
        //数据&数据源的匹配校验
        RankDataSource dataSource = dataSourceManager.match(rankDataRecord);
        if (RankDataSource.isNotNull(dataSource)) {
            //榜单数据累计
            try {
                dataSource.acceptData(rankDataRecord, chainManager);
            } catch (Exception e) {
                log.error("RankDataStreamHandler acceptData err dataSource:{} rankDataRecord:{}", dataSource, rankDataRecord, e);
            }
        }
    }
}
