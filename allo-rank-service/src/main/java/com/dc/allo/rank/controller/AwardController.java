package com.dc.allo.rank.controller;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rank.domain.award.CommonAward;
import com.dc.allo.rank.domain.award.CommonAwardOfPackage;
import com.dc.allo.rank.domain.award.CommonAwardPackage;
import com.dc.allo.rank.service.award.AwardService;
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
@RequestMapping("/award")
@Slf4j
public class AwardController {

    @Autowired
    AwardService awardService;

    /**
     * 新增公共奖池配置（后台用）
     * @param commonAward
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAward/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addCommonAward(@RequestBody CommonAward commonAward,HttpServletRequest request){
        try{
            long result = awardService.addCommonAward(
                    commonAward.getName(), commonAward.getIcon(),
                    commonAward.getReleaseType(), commonAward.getReleaseId(),
                    commonAward.getPrice(), commonAward.getPriceDisplay(),
                    commonAward.getExtend());
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
     * 更新奖品配置（后台用）
     * @param commonAward
     * @param request
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAward/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateCommonAward(@RequestBody CommonAward commonAward,HttpServletRequest request){
        try{
            long result = awardService.updateCommonAward(
                    commonAward.getId(), commonAward.getName(),
                    commonAward.getIcon(), commonAward.getReleaseType(),
                    commonAward.getReleaseId(), commonAward.getPrice(),
                    commonAward.getPriceDisplay(), commonAward.getExtend());
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
     * 分页获取奖励配置（后台用）
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAward/page")
    public AlloResp<AdminPage<CommonAward>> pageCommonAward(@RequestParam(value = "pageNum") int pageNum,@RequestParam(value = "pageSize") int pageSize,HttpServletRequest request){
        AdminPage<CommonAward> result = null;
        try{
            result = awardService.pageAward(pageNum,pageSize);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 新增礼包配置（后台用）
     * @param awardPackage
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardPackage/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addAwardPackage(@RequestBody CommonAwardPackage awardPackage,HttpServletRequest request){
        try{
            long result = awardService.addCommonAwardPackage(awardPackage);
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
     * 更新礼包配置（后台用）
     * @param awardPackage
     * @param request
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardPackage/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateAwardPackage(@RequestBody CommonAwardPackage awardPackage,HttpServletRequest request){
        try{
            long result = awardService.updateCommonAwardPackage(awardPackage);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardPackage/queryAll", method = RequestMethod.GET)
    public AlloResp<List<CommonAwardPackage>> queryAllAwardPackage(HttpServletRequest request){
        List<CommonAwardPackage> result = null;
        try{
            result = awardService.queryAllAwardPackage();
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 分页获取礼包配置（后台用）
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardPackage/page")
    public AlloResp<AdminPage<CommonAwardPackage>> pageAwardPackage(@RequestParam(value = "pageNum") int pageNum,@RequestParam(value = "pageSize") int pageSize,HttpServletRequest request){
        AdminPage<CommonAwardPackage> result = null;
        try{
            result = awardService.pageAwardPackage(pageNum, pageSize);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 新增礼包内奖品配置（后台用）
     * @param awardOfPackage
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardOfPackage/add",method = RequestMethod.POST)
    public AlloResp<Boolean> addAwardOfPackage(@RequestBody CommonAwardOfPackage awardOfPackage,HttpServletRequest request){
        try{
            long result = awardService.addCommonAwardOfPackage(awardOfPackage);
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
     * 更新礼包内奖品配置（后台用）
     * @param awardOfPackage
     * @param request
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardOfPackage/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateAwardOfPackage(@RequestBody CommonAwardOfPackage awardOfPackage,HttpServletRequest request){
        try{
            long result = awardService.updateCommonAwardOfPackage(awardOfPackage);
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
     * 分页获取礼包内奖品配置信息（后台用）
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardOfPackage/page")
    public AlloResp<AdminPage<CommonAwardOfPackage>> pageAwardOfPackage(@RequestParam(value = "pageNum") int pageNum,@RequestParam(value = "pageSize") int pageSize,HttpServletRequest request){
        AdminPage<CommonAwardOfPackage> result = null;
        try{
            result = awardService.pageAwardOfPackage(pageNum, pageSize);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }

    /**
     * 根据礼包id查礼包礼物配置详情（后台用）
     * @param packageId
     * @param request
     * @return
     */
//    @IpCheck
    @RequestMapping(value = "/admin/commonAwardOfPackage/getAwardDetailOfPackage", method = RequestMethod.GET)
    public AlloResp<List<CommonAwardOfPackage>> getAwardDetailOfPackage(
            @RequestParam(value = "packageId") long packageId,HttpServletRequest request){
        List<CommonAwardOfPackage> result = null;
        try{
            result = awardService.getAwardOfPackage(packageId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",false);
        }
        return AlloResp.success(result);
    }
}
