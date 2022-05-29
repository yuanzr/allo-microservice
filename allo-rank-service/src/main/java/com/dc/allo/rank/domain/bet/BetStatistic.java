package com.dc.allo.rank.domain.bet;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/6/3.
 */
@Data
public class BetStatistic {

    private long id;
    private long actId;
    private long totalGameRounds;
    private long totalPayAmounts;
    private long totalWinAmounts;
    private long totalAwardAmounts;
    private String ctime;
}
