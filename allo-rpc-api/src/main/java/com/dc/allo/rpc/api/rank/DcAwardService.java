package com.dc.allo.rpc.api.rank;

import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rpc.proto.AlloResp;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
public interface DcAwardService {

    /**
     * 购买礼包（免费or收费礼包 非抽奖型礼包）
     * @param uid
     * @param packageId
     */
    AlloResp<Boolean> purchaseAwardPackage(long uid, int packageId) throws Exception;

    /**
     * 购买礼包（礼包奖品配置数量无效，通过传入的购买货币类型及数量，动态计算发放奖励数量，官方抹除尾数）比如骆驼赛的赢家发奖
     * @param uid
     * @param packageId
     * @param purchaseType   货币类型
     * @param purchaseNum    货币数量 (注意，外购金额数量由业务使用方校验合法性，此处只是根据金额计算实际所需发放奖品数量）
     * @return
     */
    AlloResp<ReleasePkgResult> purchaseAwardPackage(long uid,int packageId,int purchaseType,int purchaseNum);

    /**
     * 抽奖礼包（免费or收费礼包 抽奖型礼包）
     * @param uid
     * @param packageId
     */
    AlloResp<AwardOfPackage> lotteryAwardPackage(long uid, int packageId) throws Exception;

    /**
     * 发放礼包（不走购买逻辑直接发放）
     * @param uid
     * @param packageId
     * @return
     * @throws Exception
     */
    AlloResp<ReleasePkgResult> releaseAwardPackage(long uid, int packageId) throws Exception;

    /**
     * 检查并创建月表
     * @return
     */
    AlloResp<Boolean> check2CreatNextMonthTable();
}
