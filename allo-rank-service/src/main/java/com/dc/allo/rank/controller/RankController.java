package com.dc.allo.rank.controller;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rank.domain.bet.BetSpirit;
import com.dc.allo.rank.domain.rank.config.*;
import com.dc.allo.rank.service.rank.RankManager;
import com.dc.allo.rpc.aspect.whitelistip.IpCheck;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/15.
 */

@RestController
@RequestMapping("/rank")
@Slf4j
public class RankController extends BaseController{

    @Autowired
    RankManager rankManager;

    //    @IpCheck
    @RequestMapping(value = "/admin/app/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addApp(@RequestBody App app,HttpServletRequest request){
        try{
            long result = rankManager.addApp(app.getName(),app.getAppKey());
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping(value = "/admin/app/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateApp(@RequestBody App app,HttpServletRequest request){
        try{
            long result = rankManager.updateApp(app.getId(), app.getName(), app.getAppKey(), app.getEnable());
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping(value = "/admin/app/queryAll")
    public AlloResp<List<App>> queryAllApp(HttpServletRequest request){
        try{
            return AlloResp.success(rankManager.queryAllApp());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
    }

    /**
     * 新增榜单配置
     * @param config
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/config/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addRankConfig(@RequestBody RankConfig config,HttpServletRequest request){
        try{
            long result = rankManager.addRankConfig(config);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "添加失败", false);
        }
        return AlloResp.success(false);
    }

    @RequestMapping(value = "/admin/config/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateRankConfig(@RequestBody RankConfig config,HttpServletRequest request){
        try{
            long result = rankManager.updateRankConfig(config);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping("/admin/config/page")
    public AlloResp<AdminPage<RankConfig>> pageRankConfig(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "30") int pageSize){
        AdminPage<RankConfig> result = null;
        try{
            result = rankManager.pageRankConfig(pageNum,pageSize);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

    //    @IpCheck
    @RequestMapping("/admin/config/queryRankKeys")
    public AlloResp<List<RankConfig>> queryRankKeys(){
        List<RankConfig> result = null;
        try{
            result = rankManager.queryRankKeys();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

    /**
     * 新增数据源配置
     * @param config
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/datasource/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addRankDataSourceConfig(@RequestBody RankDatasourceConfig config,HttpServletRequest request){
        try{
            long result = rankManager.addDataSourceConfig(config);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping(value = "/admin/datasource/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateRankDataSourceConfig(@RequestBody RankDatasourceConfig config,HttpServletRequest request){
        try{
            long result = rankManager.updateDataSourceConfig(config);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping("/admin/datasource/page")
    public AlloResp<AdminPage<RankDatasourceConfig>> pageDataSource(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "30") int pageSize){
        AdminPage<RankDatasourceConfig> result = null;
        try{
            result = rankManager.pageDataSourceConfig(pageNum, pageSize);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

    //    @IpCheck
    @RequestMapping(value = "/admin/datasource/queryAllId")
    public AlloResp<List<RankDatasourceConfig>> queryDataSource(HttpServletRequest request){
        try{
            return AlloResp.success(rankManager.queryAllId());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",null);
        }
    }


    //    @IpCheck
    @RequestMapping(value = "/admin/autoreward/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addRankAutoRewardConfig(@RequestBody RankAutoRewardConfig config,HttpServletRequest request){
        try{
            long result = rankManager.addAutoRewardConfig(config);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping(value = "/admin/autoreward/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateRankAutoRewardConfig(@RequestBody RankAutoRewardConfig config,HttpServletRequest request){
        try{
            long result = rankManager.updateAutoRewardConfig(config);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping("/admin/autoreward/page")
    public AlloResp<AdminPage<RankAutoRewardConfig>> pageRankAutoReward(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                    @RequestParam(value = "pageSize", defaultValue = "30") int pageSize){
        AdminPage<RankAutoRewardConfig> result = null;
        try{
            result = rankManager.pageAutoRewardConfig(pageNum, pageSize);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

    @RequestMapping(value = "/admin/bizid/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addBizIds(@RequestBody BizIdConfig bizIdConfig){
        try{
            long result = rankManager.addBizIdConfig(bizIdConfig.getRankId(), bizIdConfig.getBizIds());
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",false);
        }
        return AlloResp.success(false);
    }

    @RequestMapping(value = "/admin/bizid/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateBizIds(@RequestBody BizIdConfig bizIdConfig){
        try{
            long result = rankManager.updateBizIdConfig(bizIdConfig.getRankId(), bizIdConfig.getBizIds(), bizIdConfig.getId());
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    @RequestMapping("/admin/bizid/get")
    public AlloResp<BizIdConfig> getBizIds(@RequestParam(value = "rankId") long rankId){
        BizIdConfig result = null;
        try{
            result = rankManager.getBizIds4db(rankId);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

}
