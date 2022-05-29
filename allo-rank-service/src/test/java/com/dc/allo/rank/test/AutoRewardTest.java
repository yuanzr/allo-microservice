package com.dc.allo.rank.test;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.Application;
import com.dc.allo.rank.constants.AutoAwardEnums;
import com.dc.allo.rank.domain.rank.config.RankAutoRewardConfig;
import com.dc.allo.rank.handler.RankDataStreamHandler;
import com.dc.allo.rank.service.rank.data.AutoRewardService;
import com.dc.allo.rank.service.rpc.impl.DcRankServiceImpl;
import com.dc.allo.rpc.domain.rank.RankDataRecord;
import com.dc.allo.rpc.domain.rank.RankQueryReq;
import com.dc.allo.rpc.domain.rank.RankQueryResp;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhangzhenjun on 2020/5/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class AutoRewardTest {

    @Autowired
    AutoRewardService autoRewardService;

    @Autowired
    RankDataStreamHandler rankDataStreamHandler;

    @Autowired
    DcRankServiceImpl rankService;

    @Test
    public void testAddAutoRewardConfig(){
        RankAutoRewardConfig config = new RankAutoRewardConfig();
        config.setAwardPackageId(2);
        config.setAwardType(AutoAwardEnums.AwardType.CONTINUE.val());//连续性礼包
        config.setBegin(1);
        config.setEnd(1);
        config.setLimitScore(100);
        config.setRankId(17);
        autoRewardService.addAutoRewardConfig(config);
        config.setAwardPackageId(3);
        config.setBegin(2);
        config.setEnd(3);
        autoRewardService.addAutoRewardConfig(config);
    }

    @Test
    public void testQueryAutoRewardConfigs() throws InterruptedException {
        for (int i = 0; i < 100; ++i) {
            System.out.println(autoRewardService.queryAwardConfigs(17));
        }
        Thread.sleep(65000);
    }

    @Test
    public void recordUserGetRank() {
        for (int i = 10; i > 0; --i) {
            RankDataRecord record = new RankDataRecord();
            record.setAppKey(Constant.App.ALLO);
            record.setDataSourceKey("gift-key");
            record.setDataSourceSecret("test");
            record.setTimestamp(System.currentTimeMillis());
            record.setScore(100.0*i);
            record.setSendId(20000+i+"");
            record.setRecvId(10000 + i+"");
            record.setRoomId("300001");
            rankDataStreamHandler.handleRankDtaStream(record);
        }
    }

    @Test
    public void queryRank() {
        RankQueryReq r = new RankQueryReq();
        r.setAppKey(Constant.App.ALLO);
        r.setRankKey("hour-rank");
        r.setDivideKey(null);
        r.setPage(1);
        r.setRows(5);
        r.setTimeOffset(2);
        r.setWithOne(true);
        r.setOneId("100001");
        r.setWithRelation(true);
        r.setRelationPage(1);
        r.setRelationRows(5);
        AlloResp<RankQueryResp> res = rankService.queryRank(r);
        log.info("@@@@@@@@@@@@@@@@@="+JsonUtils.toJson(res));
    }

    @Test
    public void testAutoReward() throws Exception {
        autoRewardService.autoRewardTask();
    }

}
