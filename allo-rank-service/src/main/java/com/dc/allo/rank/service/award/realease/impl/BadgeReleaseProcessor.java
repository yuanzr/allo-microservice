package com.dc.allo.rank.service.award.realease.impl;

import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.constants.AwardEnums;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rank.domain.award.vo.ReleaseResult;
import com.dc.allo.rank.service.award.realease.AbstractReleaseProcessor;
import com.dc.allo.rpc.domain.award.AwardOfPackageMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Component
@Slf4j
public class BadgeReleaseProcessor extends AbstractReleaseProcessor {
    @Autowired
    KafkaSender kafkaSender;

    @Override
    public ReleaseResult doRelease(long uid, AwardOfPackage awardOfPackage, AwardOfPackageMessage awardOfPackageMessage) throws Exception {
        //todo 座驾发放
        if(uid<=0||awardOfPackageMessage == null){
            log.info("勋章发奖参数错误，uid:{},seqId:{},awards:{}",uid,awardOfPackageMessage.getSeqId(), JsonUtils.toJson(awardOfPackageMessage));
            return ReleaseResult.fail("勋章发奖参数错误");
        }
        kafkaSender.send(KafkaTopic.Rank.DC_RANK_AWARD,KafkaTopic.EventType.DC_RANK_AWARD_BADGE,awardOfPackageMessage);
        return ReleaseResult.success();
    }

    @Override
    public AwardEnums.ReleaseType getReleaseType() {
        return AwardEnums.ReleaseType.BADGE;
    }
}
