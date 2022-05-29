package com.dc.allo.activity.service;


import com.dc.allo.rpc.proto.AlloResp;

public interface ActChargeCamelService {

    /**
     * 领取活动奖励
     * @param level
     * @param uid
     * @return
     */
    AlloResp getPrizeAward(Byte level,Long uid);

    /**
     * 测试发奖
     * @param level
     * @param uid
     * @return
     */
    AlloResp testPrizeAward(Byte level, Long uid) ;
}
