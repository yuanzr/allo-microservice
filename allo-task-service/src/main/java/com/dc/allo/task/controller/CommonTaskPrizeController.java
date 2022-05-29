package com.dc.allo.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.task.domain.vo.CommonTaskPrizeVO;
import com.dc.allo.task.service.CommonTaskPrizeService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description: CommonTaskPrizeController
 *
 * @date: 2020年05月26日 14:38
 * @author: qinrenchuan
 */
@RestController
@Slf4j
public class CommonTaskPrizeController extends BaseController {

    @Autowired
    private CommonTaskPrizeService taskPrizeService;

    /**
     * 任务奖品礼包配置列表
     * @param queryVo
     * @param pageNumber
     * @param pageSize
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/5/26/0026 14:43
     */
    @GetMapping("/task/admin/prize/list")
    public AlloResp listByPage(CommonTaskPrizeVO queryVo, Integer pageNumber, Integer pageSize){
        log.info("listByPage request: {}, pageNumber:{}, pageSize:{}",
                JSONObject.toJSONString(queryVo), pageNumber, pageSize);
        try {
            JSONObject resultObj = new JSONObject();
            PageInfo<CommonTaskPrizeVO> pageInfo = taskPrizeService.listByPage(queryVo, pageNumber, pageSize);
            resultObj.put("total", pageInfo.getTotal());
            resultObj.put("rows", pageInfo.getList());
            return AlloResp.success(resultObj);
        } catch (Exception e) {
            log.error("listByPage failed", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 任务奖励数据初始化
     * @param actCode
     * @return com.xchat.common.result.BusiResult
     * @author qinrenchuan
     * @date 2020/5/20/0020 16:14
     */
    @PostMapping("/task/admin/prize/initTaskPrizeConfig")
    public AlloResp initTaskPrizeConfig(Byte actCode) {
        if (actCode == null) {
            return AlloResp.failed(BusiStatus.PARAMTER_ERROR);
        }
        try {
            taskPrizeService.initTaskPrizeConfig(actCode);
            return AlloResp.success();
        } catch (Exception e) {
            log.error("initTaskPrizeConfig failed", e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), e.getMessage(), null);
        }
    }

    /**
     * 设置任务奖励礼包
     * @param taskPrizeId
     * @param prizePackageId
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/5/26/0026 14:46
     */
    @PostMapping(value = "/task/admin/prize/setPrizePackage")
    public AlloResp setPrizePackage(Long taskPrizeId, Long prizePackageId) {
        if (taskPrizeId == null || taskPrizeId < 0
                || prizePackageId == null || prizePackageId < 0) {
            return AlloResp.failed(BusiStatus.PARAMTER_ERROR);
        }

        try {
            taskPrizeService.setPrizePackage(taskPrizeId, prizePackageId);
            return AlloResp.success();
        } catch (Exception e) {
            log.error("setPrizePackage failed", e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), e.getMessage(), null);
        }
    }


    @GetMapping("/h5/task/package/prize/list")
    public AlloResp listPackageByActCode(Byte actCode,Long uid,Boolean isShowStatus){
        try {
            List<CommonTaskPrizeVO> packageList = taskPrizeService.listPackageByActCode(actCode,uid,isShowStatus);
            return AlloResp.success(packageList);
        } catch (Exception e) {
            log.error("listPackageByActCode failed", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
