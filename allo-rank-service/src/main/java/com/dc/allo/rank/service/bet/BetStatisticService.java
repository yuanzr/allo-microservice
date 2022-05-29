package com.dc.allo.rank.service.bet;

import com.dc.allo.rank.domain.bet.BetStatistic;
import com.dc.allo.rpc.domain.page.AdminPage;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/6/3.
 */
public interface BetStatisticService {

    /**
     * 添加
     * @param statistic
     * @return
     */
    long add(BetStatistic statistic);

    /**
     * 翻页
     * @param actId
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    AdminPage<BetStatistic> page(long actId,Date date,long pageNum,int pageSize);
}
