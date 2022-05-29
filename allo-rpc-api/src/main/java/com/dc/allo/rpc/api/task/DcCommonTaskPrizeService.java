package com.dc.allo.rpc.api.task;

import com.dc.allo.rpc.domain.task.DcCommonTaskPrize;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.List;

public interface DcCommonTaskPrizeService {
    /**
     * 根据任务code和等级查询奖品
     * @param actCode
     * @param level
     * @return java.util.List<com.dc.allo.rpc.domain.task.DcCommonTaskPrize>
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:19
     */
    AlloResp<List<DcCommonTaskPrize>> getPackageIdByActTypeAndLevel(Byte actCode,Byte level);

}
