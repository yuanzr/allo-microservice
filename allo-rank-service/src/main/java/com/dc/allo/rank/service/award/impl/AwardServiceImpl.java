package com.dc.allo.rank.service.award.impl;

import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.dc.allo.rank.constants.AwardConstants;
import com.dc.allo.common.constants.AwardEnums;
import com.dc.allo.rank.constants.Constant;
import com.dc.allo.rank.domain.award.CommonAwardOfPackage;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rank.domain.award.CommonAward;
import com.dc.allo.rank.domain.award.CommonAwardPackage;
import com.dc.allo.rank.domain.award.vo.ReleaseResult;
import com.dc.allo.rank.mapper.award.CommonAwardUtilMapper;
import com.dc.allo.rank.service.award.AwardService;
import com.dc.allo.rank.service.award.realease.ReleaseProcessorProxy;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Slf4j
@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    ReleaseProcessorProxy releaseProcessorProxy;

    @Autowired
    CommonAwardUtilMapper awardUtilMapper;

    @Override
    public long addCommonAward(String name, String icon, int releaseType, String releaseId, int price, int priceDisplay, String extend) {
        return awardUtilMapper.addCommonAward(name, icon, releaseType, releaseId, price, priceDisplay, extend);
    }

    @Override
    public long updateCommonAward(long id, String name, String icon, int releaseType, String releaseId, int price, int priceDisplay, String extend) {
        return awardUtilMapper.updateCommonAward(id, name, icon, releaseType, releaseId, price, priceDisplay, extend);
    }

    @Override
    public AdminPage<CommonAward> pageAward(int pageNum, int pageSize) {
        AdminPage<CommonAward> page = new AdminPage<>();
        long total = awardUtilMapper.countCommonAward();
        List<CommonAward> awards = awardUtilMapper.page((pageNum - 1) * pageSize,pageSize);
        page.setTotal(total);
        page.setRows(awards);
        return page;
    }

    @Override
    @Cached(name = "CommonAwardCache.queryAllCommonAward", cacheType = CacheType.BOTH, cacheNullValue = true, expire = 1, timeUnit = TimeUnit.MINUTES)
    public List<CommonAward> queryCommonAwards() {
        return awardUtilMapper.queryAllCommonAward();
    }

    @Override
    public long addCommonAwardPackage(CommonAwardPackage awardPackage) {
        return awardUtilMapper.addAwardPackage(awardPackage);
    }

    @Override
    public long updateCommonAwardPackage(CommonAwardPackage awardPackage) {
        return awardUtilMapper.updateAwardPackage(awardPackage);
    }

    @Override
    public AdminPage<CommonAwardPackage> pageAwardPackage(int pageNum, int pageSize) {
        AdminPage<CommonAwardPackage> page = new AdminPage<>();
        long total = awardUtilMapper.countAwardPackage();
        List<CommonAwardPackage> awards = awardUtilMapper.pageAwardPackage((pageNum - 1) * pageSize, pageSize);
        page.setTotal(total);
        page.setRows(awards);
        return page;
    }

    @Override
    public List<CommonAwardPackage> queryAllAwardPackage() {
        return awardUtilMapper.queryAllAwardPackage();
    }

    @Override
    public long addCommonAwardOfPackage(CommonAwardOfPackage awardOfPackage) {
        return awardUtilMapper.addAwardOfPackage(awardOfPackage);
    }

    @Override
    public long updateCommonAwardOfPackage(CommonAwardOfPackage awardOfPackage) {
        return awardUtilMapper.updateAwardOfPackage(awardOfPackage);
    }

    @Override
    public AdminPage<CommonAwardOfPackage> pageAwardOfPackage(int pageNum, int pageSize) {
        AdminPage<CommonAwardOfPackage> page = new AdminPage<>();
        long total = awardUtilMapper.countAwardOfPackage();
        List<CommonAwardOfPackage> awards = awardUtilMapper.pageAwardOfPackage((pageNum - 1) * pageSize, pageSize);
        page.setTotal(total);
        page.setRows(awards);
        return page;
    }

    @Override
    public List<CommonAwardOfPackage> getAwardOfPackage(long packageId) {
        return awardUtilMapper.getAwardOfPackage(packageId);
    }

    @Override
    public boolean purchaseAwardPackage(long uid, long packageId) throws Exception {
        //TODO ?????????,???????????????

        return false;
    }

    @Override
    public ReleasePkgResult purchaseAwardPackage(long uid, long packageId, int purchaseType, long purchaseNum) {
        log.info(AwardConstants.INFO_LOG + "???????????? uid:{} packageId:{} purchaseType:{} purchaseNum:{} ", uid, packageId,purchaseType,purchaseNum);
        ReleasePkgResult releasePkgResult = new ReleasePkgResult();
        releasePkgResult.setResult(false);
        //?????? packageId ???????????????
        CommonAwardPackage awardPackage = awardUtilMapper.getAwardPackageById(packageId);
        if (awardPackage == null) {
            log.warn(AwardConstants.WARN_LOG + "?????????????????????????????? uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(!awardPackage.isEnable()){
            log.warn(AwardConstants.WARN_LOG + "????????????????????????????????? uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(!awardPackage.isNeedPurchase()){
            log.warn(AwardConstants.WARN_LOG + "??????????????????????????????????????? uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(awardPackage.getPurchaseType()!=purchaseType){
            log.warn(AwardConstants.WARN_LOG + "?????????????????????????????????????????????????????? uid:{} packageId:{} pucharseType:{}", uid, packageId,purchaseType);
            return releasePkgResult;
        }
        //?????????????????????
        String seqId = genRandomSqeId(uid, packageId);
        //?????? packageId ?????????????????????????????????????????????????????????
        List<AwardOfPackage> awardOfPackages = awardUtilMapper.getAwardList(packageId);

        //????????????????????????????????????????????????????????????????????????
        awardOfPackages = translateAwards(awardOfPackages,purchaseNum);

        //??????????????????
        try (HintManager hintManager = HintManager.getInstance()) {
            //????????????????????????
            hintManager.setMasterRouteOnly();

            //???????????????????????????
            awardUtilMapper.addNewReleaseRecord(uid, packageId, awardPackage.getName(), awardPackage.getIcon(), seqId,
                    AwardEnums.ReleaseStatus.INIT.val(), JSONObject.toJSONString(awardOfPackages));
            if (CollectionUtils.isEmpty(awardOfPackages)) {
                log.warn(AwardConstants.WARN_LOG + "????????????????????????????????? uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "?????????????????????????????????");
                releasePkgResult.setResult(true);
                return releasePkgResult;
            }
            //???????????????
            if (!packageStocksCheck(awardPackage)) {
                log.warn(AwardConstants.WARN_LOG + "reducePackageStockWithCheck ?????????????????????????????? uid:{} packageId:{} awardPackage:{}", uid, packageId, awardPackage);
                //???????????????????????????????????????
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "??????????????????");
                releasePkgResult.setResult(false);
                return releasePkgResult;
            }
            //???????????????????????????
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                awardUtilMapper.addNewReleaseRecordDetail(uid, seqId, awardOfPackage.getAwardId(), awardOfPackage.getName(), awardOfPackage.getIcon(),
                        awardOfPackage.getReleaseType(), awardOfPackage.getReleaseId(), awardOfPackage.getAwardCount(),
                        awardOfPackage.getValidDays(), awardOfPackage.getPrice(),
                        awardOfPackage.getExtend(), AwardEnums.ReleaseStatus.INIT.val(), awardOfPackage.getRemark()==null?"????????????":awardOfPackage.getRemark(), awardOfPackage.getId());
            }
            //????????????
            boolean packageReleaseSuccess = true;
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                boolean awardReleaseSuccess = releaseAward(uid, seqId, awardOfPackage);
                if (!awardReleaseSuccess) {
                    packageReleaseSuccess = false;
                    //???????????????????????????????????????
                    awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "???????????????:awardOfPackageId:[" + awardOfPackage.getId() + "]????????????");
                }
            }
            if (packageReleaseSuccess) {
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "??????????????????");
                releasePkgResult.setResult(true);
            }
            releasePkgResult.setAwardOfPackages(awardOfPackages);
            return releasePkgResult;
        }
    }

    @Override
    @Transactional
    public AwardOfPackage lotteryAwardPackage(long uid, long packageId) throws Exception {
        log.info(AwardConstants.INFO_LOG + "???????????? uid:{} packageId:{}", uid, packageId);
        //?????? packageId ???????????????
        CommonAwardPackage awardPackage = awardUtilMapper.getAwardPackageById(packageId);
        if (awardPackage == null) {
            log.warn(AwardConstants.WARN_LOG + "?????????????????????????????? uid:{} packageId:{}", uid, packageId);
            return null;
        }
        //????????????????????????
        if (!awardPackage.isNeedRate()) {
            log.warn(AwardConstants.WARN_LOG + "??????????????????????????? uid:{} packageId:{}", uid, packageId);
            return null;
        }

        if(!awardPackage.isEnable()){
            log.warn(AwardConstants.WARN_LOG + "????????????????????????????????? uid:{} packageId:{}", uid, packageId);
            return null;
        }
        //?????????????????????
        String seqId = genRandomSqeId(uid, packageId);
        //?????? packageId ????????????????????????
        List<AwardOfPackage> awardOfPackages = awardUtilMapper.getAwardList(packageId);

        //??????????????????
        try (HintManager hintManager = HintManager.getInstance()) {
            //????????????????????????
            hintManager.setMasterRouteOnly();

            //???????????????????????????
            awardUtilMapper.addNewReleaseRecord(uid, packageId, awardPackage.getName(), awardPackage.getIcon(), seqId,
                    AwardEnums.ReleaseStatus.INIT.val(), JSONObject.toJSONString(awardOfPackages));
            //???????????????
            if (!packageStocksCheck(awardPackage)) {
                log.warn(AwardConstants.WARN_LOG + "reducePackageStockWithCheck ?????????????????????????????? uid:{} packageId:{} awardPackage:{}", uid, packageId, awardPackage);
                //???????????????????????????????????????
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "??????????????????");
                return null;
            }
            if (CollectionUtils.isEmpty(awardOfPackages)) {
                log.warn(AwardConstants.WARN_LOG + "????????????????????????????????? uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "?????????????????????????????????");
                return null;
            }
            //??????????????????
            AwardOfPackage rollAward = rollAward(awardOfPackages);
            log.info(AwardConstants.INFO_LOG + "??????roll???????????? uid:{} packageId:{} rollAward???{}", uid, packageId, rollAward);
            if (rollAward == null) {
                log.warn(AwardConstants.WARN_LOG + "??????????????????????????????????????? uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "????????????????????????");
                return null;
            }
            //????????????????????????
            awardUtilMapper.addNewReleaseRecordDetail(uid, seqId, rollAward.getAwardId(), rollAward.getName(), rollAward.getIcon(),
                    rollAward.getReleaseType(), rollAward.getReleaseId(), rollAward.getAwardCount(),
                    rollAward.getValidDays(), rollAward.getPrice(),
                    rollAward.getExtend(), AwardEnums.ReleaseStatus.INIT.val(), rollAward.getRemark(), rollAward.getId());
            //????????????
            boolean awardReleaseSuccess = releaseAward(uid, seqId, rollAward);
            if (!awardReleaseSuccess) {
                //???????????????????????????????????????
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "???????????????:awardOfPackageId:[" + rollAward.getId() + "]????????????");
            } else {
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "??????????????????");
            }
            return awardReleaseSuccess ? rollAward : null;
        }
    }

    @Override
    public ReleasePkgResult releaseAwardPackage(long uid, long packageId) throws Exception {
        log.info(AwardConstants.INFO_LOG + "???????????? uid:{} packageId:{}", uid, packageId);
        ReleasePkgResult releasePkgResult = new ReleasePkgResult();
        releasePkgResult.setResult(false);
        //?????? packageId ???????????????
        CommonAwardPackage awardPackage = awardUtilMapper.getAwardPackageById(packageId);
        if (awardPackage == null) {
            log.warn(AwardConstants.WARN_LOG + "?????????????????????????????? uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(!awardPackage.isEnable()){
            log.warn(AwardConstants.WARN_LOG + "????????????????????????????????? uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        //?????????????????????
        String seqId = genRandomSqeId(uid, packageId);
        //?????? packageId ????????????????????????
        List<AwardOfPackage> awardOfPackages = awardUtilMapper.getAwardList(packageId);

        //??????????????????
        try (HintManager hintManager = HintManager.getInstance()) {
            //????????????????????????
            hintManager.setMasterRouteOnly();

            //???????????????????????????
            awardUtilMapper.addNewReleaseRecord(uid, packageId, awardPackage.getName(), awardPackage.getIcon(), seqId,
                    AwardEnums.ReleaseStatus.INIT.val(), JSONObject.toJSONString(awardOfPackages));
            if (CollectionUtils.isEmpty(awardOfPackages)) {
                log.warn(AwardConstants.WARN_LOG + "????????????????????????????????? uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "?????????????????????????????????");
                releasePkgResult.setResult(true);
                return releasePkgResult;
            }
            //???????????????
            if (!packageStocksCheck(awardPackage)) {
                log.warn(AwardConstants.WARN_LOG + "reducePackageStockWithCheck ?????????????????????????????? uid:{} packageId:{} awardPackage:{}", uid, packageId, awardPackage);
                //???????????????????????????????????????
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "??????????????????");
                releasePkgResult.setResult(false);
                return releasePkgResult;
            }
            //???????????????????????????
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                awardUtilMapper.addNewReleaseRecordDetail(uid, seqId, awardOfPackage.getAwardId(), awardOfPackage.getName(), awardOfPackage.getIcon(),
                        awardOfPackage.getReleaseType(), awardOfPackage.getReleaseId(), awardOfPackage.getAwardCount(),
                        awardOfPackage.getValidDays(), awardOfPackage.getPrice(),
                        awardOfPackage.getExtend(), AwardEnums.ReleaseStatus.INIT.val(), awardOfPackage.getRemark()==null?"????????????":awardOfPackage.getRemark(), awardOfPackage.getId());
            }
            //????????????
            boolean packageReleaseSuccess = true;
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                boolean awardReleaseSuccess = releaseAward(uid, seqId, awardOfPackage);
                if (!awardReleaseSuccess) {
                    packageReleaseSuccess = false;
                    //???????????????????????????????????????
                    awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "???????????????:awardOfPackageId:[" + awardOfPackage.getId() + "]????????????");
                }
            }
            if (packageReleaseSuccess) {
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "??????????????????");
                releasePkgResult.setResult(true);
            }
            releasePkgResult.setAwardOfPackages(awardOfPackages);
            return releasePkgResult;
        }
    }

    @Override
    public void creatNextMonthTable() {
        try {
            int nextMonthExist = awardUtilMapper.existNextMonthRecordTable();
            if (nextMonthExist < 1) {
                awardUtilMapper.createNextMonthRecordTable();
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        try{
            int nextMonthExist = awardUtilMapper.existNextMonthDetailTable();
            if (nextMonthExist < 1) {
                awardUtilMapper.createNextMonthDetailTable();
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    /**
     * ??????????????????
     *
     * @param uid
     * @param seqId
     * @param awardOfPackage
     * @return
     */
    private boolean releaseAward(long uid, String seqId, AwardOfPackage awardOfPackage) {
        log.info(AwardConstants.INFO_LOG + "?????????????????? uid:{} seqId:{} awardOfPackage???{}", uid, seqId, awardOfPackage);
        try {
            //???????????????
            if (awardOfPackage.getTotalCount() > 0) {
                int rows = awardUtilMapper.reduceAwardStockWithCheck(awardOfPackage.getId());
                if (rows <= 0) {
                    log.warn(AwardConstants.WARN_LOG + "reduceAwardStockWithCheck ?????????????????????????????? uid:{} awardOfPackage:{}", uid, awardOfPackage);
                    //???????????????????????????????????????
                    awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "??????????????????");
                    return false;
                }
            } else {
                awardUtilMapper.reduceAwardStockWithoutCheck(awardOfPackage.getId());
            }
            //??????????????????????????????
            ReleaseResult result = releaseProcessorProxy.release(uid, seqId, awardOfPackage);
            if (!result.isSuccess()) {
                awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.FAIL.val(), result.getMsg());
            } else {
                awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), result.getMsg());
                return true;
            }
        } catch (Exception e) {
            log.error(AwardConstants.ERR_LOG + "??????????????????????????? uid:{} seqId:{} awardOfPackage:{}", uid, seqId, awardOfPackage, e);
            awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.FAIL.val(), "????????????");
        }
        return false;
    }

    /**
     * ????????????????????????id_uid_UUID
     *
     * @param uid
     * @param packageId
     * @return
     */
    private String genRandomSqeId(long uid, long packageId) {
        return packageId + "_" + uid + "_" + UUID.randomUUID();
    }

    /**
     * ??????????????????
     *
     * @param awardPackage
     * @return
     */
    private boolean packageStocksCheck(CommonAwardPackage awardPackage) {
        if (awardPackage.getTotalCount() > 0) {
            int rows = awardUtilMapper.reducePackageStockWithCheck(awardPackage.getId());
            if (rows <= 0) {
                return false;
            }
        } else {
            awardUtilMapper.reducePackageStockWithoutCheck(awardPackage.getId());
        }
        return true;
    }

    /**
     * ??????????????????????????????
     *
     * @param awardList
     * @return
     */
    private AwardOfPackage rollAward(List<AwardOfPackage> awardList) {
        if (CollectionUtils.isEmpty(awardList)) {
            return null;
        }

        int totalRate = 0;
        for (AwardOfPackage award : awardList) {
            if (award.getRate() != null) {
                int rate = (int) (award.getRate() * 1000);
                totalRate += rate;
            }
        }

        if (totalRate != 100000) {
            log.warn(AwardConstants.WARN_LOG + "?????????????????????100% awardList:{}");
        }

        Integer rand = new Random().nextInt(totalRate) + 1;

        for (AwardOfPackage award : awardList) {
            rand -= (int) (award.getRate() * 1000);
            if (rand <= 0) {
                return award;
            }
        }
        return null;
    }


    /**
     * ??????????????????????????????????????????????????????????????????
     * @param awardOfPackages
     * @param purchaseNum
     * @return
     */
    private List<AwardOfPackage> translateAwards(List<AwardOfPackage> awardOfPackages ,long purchaseNum){
        if(CollectionUtils.isEmpty(awardOfPackages)){
            return null;
        }
        //????????????
        awardOfPackages.sort(Comparator.comparing(AwardOfPackage::getPrice).reversed());
        long less = purchaseNum;
        int price = 0;
        long awardNum = 0;
        AwardOfPackage newAward = null;
        int size = awardOfPackages.size();
        List<AwardOfPackage> newAwards = Lists.newArrayList();
        for(int i=0;i<size;i++){
            newAward = awardOfPackages.get(i);
            price = newAward.getPrice();
            awardNum = less/price;
            less = less%price;
            if(awardNum>0){
                newAward.setAwardCount(Integer.valueOf(String.valueOf(awardNum)));
                newAwards.add(newAward);
            }
        }
        return newAwards;
    }
}
