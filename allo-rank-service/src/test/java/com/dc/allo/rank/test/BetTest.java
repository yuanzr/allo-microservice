package com.dc.allo.rank.test;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.MD5Utils;
import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rank.Application;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.bet.*;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rank.domain.bet.vo.BetHttpReq;
import com.dc.allo.rank.domain.bet.vo.BetHttpResp;
import com.dc.allo.rank.service.rpc.impl.DcRankServiceImpl;
import com.dc.allo.rpc.api.utils.HttpUtils;
import com.dc.allo.rpc.domain.bet.BetGameRoundInfo;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.bet.BetSpiritInfo;
import com.dc.allo.rank.service.bet.BetFacadeService;
import com.dc.allo.rpc.domain.page.AdminPage;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.domain.rank.RankQueryReq;
import com.dc.allo.rpc.domain.rank.RankQueryResp;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class BetTest {

    @Autowired
    BetFacadeService betFacadeService;

    @Autowired
    DcRankServiceImpl rankService;

    @Test
    public void testAddActConfig(){
        BetActConfig conf = new BetActConfig();
        Date stime = new Date();
        Date etime = TimeUtils.offsetDay(stime,15);
        conf.setGameDuration(90);
        conf.setAnimationDuration(20);
        conf.setShowDuration(10);
        conf.setBetModel(0);
        conf.setBankerModel(0);
        conf.setWarnScore(10000);
        conf.setStime(stime);
        conf.setEtime(etime);
        conf.setRemark("骆驼赛跑");
        betFacadeService.addActConf(conf);
    }

    @Test
    public void testGetActInfo(){
        BetActInfo info = betFacadeService.getActInfo(1);
        log.info(JsonUtils.toJson(info));
    }

    @Test
    public void testAddSpirit(){
        BetSpirit spirit = new BetSpirit();
        spirit.setName("骆驼1号");
        spirit.setUrl("1.jpg");
        spirit.setRemark("remark1");
        betFacadeService.addSpirit(spirit);
        spirit.setName("骆驼2号");
        spirit.setUrl("2.jpg");
        spirit.setRemark("remark2");
        betFacadeService.addSpirit(spirit);
        spirit.setName("骆驼3号");
        spirit.setUrl("3.jpg");
        spirit.setRemark("remark3");
        betFacadeService.addSpirit(spirit);
        spirit.setName("骆驼4号");
        spirit.setUrl("4.jpg");
        spirit.setRemark("remark4");
        betFacadeService.addSpirit(spirit);
    }

    @Test
    public void testAddAwardConfig(){
        BetAwardConfig awardConfig = new BetAwardConfig();
        awardConfig.setActId(1);
        awardConfig.setPackageId(2);
        awardConfig.setAwardTarget(BetConstants.WAWARD_TARGET_WIN);
        betFacadeService.addAwardConf(awardConfig);
    }

    @Test
    public void testAddSpiritConf(){
        BetSpiritConfig conf = new BetSpiritConfig();
        conf.setActId(1);
        conf.setSpiritId(1);
        conf.setRate(10);
        conf.setOdds(1);
        betFacadeService.addSpiritConf(conf);
        conf.setSpiritId(2);
        conf.setRate(20);
        conf.setOdds(2);
        betFacadeService.addSpiritConf(conf);
        conf.setSpiritId(3);
        conf.setRate(30);
        conf.setOdds(3);
        betFacadeService.addSpiritConf(conf);
        conf.setSpiritId(4);
        conf.setRate(40);
        conf.setOdds(4);
        betFacadeService.addSpiritConf(conf);
    }

    @Test
    public void testQuerySpiritConfs(){
        AdminPage<BetSpiritInfo> result = betFacadeService.querySpiritConfigs(1);
        log.info(JsonUtils.toJson(result));
    }

    @Test
    public void testGenGameRound(){
        long result = betFacadeService.genGameRound(1);
        log.info("gen gameRound result:{}",result);
    }

    @Test
    public void testGetGameRound(){
        BetGameRoundInfo result = betFacadeService.getCurBetGameRound(1, 61004336);
        log.info(JsonUtils.toJson(result));
    }

    @Test
    public void testGetGameRound2(){
        BetGameRoundInfo result = betFacadeService.getBetGameRound(50, 61004337);
        log.info(JsonUtils.toJson(result));
    }

    @Test
    public void testCheck2CreateTable(){
        betFacadeService.check2CreateBetDetailTable();
        betFacadeService.check2CreateBetResultTable();
    }

    @Test
    public void testBet(){
        betFacadeService.genGameRound(1);
        BetGameRoundInfo result = betFacadeService.getCurBetGameRound(1, 61004336);
        log.info(JsonUtils.toJson(result));
        if(result!=null){
            long actId = result.getActId();
            long gameId = result.getGameId();
            betFacadeService.bet(actId,gameId,61004336,result.getSpirits().get(0).getSpiritId(),300);
            betFacadeService.bet(actId,gameId,61004336,result.getSpirits().get(0).getSpiritId(),100);
//            betFacadeService.bet(actId,gameId,61004337,result.getSpirits().get(1).getSpiritId(),200);
//            betFacadeService.bet(actId,gameId,61004338,result.getSpirits().get(1).getSpiritId(),500);
//            betFacadeService.bet(actId,gameId,61004339,result.getSpirits().get(2).getSpiritId(),200);
//            betFacadeService.bet(actId,gameId,61004340,result.getSpirits().get(3).getSpiritId(),400);
//            betFacadeService.bet(actId,gameId,61004341,result.getSpirits().get(3).getSpiritId(),600);
        }
    }

    @Test
    public void testPageResult(){
        Page<BetResultInfo> result = betFacadeService.pageBetResult(1, 61004336, "2020_06", 0, 2);
        log.info(JsonUtils.toJson(result));
    }

    @Test
    public void testQueryRank(){
        RankQueryReq r = new RankQueryReq();
        r.setAppKey(BetConstants.GAME_RANK_CONFIG.RANK_APP_KEY);
        r.setRankKey(BetConstants.GAME_RANK_CONFIG.RANK_BET_DAY_KEY);
        r.setPage(1);
        r.setRows(10);
        r.setTimeOffset(0);
        r.setWithOne(true);
        r.setOneId(61004336 + "");
        r.setWithRelation(false);
        r.setRelationPage(1);
        r.setRelationRows(5);
        r.setNeedBizInfo(true);
        //日榜
        AlloResp<RankQueryResp> res = rankService.queryRank(r);
        log.info(JsonUtils.toJson(res));
        //总榜
        r.setRankKey(BetConstants.GAME_RANK_CONFIG.RANK_BET_TOTAL_KEY);
        res = rankService.queryRank(r);
        log.info(JsonUtils.toJson(res));
    }

    @Test
    public void testBetStatistic(){
        betFacadeService.betStatistic();
    }

    @Test
    public void testPageBetStatistic() throws ParseException {
        AdminPage<BetStatistic> result = betFacadeService.pageBetStatistic(1, "2020-06-02", 0, 5);
        log.info(JsonUtils.toJson(result));
    }

    @Test
    public void testGetGameRoundInfo(){
        log.info(JsonUtils.toJson(betFacadeService.getGameRoundInfo(77)));
    }

    @Test
    public void testPageGameRound(){
        log.info(JsonUtils.toJson(betFacadeService.pageGameRound(1, null, 0, 10)));
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(1000000);
        for(int i=0;i<1000000;i++){
            list.add(i);
        }
        long now = System.currentTimeMillis();
        list.parallelStream().forEach(integer -> {

        });
        long end = System.currentTimeMillis();
        System.out.println(end - now);
        now = System.currentTimeMillis();
        list.stream().forEach(integer -> {
            });
        end = System.currentTimeMillis();
        System.out.println(end - now);
        now = System.currentTimeMillis();
        for(int i=0;i<=1000000;i++){

        }
        end = System.currentTimeMillis();
        System.out.println(end - now);
    }

    @Test
    public void testMinusCoins() throws Exception {
//        String url = "http://apibeta.allolike.com/purse/updateUserPurse";
        String url = "http://192.168.100.197/purse/updateUserPurse";
        BetHttpReq req = new BetHttpReq();
        req.setUid(61004351);
        byte objType = 103;
        req.setObjType(objType);
        req.setGoldNum(new BigDecimal(-300274344.5));
        String txt =  "61004351" + objType + "-300274344.5" ;
        String sign = MD5Utils.md5(txt, "betAppKey");
        log.info("verify :{},sign:{}",MD5Utils.verify(txt, "betAppKey",sign),sign);
        req.setSign(sign);
        long goldNum = 0;
        String result = HttpUtils.doPost(url, req);
        if(StringUtils.isNotBlank(result)){
            BetHttpResp httpResp = JsonUtils.fromJson(result,BetHttpResp.class);
            if(httpResp!=null){
                log.info(JsonUtils.toJson(httpResp));
                if(httpResp.getCode()== BusiStatus.SUCCESS.value()&&httpResp.getData()!=null){
                    goldNum = httpResp.getData().getGoldNum().longValue();
                }else if(httpResp.getCode() == 2103){
                    log.info("not enough "+BetConstants.GAME_ROUND_ERR.PAY_NOT_ENOUGH);
                }else{
                    log.info("pay err "+BetConstants.GAME_ROUND_ERR.PAY_ERR);
                }
            }else{
                log.info("pay err "+BetConstants.GAME_ROUND_ERR.PAY_ERR);
            }
        }
        log.info("goldNum: "+goldNum);
    }

}
