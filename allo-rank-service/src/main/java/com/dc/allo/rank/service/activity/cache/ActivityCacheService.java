package com.dc.allo.rank.service.activity.cache;

import com.dc.allo.rank.domain.activity.Activity;
import com.dc.allo.rpc.domain.activity.ActivityBgInfo;
import com.dc.allo.rpc.domain.activity.ActivityGiftInfo;
import com.dc.allo.rpc.domain.activity.ActivityRankInfo;
import com.dc.allo.rpc.domain.activity.ActivityRuleInfo;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/23.
 */
public interface ActivityCacheService {

    Activity getActivity(long actId);

    ActivityBgInfo getActivityBgInfo(long actId);

    ActivityRuleInfo getActivityRuleInfo(long actId);

    ActivityRankInfo getActivityRankInfo(long actId);

    List<ActivityGiftInfo> queryActivityGiftInfos(long actId);
}
