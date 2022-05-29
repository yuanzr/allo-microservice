package com.dc.allo.activity;

import com.dc.allo.activity.domain.vo.rank.DelRankCacheReq;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.rpc.domain.rank.RankQueryReq;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by zhangzhenjun on 2020/6/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class RankTest {


    @Test
    public void testQueryRank() throws IOException {
        String url = "http://gatewaybeta.allolike.com/allo-activity-service/rank/h5/queryRank";
//        String url = "http://localhost:30005/rank/h5/queryRank";
        RankQueryReq r = new RankQueryReq();
        r.setAppKey("allo");
        r.setRankKey("allo-gift-day");
        r.setPage(1);
        r.setRows(5);
        r.setTimeOffset(0);
        r.setWithOne(true);
        r.setOneId(61004536 + "");
        r.setWithRelation(false);
        r.setRelationPage(1);
        r.setRelationRows(5);
        r.setNeedBizInfo(true);
        String result = HttpUtils.doPost(url, r);
        log.info(result);
    }

    @Test
    public void testAutoReward() throws IOException {
        String url = "http://gatewaybeta.allolike.com/allo-activity-service/rank/h5/triggerRankAutoAward";
//        String url = "http://localhost:30005/rank/h5/triggerRankAutoAward";
        String result = HttpUtils.doGet(url);
        log.info(result);
    }

    @Test
    public void testGetActInfo() throws IOException {
        String url = "http://gatewaybeta.allolike.com/allo-activity-service/rank/h5/getActInfo?actId=1";
//        String url = "http://localhost:30005/rank/h5/getActInfo?actId=1";
        String result = HttpUtils.doGet(url);
        log.info(result);

    }

    @Test
    public void testDelRankCache() throws IOException {
//        String url = "http://gateway.allolike.com/allo-activity-service/rank/h5/delRankCache";
        String url = "http://localhost:30005/rank/h5/delRankCache";
        DelRankCacheReq req = new DelRankCacheReq();
        req.setKey("qwertyuiop[]");
        req.setRankRedisKey("act_center_ranks:ds[1]:allo-bet-camel-total");
        String result = HttpUtils.doPost(url,req);
        log.info(result);
    }

}
