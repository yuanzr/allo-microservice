package com.dc.allo.rank.test;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.Application;
import com.dc.allo.rank.domain.activity.*;
import com.dc.allo.rank.service.activity.ActivityService;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.rpc.domain.activity.ActivityBgInfo;
import com.dc.allo.rpc.domain.activity.ActivityRankInfo;
import com.dc.allo.rpc.domain.activity.ActivityRuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class ActivityTest {

    @Autowired
    ActivityService activityService;

    @Test
    public void testAddActivity(){
        Activity activity = new Activity();
        activity.setAppId(1);
        activity.setName("榜单活动");
        activity.setRemark("test rank ");
        activity.setType(1);
        activity.setStime(new Date());
        activity.setEtime(TimeUtils.offsetDay(new Date(), 10));
        activityService.addActivity(activity);
    }

    @Test
    public void testUpdateActivity(){
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setAppId(1);
        activity.setName("榜单活动1");
        activity.setRemark("test rank1 ");
        activity.setType(1);
        activity.setStime(new Date());
        activity.setEtime(TimeUtils.offsetDay(new Date(), 20));
        activityService.updateActivity(activity);
    }

    @Test
    public void queryActivity(){
        System.out.println(activityService.queryActivity());
    }

    @Test
    public void testAddBgInfo(){
        ActivityBgInfo bgInfo = new ActivityBgInfo();
        bgInfo.setActId(1);
        bgInfo.setBgUrls("{\"en_bg_url\":\"en.url\",\"ar_bg_url\":\"ar.url\",\"in_bg_url\":\"in.url\"}");
        activityService.addActivityBgInfo(bgInfo);
    }

    @Test
    public void testUpdateBgInfo(){
        ActivityBgInfo bgInfo = new ActivityBgInfo();
        bgInfo.setActId(1);
        bgInfo.setId(1);
        bgInfo.setBgUrls("{\"en_bg_url\":\"en1.url\",\"ar_bg_url\":\"ar1.url\",\"in_bg_url\":\"in1.url\"}");
        activityService.updateActivityBgInfo(bgInfo);
    }

    @Test
    public void testGetBgInfo(){
        System.out.println(activityService.getActivityBgInfo(1));
    }

    @Test
    public void testAddRuleInfo(){
        ActivityRuleInfo ruleInfo = new ActivityRuleInfo();
        ruleInfo.setActId(1);
        ruleInfo.setBgUrl("bgurl");
        ruleInfo.setJumpType(0);
        ruleInfo.setJumpUrl("jumpUrl");
        ruleInfo.setStyleType(1);
//        ruleInfo.setRemark("悬浮窗文案");
        activityService.addActivityRuleInfo(ruleInfo);
    }

    @Test
    public void testUpdateRuleInfo(){
        ActivityRuleInfo ruleInfo = new ActivityRuleInfo();
        ruleInfo.setId(1);
        ruleInfo.setActId(1);
        ruleInfo.setBgUrl("bgurl1");
        ruleInfo.setJumpType(0);
        ruleInfo.setJumpUrl("jumpUrl1");
        ruleInfo.setStyleType(1);
//        ruleInfo.setRemark("悬浮窗文案1");
        activityService.updateActivityRuleInfo(ruleInfo);
    }

    @Test
    public void testGetRuleInfo(){
        System.out.println(activityService.getActivityRuleInfo(1));
    }

    @Test
    public void testAddRankInfo(){
        ActivityRankInfo rankInfo = new ActivityRankInfo();
        rankInfo.setActId(1);
//        rankInfo.setBgUrl("bgurl");
        rankInfo.setBgColor("bgColor");
        rankInfo.setTabBgColor("tabBgColor");
//        rankInfo.setTabBgUrl("tabBgUrl");
        rankInfo.setTabBgColorSelect("tabBgColorSelect");
//        rankInfo.setTabBgUrlSelect("tabBgUrlSelect");
//        rankInfo.setTabRankBgUrl("tabRankBgUrl");
//        rankInfo.setTabRankBgUrlSelect("tabRankBgUrlSelect");
        rankInfo.setTabRankBgColor("tabRankColor");
        rankInfo.setTabRankBgColorSelect("tabRankColorSelect");
        ActivityRankTop3Style top3Style = new ActivityRankTop3Style();
        top3Style.setRostrumUrl("rostRumUrl");
        top3Style.setTop1Url("top1Url");
        top3Style.setTop2Url("top2Url");
        top3Style.setTop3Url("top3Url");
        rankInfo.setTop3Style(JsonUtils.toJson(top3Style));
        ActivityRankGroupInfo groupInfo = new ActivityRankGroupInfo();
        groupInfo.setLabel("收礼榜");
        ActivityRankTypeInfo typeInfo = new ActivityRankTypeInfo();
        typeInfo.setLabel("日榜");
        typeInfo.setRankKey("bet-camel-day");
        ActivityRankTypeInfo typeInfo1 = new ActivityRankTypeInfo();
        typeInfo1.setLabel("总榜");
        typeInfo1.setRankKey("bet-camel-total");
        List<ActivityRankTypeInfo> typeInfoList = new ArrayList<>();
        typeInfoList.add(typeInfo);
        typeInfoList.add(typeInfo1);
        groupInfo.setRankTypes(JsonUtils.toJson(typeInfoList));

        ActivityRankGroupInfo groupInfo1 = new ActivityRankGroupInfo();
        groupInfo1.setLabel("送礼榜");
        groupInfo1.setRankTypes(JsonUtils.toJson(typeInfoList));

        List<ActivityRankGroupInfo> groupInfos = new ArrayList<>(2);
        groupInfos.add(groupInfo);
        groupInfos.add(groupInfo1);

        rankInfo.setRankGroups(JsonUtils.toJson(groupInfos));
        activityService.addActivityRankInfo(rankInfo);

    }

    @Test
    public void testUpdateRankInfo(){
        ActivityRankInfo rankInfo = new ActivityRankInfo();
        rankInfo.setId(1);
        rankInfo.setActId(1);
//        rankInfo.setBgUrl("bgurl2");
        rankInfo.setBgColor("bgColor2");
        rankInfo.setTabBgColor("tabBgColor2");
//        rankInfo.setTabBgUrl("tabBgUrl2");
        rankInfo.setTabBgColorSelect("tabBgColorSelect2");
//        rankInfo.setTabBgUrlSelect("tabBgUrlSelect2");
//        rankInfo.setTabRankBgUrl("tabRankBgUrl2");
//        rankInfo.setTabRankBgUrlSelect("tabRankBgUrlSelect2");
        rankInfo.setTabRankBgColor("tabRankColor2");
        rankInfo.setTabRankBgColorSelect("tabRankColorSelect2");
        ActivityRankTop3Style top3Style = new ActivityRankTop3Style();
        top3Style.setRostrumUrl("rostRumUrl");
        top3Style.setTop1Url("top1Url");
        top3Style.setTop2Url("top2Url");
        top3Style.setTop3Url("top3Url");
        rankInfo.setTop3Style(JsonUtils.toJson(top3Style));
        ActivityRankGroupInfo groupInfo = new ActivityRankGroupInfo();
        groupInfo.setLabel("收礼榜");
        ActivityRankTypeInfo typeInfo = new ActivityRankTypeInfo();
        typeInfo.setLabel("日榜");
        typeInfo.setRankKey("bet-camel-day");
        ActivityRankTypeInfo typeInfo1 = new ActivityRankTypeInfo();
        typeInfo1.setLabel("总榜");
        typeInfo1.setRankKey("bet-camel-total");
        List<ActivityRankTypeInfo> typeInfoList = new ArrayList<>();
        typeInfoList.add(typeInfo);
        typeInfoList.add(typeInfo1);
        groupInfo.setRankTypes(JsonUtils.toJson(typeInfoList));

        ActivityRankGroupInfo groupInfo1 = new ActivityRankGroupInfo();
        groupInfo1.setLabel("送礼榜");
        groupInfo1.setRankTypes(JsonUtils.toJson(typeInfoList));

        List<ActivityRankGroupInfo> groupInfos = new ArrayList<>(2);
        groupInfos.add(groupInfo);
        groupInfos.add(groupInfo1);

        rankInfo.setRankGroups(JsonUtils.toJson(groupInfos));
        activityService.updateActivityRankInfo(rankInfo);

    }

    @Test
    public void testGetRankInfo(){
        System.out.println(JsonUtils.toJson(activityService.getActivityRankInfo(1)));
    }

    @Test
    public void testHttpAddActivity() throws IOException {
        String url = "http://localhost:30010/activity/admin/activity/add";
        Activity activity = new Activity();
        activity.setRemark("testest2222");
        activity.setType(1);
        activity.setName("testAddActivity3333");
        activity.setAppId(1);
        activity.setStime(new Date());
        activity.setEtime(TimeUtils.offsetDay(new Date(), 7));
        String resp = HttpUtils.doPost(url, activity);
        System.out.println(JsonUtils.toJson(resp));
    }

    @Test
    public void testGetActivityInfo(){
        System.out.println(activityService.getAcitivityInfo(1));
    }
}
