package com.dc.allo.room.controller;

import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: UserInfoRpcTestController
 *
 * @date: 2020年06月04日 15:29
 * @author: qinrenchuan
 */
@RestController
@RequestMapping("/testUserInfo")
@Slf4j
public class UserInfoRpcTestController {


    @Reference
    private DcUserInfoService dcUserInfoService;

    /**
     * 根据Uid查询用户信息
     * @param uid
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    @GetMapping("/getByUid")
    public AlloResp<UserInfo> getByUid(Long uid) {
        log.info("getByUid uid:{}", uid);
        return dcUserInfoService.getByUid(uid);
    }

    /**
     * 根据耳伴号查询用户信息
     * @param erbanNo
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:02
     */
    @GetMapping("/getByErbanNo")
    public AlloResp<UserInfo> getByErbanNo(Long erbanNo) {
        return dcUserInfoService.getByErbanNo(erbanNo);
    }

    /**
     * 根据Uid查询用户简单信息
     * @param uid
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    @GetMapping("/getSimpleUserInfoByUid")
    public AlloResp<SimpleUserInfo> getSimpleUserInfoByUid(Long uid) {
        return dcUserInfoService.getSimpleUserInfoByUid(uid);
    }

    /**
     * 根据耳伴号查询用户简单信息
     * @param erbanNo
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:02
     */
    @GetMapping("/getSimpleUserInfoByErbanNo")
    public AlloResp<SimpleUserInfo> getSimpleUserInfoByErbanNo(Long erbanNo) {
        return dcUserInfoService.getSimpleUserInfoByErbanNo(erbanNo);
    }

    /**
     * 根据uidList列表获取用户列表
     * @param uidStr
     * @return
     */
    @GetMapping("/getUserList")
    public AlloResp<List<UserInfo>> getUserList(String uidStr) {
        List<Long> uidList = new ArrayList<>();
        String[] split = uidStr.split(",");
        for (String str : split) {
            uidList.add(Long.valueOf(str));
        }

        return dcUserInfoService.getUserList(uidList);
    }

    /**
     * 根据uidList列表获取用户列表
     * @param uidStr
     * @return
     */
    @GetMapping("/getSimpleUserList")
    public AlloResp<List<SimpleUserInfo>> getSimpleUserList(String uidStr) {
        List<Long> uidList = new ArrayList<>();
        String[] split = uidStr.split(",");
        for (String str : split) {
            uidList.add(Long.valueOf(str));
        }
        return dcUserInfoService.getSimpleUserList(uidList);
    }

    /**
     * 根据uidList列表获取用户key-value对(uid->SimpleUserInfo)
     * @param uidStr
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.Map<java.lang.Long,com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:22
     */
    @GetMapping("/getSimpleUsersMap")
    public AlloResp<Map<Long, SimpleUserInfo>> getSimpleUsersMap(String uidStr) {
        List<Long> uidList = new ArrayList<>();
        String[] split = uidStr.split(",");
        for (String str : split) {
            uidList.add(Long.valueOf(str));
        }
        return dcUserInfoService.getSimpleUsersMap(uidList);
    }
}
