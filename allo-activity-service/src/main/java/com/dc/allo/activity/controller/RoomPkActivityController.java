package com.dc.allo.activity.controller;

import com.dc.allo.activity.service.RoomPkActivityService;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 因为pk活动以东三区时间为准 所以有关pk活动的时间都要加5个钟
 */
@Slf4j
@RestController
@RequestMapping("/h5/room/pk/activity")
public class RoomPkActivityController {
    @Autowired
    private RoomPkActivityService roomPkActivityService;
    /**
     * 获取PK活动信息
     * @return
     */
    @RequestMapping(value = "/getPkActivityInfo", method = RequestMethod.POST)
    public AlloResp getPkActivityInfo(Long roomUid) {
        try {
            if(roomUid == null || roomUid ==0L){
                return AlloResp.failed(BusiStatus.PARAMERROR);
            }
            return AlloResp.success(roomPkActivityService.getRoomPkActivityInfoVo(roomUid));
        } catch (Exception e) {
            log.error("getPkActivityInfo error,roomUid:{}",roomUid,e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 获取历史前几天的榜单
     * @return
     */
    @RequestMapping(value = "/getPkActivityRankHistory", method = RequestMethod.POST)
    public AlloResp getPkActivityRankHistory() {
        try {
            return AlloResp.success(roomPkActivityService.getRoomActivityRankHistoryVo());
        } catch (Exception e) {
            log.error("getPkActivityRank error",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }

    /**
     * 获取当天的榜单
     * @return
     */
    @RequestMapping(value = "/getPkActivityRankToday", method = RequestMethod.POST)
    public AlloResp getPkActivityRankToday() {
        try {
            return AlloResp.success(roomPkActivityService.getRoomActivityRankTodayVo());
        } catch (Exception e) {
            log.error("getPkActivityRank error",e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
