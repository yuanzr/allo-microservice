package com.dc.allo.rank.service.bet.impl;

import com.dc.allo.rank.domain.bet.BetStatistic;
import com.dc.allo.rank.mapper.bet.BetStatisticMapper;
import com.dc.allo.rank.service.bet.BetStatisticService;
import com.dc.allo.rpc.domain.page.AdminPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/6/3.
 */
@Service
public class BetStatisticServiceImpl implements BetStatisticService {

    @Autowired
    BetStatisticMapper statisticMapper;

    @Override
    public long add(BetStatistic statistic) {
        return statisticMapper.add(statistic);
    }

    @Override
    public AdminPage<BetStatistic> page(long actId, Date date, long pageNum, int pageSize) {
        AdminPage<BetStatistic> page = new AdminPage<>();
        page.setTotal(statisticMapper.count(actId,date));
        page.setRows(statisticMapper.page(actId,date,(pageNum - 1) * pageSize, pageSize));
        return page;
    }
}
