package com.dc.allo.rank.domain.rank;



import com.dc.allo.rpc.domain.rank.RankDataRecord;
import com.dc.allo.rpc.domain.rank.RankDetail;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
public interface IRank {
    /**
     * 单个记录
     *
     * @param record
     * @return
     */
    double record(RankDataRecord record);

    /**
     * 获取当前时间的榜单key
     *
     * @return
     */
    String getCurrentRankKey();

    /**
     * 获取当前时间的榜单key(带分割key)
     *
     * @param divideKey
     * @return
     */
    String getCurrentRankKey(String divideKey);

    /**
     * 获取当前时间的关联榜单key
     *
     * @param id
     * @return
     */
    String getCurrentRelationRankKey(String id);

    /**
     * 获取当前时间的关联榜单key(带分割key)
     *
     * @param id
     * @param divideKey
     * @return
     */
    String getCurrentRelationRankKey(String id, String divideKey);

    /**
     * 根据榜单的成员类型和收送礼类型确定记录id
     *
     * @param record
     * @return
     */
    String getRankRecordId(RankDataRecord record);

    /**
     * 根据榜单的成员类型和收送礼类型确定关联榜单的记录id
     *
     * @param record
     * @return
     */
    String getRelationRankRecordId(RankDataRecord record);

    /**
     * 获取基于榜单类型的前N个时间段的榜单key
     * （例：假设当前时间为2020-05-06 12:00:00
     * 榜单类型为小时榜，offset为3，则getLatestRankName方法返回前三个小时的key 即：2020-05-06 9:00:00
     * 榜单类型为日榜，offset为2，则getLatestRankName方法返回前两天的key 即：2020-05-04 12:00:00
     * 榜单类型为月榜，offset为1，则getLatestRankName方法返回上个月的key 即：2020-04-06 12:00:00
     * 其他类型榜单同理
     * ）
     *
     * @param offset
     * @return
     */
    String getLatestRankKey(Integer offset, String divideKey);

    /**
     * 获取基于榜单类型的前N个时间段的关联榜单key
     *
     * @param id
     * @param offset
     * @return
     */
    String getLatestRelationRankKey(String id, Integer offset, String divideKey);

    /**
     * 查询榜单列表
     *
     * @param offset
     * @param page
     * @param rows
     * @return
     */
    List<RankDetail> queryRankListByTimeOffset(int offset, int page, int rows, String divideKey);

    /**
     * 查询关联榜单列表
     *
     * @param rankId
     * @param offset
     * @param page
     * @param rows
     * @return
     */
    List<RankDetail> queryRelationRankListByTimeOffset(String rankId, int offset, int page, int rows, String divideKey);

    /**
     * 查询单个id排名明细
     *
     * @param rankId
     * @return
     */
    RankDetail queryOneRankByTimeOffset(String rankId, int offset, String divideKey);


    /**
     * 查询某个范围内排名明细
     * @param rankId
     * @param offset
     * @param begin
     * @param end
     * @param divideKey
     * @return
     */
    List<RankDetail> queryRangeRankList(String rankId,int offset,long begin,long end,String divideKey);

    /**
     * 榜单是否已结束
     * @return
     */
    boolean isEnded();

}
