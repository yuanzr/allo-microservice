package com.dc.allo.rank.service.rpc.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rank.service.award.AwardService;
import com.dc.allo.rpc.api.rank.DcAwardService;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Service
@Slf4j
public class DcAwardServiceImp implements DcAwardService {

    @Autowired
    AwardService awardService;

    @Override
    public AlloResp<Boolean> purchaseAwardPackage(long uid, int packageId) throws Exception {
        if(uid<=0 || packageId <=0){
            return AlloResp.failed(BusiStatus.PARAMERROR.value(),"param err",false);
        }
        try{
            boolean result = awardService.purchaseAwardPackage(uid,packageId);
            return AlloResp.success(result);
        }catch (DCException dce){
            return AlloResp.failed(dce,null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"server busy",false);
        }
    }

    @Override
    public AlloResp<ReleasePkgResult> purchaseAwardPackage(long uid, int packageId, int purchaseType, int purchaseNum) {
        if(uid<=0 || packageId <=0){
            return AlloResp.failed(BusiStatus.PARAMERROR.value(),"param err",null);
        }
        try{
            ReleasePkgResult result = awardService.purchaseAwardPackage(uid, packageId, purchaseType, purchaseNum);
            return AlloResp.success(result);
        }catch (DCException dce){
            return AlloResp.failed(dce,null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"server busy",null);
        }
    }

    @Override
    public AlloResp<AwardOfPackage> lotteryAwardPackage(long uid, int packageId) throws Exception {
        if(uid<=0 || packageId <=0){
            return AlloResp.failed(BusiStatus.PARAMERROR.value(),"param err",null);
        }
        try{
            AwardOfPackage result = awardService.lotteryAwardPackage(uid, packageId);
            return AlloResp.success(result);
        }catch (DCException dce){
            return AlloResp.failed(dce,null);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"server busy",null);
        }
    }

    @Override
    public AlloResp<ReleasePkgResult> releaseAwardPackage(long uid, int packageId) throws Exception {
        if (uid <= 0 || packageId <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            ReleasePkgResult result = awardService.releaseAwardPackage(uid, packageId);
            return AlloResp.success(result);
        } catch (DCException dce){
            return AlloResp.failed(dce,null);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }

    @Override
    public AlloResp<Boolean> check2CreatNextMonthTable() {
        try {
            awardService.creatNextMonthTable();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", false);
        }
        return AlloResp.success(true);
    }
}
