package com.dc.allo.rank.mapper.bet;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.domain.bet.BetStatistic;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/6/3.
 */
@Mapper
public interface BetStatisticMapper {

    @Insert(" insert into bet_statistic (act_id,total_game_rounds,total_pay_amounts,total_award_amounts,total_win_amounts,ctime) values(" +
            " #{statistic.actId},#{statistic.totalGameRounds},#{statistic.totalPayAmounts},#{statistic.totalAwardAmounts}," +
            " #{statistic.totalWinAmounts},#{statistic.ctime})")
    @Options(useGeneratedKeys = true, keyProperty = "statistic.id")
    long add(@Param(value = "statistic")BetStatistic statistic);

    @SelectProvider(value = BetStatisticSqlProvider.class,method = "count")
    long count(@Param(value = "actId") long actId, @Param(value = "date") Date date);

    @SelectProvider(value = BetStatisticSqlProvider.class,method = "page")
    List<BetStatistic> page(@Param(value = "actId") long actId, @Param(value = "date") Date date, @Param("offset") long offset, @Param(value = "pageSize") int pageSize);

    class BetStatisticSqlProvider{
        public String count(@Param(value = "actId") long actId, @Param(value = "date") Date date){
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("select count(*) from bet_statistic ");
            if(date == null){
                date = TimeUtils.offsetDay(new Date(),-10);
            }
            sqlBuf.append(" where ctime >= #{date} and act_id = #{actId} ");
            return sqlBuf.toString();
        }

        public String page(@Param(value = "actId") long actId, @Param(value = "date") Date date, @Param("offset") long offset, @Param(value = "pageSize") int pageSize){
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("select * from bet_statistic ");
            if(date == null){
                date = TimeUtils.offsetDay(new Date(),-10);
            }
            sqlBuf.append(" where ctime >= #{date} and act_id = #{actId} ");
            sqlBuf.append(" order by id desc ");
            sqlBuf.append(" limit #{offset},#{pageSize}");
            return sqlBuf.toString();
        }
    }
}
