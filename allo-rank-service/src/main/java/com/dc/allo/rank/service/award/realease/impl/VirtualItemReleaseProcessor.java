package com.dc.allo.rank.service.award.realease.impl;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.AwardConstants;
import com.dc.allo.common.constants.AwardEnums;
import com.dc.allo.rank.domain.award.vo.ReleaseResult;
import com.dc.allo.rank.service.award.realease.AbstractReleaseProcessor;
import com.dc.allo.rpc.api.finance.DcVirtualItemService;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rpc.domain.award.AwardOfPackageMessage;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/5/11.
 */
@Component
@Slf4j
public class VirtualItemReleaseProcessor extends AbstractReleaseProcessor {

    @Reference
    DcVirtualItemService virtualItemService;

    @Override
    public ReleaseResult doRelease(long uid, AwardOfPackage awardOfPackage, AwardOfPackageMessage awardOfPackageMessage) throws Exception {
        if (uid <= 0 || awardOfPackageMessage == null || StringUtils.isBlank(awardOfPackageMessage.getReleaseId())) {
            return ReleaseResult.fail("配置有误");
        }

        String seqId = awardOfPackageMessage.getSeqId();
        int itemId = Integer.valueOf(awardOfPackage.getReleaseId());
        int count = awardOfPackage.getReleaseCount();
        String bizId = "活动id："+awardOfPackage.getActId()+" 奖励id："+awardOfPackage.getAwardId();
        String context = awardOfPackage.getName();
        try {
            AlloResp<Long> result = virtualItemService.addVirtualItemBill(Constant.App.ALLO, uid, itemId, count, bizId, context);
            log.info(AwardConstants.INFO_LOG + "发放虚拟道具调用结果 uid:{} seqId:{} awardOfPackage:{} result:{}", uid, seqId, awardOfPackage, JsonUtils.toJson(result));
        } catch (Exception e) {
            log.error(AwardConstants.ERR_LOG + "发放虚拟道具失败 uid:{} seqId:{} awardOfPackage:{}", uid, seqId, awardOfPackage, e);
            return ReleaseResult.fail("发放虚拟道具失败 err:" + e.getMessage());
        }
        return ReleaseResult.success();
    }

    @Override
    public AwardEnums.ReleaseType getReleaseType() {
        return AwardEnums.ReleaseType.VIRTUALITEM;
    }
}
