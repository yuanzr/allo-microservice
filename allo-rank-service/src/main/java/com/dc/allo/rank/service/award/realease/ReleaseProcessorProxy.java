package com.dc.allo.rank.service.award.realease;

import com.dc.allo.rank.constants.AwardConstants;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rank.domain.award.vo.ReleaseResult;
import com.dc.allo.rpc.domain.award.AwardOfPackageMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Slf4j
@Component
public class ReleaseProcessorProxy {

    private Map<Integer, AbstractReleaseProcessor> processorMap = new HashMap<>();

    public ReleaseResult release(long uid, String seqId, AwardOfPackage awardOfPackage) {
        if (awardOfPackage == null) {
            return ReleaseResult.fail("空奖励");
        }
        try {
            AbstractReleaseProcessor processor = processorMap.get(awardOfPackage.getReleaseType());
            if (processor == null) {
                log.warn(AwardConstants.WARN_LOG + "未找到匹配的奖励发放器不予发放 uid:{} awardOfPackage:{}", uid, awardOfPackage);
                return ReleaseResult.fail("未找到匹配的奖励发放器");
            }
            AwardOfPackageMessage awardOfPackageMessage = new AwardOfPackageMessage();
            awardOfPackageMessage.setUid(uid);
            awardOfPackageMessage.setReleaseType(awardOfPackage.getReleaseType());
            awardOfPackageMessage.setReleaseId(awardOfPackage.getReleaseId());
            awardOfPackageMessage.setAwardCount(awardOfPackage.getAwardCount());
            awardOfPackageMessage.setValidDays(awardOfPackage.getValidDays());
            awardOfPackageMessage.setSeqId(seqId);

            ReleaseResult result = processor.doRelease(uid, awardOfPackage, awardOfPackageMessage);
            return result;
        } catch (Exception e) {
            log.error(AwardConstants.WARN_LOG + "奖励发放器处理失败 uid:{} awardOfPackage:{}", uid, awardOfPackage, e);
            return ReleaseResult.fail("奖励发放器处理失败 err:" + e.getMessage());
        }
    }

    public void register(AbstractReleaseProcessor processor) {
        this.processorMap.put(processor.getReleaseType().val(), processor);
    }
}
