package com.dc.allo.task.service.rpc;

import com.dc.allo.common.utils.BeanMapperUtils;
import com.dc.allo.rpc.api.task.DcCommonTaskPrizeService;
import com.dc.allo.rpc.domain.task.DcCommonTaskPrize;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.task.domain.entity.CommonTaskPrize;
import com.dc.allo.task.service.CommonTaskPrizeService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: DcCommonTaskPrizeServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/7/1/16:01
 */
@Service
@Slf4j
public class DcCommonTaskPrizeServiceImpl implements DcCommonTaskPrizeService {

    @Autowired
    private CommonTaskPrizeService commonTaskPrizeService;
    @Override
    public AlloResp<List<DcCommonTaskPrize>> getPackageIdByActTypeAndLevel(Byte actCode, Byte level) {
        List<CommonTaskPrize> packageList = commonTaskPrizeService.getPackageIdByActTypeAndLevel(actCode, level, null);
        List<DcCommonTaskPrize> dcCommonTaskPrizes = BeanMapperUtils.mapList(packageList, CommonTaskPrize.class, DcCommonTaskPrize.class);
        return AlloResp.success(dcCommonTaskPrizes);
    }
}
