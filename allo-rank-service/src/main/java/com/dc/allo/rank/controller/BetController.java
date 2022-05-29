package com.dc.allo.rank.controller;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.rank.domain.bet.*;
import com.dc.allo.rpc.domain.bet.BetGameRoundRecord;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rank.service.bet.BetFacadeService;
import com.dc.allo.rpc.aspect.whitelistip.IpCheck;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/27.
 */

@RestController
@RequestMapping("/bet")
@Slf4j
public class BetController {

    @Autowired
    BetFacadeService betFacadeService;

    /**
     * 新增投注游戏活动配置（后台用）
     * @param betActConf
     * @return
     */
//    @IpCheck
    @PostMapping("/admin/addActConf")
    public AlloResp<Boolean> addActConf(@RequestBody BetActConfig betActConf,HttpServletRequest request){
        try{
            long result = betFacadeService.addActConf(betActConf);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    /**
     * 更新活动配置（后台用）
     * @param betActConf
     * @param request
     * @return
     */
//    @IpCheck
    @PostMapping("/admin/updateActConf")
    public AlloResp<Boolean> updateActConf(@RequestBody BetActConfig betActConf,HttpServletRequest request){
        try{
            long result = betFacadeService.updateActConf(betActConf);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    /**
     * 查所有活动配置
     * @return
     */
    @RequestMapping("/admin/queryAllActConf")
    public AlloResp<List<BetActConfig>> queryAllActConf(){
        List<BetActConfig> result = null;
        try{
            result = betFacadeService.queryAllAct();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 新增游戏精灵（后台用）
     * @param betSpirit
     * @param request
     * @return
     */
//    @IpCheck
    @PostMapping("/admin/addSpirit")
    public AlloResp<Boolean> addSpirit(@RequestBody BetSpirit betSpirit,HttpServletRequest request){
        try{
            long result = betFacadeService.addSpirit(betSpirit);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    /**
     * 更新游戏精灵（后台用）
     * @param betSpirit
     * @param request
     * @return
     */
//    @IpCheck
    @PostMapping("/admin/updateSpirit")
    public AlloResp<Boolean> updateSpirit(@RequestBody BetSpirit betSpirit,HttpServletRequest request){
        try{
            long result = betFacadeService.updateSpirit(betSpirit);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    /**
     * 查询投注精灵信息（后台用）
     * @return
     */
    @RequestMapping("/admin/querySpirits")
    public AlloResp<AdminPage<BetSpirit>> querySpirits(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "99") int pageSize){

        AdminPage<BetSpirit> result = null;
        try{
            result = betFacadeService.pageSpirit(pageNum,pageSize);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 新增游戏精灵配置（后台用）
     * @param betSpiritConf
     * @param request
     * @return
     */
//    @IpCheck
    @PostMapping("/admin/addSpiritConf")
    public AlloResp<Boolean> addSpiritConf(@RequestBody BetSpiritConfig betSpiritConf,HttpServletRequest request){
        try{
            long result = betFacadeService.addSpiritConf(betSpiritConf);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    /**
     * 更新游戏配置信息（后台用）
     * @param betSpiritConf
     * @param request
     * @return
     */
//    @IpCheck
    @PostMapping("/admin/updateSpiritConf")
    public AlloResp<Boolean> updateSpiritConf(@RequestBody BetSpiritConfig betSpiritConf,HttpServletRequest request){
        try{
            long result = betFacadeService.updateSpiritConfig(betSpiritConf);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    /**
     * 查询游戏配置信息（后台用）
     * @param actId
     * @return
     */
    @RequestMapping("/admin/querySpiritConfs")
    public AlloResp<AdminPage<BetSpiritInfo>> querySpiritConf(@RequestParam(value = "actId") long actId){
        AdminPage<BetSpiritInfo> result = null;
        try{
            result = betFacadeService.querySpiritConfigs(actId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 新增奖励配置（后台用）
     * @param betAwardConf
     * @param request
     * @return
     */
//    @IpCheck
    @PostMapping("/admin/addAwardConf")
    public AlloResp<Boolean> addAwardConf(@RequestBody BetAwardConfig betAwardConf,HttpServletRequest request){
        try{
            long result = betFacadeService.addAwardConf(betAwardConf);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    /**
     * 更新奖励配置（后台用）
     * @param betAwardConf
     * @param request
     * @return
     */
    //@IpCheck
    @PostMapping("/admin/updateAwardConf")
    public AlloResp<Boolean> updateAwardConf(@RequestBody BetAwardConfig betAwardConf,HttpServletRequest request){
        try{
            long result = betFacadeService.updateAwardConf(betAwardConf);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }


    /**
     * 查询奖励配置（后台用）
     * @param actId
     * @return
     */
    @RequestMapping("/admin/queryAwardConfs")
    public AlloResp<AdminPage<BetAwardConfig>> queryAwardConf(@RequestParam(value = "actId") long actId){
        AdminPage<BetAwardConfig> result = null;
        try{
            result = betFacadeService.queryAwardConfigs(actId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 查询每天盈亏情况统计（后台用）
     * @param actId
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/admin/queryBetStatistic")
    public AlloResp<AdminPage<BetStatistic>> queryBetStatistic(
            @RequestParam(value = "actId") long actId, @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "pageNum") long pageNum, @RequestParam(value = "pageSize") int pageSize){
        AdminPage<BetStatistic> result = null;
        try{
            result = betFacadeService.pageBetStatistic(actId, date, pageNum, pageSize);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 分页查询游戏日志（后台用）
     * @param actId
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/admin/queryGameRound")
    public AlloResp<AdminPage<BetGameRoundRecord>> queryGameRound(@RequestParam(value = "actId") long actId,@RequestParam(value = "date") String date,@RequestParam(value = "pageNum") int pageNum,@RequestParam(value = "pageSize") int pageSize){
        AdminPage<BetGameRoundRecord> result = null;
        try{
            result = betFacadeService.pageGameRound(actId, date, pageNum, pageSize);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    @RequestMapping("/admin/genGameRound")
    public AlloResp<Long> genGameRound(@RequestParam(value = "actId")long actId,@RequestParam(value = "key")String key) {
        if (actId <= 0||!"asdfghjkl".equals(key)) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            long result = betFacadeService.genGameRound(actId);
            return AlloResp.success(result);
        } catch (DCException dce) {
            return AlloResp.failed(dce, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }
}
