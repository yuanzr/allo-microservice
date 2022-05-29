package com.dc.allo.rank.mapper.activity;

import com.dc.allo.rank.domain.activity.*;
import com.dc.allo.rpc.domain.activity.ActivityBgInfo;
import com.dc.allo.rpc.domain.activity.ActivityGiftInfo;
import com.dc.allo.rpc.domain.activity.ActivityRankInfo;
import com.dc.allo.rpc.domain.activity.ActivityRuleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/14.
 */
@Mapper
public interface ActivityMapper {

    @Insert("insert into act_center_activity (app_id,name,remark,type,stime,etime,ctime) values (#{activity.appId}," +
            "#{activity.name},#{activity.remark},#{activity.type},#{activity.stime},#{activity.etime},now()) ")
    @Options(useGeneratedKeys = true, keyProperty = "activity.id")
    long addActivity(@Param(value = "activity") Activity activity);

    @Update("update act_center_activity set name = #{activity.name},remark = #{activity.remark},type=#{activity.type},stime=#{activity.stime},etime=#{activity.etime} where id = #{activity.id}")
    long updateActivity(@Param(value = "activity") Activity activity);

    @Select("select * from act_center_activity order by id desc limit 100")
    List<Activity> queryActivity();

    @Select("select * from act_center_activity where id = #{id} limit 100")
    Activity getActivity(long id);

    @Insert("insert into act_center_activity_bg_info (act_id,bg_urls,bg_color) values (#{bgInfo.actId},#{bgInfo.bgUrls},#{bgInfo.bgColor}) ")
    @Options(useGeneratedKeys = true, keyProperty = "bgInfo.id")
    long addActivityBgInfo(@Param(value = "bgInfo") ActivityBgInfo bgInfo);

    @Update("update act_center_activity_bg_info set act_id = #{bgInfo.actId} ,bg_urls = #{bgInfo.bgUrls},bg_color = #{bgInfo.bgColor} where id = #{bgInfo.id}")
    long updateActivityBgInfo(@Param(value = "bgInfo") ActivityBgInfo bgInfo);

    @Select("select * from act_center_activity_bg_info where act_id = #{actId} limit 1")
    ActivityBgInfo getActivityBgInfo(@Param(value = "actId") long actId);

    @Insert("insert into act_center_activity_rule_info (act_id,style_type,jump_type,jump_url,bg_url) values (#{ruleInfo.actId},#{ruleInfo.styleType}," +
            "#{ruleInfo.jumpType},#{ruleInfo.jumpUrl},#{ruleInfo.bgUrl}) ")
    @Options(useGeneratedKeys = true, keyProperty = "ruleInfo.id")
    long addActivityRuleInfo(@Param(value = "ruleInfo") ActivityRuleInfo ruleInfo);

    @Update("update act_center_activity_rule_info set style_type = #{ruleInfo.styleType},jump_type = #{ruleInfo.jumpType},jump_url = #{ruleInfo.jumpUrl}, " +
            "bg_url = #{ruleInfo.bgUrl} where id = #{ruleInfo.id}")
    long updateActivityRuleInfo(@Param(value = "ruleInfo") ActivityRuleInfo ruleInfo);

    @Select("select * from act_center_activity_rule_info where act_id = #{actId} limit 1")
    ActivityRuleInfo getActivityRuleInfo(@Param(value = "actId") long actId);

    @Delete("delete from act_center_activity_rule_info where act_id = #{actId}")
    long delActivityRuleInfo(@Param(value = "actId") long actId);

    @Insert("insert into act_center_activity_rank_info (act_id,top3_style,bg_color,tab_bg_color" +
            ",tab_bg_color_select,tab_rank_bg_color,tab_rank_bg_color_select,rank_groups,with_one) " +
            "values (#{rankInfo.actId},#{rankInfo.top3Style},#{rankInfo.bgColor},#{rankInfo.tabBgColor}" +
            ",#{rankInfo.tabBgColorSelect},#{rankInfo.tabRankBgColor}" +
            ",#{rankInfo.tabRankBgColorSelect},#{rankInfo.rankGroups},#{rankInfo.withOne}) ")
    @Options(useGeneratedKeys = true, keyProperty = "rankInfo.id")
    long addActivityRankInfo(@Param(value = "rankInfo") ActivityRankInfo rankInfo);

    @Update("update act_center_activity_rank_info set top3_style=#{rankInfo.top3Style} ,bg_color=#{rankInfo.bgColor},tab_bg_color=#{rankInfo.tabBgColor}" +
            ",tab_bg_color_select=#{rankInfo.tabBgColorSelect},tab_rank_bg_color=#{rankInfo.tabRankBgColor}" +
            ",tab_rank_bg_color_select=#{rankInfo.tabRankBgColorSelect},rank_groups=#{rankInfo.rankGroups},with_one=#{rankInfo.withOne} where id = #{rankInfo.id}")
    long updateActivityRankInfo(@Param(value = "rankInfo") ActivityRankInfo rankInfo);

    @Select("select * from act_center_activity_rank_info where act_id = #{actId} limit 1")
    ActivityRankInfo getActivityRankInfo(@Param(value = "actId") long actId);

    @Delete("delete from act_center_activity_rank_info where act_id = #{actId}")
    long delActivityRankInfo(@Param(value = "actId") long actId);

    @Insert("insert into act_center_activity_gift_info (act_id,name,icon,cost) values (#{giftInfo.actId},#{giftInfo.name},#{giftInfo.icon},#{giftInfo.cost}) ")
    @Options(useGeneratedKeys = true, keyProperty = "giftInfo.id")
    long addActivityGiftInfo(@Param(value = "giftInfo") ActivityGiftInfo giftInfo);

    @Update("update act_center_activity_gift_info set act_id = #{giftInfo.actId} ,name = #{giftInfo.name},icon = #{giftInfo.icon},cost = #{giftInfo.cost} where id = #{giftInfo.id}")
    long updateActivityGiftInfo(@Param(value = "giftInfo") ActivityGiftInfo giftInfo);

    @Delete("delete from act_center_activity_gift_info where id = #{id}")
    long delActivityGiftInfo(@Param(value = "id") long id);

    @Select("select * from act_center_activity_gift_info where act_id = #{actId}")
    List<ActivityGiftInfo> queryGiftInfos(@Param(value = "actId")long actId );

}
