package com.dc.allo.rank.service.rank.data.impl;

import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.domain.rank.config.App;
import com.dc.allo.rank.domain.rank.config.RankDataRecordDb;
import com.dc.allo.rank.mapper.rank.AppMapper;
import com.dc.allo.rank.mapper.rank.RankDataRecordMapper;

import com.dc.allo.rank.service.rank.data.DataRecordService;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Service
@Slf4j
public class DataRecordServiceImpl implements DataRecordService {

    @Autowired
    RankDataRecordMapper rankDataRecordMapper;

    @Autowired
    AppMapper appMapper;


    @Override
    public long addApp(String name, String appKey) {
        if(StringUtils.isBlank(name)||StringUtils.isBlank(appKey)){
            return 0;
        }
        return appMapper.add(name,appKey);
    }

    /**
     * 入库榜单数据记录
     *
     * @param record
     * @param checkPass
     * @return
     */
    @Override
    public long addRankDataRecord(RankDataRecord record, boolean checkPass) {
        //榜单数据落库
        try {
            String tableNameSuffix = String.format(Constant.RankDataRecordConstant.TABLE_NAME_FORMAT, record.getAppKey(), getDate(new Date(record.getTimestamp())));
            RankDataRecordDb vo = buildVo(record, checkPass);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("tableName", tableNameSuffix);
            paramMap.put("bean", vo);
            return rankDataRecordMapper.insertAndReturnId(paramMap);
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "榜单数据落库失败", e);
        }
        return -1;
    }

    /**
     * 判断本月和次月的表存不存在，不存在则建表
     */
    @Override
    public void createTable() {
        List<App> apps = appMapper.queryAllApp();
        if (CollectionUtils.isNotEmpty(apps)) {
            for (App app : apps) {
                String appKey = app.getAppKey();
                Calendar calendar = Calendar.getInstance();
                String currMonthTableName = String.format(Constant.RankDataRecordConstant.TABLE_NAME_FORMAT, appKey, getDate(calendar.getTime()));
                calendar.add(Calendar.MONTH, 1);
                String nextMonthTableName = String.format(Constant.RankDataRecordConstant.TABLE_NAME_FORMAT, appKey, getDate(calendar.getTime()));
                int currMonthExist = rankDataRecordMapper.existTable(currMonthTableName);
                int nextMonthExist = rankDataRecordMapper.existTable(nextMonthTableName);
                if (currMonthExist < 1) {
                    rankDataRecordMapper.createNewTable(currMonthTableName);
                }
                if (nextMonthExist < 1) {
                    rankDataRecordMapper.createNewTable(nextMonthTableName);
                }
            }
        }
    }

    /**
     * 构造入库vo
     *
     * @param dataRecord
     * @param checkPass
     * @return
     */
    private RankDataRecordDb buildVo(RankDataRecord dataRecord, boolean checkPass) {
        RankDataRecordDb vo = new RankDataRecordDb();
        BeanUtils.copyProperties(dataRecord, vo);
        //分值的数据类型不匹配无法自动copy，手动set
        vo.setScore(new BigDecimal(dataRecord.getScore()));
        vo.setCheckPass(checkPass ? 1 : 0);
        return vo;
    }

    /**
     * 根据日期返回表名后缀
     *
     * @param date
     * @return
     */
    private String getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM");
        return simpleDateFormat.format(date);
    }
}
