package com.dc.allo.rank.mapper.bet;

import com.dc.allo.common.utils.TableUtils;
import com.dc.allo.rank.domain.bet.BetDetail;
import com.dc.allo.rank.domain.bet.vo.BetSpiritAmount;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Mapper
public interface BetDetailMapper {
    /**
     * 判断是否存在下月表
     *
     * @return
     */
    @SelectProvider(value = BetDetailMapperProvider.class, method = "existNextMonthTable")
    int existNextMonthTable();

    /**
     * 创建下月表
     */
    @InsertProvider(value = BetDetailMapperProvider.class, method = "createNextMonthTable")
    void createNextMonthTable();

    /**
     * 投注
     *
     * @param betDetail
     * @return
     */
    @InsertProvider(value = BetDetailMapperProvider.class, method = "add")
    @Options(useGeneratedKeys = true, keyProperty = "betDetail.id")
    long add(@Param(value = "betDetail") BetDetail betDetail);

    /**
     * 更新投注结果
     *
     * @param detailId
     * @param payStatus
     * @param remark
     * @return
     */
    @UpdateProvider(value = BetDetailMapperProvider.class, method = "update")
    long updateDetailResult(@Param(value = "detailId") long detailId, @Param(value = "payStatus") int payStatus, @Param(value = "remark") String remark);

    /**
     * 统计某场游戏每个精灵累计分值
     *
     * @param actId
     * @param gameId
     * @return
     */
    @SelectProvider(value = BetDetailMapperProvider.class, method = "statisticAmount")
    List<BetSpiritAmount> statisticAmount(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId);

    /**
     * 统计某场游戏每个精灵每个用户累计分值
     *
     * @param actId
     * @param gameId
     * @return
     */
    @SelectProvider(value = BetDetailMapperProvider.class, method = "statisticUidAmount")
    List<BetSpiritAmount> statisticUidAmount(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId);

    /**
     * 统计某场单个用户每个精灵投注分值
     * @param actId
     * @param gameId
     * @param uid
     * @return
     */
    @SelectProvider(value = BetDetailMapperProvider.class, method = "statisticAmountByUid")
    List<BetSpiritAmount> statisticAmountByUid(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId, @Param(value = "uid") long uid);


    class BetDetailMapperProvider {

        public String existNextMonthTable() {
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer(" select count(1)as cnt from information_schema.TABLES where table_name = 'bet_detail_").append(tableSuffix).append("'");
            return sqlBuf.toString();
        }

        public String createNextMonthTable() {
            String tableSuffix = TableUtils.getNextMonthTableSuffix();
            StringBuffer sqlBuf = new StringBuffer("create table bet_detail_").append(tableSuffix).append(" like bet_detail");
            return sqlBuf.toString();
        }

        public String add(@Param(value = "betDetail") BetDetail betDetail) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" insert into bet_detail_").append(tableSuffix);
            sqlBuf.append(" (act_id,game_id,spirit_id,uid,pay_price,pay_status,pay_seq_id,ctime) values (#{betDetail.actId},")
                    .append("#{betDetail.gameId},#{betDetail.spiritId},#{betDetail.uid},#{betDetail.payPrice},")
                    .append("#{betDetail.payStatus},#{betDetail.paySeqId},now()) ");
            return sqlBuf.toString();
        }

        public String update(@Param(value = "detailId") long detailId, @Param(value = "payStatus") int payStatus, @Param(value = "remark") String remark) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" update bet_detail_").append(tableSuffix);
            sqlBuf.append(" set pay_status = #{payStatus} ,remark = #{remark} , utime = now() where id = #{detailId} ");
            return sqlBuf.toString();
        }

        public String statisticAmount(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" select spirit_id,sum(pay_price) as totalAmount from bet_detail_").append(tableSuffix);
            sqlBuf.append("  where act_id= #{actId} and game_id = #{gameId} and pay_status =1 group by spirit_id ");
            return sqlBuf.toString();
        }

        public String statisticUidAmount(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" select spirit_id,uid,sum(pay_price) as totalAmount from bet_detail_").append(tableSuffix);
            sqlBuf.append("  where act_id= #{actId} and game_id = #{gameId} and pay_status =1 group by spirit_id,uid ");
            return sqlBuf.toString();
        }

        public String statisticAmountByUid(@Param(value = "actId") long actId, @Param(value = "gameId") long gameId, @Param(value = "uid") long uid) {
            StringBuffer sqlBuf = new StringBuffer();
            String tableSuffix = TableUtils.getMonthTableSuffix();
            sqlBuf.append(" select spirit_id,uid,sum(pay_price) as totalAmount from bet_detail_").append(tableSuffix);
            sqlBuf.append("  where act_id= #{actId} and game_id = #{gameId} and uid=#{uid} and pay_status =1 group by spirit_id ");
            return sqlBuf.toString();
        }
    }
}
