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
        //TODO 待实现,金币购买？

        return false;
    }

    @Override
    public ReleasePkgResult purchaseAwardPackage(long uid, long packageId, int purchaseType, long purchaseNum) {
        log.info(AwardConstants.INFO_LOG + "外购礼包 uid:{} packageId:{} purchaseType:{} purchaseNum:{} ", uid, packageId,purchaseType,purchaseNum);
        ReleasePkgResult releasePkgResult = new ReleasePkgResult();
        releasePkgResult.setResult(false);
        //根据 packageId 查出礼包类
        CommonAwardPackage awardPackage = awardUtilMapper.getAwardPackageById(packageId);
        if (awardPackage == null) {
            log.warn(AwardConstants.WARN_LOG + "礼包配置为空不予发放 uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(!awardPackage.isEnable()){
            log.warn(AwardConstants.WARN_LOG + "礼包配置未上架不予发放 uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(!awardPackage.isNeedPurchase()){
            log.warn(AwardConstants.WARN_LOG + "礼包配置无需购买，不予发放 uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(awardPackage.getPurchaseType()!=purchaseType){
            log.warn(AwardConstants.WARN_LOG + "礼包配置购买货币与参数不符，不予发放 uid:{} packageId:{} pucharseType:{}", uid, packageId,purchaseType);
            return releasePkgResult;
        }
        //生成唯一序列号
        String seqId = genRandomSqeId(uid, packageId);
        //根据 packageId 查出礼包内的礼物（配置信息，需要换算）
        List<AwardOfPackage> awardOfPackages = awardUtilMapper.getAwardList(packageId);

        //根据传入的外购货币金额换算礼包内礼物实际发放数量
        awardOfPackages = translateAwards(awardOfPackages,purchaseNum);

        //指定主库操作
        try (HintManager hintManager = HintManager.getInstance()) {
            //指定只从主库读写
            hintManager.setMasterRouteOnly();

            //初始化礼包发放记录
            awardUtilMapper.addNewReleaseRecord(uid, packageId, awardPackage.getName(), awardPackage.getIcon(), seqId,
                    AwardEnums.ReleaseStatus.INIT.val(), JSONObject.toJSONString(awardOfPackages));
            if (CollectionUtils.isEmpty(awardOfPackages)) {
                log.warn(AwardConstants.WARN_LOG + "礼包内没有启用中的礼物 uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "礼包内没有启用中的礼物");
                releasePkgResult.setResult(true);
                return releasePkgResult;
            }
            //减礼包库存
            if (!packageStocksCheck(awardPackage)) {
                log.warn(AwardConstants.WARN_LOG + "reducePackageStockWithCheck 礼包库存不足不予发放 uid:{} packageId:{} awardPackage:{}", uid, packageId, awardPackage);
                //更新礼包发放记录为发放失败
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "礼包库存不足");
                releasePkgResult.setResult(false);
                return releasePkgResult;
            }
            //初始化礼物发放记录
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                awardUtilMapper.addNewReleaseRecordDetail(uid, seqId, awardOfPackage.getAwardId(), awardOfPackage.getName(), awardOfPackage.getIcon(),
                        awardOfPackage.getReleaseType(), awardOfPackage.getReleaseId(), awardOfPackage.getAwardCount(),
                        awardOfPackage.getValidDays(), awardOfPackage.getPrice(),
                        awardOfPackage.getExtend(), AwardEnums.ReleaseStatus.INIT.val(), awardOfPackage.getRemark()==null?"礼包礼物":awardOfPackage.getRemark(), awardOfPackage.getId());
            }
            //遍历礼物
            boolean packageReleaseSuccess = true;
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                boolean awardReleaseSuccess = releaseAward(uid, seqId, awardOfPackage);
                if (!awardReleaseSuccess) {
                    packageReleaseSuccess = false;
                    //更新礼包发放记录为发放失败
                    awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "礼包内礼物:awardOfPackageId:[" + awardOfPackage.getId() + "]发放失败");
                }
            }
            if (packageReleaseSuccess) {
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "礼包发放成功");
                releasePkgResult.setResult(true);
            }
            releasePkgResult.setAwardOfPackages(awardOfPackages);
            return releasePkgResult;
        }
    }

    @Override
    @Transactional
    public AwardOfPackage lotteryAwardPackage(long uid, long packageId) throws Exception {
        log.info(AwardConstants.INFO_LOG + "礼包抽奖 uid:{} packageId:{}", uid, packageId);
        //根据 packageId 查出礼包类
        CommonAwardPackage awardPackage = awardUtilMapper.getAwardPackageById(packageId);
        if (awardPackage == null) {
            log.warn(AwardConstants.WARN_LOG + "礼包配置为空不予发放 uid:{} packageId:{}", uid, packageId);
            return null;
        }
        //是否为抽奖型礼包
        if (!awardPackage.isNeedRate()) {
            log.warn(AwardConstants.WARN_LOG + "礼包配置不支持抽奖 uid:{} packageId:{}", uid, packageId);
            return null;
        }

        if(!awardPackage.isEnable()){
            log.warn(AwardConstants.WARN_LOG + "礼包配置未上架不予发放 uid:{} packageId:{}", uid, packageId);
            return null;
        }
        //生成唯一序列号
        String seqId = genRandomSqeId(uid, packageId);
        //根据 packageId 查出礼包内的礼物
        List<AwardOfPackage> awardOfPackages = awardUtilMapper.getAwardList(packageId);

        //指定主库操作
        try (HintManager hintManager = HintManager.getInstance()) {
            //指定只从主库读写
            hintManager.setMasterRouteOnly();

            //初始化礼包发放记录
            awardUtilMapper.addNewReleaseRecord(uid, packageId, awardPackage.getName(), awardPackage.getIcon(), seqId,
                    AwardEnums.ReleaseStatus.INIT.val(), JSONObject.toJSONString(awardOfPackages));
            //减礼包库存
            if (!packageStocksCheck(awardPackage)) {
                log.warn(AwardConstants.WARN_LOG + "reducePackageStockWithCheck 礼包库存不足不予发放 uid:{} packageId:{} awardPackage:{}", uid, packageId, awardPackage);
                //更新礼包发放记录为发放失败
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "礼包库存不足");
                return null;
            }
            if (CollectionUtils.isEmpty(awardOfPackages)) {
                log.warn(AwardConstants.WARN_LOG + "礼包内没有启用中的礼物 uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "礼包内没有启用中的礼物");
                return null;
            }
            //抽取一个礼物
            AwardOfPackage rollAward = rollAward(awardOfPackages);
            log.info(AwardConstants.INFO_LOG + "礼包roll奖励结果 uid:{} packageId:{} rollAward：{}", uid, packageId, rollAward);
            if (rollAward == null) {
                log.warn(AwardConstants.WARN_LOG + "礼物概率配置有误，不予发放 uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "礼物概率配置有误");
                return null;
            }
            //初始礼物发放记录
            awardUtilMapper.addNewReleaseRecordDetail(uid, seqId, rollAward.getAwardId(), rollAward.getName(), rollAward.getIcon(),
                    rollAward.getReleaseType(), rollAward.getReleaseId(), rollAward.getAwardCount(),
                    rollAward.getValidDays(), rollAward.getPrice(),
                    rollAward.getExtend(), AwardEnums.ReleaseStatus.INIT.val(), rollAward.getRemark(), rollAward.getId());
            //发放礼物
            boolean awardReleaseSuccess = releaseAward(uid, seqId, rollAward);
            if (!awardReleaseSuccess) {
                //更新礼包发放记录为发放失败
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "礼包内礼物:awardOfPackageId:[" + rollAward.getId() + "]发放失败");
            } else {
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "礼包发放成功");
            }
            return awardReleaseSuccess ? rollAward : null;
        }
    }

    @Override
    public ReleasePkgResult releaseAwardPackage(long uid, long packageId) throws Exception {
        log.info(AwardConstants.INFO_LOG + "发放礼包 uid:{} packageId:{}", uid, packageId);
        ReleasePkgResult releasePkgResult = new ReleasePkgResult();
        releasePkgResult.setResult(false);
        //根据 packageId 查出礼包类
        CommonAwardPackage awardPackage = awardUtilMapper.getAwardPackageById(packageId);
        if (awardPackage == null) {
            log.warn(AwardConstants.WARN_LOG + "礼包配置为空不予发放 uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        if(!awardPackage.isEnable()){
            log.warn(AwardConstants.WARN_LOG + "礼包配置未上架不予发放 uid:{} packageId:{}", uid, packageId);
            return releasePkgResult;
        }
        //生成唯一序列号
        String seqId = genRandomSqeId(uid, packageId);
        //根据 packageId 查出礼包内的礼物
        List<AwardOfPackage> awardOfPackages = awardUtilMapper.getAwardList(packageId);

        //指定主库操作
        try (HintManager hintManager = HintManager.getInstance()) {
            //指定只从主库读写
            hintManager.setMasterRouteOnly();

            //初始化礼包发放记录
            awardUtilMapper.addNewReleaseRecord(uid, packageId, awardPackage.getName(), awardPackage.getIcon(), seqId,
                    AwardEnums.ReleaseStatus.INIT.val(), JSONObject.toJSONString(awardOfPackages));
            if (CollectionUtils.isEmpty(awardOfPackages)) {
                log.warn(AwardConstants.WARN_LOG + "礼包内没有启用中的礼物 uid:{} packageId:{} awardOfPackages:{}", uid, packageId, awardOfPackages);
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "礼包内没有启用中的礼物");
                releasePkgResult.setResult(true);
                return releasePkgResult;
            }
            //减礼包库存
            if (!packageStocksCheck(awardPackage)) {
                log.warn(AwardConstants.WARN_LOG + "reducePackageStockWithCheck 礼包库存不足不予发放 uid:{} packageId:{} awardPackage:{}", uid, packageId, awardPackage);
                //更新礼包发放记录为发放失败
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "礼包库存不足");
                releasePkgResult.setResult(false);
                return releasePkgResult;
            }
            //初始化礼物发放记录
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                awardUtilMapper.addNewReleaseRecordDetail(uid, seqId, awardOfPackage.getAwardId(), awardOfPackage.getName(), awardOfPackage.getIcon(),
                        awardOfPackage.getReleaseType(), awardOfPackage.getReleaseId(), awardOfPackage.getAwardCount(),
                        awardOfPackage.getValidDays(), awardOfPackage.getPrice(),
                        awardOfPackage.getExtend(), AwardEnums.ReleaseStatus.INIT.val(), awardOfPackage.getRemark()==null?"礼包礼物":awardOfPackage.getRemark(), awardOfPackage.getId());
            }
            //遍历礼物
            boolean packageReleaseSuccess = true;
            for (AwardOfPackage awardOfPackage : awardOfPackages) {
                boolean awardReleaseSuccess = releaseAward(uid, seqId, awardOfPackage);
                if (!awardReleaseSuccess) {
                    packageReleaseSuccess = false;
                    //更新礼包发放记录为发放失败
                    awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.FAIL.val(), "礼包内礼物:awardOfPackageId:[" + awardOfPackage.getId() + "]发放失败");
                }
            }
            if (packageReleaseSuccess) {
                awardUtilMapper.updateReleaseRecordStatus(packageId, seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), "礼包发放成功");
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
     * 处理奖励发放
     *
     * @param uid
     * @param seqId
     * @param awardOfPackage
     * @return
     */
    private boolean releaseAward(long uid, String seqId, AwardOfPackage awardOfPackage) {
        log.info(AwardConstants.INFO_LOG + "处理礼物发放 uid:{} seqId:{} awardOfPackage：{}", uid, seqId, awardOfPackage);
        try {
            //减礼物库存
            if (awardOfPackage.getTotalCount() > 0) {
                int rows = awardUtilMapper.reduceAwardStockWithCheck(awardOfPackage.getId());
                if (rows <= 0) {
                    log.warn(AwardConstants.WARN_LOG + "reduceAwardStockWithCheck 礼物库存不足不予发放 uid:{} awardOfPackage:{}", uid, awardOfPackage);
                    //更新礼物发放记录为发放失败
                    awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.STOCK_NOT_ENOUGH.val(), "礼物库存不足");
                    return false;
                }
            } else {
                awardUtilMapper.reduceAwardStockWithoutCheck(awardOfPackage.getId());
            }
            //根据礼物类型进行发放
            ReleaseResult result = releaseProcessorProxy.release(uid, seqId, awardOfPackage);
            if (!result.isSuccess()) {
                awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.FAIL.val(), result.getMsg());
            } else {
                awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.SUCCESS.val(), result.getMsg());
                return true;
            }
        } catch (Exception e) {
            log.error(AwardConstants.ERR_LOG + "礼包内礼物发放失败 uid:{} seqId:{} awardOfPackage:{}", uid, seqId, awardOfPackage, e);
            awardUtilMapper.updateReleaseRecordDetailStatus(awardOfPackage.getId(), seqId, AwardEnums.ReleaseStatus.FAIL.val(), "系统异常");
        }
        return false;
    }

    /**
     * 唯一序列号：礼包id_uid_UUID
     *
     * @param uid
     * @param packageId
     * @return
     */
    private String genRandomSqeId(long uid, long packageId) {
        return packageId + "_" + uid + "_" + UUID.randomUUID();
    }

    /**
     * 扣减礼包库存
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
     * 按照概率随机抽取奖励
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
            log.warn(AwardConstants.WARN_LOG + "礼包概率不满足100% awardList:{}");
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
     * 根据外购金额计算礼包实际需要发放的礼物的数量
     * @param awardOfPackages
     * @param purchaseNum
     * @return
     */
    private List<AwardOfPackage> translateAwards(List<AwardOfPackage> awardOfPackages ,long purchaseNum){
        if(CollectionUtils.isEmpty(awardOfPackages)){
            return null;
        }
        //降序排序
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
