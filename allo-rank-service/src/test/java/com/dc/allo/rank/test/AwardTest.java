package com.dc.allo.rank.test;

import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.Application;
import com.dc.allo.common.constants.AwardEnums;
import com.dc.allo.rank.domain.award.CommonAward;
import com.dc.allo.rank.domain.award.CommonAwardOfPackage;
import com.dc.allo.rank.domain.award.CommonAwardPackage;
import com.dc.allo.rank.service.award.AwardService;
import com.dc.allo.rpc.domain.award.AwardOfPackage;
import com.dc.allo.rpc.domain.award.ReleasePkgResult;
import com.dc.allo.rpc.domain.page.AdminPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class AwardTest {

    @Autowired
    AwardService awardService;

    @Test
    public void testAddCommonAward(){
        awardService.addCommonAward("斋月神话","icon3",AwardEnums.ReleaseType.VIRTUALITEM.val(),"1",600,600,"");
        awardService.addCommonAward("斋月五功","icon4", AwardEnums.ReleaseType.AVATAR.val(),"100",500,1000,"");
    }

    @Test
    public void testUpdateCommonAward(){

    }

    @Test
    public void testPageCommonAward(){
        AdminPage<CommonAward> result = awardService.pageAward(0, 5);
        log.info(result.toString());
    }

    @Test
    public void testAddAwardPackage(){
        CommonAwardPackage awardPackage = new CommonAwardPackage();
        awardPackage.setName("斋月礼包2");
        awardPackage.setPrice(600);
        awardPackage.setIcon("icon4");
        awardPackage.setNeedRate(true);
        awardPackage.setNeedPurchase(false);
        awardPackage.setDayLimitCount(10);
        awardPackage.setTotalCount(-1);
        awardService.addCommonAwardPackage(awardPackage);
    }

    @Test
    public void testUpdateAwardPackage(){

    }

    @Test
    public void testPageAwardPackage(){
        AdminPage<CommonAwardPackage> result = awardService.pageAwardPackage(0, 5);
        log.info(result.toString());
    }

    @Test
    public void testAddAwardOfPackage(){
        CommonAwardOfPackage awardOfPackage = new CommonAwardOfPackage();
        awardOfPackage.setActId(1);
        awardOfPackage.setPackageId(2);
        awardOfPackage.setAwardId(3);
        awardOfPackage.setAwardCount(1);
        awardOfPackage.setEnable(true);
        awardOfPackage.setRate(50);
        awardOfPackage.setDisplayRate(50);
        awardOfPackage.setTotalCount(-1);
        awardOfPackage.setRemark("武功");
        awardService.addCommonAwardOfPackage(awardOfPackage);
        awardOfPackage.setAwardId(4);
        awardOfPackage.setRemark("神话");
        awardService.addCommonAwardOfPackage(awardOfPackage);
    }

    @Test
    public void testUpdateWardOfPackage(){

    }

    @Test
    public void testPageAwardOfPackage(){
        AdminPage<CommonAwardOfPackage> result = awardService.pageAwardOfPackage(0, 3);
        log.info(result.toString());
    }

    @Test
    public void testQueryCommonAwards(){
        System.out.println(JsonUtils.toJson(awardService.queryCommonAwards()));
    }

    @Test
    public void testLotteryAward(){
        try {
            AwardOfPackage result = awardService.lotteryAwardPackage(111l, 3);
            System.out.println(JsonUtils.toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void releaseAward(){
        try {
            ReleasePkgResult result = awardService.releaseAwardPackage(222l, 2);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
