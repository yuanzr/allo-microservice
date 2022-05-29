package com.dc.allo.rank.mapper.bet;

import com.dc.allo.rank.domain.bet.BetActConfig;
import com.dc.allo.rpc.domain.bet.BetAwardInfo;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Mapper
public interface BetActConfigMapper {

    @Insert("insert into bet_act_config (name,game_duration,trans_animation_duration,animation_duration,show_duration,stime,etime,banker_model,bet_model,animation_model,warn_score,deduct_rate,pay_type,remark) values (#{betActConfig.name},#{betActConfig.gameDuration}," +
            "#{betActConfig.transAnimationDuration},#{betActConfig.animationDuration},#{betActConfig.showDuration},#{betActConfig.stime},#{betActConfig.etime}," +
            "#{betActConfig.bankerModel},#{betActConfig.betModel},#{betActConfig.animationModel},#{betActConfig.warnScore},#{betActConfig.deductRate},#{betActConfig.payType},#{betActConfig.remark}) ")
    @Options(useGeneratedKeys = true, keyProperty = "betActConfig.id")
    long add(@Param(value = "betActConfig") BetActConfig betActConfig);

    @UpdateProvider(value = BetActConfMapperProvider.class, method = "updateConfSql")
    long update(@Param(value = "betActConfig") BetActConfig betActConfig);

    @Select("select * from bet_act_config where id=#{id}")
    BetActConfig get(@Param(value = "id") long id);

    @Select("select * from bet_act_config ")
    List<BetActConfig> queryAll();

    @Select(" select config.act_id,config.spirit_id ,config.rate ,spirit.`name` as spiritName,spirit.url,spirit.svga_url,config.odds,config.win_num,config.loss_num,spirit.remark from bet_spirit_config config JOIN" +
            " bet_spirit spirit on config.spirit_id = spirit.id where act_id = #{actId}")
    List<BetSpiritInfo> querySpiritConfigs(@Param(value = "actId") long actId);

    @Select(" select config.award_target,package.id as packageId,package.name as packageName,package.price,package.icon from bet_award_config config left JOIN" +
            " common_award_package package on config.package_id = package.id where config.act_id = #{actId}")
    List<BetAwardInfo> queryAwardConfigs(@Param(value = "actId") long actId);

    class BetActConfMapperProvider {

        public String updateConfSql(@Param(value = "betActConfig") BetActConfig betActConfig) {
            StringBuffer sqlBuf = new StringBuffer("update bet_act_config set utime = now() ");
            if (StringUtils.isNotBlank(betActConfig.getName())) {
                sqlBuf.append(" ,name = #{betActConfig.name}");
            }
            if (betActConfig.getTransAnimationDuration() > 0) {
                sqlBuf.append(" ,trans_animation_duration = #{betActConfig.transAnimationDuration}");
            }
            if (betActConfig.getStime() != null) {
                sqlBuf.append(" ,stime = #{betActConfig.stime} ");
            }
            if (betActConfig.getEtime() != null) {
                sqlBuf.append(" ,etime = #{betActConfig.etime} ");
            }
            if (betActConfig.getGameDuration() > 0) {
                sqlBuf.append(" ,game_duration = #{betActConfig.gameDuration} ");
            }
            if (betActConfig.getAnimationDuration() > 0) {
                sqlBuf.append(" ,animation_duration = #{betActConfig.animationDuration} ");
            }
            if (betActConfig.getShowDuration() > 0) {
                sqlBuf.append(" ,show_duration = #{betActConfig.showDuration} ");
            }
            if (betActConfig.getBankerModel() >= 0) {
                sqlBuf.append(" ,banker_model = #{betActConfig.bankerModel} ");
            }
            if (betActConfig.getBetModel() >= 0) {
                sqlBuf.append(" ,bet_model = #{betActConfig.betModel} ");
            }
            if (betActConfig.getWarnScore() >= 0) {
                sqlBuf.append(" ,warn_score = #{betActConfig.warnScore} ");
            }
            if (betActConfig.getDeductRate() >= 0) {
                sqlBuf.append(" ,deduct_rate = #{betActConfig.deductRate}");
            }
            if (betActConfig.getPayType() >= 0) {
                sqlBuf.append(" ,pay_type = #{betActConfig.payType} ");
            }
            if (StringUtils.isNotBlank(betActConfig.getRemark())) {
                sqlBuf.append(" ,remark = #{betActConfig.remark} ");
            }
            if (StringUtils.isNotBlank(betActConfig.getPayPrices())) {
                sqlBuf.append(" ,pay_prices = #{betActConfig.payPrices} ");
            }
            if (betActConfig.getJoinSpiritNum() >= 0) {
                sqlBuf.append(" ,join_spirit_num = #{betActConfig.joinSpiritNum} ");
            }
            sqlBuf.append(" where id=#{betActConfig.id} ");
            return sqlBuf.toString();
        }
    }

}
