package com.dc.allo.biznotice.service.firebase.impl;

import com.dc.allo.biznotice.mapper.firebase.NoticeMsgPushRecordMapper;
import com.dc.allo.biznotice.model.firebase.NoticeMsgPushRecord;
import com.dc.allo.biznotice.service.firebase.NoticeMsgPushRecordService;
import com.dc.allo.common.utils.DateTimeUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: NoticeMsgPushRecordServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/1/21:30
 */
@Service
public class NoticeMsgPushRecordServiceImpl implements NoticeMsgPushRecordService {


    @Autowired
    private NoticeMsgPushRecordMapper noticeMsgPushRecordMapper;

    /**
     * 新增或更新
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdate(NoticeMsgPushRecord record){
        int result = 0;
        if (record.getRecordId() == null){
            record.setCreateTime(new Date());
            result = noticeMsgPushRecordMapper.insert(record);
        }else {
            result = noticeMsgPushRecordMapper.update(record);
        }
        return result;
    }

    @Override
    public int updateStatus(Long recordId,Byte pushStatus,String messageId){
        int result = noticeMsgPushRecordMapper.updateStatus(recordId,pushStatus,messageId);
        return result;
    }

    @Override
    public int updateStatusBatch(List<Long> recordIdList, Byte pushStatus) {
        int result = noticeMsgPushRecordMapper.updateStatusBatch(recordIdList,pushStatus);
        return result;
    }


    @Override
    public NoticeMsgPushRecord get(Long recordId){
        NoticeMsgPushRecord record = noticeMsgPushRecordMapper.get(recordId);
        return record;
    }


    @Override
    public List<NoticeMsgPushRecord> queryPage(Date starTime,Date endTime,Integer pageNum,Integer pageSize){
        int page = (pageNum-1)*pageSize;
        List<NoticeMsgPushRecord> list = noticeMsgPushRecordMapper.queryPage(starTime, endTime, page, pageSize);
        return list;
    }

    @Override
    public int queryPageCount(Date starTime,Date endTime){
        int count = noticeMsgPushRecordMapper.queryPageCount(starTime, endTime);
        return count;
    }

    @Override
    public List<NoticeMsgPushRecord> queryPendingRecord() {
        Date currentTime = new Date();
        Date expireTime = DateTimeUtil.addMinute(currentTime, 5);
        List<NoticeMsgPushRecord> list = noticeMsgPushRecordMapper.queryPendingRecord(currentTime,expireTime);
        return list;
    }

}
