package com.dc.allo.biznotice.service.firebase;


import com.dc.allo.biznotice.model.firebase.NoticeMsgPushRecord;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: NoticeMsgPushRecordService
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/1/16:31
 */
public interface NoticeMsgPushRecordService {


    /**
     * 新增/更新
     * @param record
     * @return
     */
    int saveOrUpdate(NoticeMsgPushRecord record);


    /**
     * 更新状态
     * @param recordId
     * @param pushStatus
     * @param messageId
     * @return
     */
    int updateStatus(Long recordId,Byte pushStatus, String messageId);

    /**
     * 批量更新状态
     * @param recordIdList
     * @param pushStatus
     * @return
     */
    int updateStatusBatch(List<Long> recordIdList,Byte pushStatus);


    /**
     * 查询一条记录
     * @param recordId
     * @return
     */
    NoticeMsgPushRecord get(Long recordId);

    /**
     *  分页查询消息记录
     * @param starTime
     * @param endTime
     * @param pageNum
     * @param PageSize
     * @return
     */
    List<NoticeMsgPushRecord> queryPage(Date starTime, Date endTime,Integer pageNum,Integer PageSize);

    /**
     *  分页查询消息记录Count
     * @param starTime
     * @param endTime
     * @return
     */
    int queryPageCount(Date starTime,Date endTime);


    /**
     * 查询出待投递的订单 5分钟后
     * @return
     */
    List<NoticeMsgPushRecord> queryPendingRecord();


}
