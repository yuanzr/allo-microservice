package com.dc.allo.rank.mapper.bet;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.domain.bet.BetGameRound;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/20.
 */
@Mapper
public interface BetGameRoundMapper {

    @Insert("insert into bet_game_round (act_id,spirits,trans_animation_duration,stime,etime,atime,rtime) values (#{gameRound.actId}," +
            "#{gameRound.spirits},#{gameRound.transAnimationDuration},#{gameRound.stime},#{gameRound.etime},#{gameRound.atime},#{gameRound.rtime}) ")
    @Options(useGeneratedKeys = true, keyProperty = "gameRound.id")
    long add(@Param(value = "gameRound") BetGameRound gameRound);

    /**
     * 更新游戏状态
     * @param id
     * @return
     */
    @Update("update bet_game_round set game_status = 2 where id = #{id} ")
    long stopGame(@Param(value = "id") long id);


    @Update("update bet_game_round set game_status = 1 where id = #{id} ")
    long stopBet(@Param(value = "id") long id);


    /**
     * 设置已结算
     * @param id
     * @return
     */
    @Update("update bet_game_round set is_settle = 1 ,win_spirit_id = #{winSpiritId} where id=#{id}  ")
    long settleGameRound(@Param(value = "id") long id, @Param(value = "winSpiritId") long winSpiritId);

    @Update("update bet_game_round set bet_amounts = #{betAmounts} where id=#{id}  ")
    long updateBetAmount(@Param(value = "id") long id, @Param(value = "betAmounts") String betAmounts);

    @Update("update bet_game_round set animations = #{animations} where id=#{id}  ")
    long updateBetAnimations(@Param(value = "id") long id, @Param(value = "animations") String animations);

    @Select("select animations from bet_game_round where id=#{id} ")
    String getAnimations(@Param(value = "id") long id);

    @Select("select * from bet_game_round where id=#{id}")
    BetGameRound get(@Param(value = "id") long id);

    @Select("select count(*) from bet_game_round where act_id = #{actId} and stime>= #{date}")
    long count(@Param(value = "actId") long actId,@Param(value = "date") String date);

    @Select("select * from bet_game_round where act_id = #{actId} and stime>= #{date} order by id desc limit #{offset},#{pageSize}")
    List<BetGameRound> page(@Param(value = "actId") long actId,@Param(value = "date") String date,@Param(value = "offset") long offset,@Param(value = "pageSize") int pageSize);

    @SelectProvider(value = BetGameRoundSqlProvider.class,method = "pageGameRound")
    List<BetGameRound> pageGameRound(@Param(value = "actId") long actId, @Param("id") long id, @Param(value = "pageSize") int pageSize);

    class BetGameRoundSqlProvider{
        public String pageGameRound(@Param(value = "actId") long actId, @Param("id") long id, @Param(value = "pageSize") int pageSize){
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("select * from bet_game_round");
            sqlBuf.append(" where act_id = #{actId} ");
            if (id <= 0) {
                sqlBuf.append(" order by id desc ");
            } else {
                sqlBuf.append(" and id<#{id} order by id desc");
            }
            sqlBuf.append(" limit 0,#{pageSize}");
            return sqlBuf.toString();
        }
    }
}
