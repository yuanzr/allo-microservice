package com.dc.allo.rank.service.award.realease;

import com.dc.allo.common.constants.AwardEnums;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rank.domain.award.vo.ReleaseResult;
import com.dc.allo.rpc.domain.award.AwardOfPackageMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
public abstract class AbstractReleaseProcessor {
    @Autowired
    private ReleaseProcessorProxy releaseProcessorProxy;

    abstract public ReleaseResult doRelease(long uid, AwardOfPackage awardOfPackage, AwardOfPackageMessage awardOfPackageMessage) throws Exception;

    abstract public AwardEnums.ReleaseType getReleaseType();

    @PostConstruct
    public void regist() {
        releaseProcessorProxy.register(this);
    }
}
