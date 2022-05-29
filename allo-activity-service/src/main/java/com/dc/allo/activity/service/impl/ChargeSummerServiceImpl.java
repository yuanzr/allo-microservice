package com.dc.allo.activity.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dc.allo.activity.constant.Constant.ActRankDateType;
import com.dc.allo.activity.constant.Constant.ChargeSummerStatus;
import com.dc.allo.activity.constant.Constant.RankActType;
import com.dc.allo.activity.domain.vo.recharge.ActivityInfoConfig;
import com.dc.allo.activity.domain.vo.recharge.PrizeRankActItem;
import com.dc.allo.activity.service.BaseService;
import com.dc.allo.activity.service.ChargeSummerService;
import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.constants.Constant.SysConfId;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.utils.RedisKeyUtil.RedisKey;
import com.dc.allo.rpc.api.sysconf.DcSysConfService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.sysconf.SysConf;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ChargeSummerServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/6/10/14:20
 */
@Slf4j
@Service
public class ChargeSummerServiceImpl extends BaseService implements ChargeSummerService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private KafkaSender kafkaSender;
    @Reference
    private DcSysConfService  dcSysConfService;
    @Reference
    private DcUserInfoService dcUserInfoService;


    @Override
    public AlloResp getChargeSummerPrizeList(Long uid) {
        Map<String, Object> data = new HashMap<>();
        String uidStr = uid.toString();
        //1.获取首充奖励列表
        String firstKey = getPrizeKey(RankActType.CHARGE_SUMMER, ActRankDateType.day);
        Map<Object, Object> firstPrizeData = redisUtil.hGetAll(firstKey);
        Map<String, Object> firstPrizeMap = getPrizeListData(firstPrizeData,ActRankDateType.day,uidStr);
        data.put(ActRankDateType.day.toString(),firstPrizeMap);
        //2.获取累充奖励列表
        String totalKey = getPrizeKey(RankActType.CHARGE_SUMMER, ActRankDateType.total);
        Map<Object, Object> totalPrizeData = redisUtil.hGetAll(totalKey);
        Map<String, Object> totalPrizeMap   = getPrizeListData(totalPrizeData,ActRankDateType.total,uidStr);
        data.put(ActRankDateType.total.toString(),totalPrizeMap);
        //3.计算活动倒数计时
        data.put("countDowntime",getActivityCountDowntime());
        //4.获取用户当前累计充值数
        Double score = redisUtil.zScore(RedisKeyUtil.getKey(RedisKeyUtil.RedisKey.act_charge_summer_job_total.name()),uidStr);
        data.put("score",score == null ? 0:score.intValue());
        //5.前端需要显示用户信息
        AlloResp<UserInfo> userResult = dcUserInfoService.getByUid(uid);
        if (userResult.getCode() == AlloResp.success().getCode() && userResult.getData()!= null){
            UserInfo user = userResult.getData();
            data.put("nick",user.getNick());
            data.put("avatar",user.getAvatar());
        }
        return AlloResp.success(data);
    }

    private Map<String, Object> getPrizeListData(Map<Object, Object> prizeMap,Byte rankDateType,String uidStr) {
        //1.获取配置新增
        SysConf sysConf = null;
        if (rankDateType.equals(ActRankDateType.day)){
            AlloResp<SysConf> sysConfById = dcSysConfService.getSysConfById(SysConfId.activity_summer_charge_first_limit);
            sysConf = sysConfById.getData();
        }else if (rankDateType.equals(ActRankDateType.total)){
            AlloResp<SysConf> sysConfById = dcSysConfService.getSysConfById(Constant.SysConfId.activity_summer_charge_total_limit);
            sysConf = sysConfById.getData();
        }else{
            return null;
        }
        String[] prizeLevel = sysConf.getConfigValue().split(",");
        List<String> configList = Arrays.asList(prizeLevel);

        //2.转换奖品信息
        List<PrizeRankActItem> rankList = new ArrayList();
        for (Object weekPrizeJson : prizeMap.values()) {
            PrizeRankActItem item = JSON.parseObject(weekPrizeJson.toString(), PrizeRankActItem.class);
            //奖品名称
            item.setPrizeName(getMessage(item.getPrizeName()));
            rankList.add(item);
        }
        //按照榜单排序
        Map<Byte, List<PrizeRankActItem>> topMap = rankList.stream().collect(Collectors.groupingBy(PrizeRankActItem::getRankSeq));
        Map<String, Object> topTotalLinkedtMap = new LinkedHashMap<>();
        List<Byte> weekTypeList = new ArrayList<>(topMap.keySet());
        Collections.sort(weekTypeList);
        for (int i = 0; i < configList.size(); i++) {
            //设置任务信息
            Map<String, Object> topLinkedtMap = new LinkedHashMap<>();
            String jobValue = configList.get(i);
            Byte   ele    = weekTypeList.get(i);
            List<PrizeRankActItem> prizeRankActItems = topMap.get(ele);
            //按照序号排序
            prizeRankActItems.sort(Comparator.comparing(prize -> prize.getSeq()));
            //设置任务状态
            String statusKey = "";
            if (ActRankDateType.day.equals(rankDateType)){
                statusKey = RedisKeyUtil.getKey(RedisKey.act_charge_summer_job_status.name(), rankDateType.toString());
            }else if (ActRankDateType.total.equals(rankDateType)){
                statusKey = RedisKeyUtil.getKey(RedisKey.act_charge_summer_job_status.name(), rankDateType+":"+jobValue);
            }
            Object jobStatusObj = redisUtil.hGet(statusKey, uidStr);
            String jobStatus = "";
            if (jobStatusObj!= null && ChargeSummerStatus.PENGDING.equals(jobStatusObj.toString()) ){
                jobStatus = ChargeSummerStatus.PENGDING;
            }else{
                jobStatus = ChargeSummerStatus.UNFINISH;
            }
            topLinkedtMap.put("prizeList", prizeRankActItems);
            topLinkedtMap.put("jobStatus", jobStatus);
            topTotalLinkedtMap.put(jobValue, topLinkedtMap);
        }
        return topTotalLinkedtMap;
    }

    /**
     * 获取倒计时时间
     * @return
     */
    private Long getActivityCountDowntime(){
        Long currentTime = DateTimeUtil.getNextHour(Calendar.getInstance().getTime(), -5).getTime();
        SysConf sysConfById = dcSysConfService.getSysConfById(SysConfId.activity_summer_charge_time).getData();
        ActivityInfoConfig config = JSONObject.parseObject(sysConfById.getConfigValue(), ActivityInfoConfig.class);
        Long endTime = config.getEndTime().getTime();
        Long countDownTime = endTime - currentTime;
        Long result = countDownTime > 0L ? countDownTime : 0L;
        return result;
    }

    /**
     * 判断是否在活动时间
     * @return
     */
    private Boolean isActivityTime(){
        Long currentTime = DateTimeUtil.getNextHour(Calendar.getInstance().getTime(), -5).getTime();
        SysConf sysConfById = dcSysConfService.getSysConfById(SysConfId.activity_summer_charge_time).getData();
        ActivityInfoConfig config = JSONObject.parseObject(sysConfById.getConfigValue(), ActivityInfoConfig.class);
        Long startTime = config.getStartTime().getTime();
        Long endTime   = config.getEndTime().getTime();
        if (currentTime >= startTime && currentTime<=endTime){
            return false;
        }
        return true;
    }


    @Override
    public AlloResp getPrizeAward(Long uid, Byte rankDateType, String jobName) {
         //1.判断是否在活动时间
         if (isActivityTime()){
             log.error("getPrizeAward-error-expired:uid={},rankType={},jobName={}",uid,rankDateType,jobName);
             return AlloResp.failed(BusiStatus.ACTIVITY_EXPIRED_ERROR);
         }
        //2.判断用户该任务状态
        String statusKey = "";
        if (ActRankDateType.day.equals(rankDateType)){
           statusKey = RedisKeyUtil.getKey(RedisKey.act_charge_summer_job_status.name(), rankDateType.toString());
        }else if (ActRankDateType.total.equals(rankDateType)){
            statusKey = RedisKeyUtil.getKey(RedisKey.act_charge_summer_job_status.name(), rankDateType+":"+jobName);
        }
        Object jobStatusObj = redisUtil.hGet(statusKey, uid.toString());
        if ( jobStatusObj == null ){
            //0:未达到条件:提示未达到领奖条件
            return AlloResp.failed(BusiStatus.ACTIVITY_TARGET_NOT_ENOUGH);
        }
        if (ChargeSummerStatus.FINISHED.equals(jobStatusObj.toString())){
            //2:已经领取:提示已经领取过了
            return AlloResp.failed(BusiStatus.ACTIVITY_REPEAT_PRIZE_SEND);
        }
        //3.设置领奖状态:已完成
        redisUtil.hPut(statusKey, uid.toString(),ChargeSummerStatus.FINISHED);

        //4.发送kafka消息给xchat应用进行发奖
        Map<String, String> messageData = new HashMap<>();
        messageData.put("uid",uid.toString());
        messageData.put("rankDateType",rankDateType.toString());
        messageData.put("jobName",jobName);
        kafkaSender.send(KafkaTopic.Activity.DC_CHARGE_SUMMER_PRIZE_SEND, KafkaTopic.EventType.ACTIVITY_EVENT_CHARGE_SUMMER, JSONObject.toJSONString(messageData));
        return AlloResp.success();
    }

    @Override
    public String getPrizeKey(Byte actType, Byte rankType) {
        return RedisKeyUtil.getKey(RedisKeyUtil.RedisKey.activity_rank_award.name(),actType + ":" + rankType);
    }
}
