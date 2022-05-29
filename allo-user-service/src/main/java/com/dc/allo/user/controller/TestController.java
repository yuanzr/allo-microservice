package com.dc.allo.user.controller;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.rpc.domain.Check;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.user.service.sysconf.SysConfService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: TestController
 *
 * @date: 2020年05月09日 11:25
 * @author: qinrenchuan
 */
@RestController
public class TestController {

    @Autowired
    private SysConfService sysConfService;

    @GetMapping("/getSysConfById")
    public AlloResp getSysConfById() {
        return AlloResp.success(sysConfService.getSysConf(Constant.SysConfId.Auto_Log_Methods));
    }

    @GetMapping("/getSysConfValueById")
    public AlloResp getSysConfValueById() {
        return AlloResp.success(sysConfService.getSysConfValueById(Constant.SysConfId.Auto_Log_Methods));
    }
}
