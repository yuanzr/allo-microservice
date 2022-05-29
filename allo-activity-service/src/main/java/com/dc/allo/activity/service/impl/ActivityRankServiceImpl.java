package com.dc.allo.activity.service.impl;

import com.dc.allo.activity.service.ActivityRankService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.rank.DcRankService;
import com.dc.allo.rpc.domain.rank.RankQueryReq;
import com.dc.allo.rpc.domain.rank.RankQueryResp;
import com.dc.allo.rpc.proto.AlloResp;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * Created by zhangzhenjun on 2020/6/19.
 */
@Service
public class ActivityRankServiceImpl implements ActivityRankService {

    @Reference
    DcRankService dcRankService;

    @Override
    public AlloResp<RankQueryResp> queryRank(RankQueryReq req) {
        return dcRankService.queryRank(req);
    }

    @Override
    public AlloResp<Boolean> delRankCache(String key, String rankRedisKey) {
        if(!"qwertyuiop[]".equals(key)|| StringUtils.isBlank(rankRedisKey)){
            return AlloResp.failed(BusiStatus.PARAMERROR.value(),"params err",null);
        }
        return dcRankService.delRankCache(rankRedisKey);
    }
}
