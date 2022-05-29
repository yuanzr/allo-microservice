package com.tc.allo.room;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.room.RoomServiceApplication;
import com.dc.allo.room.service.room.RoomService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description: RoomServiceTest
 *
 * @date: 2020年04月29日 10:59
 * @author: qinrenchuan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoomServiceApplication.class)
@Slf4j
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Reference
    DcUserInfoService dcUserInfoService;

    @Test
    public void testGetSimpleUserList() {
        List<Long> uidList = new ArrayList<>();
        uidList.add(61004356L);
        uidList.add(61004354L);
        AlloResp<List<SimpleUserInfo>> simpleUserList = dcUserInfoService.getSimpleUserList(uidList);
        log.info("===============");
        log.info(JSONObject.toJSONString(simpleUserList));

    }

    @Test
    public void testGetSimpleUsersMap() {
        List<Long> uidList = new ArrayList<>();
        uidList.add(61004356L);
        uidList.add(61004354L);
        AlloResp<Map<Long, SimpleUserInfo>> simpleUsersMap = dcUserInfoService.getSimpleUsersMap(uidList);
        log.info("===============");
        log.info(JSONObject.toJSONString(simpleUsersMap));

    }

    @Test
    public void testQueryByRoomUids() {
        List<Long> uids = new ArrayList<>();
        uids.add(61004335L);
        uids.add(61004336L);
        uids.add(61004337L);
        uids.add(61004338L);
        uids.add(61004339L);
        uids.add(61004340L);
        List<Room> rooms = roomService.queryByRoomUids(uids);
        System.out.println(rooms);
    }

    @Test
    public void testGetHomeRunningRoomList() {
        roomService.getHomeRunningRoomList();
    }
}
