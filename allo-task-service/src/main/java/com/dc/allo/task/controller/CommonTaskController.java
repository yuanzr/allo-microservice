package com.dc.allo.task.controller;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.task.domain.entity.CommonTask;
import com.dc.allo.task.service.CommonTaskService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: 任务相关接口
 *
 * @date: 2020年05月26日 10:55
 * @author: qinrenchuan
 */
@RestController
@Slf4j
public class CommonTaskController extends BaseController {

    @Autowired
    private CommonTaskService taskService;

    /**
     * 查询任务列表
     * @param
     * @return com.xchat.common.result.BusiResult
     * @author qinrenchuan
     * @date 2020/5/21/0021 14:59
     */
    @GetMapping("/task/admin/taskList")
    public AlloResp queryTaskLlist() {
        try {
            List<CommonTask> list = taskService.queryTaskList();
            return AlloResp.success(list);
        } catch (Exception e) {
            log.error("getFirstRechargePrize error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
