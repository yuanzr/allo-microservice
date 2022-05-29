package com.dc.allo.task.service.impl;

import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.constants.Constant.RankActType;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.task.constant.TaskConstant;
import com.dc.allo.task.domain.entity.PrizeRankActItem;
import com.dc.allo.task.domain.entity.SignInRecord;
import com.dc.allo.task.domain.entity.TaskAwardRecord;
import com.dc.allo.task.domain.vo.SignRecordPrizeVO;
import com.dc.allo.task.domain.vo.SignRecordVO;
import com.dc.allo.task.service.CommonTaskPrizeService;
import com.dc.allo.task.service.SignInRecordService;
import com.dc.allo.task.service.SignInService;
import com.dc.allo.task.service.TaskAwardRecordService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dc.allo.task.service.cache.SignCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * description: SignInServiceImpl
 *
 * @date: 2020年05月26日 18:00
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SignInRecordService signInRecordService;
    @Autowired
    private CommonTaskPrizeService taskPrizeService;

    @Autowired
    private TaskAwardRecordService taskAwardRecordService;
    @Autowired
    private SignCache signCache;
    @Autowired
    private KafkaSender kafkaSender;

    /**
     * 签到
     * @param uid
     * @return java.util.List<com.dc.allo.task.domain.vo.SignVO>
     * @author qinrenchuan
     * @date 2020/5/26/0026 18:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<PrizeRankActItem> signIn(Long uid) {
        log.info("SignInServiceImpl signIn uid:{}", uid);
        List<SignInRecord> latestSignInRecordList = signInRecordService.getLatestSignInRecordByUid(uid, TaskConstant.DIGITAL_ONE);
        // 查询今天是第几次签到
        Integer todaySignTimes = getSignTimes(latestSignInRecordList);

        // 签到奖励
        List<PrizeRankActItem> signInPrizs = taskPrizeService.getPrizePackageByActTypeAndLevel(
                Constant.RankActType.SIGN_IN, todaySignTimes.byteValue(),
                Constant.SignInTaskType.CUMULATIVE);

        //新注册用户第一次签到的奖励
        List<PrizeRankActItem> newUserFirstSignAward = null;
        //新注册用户第一次签到
        if(CollectionUtils.isEmpty(latestSignInRecordList) && signCache.newRegisterUser(uid)){
            newUserFirstSignAward = taskPrizeService.getPrizePackageByActTypeAndLevel(Constant.RankActType.NEW_USER_GUIDE_SIGN, todaySignTimes.byteValue(),Constant.SignInTaskType.CUMULATIVE);
            signInPrizs = CollectionUtils.isEmpty(newUserFirstSignAward) ? signInPrizs : newUserFirstSignAward;
        }

        // 判断今天是否已经签到
        Boolean signedToday = isSignedToday(latestSignInRecordList);
        if (signedToday) {
            return signInPrizs;
        }

        // 保存签到记录
        SignInRecord signInRecord = new SignInRecord();
        signInRecord.setUid(uid);
        signInRecord.setTimes(todaySignTimes);
        signInRecordService.save(signInRecord);

        if (signInPrizs != null && signInPrizs.size() > 0) {
            // 插入发奖记录
            List<TaskAwardRecord> awardRecords = new ArrayList<>(signInPrizs.size());
            for (PrizeRankActItem actItem : signInPrizs) {
                TaskAwardRecord taskAwardRecord = new TaskAwardRecord();
                BeanUtils.copyProperties(actItem, taskAwardRecord);
                taskAwardRecord.setActualPrizeId(actItem.getReferenceId());
                taskAwardRecord.setBusinessId(signInRecord.getId());
                taskAwardRecord.setActType(Constant.RankActType.SIGN_IN);
                awardRecords.add(taskAwardRecord);

                actItem.setUid(uid);
            }
            taskAwardRecordService.insertBatch(awardRecords);

            //  KAFKA 发奖
            kafkaSender.send(KafkaTopic.Task.DC_TASK_SIGN,
                    KafkaTopic.EventType.DC_TASK_SIGN_AWARD,
                    signInPrizs);
            log.info("SignInServiceImpl topic:{},  eventType:{}",
                    KafkaTopic.Task.DC_TASK_SIGN,
                    KafkaTopic.EventType.DC_TASK_SIGN_AWARD);
        }

        //  更改任务完成状态
        kafkaSender.send(KafkaTopic.Task.DC_TASK_SIGN,
                KafkaTopic.EventType.DC_TASK_SIGN_Finish_MISSION,
                signInRecord);
        log.info("SignInServiceImpl topic:{},  eventType:{}",
                KafkaTopic.Task.DC_TASK_SIGN,
                KafkaTopic.EventType.DC_TASK_SIGN_Finish_MISSION);

        return signInPrizs;
    }

    /**
     * 查询今天是第几次签到
     * @param latestSignInRecordList
     * @return java.lang.Integer
     * @author qinrenchuan
     * @date 2020/5/27/0027 11:46
     */
    private Integer getSignTimes(List<SignInRecord> latestSignInRecordList) {
        if (latestSignInRecordList == null || latestSignInRecordList.size() == 0) {
            return TaskConstant.DIGITAL_ONE;
        }

        SignInRecord latestSignInRecord = latestSignInRecordList.get(0);
        if (latestSignInRecord == null || latestSignInRecord.getTimes().equals(TaskConstant.DIGITAL_SEVEN)) {
            return TaskConstant.DIGITAL_ONE;
        }

        return latestSignInRecord.getTimes() + TaskConstant.DIGITAL_ONE;
    }


    /**
     * 查询用户的签到记录
     * @param uid
     * @return com.dc.allo.task.domain.vo.SignRecordVO
     * @author qinrenchuan
     * @date 2020/5/26/0026 18:08
     */
    @Override
    public SignRecordVO signRecord(Long uid) {
        SignRecordVO signRecordVO = new SignRecordVO();

        // 查询最近一轮的签到记录
        List<SignInRecord> latestSignInRecordList = signInRecordService.getLatestSignInRecordByUid(uid, TaskConstant.DIGITAL_SEVEN);

        // 判断今天是否已签
        Boolean signedToday = isSignedToday(latestSignInRecordList);
        signRecordVO.setTodaySigned(signedToday);

        // 签到记录本次应该进行到第几轮. 但是这轮还没签
        Integer thisRoundSignedTimes = getThisRoundSignedTimes(latestSignInRecordList, signedToday);

        // 奖品
        List<SignRecordPrizeVO> signRecordPrizeVOS = new ArrayList<>();
        Map<Byte, List<PrizeRankActItem>> signInAllPrizes = taskPrizeService.getAllPrizesByActCode(RankActType.SIGN_IN);
        for (int i = 1; i <= TaskConstant.DIGITAL_SEVEN; i++) {
            SignRecordPrizeVO signRecordPrizeVO = new SignRecordPrizeVO();
            signRecordPrizeVO.setPrizeRankActItems(signInAllPrizes.get(Byte.valueOf(i + "")));

            if (i < thisRoundSignedTimes) {
                signRecordPrizeVO.setSigned(true);
            } else {
                signRecordPrizeVO.setSigned(false);
            }
            signRecordPrizeVOS.add(signRecordPrizeVO);
        }
        signRecordVO.setSignRecordPrizeVOS(signRecordPrizeVOS);

        return signRecordVO;
    }

    /**
     * 判断今天是否已经签到
     * @param signInRecords
     * @return java.lang.Boolean
     * @author qinrenchuan
     * @date 2020/5/27/0027 15:33
     */
    private Boolean isSignedToday(List<SignInRecord> signInRecords) {
        if (signInRecords == null || signInRecords.size() == 0) {
            return false;
        }

        SignInRecord signInRecord = signInRecords.get(0);
        Date signTime = signInRecord.getSignTime();
        String signTimeStr = DateTimeUtil.convertDate(signTime, DateTimeUtil.DEFAULT_DATE_PATTERN);
        String todayStr = DateTimeUtil.convertDate(new Date(), DateTimeUtil.DEFAULT_DATE_PATTERN);

        return signTimeStr.equals(todayStr);
    }

    /**
     * 签到记录本次应该进行到第几轮。但是这一轮还没签
     * @param latestSignInRecordList
     * @param signedToday
     * @return java.lang.Integer
     * @author qinrenchuan
     * @date 2020/5/27/0027 15:43
     */
    private Integer getThisRoundSignedTimes(List<SignInRecord> latestSignInRecordList, Boolean signedToday) {
        // 如果今天已签到
        if (signedToday) {
            return latestSignInRecordList.get(0).getTimes() + 1;
        }

        // 如果今天未签到
        if (latestSignInRecordList == null || latestSignInRecordList.size() == 0) {
            return TaskConstant.DIGITAL_ONE;
        }

        SignInRecord signInRecord = latestSignInRecordList.get(0);
        if (signInRecord.getTimes().equals(TaskConstant.DIGITAL_SEVEN)) {
            return TaskConstant.DIGITAL_ONE;
        }

        return signInRecord.getTimes() + 1;
    }

    /**
     * 查询用户今天是否已经签到
     * @param uid
     * @return java.lang.Boolean
     * @author qinrenchuan
     * @date 2020/6/3/0003 17:43
     */
    @Override
    public Boolean getSignStatusForToday(Long uid) {
        // 查询最近一次的签到记录
        List<SignInRecord> latestSignInRecordList = signInRecordService.getLatestSignInRecordByUid(uid, TaskConstant.DIGITAL_ONE);
        // 判断今天是否已签
        return isSignedToday(latestSignInRecordList);
    }
}
