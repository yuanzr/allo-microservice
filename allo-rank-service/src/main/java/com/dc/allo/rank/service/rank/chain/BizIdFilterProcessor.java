package com.dc.allo.rank.service.rank.chain;

import com.dc.allo.common.component.chain.AbstractChainNode;
import com.dc.allo.common.component.chain.ChainRegister;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.domain.rank.RankRecordContext;
import com.dc.allo.rank.domain.rank.config.BizIdConfig;
import com.dc.allo.rank.service.rank.data.BizIdConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/13.
 */
@Component
@Slf4j
public class BizIdFilterProcessor extends AbstractChainNode<RankRecordContext> {

    @Autowired
    BizIdConfigService bizIdConfigService;

    @Override
    public boolean doProcess(RankRecordContext context) throws Exception {
        if (context == null || context.getRank() == null || context.getRecord() == null) {
            log.warn(Constant.Rank.WARN + "BizIdFilterProcess 上下文异常 context:{}", context);
            return false;
        }
        //todo 按配置信息对比bizid过滤
        String bizId = context.getRecord().getBizId();
        Long rankId = context.getRank().getId();
        BizIdConfig bizIdConfig = null;
        if (rankId != null) {
            bizIdConfig = bizIdConfigService.get(rankId);
            //没有配置，直接跳过
            if (bizIdConfig == null) {
                return true;
            }
            //有配置，有匹配才返回true
            String bizIds = bizIdConfig.getBizIds();
            if (StringUtils.isNotBlank(bizIds)) {
                String[] idArr = bizIds.split(",");
                for (String id : idArr) {
                    if (id.equals(bizId)) {
                        return true;
                    }
                }
            }
        }
        log.info(Constant.Rank.INFO + "BizIdFilterProcess 榜单需要根据bizid过滤 但数据中的bizid与频道不匹配 不予记录 bizId:{} rankId:{} bizIdConfig:{}", bizId, rankId, JsonUtils.toJson(bizIdConfig));
        return false;

    }

    @Override
    public ChainRegister belongTo() {
        return ChainRegister.RANK_DATA_PROCESS_CHAIN;
    }

    @Override
    public Integer priority() {
        return 4;
    }
}
