package com.dc.allo.rank.controller;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rank.domain.activity.Activity;
import com.dc.allo.rpc.domain.activity.ActivityBgInfo;
import com.dc.allo.rpc.domain.activity.ActivityGiftInfo;
import com.dc.allo.rpc.domain.activity.ActivityRankInfo;
import com.dc.allo.rpc.domain.activity.ActivityRuleInfo;
import com.dc.allo.rank.service.activity.ActivityService;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/15.
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/admin/activity/add",method = RequestMethod.POST)
    public AlloResp<Long> addActivity(@RequestBody Activity activity,HttpServletRequest request){
        try{
            long result = activityService.addActivity(activity);
            if(result > 0) {
                return AlloResp.success(activity.getId());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",0L);
        }
        return AlloResp.success(activity.getId());
    }

    @RequestMapping(value = "/admin/activity/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateActivity(@RequestBody Activity activity,HttpServletRequest request){
        try{
            long result = activityService.updateActivity(activity);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping(value = "/admin/activity/queryAll")
    public AlloResp<List<Activity>> queryAllActivity(HttpServletRequest request){
        try{
            return AlloResp.success(activityService.queryActivity());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
    }

    @RequestMapping(value = "/admin/activityBgInfo/add",method = RequestMethod.POST)
    public AlloResp<Long> addActivityBgInfo(@RequestBody ActivityBgInfo activityBgInfo,HttpServletRequest request){
        try{
            long result = activityService.addActivityBgInfo(activityBgInfo);
            if(result > 0) {
                return AlloResp.success(activityBgInfo.getId());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",0L);
        }
        return AlloResp.success(activityBgInfo.getId());
    }

    @RequestMapping(value = "/admin/activityBgInfo/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateActivityBgInfo(@RequestBody ActivityBgInfo activityBgInfo,HttpServletRequest request){
        try{
            long result = activityService.updateActivityBgInfo(activityBgInfo);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping("/admin/activityBgInfo/get")
    public AlloResp<ActivityBgInfo> getActivityBgInfo(@RequestParam(value = "actId", defaultValue = "1") long actId){
        ActivityBgInfo result = null;
        try{
            result = activityService.getActivityBgInfo(actId);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

    @RequestMapping(value = "/admin/activityRuleInfo/add",method = RequestMethod.POST)
    public AlloResp<Long> addActivityRuleInfo(@RequestBody ActivityRuleInfo activityRuleInfo,HttpServletRequest request){
        try{
            long result = activityService.addActivityRuleInfo(activityRuleInfo);
            if(result > 0) {
                return AlloResp.success(activityRuleInfo.getId());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",0L);
        }
        return AlloResp.success(0L);
    }

    @RequestMapping(value = "/admin/activityRuleInfo/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateActivityRuleInfo(@RequestBody ActivityRuleInfo activityRuleInfo,HttpServletRequest request){
        try{
            long result = activityService.updateActivityRuleInfo(activityRuleInfo);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    @RequestMapping("/admin/activityRuleInfo/del")
    public AlloResp<Boolean> delActivityBgInfo(@RequestParam(value = "actId", defaultValue = "1") long actId){
        long result;
        try{
            result = activityService.delActivityRuleInfo(actId);
            if(result>0){
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"删除失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping("/admin/activityRuleInfo/get")
    public AlloResp<ActivityRuleInfo> getActivityRuleInfo(@RequestParam(value = "actId", defaultValue = "1") long actId){
        ActivityRuleInfo result = null;
        try{
            result = activityService.getActivityRuleInfo(actId);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

    @RequestMapping(value = "/admin/activityRankInfo/add",method = RequestMethod.POST)
    public AlloResp<Long> addActivityRankInfo(@RequestBody ActivityRankInfo activityRankInfo,HttpServletRequest request){
        try{
            long result = activityService.addActivityRankInfo(activityRankInfo);
            if(result > 0) {
                return AlloResp.success(activityRankInfo.getId());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",0L);
        }
        return AlloResp.success(0L);
    }

    @RequestMapping(value = "/admin/activityRankInfo/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateActivityRankInfo(@RequestBody ActivityRankInfo activityRankInfo,HttpServletRequest request){
        try{
            long result = activityService.updateActivityRankInfo(activityRankInfo);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    @RequestMapping("/admin/activityRankInfo/del")
    public AlloResp<Boolean> delActivityRankInfo(@RequestParam(value = "actId", defaultValue = "1") long actId){
        long result;
        try{
            result = activityService.delActivityRankInfo(actId);
            if(result>0){
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"删除失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping("/admin/activityRankInfo/get")
    public AlloResp<ActivityRankInfo> getActivityRankInfo(@RequestParam(value = "actId", defaultValue = "1") long actId){
        ActivityRankInfo result = null;
        try{
            result = activityService.getActivityRankInfo(actId);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

    @RequestMapping(value = "/admin/activityGiftInfo/add",method = RequestMethod.POST)
    public AlloResp<Long> addActivityGiftInfo(@RequestBody ActivityGiftInfo activityGiftInfo,HttpServletRequest request){
        try{
            long result = activityService.addActivityGiftInfo(activityGiftInfo);
            if(result > 0) {
                return AlloResp.success(activityGiftInfo.getId());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"添加失败",0L);
        }
        return AlloResp.success(0L);
    }

    @RequestMapping(value = "/admin/activityGiftInfo/update",method = RequestMethod.POST)
    public AlloResp<Boolean> updateActivityGiftInfo(@RequestBody ActivityGiftInfo activityGiftInfo,HttpServletRequest request){
        try{
            long result = activityService.updateActivityGiftInfo(activityGiftInfo);
            if(result > 0) {
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"更新失败",false);
        }
        return AlloResp.success(false);
    }

    @RequestMapping("/admin/activityGiftInfo/del")
    public AlloResp<Boolean> delActivityGiftInfo(@RequestParam(value = "id", defaultValue = "1") long id){
        long result;
        try{
            result = activityService.deleteActivityGiftInfo(id);
            if(result>0){
                return AlloResp.success(true);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"删除失败",false);
        }
        return AlloResp.success(false);
    }

    //    @IpCheck
    @RequestMapping("/admin/activityGiftInfo/query")
    public AlloResp<List<ActivityGiftInfo>> getActivityGiftInfo(@RequestParam(value = "actId", defaultValue = "1") long actId){
        List<ActivityGiftInfo> result = null;
        try{
            result = activityService.queryGiftInfos(actId);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"查询失败",null);
        }
        return AlloResp.success(result);
    }

}
