package com.dc.allo.rank.mapper.bet;

import com.dc.allo.rank.domain.bet.BetAwardConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@Mapper
public interface BetAwardConfigMapper {

    @Insert(" insert into bet_award_config (act_id,package_id,award_target) values (#{awardConfig.actId}," +
            "#{awardConfig.packageId},#{awardConfig.awardTarget}) ")
    @Options(useGeneratedKeys = true, keyProperty = "awardConfig.id")
    long add(@Param(value = "awardConfig")BetAwardConfig awardConfig);

    @Update(" update bet_award_config set act_id = #{awardConfig.actId} ,package_id = #{awardConfig.packageId} ,award_target = #{awardConfig.awardTarget} where id = #{awardConfig.id}")
    long update(@Param(value = "awardConfig")BetAwardConfig awardConfig);

    @Select(" select * from bet_award_config where act_id = #{actId} ")
    List<BetAwardConfig> queryAwardConfigs(@Param(value = "actId")long actId);
}
