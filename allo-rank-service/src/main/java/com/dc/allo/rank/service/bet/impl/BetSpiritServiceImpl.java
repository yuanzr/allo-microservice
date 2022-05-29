package com.dc.allo.rank.service.bet.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.domain.bet.BetSpirit;
import com.dc.allo.rank.domain.bet.BetSpiritConfig;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rank.mapper.bet.BetSpiritMapper;
import com.dc.allo.rank.service.bet.BetSpiritService;
import com.dc.allo.rpc.domain.page.AdminPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@Service
public class BetSpiritServiceImpl implements BetSpiritService {

    @Autowired
    BetSpiritMapper spiritMapper;

    private boolean validSpirit(BetSpirit betSpirit){
        boolean flag = false;
        if(betSpirit!=null&& StringUtils.isNotBlank(betSpirit.getName())){
            flag = true;
        }
        return flag;
    }

    private boolean validSpiritConf(BetSpiritConfig spiritConfig){
        boolean flag = false;
        if(spiritConfig!=null&&spiritConfig.getActId()>0&&spiritConfig.getSpiritId()>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public long addSpirit(BetSpirit spirit) {
        if(!validSpirit(spirit)){
            return 0;
        }
        return spiritMapper.addSpirit(spirit);
    }

    @Override
    public long updateSpirit(BetSpirit spirit) {
        if(spirit == null|| spirit.getId() <=0){
            return 0;
        }
        return spiritMapper.updateSpirit(spirit);
    }

    @Override
    public AdminPage<BetSpirit> pageSpirit(int pageNum, int pageSize) {
        AdminPage<BetSpirit> spirits = new AdminPage<>();
        long offset =0;
        if(pageNum>0){
            offset = (pageNum - 1) * pageSize;
        }
        if(pageSize<=0){
            pageSize = 10;
        }
        if(pageSize>500){
            pageSize = 500;
        }
        spirits.setTotal(spiritMapper.countSpirit());
        spirits.setRows(spiritMapper.pageSpirit(offset,pageSize));
        return spirits;
    }

    @Override
    public long addSpiritConfig(BetSpiritConfig spiritConfig) {
        if(!validSpiritConf(spiritConfig)){
            return 0;
        }
        return spiritMapper.addSpiritConfig(spiritConfig);
    }

    @Override
    @Cached(name = "act-center:bet:spiritConfigCache.querySpiritConfigs", key = "#actId", cacheType = CacheType.BOTH, expire = 1, timeUnit = TimeUnit.MINUTES, cacheNullValue = true)
    public List<BetSpiritInfo> querySpiritConfigs(long actId) {
        return spiritMapper.querySpiritConfigs(actId);
    }

    @Override
    public List<BetSpiritInfo> querySpiritConfigsForAdmin(long actId) {
        return spiritMapper.querySpiritConfigs(actId);
    }

    @Override
    public long updateSpiritConfig(BetSpiritConfig spiritConfig) {
        if(!validSpiritConf(spiritConfig)){
            return 0;
        }
        return spiritMapper.updateSpiritConfig(spiritConfig);
    }

    @Override
    public long updateSpiritConfigWinNum(long spiritId, long actId) {
        return spiritMapper.updateSpiritConfigWinNum(spiritId,actId);
    }

    @Override
    public long updateSpiritConfigLossNum(long spiritId, long actId) {
        return spiritMapper.updateSpiritConfigLossNum(spiritId,actId);
    }

    @Override
    public long updateSpiritConfigAmount(long spiritId, long actId, long totalAmount, long totalAwardAmount) {
        return spiritMapper.updateSpiritConfigAmount(spiritId,actId,totalAmount,totalAwardAmount);
    }
}
