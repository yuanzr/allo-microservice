package com.dc.allo.user.service.baseinfo.cache;

import java.math.BigDecimal;

public interface UserCache {
    /**
     * 热力值升级添加金币和战力值
     */
    void heatUpLevelAddCoinAndAddCombatPower(Long roomUid, BigDecimal goldNum,Double combatPower);
}
