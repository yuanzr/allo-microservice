package com.dc.allo.rank.service.activity.Impl;

import com.dc.allo.rank.domain.activity.Activity;
import com.dc.allo.rank.service.activity.cache.ActivityCacheService;
import com.dc.allo.rpc.domain.activity.*;
import com.dc.allo.rank.mapper.activity.ActivityMapper;
import com.dc.allo.rank.service.activity.ActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/15.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    ActivityCacheService activityCacheService;

    private boolean validActivity(Activity activity){
        if (activity == null || activity.getStime() == null || activity.getEtime() == null || StringUtils.isBlank(activity.getName())) {
            return false;
        }
        return true;
    }

    private boolean validActivityBgInfo(ActivityBgInfo bgInfo){
        if(bgInfo ==null ||bgInfo.getActId()<=0||StringUtils.isBlank(bgInfo.getBgUrls())){
            return false;
        }
        return true;
    }

    private boolean validActivityRuleInfo(ActivityRuleInfo ruleInfo){
        if(ruleInfo ==null||ruleInfo.getActId()<=0){
            return false;
        }
        return true;
    }

    private boolean validActivityRankInfo(ActivityRankInfo rankInfo){
        if(rankInfo == null||rankInfo.getActId()<=0){
            return false;
        }
        return true;
    }

    @Override
    public long addActivity(Activity activity) {
        if(!validActivity(activity)){
            return 0;
        }
        return activityMapper.addActivity(activity);
    }

    @Override
    public long updateActivity(Activity activity) {
        if(!validActivity(activity)||activity.getId()<=0){
            return 0;
        }
        return activityMapper.updateActivity(activity);
    }

    @Override
    public List<Activity> queryActivity() {
        return activityMapper.queryActivity();
    }

    @Override
    public long addActivityBgInfo(ActivityBgInfo bgInfo) {
        if(!validActivityBgInfo(bgInfo)){
            return 0;
        }
        return activityMapper.addActivityBgInfo(bgInfo);
    }

    @Override
    public long updateActivityBgInfo(ActivityBgInfo bgInfo) {
        if(!validActivityBgInfo(bgInfo)||bgInfo.getId()<=0){
            return 0;
        }
        return activityMapper.updateActivityBgInfo(bgInfo);
    }

    @Override
    public ActivityBgInfo getActivityBgInfo(long actId) {
        return activityMapper.getActivityBgInfo(actId);
    }

    @Override
    public long addActivityRuleInfo(ActivityRuleInfo ruleInfo) {
        if(!validActivityRuleInfo(ruleInfo)){
            return 0;
        }
        return activityMapper.addActivityRuleInfo(ruleInfo);
    }

    @Override
    public long updateActivityRuleInfo(ActivityRuleInfo ruleInfo) {
        if(!validActivityRuleInfo(ruleInfo)||ruleInfo.getId()<=0){
            return 0;
        }
        return activityMapper.updateActivityRuleInfo(ruleInfo);
    }

    @Override
    public ActivityRuleInfo getActivityRuleInfo(long actId) {
        return activityMapper.getActivityRuleInfo(actId);
    }

    @Override
    public long delActivityRuleInfo(long actId) {
        return activityMapper.delActivityRuleInfo(actId);
    }

    @Override
    public long addActivityRankInfo(ActivityRankInfo rankInfo) {
        if(!validActivityRankInfo(rankInfo)){
            return 0;
        }
        return activityMapper.addActivityRankInfo(rankInfo);
    }

    @Override
    public long updateActivityRankInfo(ActivityRankInfo rankInfo) {
        if(!validActivityRankInfo(rankInfo)||rankInfo.getId()<=0){
            return 0;
        }
        return activityMapper.updateActivityRankInfo(rankInfo);
    }

    @Override
    public ActivityRankInfo getActivityRankInfo(long actId) {
        return activityMapper.getActivityRankInfo(actId);
    }

    @Override
    public long delActivityRankInfo(long actId) {
        return activityMapper.delActivityRankInfo(actId);
    }

    @Override
    public ActivityInfo getAcitivityInfo(long actId) {
        Activity activity = activityCacheService.getActivity(actId);
        if(activity == null){
            return null;
        }
        ActivityBgInfo bgInfo = activityCacheService.getActivityBgInfo(actId);
        ActivityRuleInfo ruleInfo = activityCacheService.getActivityRuleInfo(actId);
        ActivityRankInfo rankInfo = activityCacheService.getActivityRankInfo(actId);
        List<ActivityGiftInfo> giftInfos = activityCacheService.queryActivityGiftInfos(actId);
        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.setId(activity.getId());
        activityInfo.setName(activity.getName());
        activityInfo.setAppId(activity.getAppId());
        activityInfo.setStime(activity.getStime());
        activityInfo.setEtime(activity.getEtime());
        activityInfo.setRemark(activity.getRemark());
        activityInfo.setType(activity.getType());
        activityInfo.setActivityBgInfo(bgInfo);
        activityInfo.setActivityRuleInfo(ruleInfo);
        activityInfo.setActivityRankInfo(rankInfo);
        activityInfo.setActivityGiftInfos(giftInfos);
        return activityInfo;
    }

    @Override
    public long addActivityGiftInfo(ActivityGiftInfo giftInfo) {
        return activityMapper.addActivityGiftInfo(giftInfo);
    }

    @Override
    public long updateActivityGiftInfo(ActivityGiftInfo giftInfo) {
        return activityMapper.updateActivityGiftInfo(giftInfo);
    }

    @Override
    public long deleteActivityGiftInfo(long id) {
        return activityMapper.delActivityGiftInfo(id);
    }

    @Override
    public List<ActivityGiftInfo> queryGiftInfos(long actId) {
        return activityMapper.queryGiftInfos(actId);
    }
}
