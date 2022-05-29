package com.dc.allo.biznotice.controller;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.sysconf.DcSysConfService;
import com.dc.allo.rpc.api.user.language.DcUserLanguageService;
import com.dc.allo.rpc.domain.Check;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.Date;
import org.apache.dubbo.config.annotation.Reference;
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
    @Value("${spring.application.name}")
    private String appName;
    
    @Reference
    private DcRoomInfoService dcRoomInfoService;
    @Reference
    private DcSysConfService dcSysConfService;
    @Reference
    private DcUserLanguageService dcUserLanguageService;

    @GetMapping("/getUidRoom")
    public Room getRoom(Long uid) {
        AlloResp<Room> resp = dcRoomInfoService.getByUid(uid);
        System.out.println(resp.getCode());
        System.out.println(resp.getData());
        return resp.getData();
    }


    @RequestMapping("/check")
    public AlloResp<Check> check(){
        Check check = new Check();
        check.setAppName(appName);
        check.setCheckTime(new Date());
        return AlloResp.success(check);
    }

    @GetMapping("/getSysConfById")
    public AlloResp getSysConfById() {
        return dcSysConfService.getSysConfById(Constant.SysConfId.Auto_Log_Methods);
    }

    @GetMapping("/getSysConfValueById")
    public AlloResp getSysConfValueById() {
        return dcSysConfService.getSysConfValueById(Constant.SysConfId.Auto_Log_Methods);
    }

    @GetMapping("/getUserLanguageByUid")
    public AlloResp getUserLanguageByUid(Long uid) {
        return dcUserLanguageService.getUserLanguageByUid(uid);
    }
}
