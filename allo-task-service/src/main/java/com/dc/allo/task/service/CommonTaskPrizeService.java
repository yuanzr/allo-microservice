package com.dc.allo.task.service;

import com.dc.allo.task.domain.entity.CommonTaskPrize;
import com.dc.allo.task.domain.entity.PrizeRankActItem;
import com.dc.allo.task.domain.vo.CommonTaskPrizeVO;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

/**
 * description: CommonTaskPrizeService
 * date: 2020年05月26日 11:32
 * author: qinrenchuan
 */
public interface CommonTaskPrizeService {
    /**
     * 分页查询
     * @param queryVo
     * @param pageNumber
     * @param pageSize
     * @return com.github.pagehelper.PageInfo<com.dc.allo.task.domain.vo.CommonTaskPrizeVO>
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:46
     */
    PageInfo<CommonTaskPrizeVO> listByPage(CommonTaskPrizeVO queryVo, Integer pageNumber, Integer pageSize);

    /**
     * 任务奖励数据初始化
     * @param actCode
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:47
     */
    void initTaskPrizeConfig(Byte actCode);

    /**
     * 设置任务奖励礼包
     * @param taskPrizeId
     * @param prizePackageId
     * @author qinrenchuan
     * @date 2020/5/26/0026 11:47
     */
    void setPrizePackage(Long taskPrizeId, Long prizePackageId);

    /**
     * 根据任务code和等级查询奖品
     * @param actCode
     * @param level
     * @return java.util.List<com.dc.allo.task.domain.entity.CommonTaskPrize>
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:19
     */
    List<CommonTaskPrize> getPackageIdByActTypeAndLevel(Byte actCode, Byte level, Byte taskTypeCode);

    /**
     * 根据任务和签到等级查询签到奖励列表
     * @param actCode
     * @param level
     * @return java.util.List<com.dc.allo.task.domain.entity.PrizeRankActItem>
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:29
     */
    List<PrizeRankActItem> getPrizePackageByActTypeAndLevel(Byte actCode, Byte level, Byte taskTypeCode);

    /**
     * 查询所有签到奖品
     * @param actCode
     * @return java.util.Map<java.lang.Byte,java.util.List<com.dc.allo.task.domain.entity.PrizeRankActItem>>
     * @author qinrenchuan
     * @date 2020/5/27/0027 16:10
     */
    Map<Byte, List<PrizeRankActItem>> getAllPrizesByActCode(Byte actCode);

    /**
     * 查询活动下的礼包列表
     * @param actCode
     * @return
     */
    List<CommonTaskPrizeVO> listPackageByActCode(Byte actCode,Long uid,Boolean isShowStatus);
}
