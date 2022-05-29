package com.dc.allo.rank.test;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.Application;
import com.dc.allo.rank.domain.rank.Rank;
import com.dc.allo.rank.domain.rank.config.RankAutoRewardConfig;
import com.dc.allo.rank.domain.rank.config.RankConfig;
import com.dc.allo.rank.domain.rank.config.RankDatasourceConfig;
import com.dc.allo.rank.handler.RankDataStreamHandler;
import com.dc.allo.rank.mapper.rank.AppMapper;
import com.dc.allo.rank.service.rank.RankManager;
import com.dc.allo.rank.service.rank.data.BizIdConfigService;
import com.dc.allo.rank.service.rank.data.DataRecordService;
import com.dc.allo.rank.service.rank.data.DataSourceConfigService;
import com.dc.allo.rank.service.rank.data.RankConfigService;
import com.dc.allo.rank.service.rpc.impl.DcRankServiceImpl;
import com.dc.allo.rpc.api.utils.HttpUtils;
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

import java.io.IOException;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class RankTest {

    @Autowired
    RankConfigService rankConfigService;


    @Autowired
    DataSourceConfigService dataSourceConfigService;

    @Autowired
    DataRecordService dataRecordService;

    @Autowired
    AppMapper appMapper;

    @Autowired
    DcRankServiceImpl rankService;

    @Autowired
    RankDataStreamHandler rankDataStreamHandler;

    @Autowired
    RankManager rankManager;

    @Autowired
    BizIdConfigService bizIdConfigService;



    @Test
    public void testAddApp(){
        dataRecordService.addApp("allo1","allo1-test");
    }

    @Test
    public void testAddDataSource(){
        RankDatasourceConfig datasourceConfig = new RankDatasourceConfig();
        datasourceConfig.setAppId(2);
        datasourceConfig.setAppKey("allo1-test");
        datasourceConfig.setDataSourceKey("allo1-data-source");
        datasourceConfig.setName("allo1-data-source");
        datasourceConfig.setSecret("allo1-secret");
        datasourceConfig.setType(com.dc.allo.rank.constants.Constant.Rank.RankType.GIFT.val());

        dataSourceConfigService.addDataSourceConfig(datasourceConfig);
    }

    @Test
    public void testAddRankConfig(){
        RankConfig rankConfig = new RankConfig();
        rankConfig.setRankKey("allo-rank-key");
        rankConfig.setName("allo-rank1");
        rankConfig.setAppKey("allo1-test");
        rankConfig.setCalcType(com.dc.allo.rank.constants.Constant.Rank.RankCalcType.SCORE.val());
        rankConfig.setDataSourceId(4);
        rankConfig.setDivideByBizId(false);
        rankConfig.setDirection(com.dc.allo.rank.constants.Constant.Rank.RankDirection.GET.val());
        rankConfig.setGenRelation(true);
        rankConfig.setStartTime(new Date());
        rankConfig.setEndTime(TimeUtils.offsetDay(new Date(), 7));
        rankConfig.setExpireUnit(com.dc.allo.rank.constants.Constant.Rank.RankExpireTimeUnit.DAY.val());
        rankConfig.setExpireValue(15);
        rankConfig.setMemberType(com.dc.allo.rank.constants.Constant.Rank.RankMemberType.USER.val());
        rankConfig.setTimeType(com.dc.allo.rank.constants.Constant.Rank.RankTimeType.DAY.val());
        rankConfigService.addRankConfig(rankConfig);
    }




    @Test
    public void getRankConfig() throws Exception {
        for (int i = 0; i < 100; ++i) {
            System.out.println(rankConfigService.getRankConfig(Constant.App.ALLO, "test"));
        }
        Thread.sleep(65000);

    }

    @Test
    public void getRankConfigListByDataSourceId() throws Exception {
        for (int i = 0; i < 100; ++i) {
            System.out.println(rankConfigService.getRankConfigListByDataSourceId(1, new Date()));
        }
        Thread.sleep(65000);

    }

    @Test
    public void getRankConfigListByAppKey() throws Exception {
        for (int i = 0; i < 100; ++i) {
            System.out.println(rankConfigService.getRankConfigListByAppKey(Constant.App.ALLO));
        }
        Thread.sleep(65000);

    }


    @Test
    public void getDataSource() throws Exception {
        for (int i = 0; i < 100; ++i) {
            System.out.println(dataSourceConfigService.getDataSource("allo-gift", "gift-key"));
        }
        Thread.sleep(65000);
    }

    @Test
    public void queryRankApp() {
        System.out.println(appMapper.queryAllApp());
    }

    @Test
    public void createTable() {
        dataRecordService.createTable();
    }



    @Test
    public void queryRank() {
        RankQueryReq r = new RankQueryReq();
        r.setAppKey(Constant.App.ALLO);
        r.setRankKey("allo-gift-day");
        r.setPage(1);
        r.setRows(5);
        r.setTimeOffset(0);
        r.setWithOne(true);
        r.setOneId("1000");
        r.setWithRelation(true);
        r.setRelationPage(1);
        r.setRelationRows(5);
        AlloResp<RankQueryResp> res = rankService.queryRank(r);
        System.out.println(JsonUtils.toJson(res));
        r.setRankKey("allo-gift-day");
        r.setOneId("1000003");
        r.setDivideKey("did2");
        res = rankService.queryRank(r);
        System.out.println(JsonUtils.toJson(res));
    }

    private RankDataRecord buildRecord() {
        RankDataRecord record = new RankDataRecord();
        record.setAppKey(Constant.App.ALLO);
        record.setDataSourceKey("allo-gift-ds");
        record.setDataSourceSecret("allo-gift-secret");
        record.setTimestamp(System.currentTimeMillis());
        record.setScore(100.0);
        record.setSendId("1000");
        record.setRecvId("2000");
        record.setRoomId("1000001");
        record.setRoomType(1);
        record.setBizId("3");
        return record;
    }

    @Test
    public void recordRank() {
        rankDataStreamHandler.handleRankDtaStream(buildRecord());
    }

    @Test
    public void recordUserGetRank() {
        for (int i = 10; i > 0; --i) {
            RankDataRecord record = new RankDataRecord();
            record.setAppKey(Constant.App.ALLO);
            record.setDataSourceKey("allo-gift-ds");
            record.setDataSourceSecret("allo-gift-secret");
            record.setTimestamp(System.currentTimeMillis());
            record.setScore(100.0);
            record.setSendId("tuhao");
            record.setRecvId("user_" + i);
            record.setRoomId("1000001");
            record.setBizId(i+"");
            rankDataStreamHandler.handleRankDtaStream(record);
        }
    }

    @Test
    public void queryUserGetRank() {
        RankQueryReq r = new RankQueryReq();
        r.setAppKey(Constant.App.ALLO);
        r.setRankKey("hour-rank");
        r.setPage(1);
        r.setRows(5);
        r.setTimeOffset(1);
        r.setWithOne(true);
        r.setOneId("user_1");
        r.setWithRelation(true);
        r.setRelationPage(1);
        r.setRelationRows(5);
        AlloResp<RankQueryResp> res = rankService.queryRank(r);
        System.out.println(JsonUtils.toJson(res));
    }

    @Test
    public void recordUserSendRank() {
        for (int i = 10; i > 0; --i) {
            RankDataRecord record = new RankDataRecord();
            record.setAppKey(Constant.App.ALLO);
            record.setDataSourceKey("gift-key");
            record.setDataSourceSecret("test");
            record.setTimestamp(System.currentTimeMillis());
            record.setScore(100.0);
            record.setSendId("tuhao_" + i);
            record.setRecvId("user_" + i);
            record.setRoomId("1000001");
            rankDataStreamHandler.handleRankDtaStream(record);
            record.setRecvId("user_" + (11 - i));
            rankDataStreamHandler.handleRankDtaStream(record);
        }
    }

    @Test
    public void queryUserSendRank() {
        RankQueryReq r = new RankQueryReq();
        r.setAppKey(Constant.App.ALLO);
        r.setRankKey("test");
        r.setPage(1);
        r.setRows(10);
        r.setTimeOffset(0);
        r.setWithOne(true);
        r.setOneId("tuhao_10");
        r.setWithRelation(true);
        r.setRelationPage(1);
        r.setRelationRows(5);
        AlloResp<RankQueryResp> res = rankService.queryRank(r);
        System.out.println(JsonUtils.toJson(res));
    }

    @Test
    public void recordRoomSendRank() {
        for (int i = 10; i > 0; --i) {
            RankDataRecord record = new RankDataRecord();
            record.setAppKey(Constant.App.ALLO);
            record.setDataSourceKey("gift-key");
            record.setDataSourceSecret("test");
            record.setTimestamp(System.currentTimeMillis());
            record.setScore(100.0);
            record.setSendId("tuhao_" + i);
            record.setRecvId("user_" + i);
            record.setRoomId("100000" + i % 3);
            rankDataStreamHandler.handleRankDtaStream(record);
        }
    }

    @Test
    public void queryRoomSendRank() {
        RankQueryReq r = new RankQueryReq();
        r.setAppKey(Constant.App.ALLO);
        r.setRankKey("roomSendRank");
        r.setPage(1);
        r.setRows(10);
        r.setTimeOffset(0);
        r.setWithOne(true);
        r.setOneId("1000001");
        r.setWithRelation(true);
        r.setRelationPage(1);
        r.setRelationRows(5);
        AlloResp<RankQueryResp> res = rankService.queryRank(r);
        System.out.println(JsonUtils.toJson(res));
    }

    @Test
    public void testIpcheck() throws IOException {
        RankDatasourceConfig datasourceConfig = new RankDatasourceConfig();
        datasourceConfig.setAppId(2);
        datasourceConfig.setAppKey("allo2-test");
        datasourceConfig.setDataSourceKey("allo2-data-source");
        datasourceConfig.setName("allo2-data-source");
        datasourceConfig.setSecret("allo2-secret");
        datasourceConfig.setType(com.dc.allo.rank.constants.Constant.Rank.RankType.NORMAL.val());
        String url = "http://localhost:30000/allo-rank-service/rank/admin/datasource/add";
        String resp = HttpUtils.doPost(url, datasourceConfig);
        log.info(JsonUtils.toJson(resp));
    }

    @Test
    public void testUpdateRankConf(){
        RankConfig rankConfig = new RankConfig();
        rankConfig.setId(16L);
        rankConfig.setRankKey("allo-rank-key");
        rankConfig.setName("allo-rank12222333");
        rankConfig.setAppKey("allo1-test");
        rankConfig.setCalcType(com.dc.allo.rank.constants.Constant.Rank.RankCalcType.SCORE.val());
        rankConfig.setDataSourceId(4);
        rankConfig.setDivideByBizId(false);
        rankConfig.setDirection(com.dc.allo.rank.constants.Constant.Rank.RankDirection.GET.val());
        rankConfig.setGenRelation(true);
        rankConfig.setStartTime(new Date());
        rankConfig.setEndTime(TimeUtils.offsetDay(new Date(), 7));
        rankConfig.setExpireUnit(com.dc.allo.rank.constants.Constant.Rank.RankExpireTimeUnit.DAY.val());
        rankConfig.setExpireValue(15);
        rankConfig.setMemberType(com.dc.allo.rank.constants.Constant.Rank.RankMemberType.USER.val());
        rankConfig.setTimeType(com.dc.allo.rank.constants.Constant.Rank.RankTimeType.DAY.val());
        rankConfigService.upateRankConfig(rankConfig);
    }

    @Test
    public void testUpdateDataSource(){
        RankDatasourceConfig config = new RankDatasourceConfig();
        config.setAppId(1);
        config.setName("uypdateDs");
        config.setAppKey("updateAppkey");
        config.setDataSourceKey("updateDsKey");
        config.setSecret("updateSecret");
        config.setId(3);
        rankManager.updateDataSourceConfig(config);
    }

    @Test
    public void testUpdateAutoReward(){
        RankAutoRewardConfig config = new RankAutoRewardConfig();
        config.setId(1);
        config.setAwardPackageId(20);
        config.setAwardType(1);
        config.setBegin(1);
        config.setEnd(2);
        config.setLimitScore(1000);
        config.setRankId(10);
        rankManager.updateAutoRewardConfig(config);
    }

    @Test
    public void testAddBizIdConfig(){
        bizIdConfigService.addBizIdConfig(17, "1,3,5");
    }

    @Test
    public void testUpdateBizIdConfig(){
        bizIdConfigService.updateBizIdConfig(17, "2,4,6", 1);
    }

    @Test
    public void testQueryBizIds() throws IOException {
        String url = "http://gatewaybeta.allolike.com/allo-rank-service/rank/admin/bizid/get?rankId=17";

//        String url = "http://localhost:30000/allo-rank-service/rank/admin/bizid/get?rankId=17";
        String resp = HttpUtils.doGet(url);
        log.info(JsonUtils.toJson(resp));
//        System.out.println(bizIdConfigService.get(17));
    }
}
