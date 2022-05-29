package com.dc.allo.activity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.activity.service.ActChargeCamelService;
import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.rpc.api.task.DcCommonTaskPrizeService;
import com.dc.allo.rpc.domain.task.DcCommonTaskPrize;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ActChargeCamelServiceImpl implements ActChargeCamelService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private KafkaSender kafkaSender;
    @Reference
    private DcCommonTaskPrizeService dcCommonTaskPrizeService;

    @Override
    public AlloResp getPrizeAward(Byte level,Long uid) {
        Byte actType = Constant.RankActType.CAMEL_CHBARGE;
        String statusKey = RedisKeyUtil.getKey(RedisKeyUtil.RedisKey.activity_job_user.name(), actType + ":" + level);
        Object jobStatusObj = redisUtil.hGet(statusKey, uid.toString());
        log.info("ActChargeCamel-getPrizeAward:uid={},level={}",uid,level);
        //1.检验领奖条件
        if ( jobStatusObj == null ){
            //0:未达到条件:提示未达到领奖条件
            return AlloResp.failed(BusiStatus.ACTIVITY_TARGET_NOT_ENOUGH);
        }
        if (Constant.ActJobProgress.FINISHED.equals(jobStatusObj.toString())){
            //2:已经领取:提示已经领取过了
            return AlloResp.failed(BusiStatus.ACTIVITY_REPEAT_PRIZE_SEND);
        }
        //2.根据level查询出礼包ID
        AlloResp<List<DcCommonTaskPrize>> packageResult = dcCommonTaskPrizeService.getPackageIdByActTypeAndLevel(actType, level);
        List<DcCommonTaskPrize> packageList = packageResult.getData();
        if (CollectionUtils.isEmpty(packageList)){
            //奖品不存在
            return AlloResp.failed(BusiStatus.GIFTDOWNORNOTEXISTS);
        }
        //3.设置领奖状态:已完成
        redisUtil.hPut(statusKey, uid.toString(),Constant.ActJobProgress.FINISHED);
        //4.发送kafka消息给xchat应用进行发奖
        Map<String, String> messageData = new HashMap<>();
        messageData.put("uid",uid.toString());
        messageData.put("packageId",packageList.get(0).getPackageId().toString());
        kafkaSender.send(KafkaTopic.Activity.DC_CHARGE_CAMEL_PRIZE_SEND, KafkaTopic.EventType.ACTIVITY_EVENT_CHARGE_CAMEL, JSONObject.toJSONString(messageData));
        return AlloResp.success();
    }

    @Override
    public AlloResp testPrizeAward(Byte level, Long uid) {
        //2.根据level查询出礼包ID
        AlloResp<List<DcCommonTaskPrize>> packageResult = dcCommonTaskPrizeService.getPackageIdByActTypeAndLevel(Constant.RankActType.CAMEL_CHBARGE, level);
        List<DcCommonTaskPrize> packageList = packageResult.getData();
        if (CollectionUtils.isEmpty(packageList)){
            //奖品不存在
            return AlloResp.failed(BusiStatus.GIFTDOWNORNOTEXISTS);
        }
        //4.发送kafka消息给xchat应用进行发奖
        Map<String, String> messageData = new HashMap<>();
        messageData.put("uid",uid.toString());
        messageData.put("packageId",packageList.get(0).getPackageId().toString());
        kafkaSender.send(KafkaTopic.Activity.DC_CHARGE_CAMEL_PRIZE_SEND, KafkaTopic.EventType.ACTIVITY_EVENT_CHARGE_CAMEL, JSONObject.toJSONString(messageData));
        return AlloResp.success();
    }
}
