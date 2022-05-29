package com.dc.allo.rank.service.award;

import com.dc.allo.rank.domain.award.CommonAwardOfPackage;
import com.dc.allo.rank.domain.award.CommonAwardPackage;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rank.domain.award.CommonAward;
import com.dc.allo.rpc.domain.page.AdminPage;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
public interface AwardService {

    /**
     * 新增奖品（后台用）
     * @param name
     * @param icon
     * @param releaseType
     * @param releaseId
     * @param price
     * @param priceDisplay
     * @param extend
     * @return
     */
    long addCommonAward(String name,String icon,int releaseType,String releaseId,int price,int priceDisplay,String extend);

    /**
     * 更新奖品配置（后台用）
     * @param id
     * @param name
     * @param icon
     * @param releaseType
     * @param releaseId
     * @param price
     * @param priceDisplay
     * @param extend
     * @return
     */
    long updateCommonAward(long id,String name,String icon,int releaseType,String releaseId,int price,int priceDisplay,String extend);

    /**
     * 奖品池分页（后台用）
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<CommonAward> pageAward(int pageNum,int pageSize);

    /**
     * 获取公共奖品配置
     * @return
     */
    List<CommonAward> queryCommonAwards();

    /**
     * 新增礼包 （后台用）
     * @param awardPackage
     * @return
     */
    long addCommonAwardPackage(CommonAwardPackage awardPackage);

    /**
     * 更新礼包配置（后台用）
     * @param awardPackage
     * @return
     */
    long updateCommonAwardPackage(CommonAwardPackage awardPackage);

    /**
     * 礼包分页（后台用）
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<CommonAwardPackage> pageAwardPackage(int pageNum,int pageSize);

    /**
     * 查所有礼包接口
     * @return
     */
    List<CommonAwardPackage> queryAllAwardPackage();

    /**
     * 新增礼包奖品配置
     * @param awardOfPackage
     * @return
     */
    long addCommonAwardOfPackage(CommonAwardOfPackage awardOfPackage);

    /**
     * 更新礼包奖品配置
     * @param awardOfPackage
     * @return
     */
    long updateCommonAwardOfPackage(CommonAwardOfPackage awardOfPackage);

    /**
     * 分页查礼包奖品配置
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<CommonAwardOfPackage> pageAwardOfPackage(int pageNum,int pageSize);

    /**
     * 查询礼包信息
     * @param packageId
     * @return
     */
    List<CommonAwardOfPackage> getAwardOfPackage(long packageId);

    /**
     * 购买礼包（免费or收费礼包 非抽奖型礼包）
     * @param uid
     * @param packageId
     */
    boolean purchaseAwardPackage(long uid, long packageId) throws Exception;

    /**
     * 购买礼包（礼包奖品配置数量无效，通过传入的购买货币类型及数量，动态计算发放奖励数量，官方抹除尾数）比如骆驼赛的赢家发奖
     * @param uid
     * @param packageId
     * @param purchaseType   货币类型
     * @param purchaseNum    货币数量 (注意，外购金额数量由业务使用方校验合法性，此处只是根据金额计算实际所需发放奖品数量）
     * @return
     */
    ReleasePkgResult purchaseAwardPackage(long uid,long packageId,int purchaseType,long purchaseNum);

    /**
     * 抽奖礼包（免费or收费礼包 抽奖型礼包）
     * @param uid
     * @param packageId
     */
    AwardOfPackage lotteryAwardPackage(long uid, long packageId) throws Exception;

    /**
     * 发放礼包（不走购买逻辑直接发放）
     * @param uid
     * @param packageId
     * @return
     */
    ReleasePkgResult releaseAwardPackage(long uid, long packageId) throws Exception;

    void creatNextMonthTable();
}
