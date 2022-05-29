package com.dc.allo.rank.service.bet;

import com.dc.allo.rank.domain.bet.BetSpirit;
import com.dc.allo.rank.domain.bet.BetSpiritConfig;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rpc.domain.page.AdminPage;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
public interface BetSpiritService {

    /**
     * 增加投注精灵
     * @param spirit
     * @return
     */
    long addSpirit(BetSpirit spirit);

    /**
     * 更新精灵
     * @param spirit
     * @return
     */
    long updateSpirit(BetSpirit spirit);

    /**
     * 分页获取投注精灵
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<BetSpirit> pageSpirit(int pageNum,int pageSize);

    /**
     * 增加投注精灵配置信息
     * @param spiritConfig
     * @return
     */
    long addSpiritConfig(BetSpiritConfig spiritConfig);

    /**
     * 查询某活动投注精灵
     * @param actId
     * @return
     */
    List<BetSpiritInfo> querySpiritConfigs(long actId);

    /**
     * 查询某活动投注精灵(Admin)
     * @param actId
     * @return java.util.List<com.dc.allo.rpc.domain.bet.BetSpiritInfo>
     * @author qinrenchuan
     * @date 2020/6/16/0016 11:09
     */
    List<BetSpiritInfo> querySpiritConfigsForAdmin(long actId);

    /**
     * 更新投注精灵配置信息
     * @param spiritConfig
     * @return
     */
    long updateSpiritConfig(BetSpiritConfig spiritConfig);

    /**
     * 更新投注精灵获胜次数
     * @param spiritId
     * @param actId
     * @return
     */
    long updateSpiritConfigWinNum(long spiritId,long actId);

    /**
     * 更新投注精灵失败次数
     * @param spiritId
     * @param actId
     * @return
     */
    long updateSpiritConfigLossNum(long spiritId,long actId);

    /**
     * 更新投注精灵投注额
     * @param spiritId
     * @param actId
     * @param totalAmount
     * @param totalAwardAmount
     * @return
     */
    long updateSpiritConfigAmount(long spiritId,long actId,long totalAmount,long totalAwardAmount);
}
