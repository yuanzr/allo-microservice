package com.dc.allo.rank.controller;

import com.dc.allo.rank.domain.award.vo.ReleaseResult;
import com.dc.allo.rank.service.award.realease.ReleaseProcessorProxy;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tudoutiao
 * @version v1.0.0
 * @description: TestAwardController
 * @since 2020/06/28 10:02
 */
@RestController
@RequestMapping("/rank")
@Slf4j
public class TestAwardController {

    @Autowired
    private ReleaseProcessorProxy releaseProcessorProxy;

    /**
     * @description 测试发奖
     * @param uid 用户id
     * @param releaseType 奖品类型
     * @param releaseId 发放奖励id
     * @param releaseCount 发放数量
     * @param validDays 有效天数
     * @return com.dc.allo.rank.domain.award.vo.ReleaseResult
     * @author tudoutiao
     * @date 10:14 2020/6/28
     **/
    @PostMapping("/testReleaseAward")
    public ReleaseResult testReleaseAward(Long uid, Integer releaseType, String releaseId, Integer releaseCount, Integer validDays) {
        String seqId = UUID.randomUUID().toString().replaceAll("-", "");

        AwardOfPackage awardOfPackage = new AwardOfPackage();
        awardOfPackage.setReleaseType(releaseType);
        awardOfPackage.setReleaseId(releaseId);
        awardOfPackage.setReleaseCount(releaseCount);
        awardOfPackage.setAwardCount(releaseCount);
        awardOfPackage.setValidDays(validDays);
        log.info("testReleaseAward-uid:{}, seqId:{}, awardOfPackage:{}", uid, seqId, awardOfPackage);
        //根据礼物类型进行发放
        return releaseProcessorProxy.release(uid, seqId, awardOfPackage);
    }
}
