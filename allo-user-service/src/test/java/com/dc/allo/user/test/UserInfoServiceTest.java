package com.dc.allo.user.test;

import com.dc.allo.user.UserServiceApplication;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.user.service.baseinfo.UserInfoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description: UserInfoServiceTest
 *
 * @date: 2020年04月01日 20:48
 * @author: qinrenchuan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Slf4j
public class UserInfoServiceTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testGetById() {
        Long uid = 70006502L;
        UserInfo userInfo = userInfoService.getByUid(uid);
        log.info("testGetById uid:{}", uid);
        log.info("testGetById userInfo:{}", userInfo);
    }

    @Test
    public void getByErbanNo() {
        Long erbanNO = 9100850L;
        UserInfo userInfo = userInfoService.getByErbanNo(erbanNO);
        log.info("testGetById erbanNO:{}", erbanNO);
        log.info("testGetById userInfo:{}", userInfo);
    }

    @Test
    public void testQueryByUid() {
        List<Long> list = new ArrayList<>();
        list.add(61004335L);
        list.add(61004336L);
        list.add(61004337L);
        list.add(61004338L);
        list.add(61004339L);
        list.add(61004340L);
        List<UserInfo> userInfos = userInfoService.queryByUids(list);
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo.getErbanNo() + "---" + userInfo.getUid());
        }
    }


    @Test
    public void testQueryErbanNo2UidMapByErbanNos() {
        /**
         10000---61004335
         3868359---61004336
         3939955---61004337
         1734682---61004338
         4260084---61004339
         6304300---61004340
         */
        List<Long> list = new ArrayList<>();
        list.add(10000L);
        list.add(3868359L);
        list.add(3939955L);
        list.add(393995511L);
        list.add(1734682L);
        list.add(1734682L);

        Map<Long, Long> erbanNo2UidMapByErbanNos =
                userInfoService.queryErbanNo2UidMapByErbanNos(list);
        System.out.println(erbanNo2UidMapByErbanNos);
    }
}
