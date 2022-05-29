package com.dc.allo.activity.service;

import com.dc.allo.rpc.proto.AlloResp;

/**
 * @ClassName: ChargeSummerService
 * @Description: 夏日充值活动
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/10/14:12
 */
public interface ChargeSummerService {

    /**
     * 奖品列表
     * @param uid
     * @return
     */
    AlloResp getChargeSummerPrizeList(Long uid);


    /**
     * 领取夏日充值奖励
     * @param uid
     * @param rankDateType  奖励类型: 1首充 4.累加充值
     * @param jobName       任务名称
     * @return
     */
    AlloResp getPrizeAward(Long uid, Byte rankDateType,String jobName);

    /**
     * 获取奖励Key
     * @param actType      活动类型(这里固定9)
     * @param rankType     任务类型:1首充 4累加
     * @return
     */
    String getPrizeKey(Byte actType, Byte rankType);
}
