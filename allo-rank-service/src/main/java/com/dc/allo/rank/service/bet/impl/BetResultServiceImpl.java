package com.dc.allo.rank.service.bet.impl;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.BetResult;
import com.dc.allo.rank.domain.bet.BetStatistic;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rank.mapper.bet.BetResultMapper;
import com.dc.allo.rank.service.bet.BetResultService;
import com.dc.allo.rank.service.bet.cache.BetCache;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.page.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Service
@Slf4j
public class BetResultServiceImpl implements BetResultService {


    @Autowired
    BetResultMapper betResultMapper;

    @Autowired
    BetCache betCache;


    private boolean validResult(BetResult betResult){
        boolean flag = false;
        if(betResult!=null&&betResult.getActId()>0
                &&betResult.getGameId()>0
                &&betResult.getUid()>0
                &&betResult.getPayTotalAmount()>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public void check2CreateNextMonthTable() {
        int nextMonthExist = betResultMapper.existNextMonthTable();
        if (nextMonthExist < 1) {
            betResultMapper.createNextMonthTable();
        }
    }

    @Override
    public long saveResult(BetResult betResult) {
        if(!validResult(betResult)){
            return 0;
        }
        try {
            return betResultMapper.add(betResult);
        } catch (Exception e) {
            log.error(BetConstants.ERROR_LOG+"保存中奖纪录失败...",e);
        }
        return 0;
    }

    private long getOffset(int pageNum,int pageSize){
        int offset = 0;
        if(pageSize<=0){
            pageSize = 10;
        }else{
            if(pageSize>100){
                pageSize=100;
            }
        }
        if(pageNum <=0){
            offset = 0;
        }else{
            offset = (pageNum-1)*pageSize;
        }
        return offset;
    }


    @Override
    public Page<BetResult> pageBetResult(long actId, long uid, String month,long id, int pageSize) {
        Page<BetResult> page = new Page<>();
        String tableSuffix = TimeUtils.toStr(new Date(), TimeUtils.YEAR2MONTH4SQL);
        if (StringUtils.isBlank(month)) {
            month = tableSuffix;
        }
        page.setSuffix(month);
        //多取一条记录，判断是否有下页
        List<BetResult> results =  betResultMapper.page(actId,uid,month,id,pageSize+1);
        if (CollectionUtils.isNotEmpty(results)) {
            int size = results.size();
            if (size > pageSize) {
                page.setHasMore(true);
                page.setRows(results.subList(0, size - 1));
            } else {
                page.setHasMore(false);
                page.setRows(results);
            }
            long lastId = results.get(results.size() - 1).getId();
            page.setId(lastId);
        } else {
            page.setHasMore(false);
        }
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public List<BetResultInfo> getBetResult(long actId, long gameId, long uid) {
        List<BetResultInfo> results = betCache.queryBetResult(actId, gameId, uid);
        if(results==null) {
            results = betResultMapper.get(actId, gameId, uid);
            if(results!=null){
                betCache.setBetResult(actId,gameId,uid,results);
            }
        }
        return results;
    }

    @Override
    public long updateResultStatus(long resultId, int awardStatus, String pkgContent,String remark) {
        return betResultMapper.updateResult(resultId,awardStatus,pkgContent,remark);
    }

    @Override
    public List<Long> queryActIds(String date) {
        return betResultMapper.queryActIds(date);
    }

    @Override
    public BetStatistic statistic(long actId, String date) {
        return betResultMapper.statistic(date,actId);
    }

    @Override
    public long statisticWinPay(long actId, String date) {
        return betResultMapper.statisticWinPay(date,actId);
    }

    @Override
    public List<BetSpiritAmount> statisticSpiritByGameId(long actId, long gameId) {
        return betResultMapper.statisticSpiritByGameId(actId,gameId);
    }

    @Override
    public long batchUpdateAnimations(long gameId, String animations) {
        return betResultMapper.batchUpdateAnimations(gameId,animations);
    }
}
