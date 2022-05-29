package com.dc.allo.rank.mapper.bet;

import com.dc.allo.common.utils.TableUtils;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.domain.bet.BetResult;
import com.dc.allo.rank.domain.bet.BetStatistic;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Mapper
public interface BetResultMapper {

    /**
     * 判断是否存在下月表
     * @return
     */
    @SelectProvider(value = BetResultSqlProvider.class,method = "existNextMonthTable")
    int existNextMonthTable();

    /**
     * 创建下月表
     */
    @InsertProvider(value = BetResultSqlProvider.class,method = "createNextMonthTable")
    void createNextMonthTable();

    @InsertProvider(value = BetResultSqlProvider.class,method = "add")
    @Options(useGeneratedKeys = true, keyProperty = "betResult.id")
    long add(@Param(value = "betResult") BetResult betResult);

    @UpdateProvider(value = BetResultSqlProvider.class,method = "update")
    long updateResult(@Param(value = "resultId") long resultId, @Param(value = "awardStatus") int awardStatus, @Param(value = "pkgContent") String pkgContent, @Param(value = "remark") String remark);

    @UpdateProvider(value = BetResultSqlProvider.class,method = "batchUpdateAnimations")
    long batchUpdateAnimations(@Param(value = "gameId") long gameId,@Param(value = "animations")String animations);

    @SelectProvider(value = BetResultSqlProvider.class,method = "page")
    List<BetResult> page(@Param(value = "actId") long actId, @Param(value = "uid") long uid, @Param(value = "month") String month,@Param("id") long id, @Param(value = "pageSize") int pageSize);

    @SelectProvider(value = BetResultSqlProvider.class,method = "query")
    List<BetResultInfo> get(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId, @Param(value = "uid") long uid);

    @SelectProvider(value = BetResultSqlProvider.class,method = "statistic")
    BetStatistic statistic(@Param(value = "date")String date ,@Param(value = "actId")long actId);

    @SelectProvider(value = BetResultSqlProvider.class,method = "statisticWinPay")
    long statisticWinPay(@Param(value = "date")String date ,@Param(value = "actId")long actId);

    @SelectProvider(value = BetResultSqlProvider.class,method = "queryActIds")
    List<Long> queryActIds(@Param(value = "date")String date);

    @SelectProvider(value = BetResultSqlProvider.class,method = "statisticSpiritByGameId")
    List<BetSpiritAmount> statisticSpiritByGameId(@Param(value = "actId")long actId,@Param(value = "gameId")long gameId);

    class BetResultSqlProvider{

        public String existNextMonthTable(){
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer(" select count(1)as cnt from information_schema.TABLES where table_name = 'bet_result_").append(tableSuffix).append("'");
            return sqlBuf.toString();
        }

        public String createNextMonthTable(){
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer("create table bet_result_").append(tableSuffix).append(" like bet_result");
            return sqlBuf.toString();
        }

        public String add(@Param(value = "betResult") BetResult betResult){
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" insert into bet_result_").append(tableSuffix);
            sqlBuf.append(" (act_id,game_id,uid,spirit_id,win_spirit_id,is_win,pay_total_amount,take_back_amount,award_amount,remark,ctime) values (#{betResult.actId},")
                    .append(" #{betResult.gameId},#{betResult.uid},#{betResult.spiritId},#{betResult.winSpiritId},")
                    .append(" #{betResult.isWin},#{betResult.payTotalAmount},#{betResult.takeBackAmount},#{betResult.awardAmount},#{betResult.remark},now()) ");
            return sqlBuf.toString();
        }

        public String update(@Param(value = "resultId") long resultId, @Param(value = "awardStatus") int awardStatus, @Param(value = "pkgContent") String pkgContent, @Param(value = "remark") String remark){
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" update bet_result_").append(tableSuffix);
            sqlBuf.append(" set award_status = #{awardStatus},package_content=#{pkgContent} ,remark = #{remark} where id = #{resultId}");
            return sqlBuf.toString();
        }

        public String query(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId, @Param(value = "uid") long uid){
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" select spirit_id,is_win,pay_total_amount,take_back_amount,award_amount,package_content as awards from bet_result_").append(tableSuffix);
            sqlBuf.append(" where act_id = #{actId} and game_id = #{gameId} and uid = #{uid}");
            return sqlBuf.toString();
        }

        public String page(@Param(value = "actId") long actId, @Param(value = "uid") long uid, @Param(value = "month") String month,@Param("id") long id, @Param(value = "pageSize") int pageSize){
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TimeUtils.toStr(new Date(), TimeUtils.YEAR2MONTH4SQL);
            if (StringUtils.isBlank(month)) {
                month = tableSuffix;
            }
            sqlBuf.append("select * from bet_result_").append(month);
            sqlBuf.append(" where act_id = #{actId} and uid = #{uid} ");
            if (id <= 0) {
                sqlBuf.append(" order by id desc ");
            } else {
                sqlBuf.append(" and id<#{id} order by id desc");
            }
            sqlBuf.append(" limit 0,#{pageSize}");
            return sqlBuf.toString();
        }

        public String batchUpdateAnimations(@Param(value = "gameId") long gameId,@Param(value = "animations")String animations){
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append("update bet_result_").append(tableSuffix).append(" set animations = #{animations} where game_id = #{gameId}");
            return sqlBuf.toString();
        }

        public String queryActIds(@Param(value = "date")String date) throws ParseException {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TimeUtils.toStr(TimeUtils.fromStrDate(date), TimeUtils.YEAR2MONTH4SQL);
            sqlBuf.append(" select act_id from bet_result_").append(tableSuffix).append(" where DATE_FORMAT( ctime, '%Y-%m-%d' ) = #{date}  GROUP BY act_id");
            return sqlBuf.toString();
        }

        public String statistic(@Param(value = "date")String date ,@Param(value = "actId")long actId) throws ParseException {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TimeUtils.toStr(TimeUtils.fromStrDate(date), TimeUtils.YEAR2MONTH4SQL);
            sqlBuf.append("select act_id,count( game_id ) total_game_rounds, sum( pay_total_amount ) total_pay_amounts," +
                    "sum( award_amount ) total_award_amounts,DATE_FORMAT( ctime, '%Y-%m-%d' ) ctime from bet_result_").append(tableSuffix);
            sqlBuf.append(" where act_id = #{actId} AND DATE_FORMAT( ctime, '%Y-%m-%d' ) = #{date}");
            return sqlBuf.toString();
        }

        public String statisticWinPay(@Param(value = "date")String date ,@Param(value = "actId")long actId) throws ParseException {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TimeUtils.toStr(TimeUtils.fromStrDate(date), TimeUtils.YEAR2MONTH4SQL);
            sqlBuf.append("select sum( pay_total_amount ) total_win_amounts from bet_result_").append(tableSuffix);
            sqlBuf.append(" where act_id = #{actId} AND DATE_FORMAT( ctime, '%Y-%m-%d' ) = #{date} and award_amount>0 ");
            return sqlBuf.toString();
        }

        public String statisticSpiritByGameId(@Param(value = "actId")long actId,@Param(value = "gameId")long gameId) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append("select spirit_id,sum(pay_total_amount) total_amount,sum(award_amount) total_award_amount from bet_result_").append(tableSuffix);
            sqlBuf.append(" where act_id = #{actId} and game_id = #{gameId} group by spirit_id");
            return sqlBuf.toString();
        }
    }
}
