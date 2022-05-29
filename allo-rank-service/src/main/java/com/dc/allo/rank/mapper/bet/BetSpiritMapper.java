package com.dc.allo.rank.mapper.bet;

import com.dc.allo.rank.domain.bet.BetSpirit;
import com.dc.allo.rank.domain.bet.BetSpiritConfig;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Mapper
public interface BetSpiritMapper {

    @Insert("insert into bet_spirit (name,url,svga_url,remark) values(#{spirit.name},#{spirit.url},#{spirit.svgaUrl},#{spirit.remark})")
    @Options(useGeneratedKeys = true, keyProperty = "spirit.id")
    long addSpirit(@Param(value = "spirit")BetSpirit spirit);

    @UpdateProvider(value = BetSpiritMapperProvider.class,method = "updateSpirit")
    long updateSpirit(@Param(value = "spirit")BetSpirit spirit);

    @Select("select count(*) from bet_spirit")
    long countSpirit();

    @Select("select * from bet_spirit limit #{offset},#{pageSize}")
    List<BetSpirit> pageSpirit(@Param(value = "offset")long offset,@Param(value = "pageSize")int pageSize);

    @Insert("insert into bet_spirit_config (act_id,spirit_id,rate,odds,win_num,loss_num,ctime) values(#{spiritConfig.actId},#{spiritConfig.spiritId},#{spiritConfig.rate},#{spiritConfig.odds},#{spiritConfig.winNum},#{spiritConfig.lossNum},now())")
    @Options(useGeneratedKeys = true, keyProperty = "spiritConfig.id")
    long addSpiritConfig(@Param(value = "spiritConfig")BetSpiritConfig spiritConfig);

    @SelectProvider(value = BetSpiritMapperProvider.class,method = "querySpiritConfigs")
    List<BetSpiritInfo> querySpiritConfigs(@Param(value = "actId")long actId);

    @UpdateProvider(value = BetSpiritMapperProvider.class,method = "updateConfSql")
    long updateSpiritConfig(@Param(value = "spiritConfig")BetSpiritConfig spiritConfig);

    @Update("update bet_spirit_config set win_num = win_num + 1 where spirit_id = #{spiritId} and act_id=#{actId}  ")
    long updateSpiritConfigWinNum(@Param(value = "spiritId")long spiritId,@Param(value = "actId")long actId);

    @Update("update bet_spirit_config set loss_num = loss_num + 1 where spirit_id = #{spiritId} and act_id=#{actId} ")
    long updateSpiritConfigLossNum(@Param(value = "spiritId")long spiritId,@Param(value = "actId")long actId);

    @Update("update bet_spirit_config set total_amount = total_amount +  #{totalAmount}, total_award_amount = total_award_amount +  #{totalAwardAmount} where spirit_id = #{spiritId} and act_id=#{actId} ")
    long updateSpiritConfigAmount(@Param(value = "spiritId")long spiritId,@Param(value = "actId")long actId,@Param(value = "totalAmount")long totalAmount,@Param(value = "totalAwardAmount")long totalAwardAmount);

    class BetSpiritMapperProvider {

        public String updateSpirit(@Param(value = "spirit")BetSpirit spirit){
            StringBuffer sqlBuf = new StringBuffer("update bet_spirit set utime = now() ");
            if(StringUtils.isNotBlank(spirit.getName())){
                sqlBuf.append(" ,name = #{spirit.name}");
            }
            if(StringUtils.isNotBlank(spirit.getUrl())){
                sqlBuf.append(" ,url = #{spirit.url}");
            }
            if(StringUtils.isNotBlank(spirit.getSvgaUrl())){
                sqlBuf.append(" ,svga_url = #{spirit.svgaUrl}");
            }
            if(StringUtils.isNotBlank(spirit.getRemark())){
                sqlBuf.append(" ,remark = #{spirit.remark}");
            }
            sqlBuf.append(" ,svga_url = #{spirit.svgaUrl}");
            sqlBuf.append(" where id = #{spirit.id}");
            return sqlBuf.toString();
        }

        public String querySpiritConfigs(@Param(value = "actId")long actId){
            StringBuffer sqlBuf = new StringBuffer("select config.act_id,config.spirit_id ,config.rate ,spirit.`name` as spiritName,spirit.url,spirit.svga_url,config.odds,config.win_num,config.loss_num, config.total_amount, config.total_award_amount from bet_spirit_config config JOIN" +
                    " bet_spirit spirit on config.spirit_id = spirit.id where 1=1 ");
            if(actId>0){
                sqlBuf.append(" and act_id = #{actId}");
            }
            return sqlBuf.toString();
        }

        public String updateConfSql(@Param(value = "spiritConfig")BetSpiritConfig spiritConfig){
            StringBuffer sqlBuf = new StringBuffer("update bet_spirit_config set utime = now() ");
            if(spiritConfig.getRate()>0){
                sqlBuf.append(" ,rate = #{spiritConfig.rate} ");
            }
            if(spiritConfig.getOdds()>0){
                sqlBuf.append(" ,odds = #{spiritConfig.odds} ");
            }
            sqlBuf.append(" where spirit_id = #{spiritConfig.spiritId} and act_id=#{spiritConfig.actId} ");
            return sqlBuf.toString();
        }
    }
}
