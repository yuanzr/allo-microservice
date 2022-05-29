package com.dc.allo.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.task.domain.entity.SignInRecord;
import com.dc.allo.task.service.SignInRecordService;
import com.dc.allo.task.service.TaskAwardRecordService;
import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: SignInRecordController
 *
 * @date: 2020年05月28日 16:32
 * @author: qinrenchuan
 */
@RestController
@Slf4j
public class SignInRecordController extends BaseController {

    @Autowired
    private SignInRecordService signInRecordService;
    @Autowired
    private TaskAwardRecordService taskAwardRecordService;

    /**
     * 列表查询
     * @param pageNumber
     * @param pageSize
     * @param uid
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/5/28/0028 16:35
     */
    @GetMapping("/sign/admin/signRecord/listByPage")
    public AlloResp listByPage(Integer pageNumber, Integer pageSize, Long uid) {
        log.info("listByPage request uid: {}, pageNumber:{}, pageSize:{}",
                uid, pageNumber, pageSize);
        try {
            JSONObject resultObj = new JSONObject();
            PageInfo<SignInRecord> pageInfo = signInRecordService.listByPage(pageNumber, pageSize, uid);
            resultObj.put("total", pageInfo.getTotal());
            resultObj.put("rows", pageInfo.getList());
            return AlloResp.success(resultObj);
        } catch (Exception e) {
            log.error("listByPage failed", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    @GetMapping("/sign/admin/signPrizes")
    public AlloResp querySignPrizesBySignId(Long signId) {
        log.info("querySignPrizesBySignId request signId: {}", signId);
        try {
            List<Long> prizeList = taskAwardRecordService.queryPrizesByBusinessId(signId);
            return AlloResp.success(prizeList);
        } catch (Exception e) {
            log.error("querySignPrizesBySignId failed", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

}
