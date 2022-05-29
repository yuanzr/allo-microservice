package com.dc.allo.job.job;

import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.roomplay.pk.DcRoomPkService;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("preview")
public class RoomTask{
    @Reference
    private DcRoomPkService dcRoomPkService;
    @Reference
    private DcRoomInfoService dcRoomInfoService;
    /**
     * 房间战力值计算
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void roomCombatPowerCompute() {
        log.info("roomCombatPowerCompute start==============");
        try {
            AlloResp alloResp = dcRoomPkService.roomCombatPowerCompute();
            log.info("roomCombatPowerCompute.code:{}", alloResp.getCode());
        } catch (Exception e) {
            log.error("roomCombatPowerCompute error", e);
        }
        log.info("roomCombatPowerCompute end=============");
    }

    /**
     * 取消过期的房间Pk邀请
     * 30秒扫一次
     */
    @Scheduled(cron = "0 */1 * * * ? ")
    public void cancelExpireRoomPkInvite() {
        log.info("cancelExpireRoomPkInvite start==============");
        try {
            AlloResp alloResp = dcRoomPkService.cancelExpireRoomPkInvite();
            log.info("cancelExpireRoomPkInvite.code:{}", alloResp.getCode());
        }catch (Exception e){
            log.error("cancelExpireRoomPkInvite error",e);
        }
        log.info("cancelExpireRoomPkInvite end=============");
    }

    /**
     * 处理过期的pk中记录
     */
    @Scheduled(cron = "0 */1 * * * ? ")
    public void handlerExpireRoomPking() {
        log.info("handlerExpireRoomPking start==============");
        try {
            AlloResp alloResp = dcRoomPkService.handlerExpireRoomPking();
            log.info("handlerExpireRoomPking.code:{}", alloResp.getCode());
        }catch (Exception e){
            log.error("handlerExpireRoomPking error",e);
        }
        log.info("handlerExpireRoomPking end=============");
    }

    /**
     * 更新当天的热力值榜单
     */
    @Scheduled(cron = "0 */1 * * * ? ")
    public void updateTodayHeatValueRank() {
        log.info("updateTodayHeatValueRank start==============");
        try {
            AlloResp alloResp =  dcRoomPkService.updateTodayHeatValueRank();
            log.info("updateTodayHeatValueRank.code:{}", alloResp.getCode());
        }catch (Exception e){
            log.error("updateTodayHeatValueRank error",e);
        }
        log.info("updateTodayHeatValueRank end=============");
    }
    /**
     * 结算今天的热度值
     */
    @Scheduled(cron = "0 1 5 * * ?")
    public void computeRoomHeatValueAward() {
        log.info("computeRoomHeatValueAward start==============");
        try {
            AlloResp alloResp =  dcRoomPkService.computeRoomHeatValueAward(-1);
            log.info("computeRoomHeatValueAward.code:{},size:{}", alloResp.getCode(),alloResp.getData());
        }catch (Exception e){
            log.error("computeRoomHeatValueAward error",e);
        }
        log.info("computeRoomHeatValueAward end=============");
    }
}
