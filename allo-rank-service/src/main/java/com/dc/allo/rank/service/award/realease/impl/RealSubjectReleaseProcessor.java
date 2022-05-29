package com.dc.allo.rank.service.award.realease.impl;

import com.dc.allo.common.constants.AwardEnums;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rank.domain.award.vo.ReleaseResult;
import com.dc.allo.rank.service.award.realease.AbstractReleaseProcessor;
import com.dc.allo.rpc.domain.award.AwardOfPackageMessage;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Component
public class RealSubjectReleaseProcessor extends AbstractReleaseProcessor {

    @Override
    public ReleaseResult doRelease(long uid, AwardOfPackage awardOfPackage, AwardOfPackageMessage awardOfPackageMessage) throws Exception {
        //实物奖励直接成功
        return ReleaseResult.success();
    }

    @Override
    public AwardEnums.ReleaseType getReleaseType() {
        return AwardEnums.ReleaseType.REAL_SUBJECT;
    }
}
