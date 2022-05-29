package com.dc.allo.rank.service.rank;

import com.dc.allo.common.component.threadpool.DCCommonThreadPool;
import com.dc.allo.rank.domain.rank.Rank;
import com.dc.allo.rank.domain.rank.RankDataSource;
import com.dc.allo.rank.domain.rank.config.RankDatasourceConfig;
import com.dc.allo.rank.service.rank.data.DataRecordService;
import com.dc.allo.rank.service.rank.data.DataSourceConfigService;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import com.dc.allo.rank.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Slf4j
@Component
public class DataSourceManager {

    @Autowired
    RankManager rankManager;

    @Autowired
    DataSourceConfigService dataSourceConfigService;

    @Autowired
    DataRecordService dataRecordService;

    @Autowired
    DCCommonThreadPool commonThreadPool;

    /**
     * 生成数据源对象
     *
     * @param appKey
     * @param dataSourceKey
     * @return
     */
    public RankDataSource loadDataSource(String appKey, String dataSourceKey) {
        try {
            RankDataSource dataSource = RankDataSource.NULL;
            RankDatasourceConfig datasourceConfig = dataSourceConfigService.getDataSource(appKey, dataSourceKey);
            if (datasourceConfig == null) {
                return dataSource;
            }
            dataSource = new RankDataSource();
            dataSource.setConfig(datasourceConfig);
            //数据源绑定关联榜单
            List<Rank> rankList = rankManager.getRankListBelongToDataSource(datasourceConfig);
            dataSource.bindRank(rankList);
            return dataSource;
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "从缓存获取数据源对象失败 appKey:{} dataSourceKey:{}", appKey, dataSourceKey, e);
        }
        return RankDataSource.NULL;
    }

    /**
     * 寻找与数据匹配的数据源
     *
     * @param rankDataRecord
     * @return
     */
    public RankDataSource match(RankDataRecord rankDataRecord) {
        RankDataSource dataSource = RankDataSource.NULL;
        try {
            boolean fieldCheck = fieldCheck(rankDataRecord);
            boolean secretCheck = false;
            if (fieldCheck) {
                //从缓存中获取数据源对象
                dataSource = loadDataSource(rankDataRecord.getAppKey(), rankDataRecord.getDataSourceKey());
                //数据与数据源secret校验
                secretCheck = RankDataSource.valid(rankDataRecord, dataSource);
                if (!secretCheck) {
                    log.warn(Constant.Rank.WARN + "数据源secret密钥不匹配 rankDataRecord:{} dataSource:{}", rankDataRecord, dataSource);
                }
            } else {
                log.warn(Constant.Rank.WARN + "数据字段校验不通过 rankDataRecord:{}", rankDataRecord);
            }
            final boolean checkPass = fieldCheck && secretCheck;
            //数据异步落库
            commonThreadPool.execute(() -> dataRecordService.addRankDataRecord(rankDataRecord, checkPass));
            return dataSource;
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "数据与数据源匹配失败 rankDataRecord:{} ", rankDataRecord, e);
        }
        return dataSource;
    }

    /**
     * 数据字段非空校验
     *
     * @param rankDataRecord
     * @return
     */
    private boolean fieldCheck(RankDataRecord rankDataRecord) {
        if (rankDataRecord == null) {
            return false;
        }
        boolean checkPass = true;
        if (StringUtils.isEmpty(rankDataRecord.getAppKey())) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getAppKey() is null. rankDataRecord:{}", rankDataRecord);
            return checkPass;
        }
        if (StringUtils.isEmpty(rankDataRecord.getDataSourceKey())) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getDataSourceKey() is null. rankDataRecord:{}", rankDataRecord);
            return checkPass;
        }
        if (StringUtils.isEmpty(rankDataRecord.getDataSourceSecret())) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getDataSourceSecret() is null. rankDataRecord:{}", rankDataRecord);
            return checkPass;
        }
        if (StringUtils.isEmpty(rankDataRecord.getRecvId())) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getRecvId() is null. rankDataRecord:{}", rankDataRecord);
            return checkPass;
        }
        if (StringUtils.isEmpty(rankDataRecord.getSendId())) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getSendId() is null. rankDataRecord:{}", rankDataRecord);
            return checkPass;
        }
        if (rankDataRecord.getScore() == null) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getScore() is null. rankDataRecord:{}", rankDataRecord);
            return checkPass;
        }
        if (rankDataRecord.getCount() == null || rankDataRecord.getCount() < 1) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getCount() is null or <1. rankDataRecord:{}", rankDataRecord);
            return checkPass;
        }
        if (rankDataRecord.getTimestamp() == null) {
            checkPass = false;
            log.warn(Constant.Rank.WARN + "rankDataRecord.getTimestamp() is null. rankDataRecord:{}", rankDataRecord);
        }
        return checkPass;
    }

}

