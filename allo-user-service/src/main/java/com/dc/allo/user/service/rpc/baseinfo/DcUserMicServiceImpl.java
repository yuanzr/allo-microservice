package com.dc.allo.user.service.rpc.baseinfo;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.domain.room.RoomMicPositionVo;
import com.dc.allo.rpc.api.user.mic.DcUserMicService;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.user.service.baseinfo.UserMicService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: DcUserMicServiceImpl
 *
 * @date: 2020年04月30日 18:24
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class DcUserMicServiceImpl implements DcUserMicService {

    @Autowired
    private UserMicService userMicService;

    /**
     * 获取用户信息非实时的麦序列表-UserMicService.getRoomMicListNotRealTime
     * 缓存名格式要用RedisKeyUtil.getKey方法
     * @param roomUid
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.Map<java.lang.String,com.dc.allo.common.domain.room.RoomMicPositionVo>>
     * @author qinrenchuan
     * @date 2020/4/30/0030 18:33
     */
    @Override
    public AlloResp<Map<String, RoomMicPositionVo>> getRoomMicListNotRealTime(Long roomUid) {
        if (roomUid == null || roomUid < 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        try {
            Map<String, RoomMicPositionVo> roomMicPositionVoMap = userMicService.getRoomMicListNotRealTime(roomUid);
            return AlloResp.success(roomMicPositionVoMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }
}
