package com.dc.allo.job.controller;

import com.dc.allo.rpc.domain.Check;
import com.dc.allo.rpc.proto.AlloResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class CheckController {

    @Value("${spring.application.name}")
    private String appName;

    @RequestMapping("/check")
    public AlloResp<Check> check(){
        Check check = new Check();
        check.setAppName(appName);
        return AlloResp.success(check);
    }
}
