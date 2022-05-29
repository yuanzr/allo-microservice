package com.dc.allo.user.kafka.handler;

import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.kafka.base.KafkaEvent;
import com.dc.allo.common.kafka.message.GiftMessage;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.vo.RoomPkActivityUpLevelVo;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.user.service.baseinfo.UserRoomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Component
@Slf4j
public class UserKafkaHandler {

    @Autowired
    private UserRoomService userRoomService;

    public void handler(KafkaEvent event) throws Exception{
    }
}
