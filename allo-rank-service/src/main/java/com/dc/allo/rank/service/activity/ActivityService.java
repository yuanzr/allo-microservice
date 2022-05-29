package com.dc.allo.rank.service.activity;

import com.dc.allo.rank.domain.activity.Activity;
import com.dc.allo.rpc.domain.activity.*;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/15.
 */
public interface ActivityService {

    long addActivity(Activity activity);

    long updateActivity(Activity activity);

    List<Activity> queryActivity();

    long addActivityBgInfo(ActivityBgInfo bgInfo);

    long updateActivityBgInfo(ActivityBgInfo bgInfo);

    ActivityBgInfo getActivityBgInfo(long actId);

    long addActivityRuleInfo(ActivityRuleInfo ruleInfo);

    long updateActivityRuleInfo(ActivityRuleInfo ruleInfo);

    ActivityRuleInfo getActivityRuleInfo(long actId);

    long delActivityRuleInfo(long actId);

    long addActivityRankInfo(ActivityRankInfo rankInfo);

    long updateActivityRankInfo(ActivityRankInfo rankInfo);

    ActivityRankInfo getActivityRankInfo(long actId);

    long delActivityRankInfo(long actId);

    ActivityInfo getAcitivityInfo(long actId);

    long addActivityGiftInfo(ActivityGiftInfo giftInfo);

    long updateActivityGiftInfo(ActivityGiftInfo giftInfo);

    long deleteActivityGiftInfo(long id);

    List<ActivityGiftInfo> queryGiftInfos(long actId);
}
