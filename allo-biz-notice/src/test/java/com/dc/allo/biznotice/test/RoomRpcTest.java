package com.dc.allo.biznotice.test;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.biznotice.BizNoticeApplication;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**F
 * description: RoomRpcTest
 *
 * @date: 2020年06月03日 16:24
 * @author: qinrenchuan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BizNoticeApplication.class)
@Slf4j
public class RoomRpcTest {

    @Reference
    DcRoomInfoService dcRoomInfoService;

    @Test
    public void testQuerySimpleRoomListByUids() {
        List<Long> uidList = new ArrayList<>();
        uidList.add(61004336L);
        uidList.add(61004379L);

        AlloResp<List<SimpleRoom>> listAlloResp = dcRoomInfoService.querySimpleRoomListByUids(uidList);
        log.info("======================");
        log.info(JSONObject.toJSONString(listAlloResp));
    }

    @Test
    public void testSimpleRoomMapByUids() {
        List<Long> uidList = new ArrayList<>();
        uidList.add(61004336L);
        uidList.add(61004379L);

        AlloResp<Map<Long, SimpleRoom>> listAlloResp = dcRoomInfoService.querySimpleRoomMapByUids(uidList);
        log.info("======================");
        log.info(JSONObject.toJSONString(listAlloResp));
    }
}
