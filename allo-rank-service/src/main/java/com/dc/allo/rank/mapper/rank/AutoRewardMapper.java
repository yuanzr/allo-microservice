package com.dc.allo.rank.mapper.rank;

import com.dc.allo.rank.domain.rank.config.RankAutoRewardConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
@Mapper
public interface AutoRewardMapper {

    @Insert("insert into act_center_rank_auto_reward_config (rank_id,award_type,award_package_id,begin,end,limit_score) " +
            "values(#{rewardConfig.rankId},#{rewardConfig.awardType},#{rewardConfig.awardPackageId},#{rewardConfig.begin},#{rewardConfig.end},#{rewardConfig.limitScore})")
    long addConfig(@Param("rewardConfig") RankAutoRewardConfig rewardConfig);

    @UpdateProvider(value = AutoRewardSqlProvider.class,method = "update")
    long updateConfig(@Param("autoRewardConfig") RankAutoRewardConfig autoRewardConfig);

    @Select("select count(*) from act_center_rank_auto_reward_config")
    long countConfig();

    @Select("select * from act_center_rank_auto_reward_config limit #{offset}, #{pageSize}")
    List<RankAutoRewardConfig> pageConfig(@Param("offset")long offset,@Param("pageSize")int pageSize);

    @Select("select * from act_center_rank_auto_reward_config where rank_id = #{rankId}")
    List<RankAutoRewardConfig> queryByRankId(@Param("rankId") long rankId);

    @Insert("insert into act_center_rank_auto_reward_detail (rank_id,rank_key,award_config_id,member_type,member_id,award_result) " +
            "values(#{rankId},#{rankKey},#{awardConfigId},#{memberType},#{memberId},#{awardResult})")
    long addDetail(@Param("rankId") long rankId, @Param("rankKey") String rankKey, @Param("awardConfigId") long awardConfigId, @Param("memberType") int memberType, @Param("memberId") String memberId, @Param("awardResult") boolean awardResult);


    class AutoRewardSqlProvider {

        public String update(@Param("autoRewardConfig") RankAutoRewardConfig autoRewardConfig) {
            StringBuffer sqlBuf = new StringBuffer("update act_center_rank_auto_reward_config set utime = now() ");

            if (autoRewardConfig.getRankId() > 0) {
                sqlBuf.append(" ,rank_id = #{autoRewardConfig.rankId}");
            }
            if (autoRewardConfig.getAwardPackageId() > 0) {
                sqlBuf.append(" ,award_package_id = #{autoRewardConfig.awardPackageId}");
            }
            if (autoRewardConfig.getAwardType() > 0) {
                sqlBuf.append(" ,award_type = #{autoRewardConfig.awardType}");
            }
            if (autoRewardConfig.getBegin() > 0) {
                sqlBuf.append(" ,begin = #{autoRewardConfig.begin}");
            }
            if (autoRewardConfig.getEnd() > 0) {
                sqlBuf.append(" ,end = #{autoRewardConfig.end}");
            }
            if (autoRewardConfig.getLimitScore() >= 0) {
                sqlBuf.append(" ,limit_score = #{autoRewardConfig.limitScore}");
            }
            sqlBuf.append(" where id = #{autoRewardConfig.id}");
            return sqlBuf.toString();
        }
    }
}
