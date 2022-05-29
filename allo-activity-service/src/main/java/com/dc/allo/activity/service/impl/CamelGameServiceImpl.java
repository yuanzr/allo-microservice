package com.dc.allo.activity.service.impl;

import com.dc.allo.activity.domain.vo.camel.BetActInfoResp;
import com.dc.allo.activity.domain.vo.camel.BetGameRoundInfoResp;
import com.dc.allo.activity.service.CamelGameService;
import com.dc.allo.common.component.i18n.IMessageSource;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.api.rank.DcBetService;
import com.dc.allo.rpc.domain.bet.*;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.proto.AlloResp;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/6/15.
 */
@Service
public class CamelGameServiceImpl implements CamelGameService {

    @Reference
    DcBetService dcBetService;

    @Resource(name = "messageNacosSource")
    protected IMessageSource messageSource;

    @Override
    public AlloResp<BetActInfoResp> getActInfo(long actId) {
        BetActInfoResp actInfoResp = null;
        AlloResp<BetActInfo> resp = dcBetService.getActInfo(actId);
        if(resp !=null && resp.getCode() == BusiStatus.SUCCESS.value()){
            actInfoResp = new BetActInfoResp();
            i18n(resp.getData());
            actInfoResp.setActInfo(resp.getData());
        }
        return AlloResp.success(actInfoResp);
    }

    @Override
    public BetGameRoundInfo getCamelGameRound(long actId, long uid) {
        BetGameRoundInfo gameRoundInfo = null;
        AlloResp<BetGameRoundInfo> resp = dcBetService.getCurBetGameRound(actId, uid);
        if(resp !=null && resp.getCode() == BusiStatus.SUCCESS.value()){
            gameRoundInfo = resp.getData();
            i18n(gameRoundInfo);
        }
        return gameRoundInfo;
    }

    @Override
    public AlloResp<BetGameRoundInfoResp> getBetGameRound(long gameId, long uid) {
        AlloResp<BetGameRoundInfo> gameRoundInfo = dcBetService.getBetGameRound(gameId,uid);
        BetGameRoundInfoResp resp = new BetGameRoundInfoResp();
        if(gameRoundInfo !=null && gameRoundInfo.getCode() == BusiStatus.SUCCESS.value()) {
            i18n(gameRoundInfo.getData());
            resp.setGameRoundInfo(gameRoundInfo.getData());
        }
        return AlloResp.success(resp);
    }

    @Override
    public AlloResp<Long> bet(long actId, long gameId, long uid, long spiritId, int amount) {
        return dcBetService.bet(actId, gameId, uid, spiritId, amount);
    }

    @Override
    public AlloResp<Page<BetGameRoundRecord>> pageGameRound(long actId, long id, int pageSize) {
        return dcBetService.pageBetGameRound(actId, id, pageSize);
    }

    @Override
    public AlloResp<Page<BetResultInfo>> pageResultInfo(long actId, long id, long uid, String suffix, int pageSize) {
        return dcBetService.pageBetResult(actId,uid,suffix,id,pageSize);
    }

    private void i18n(BetActInfo actInfo){
        if(actInfo == null){
            return;
        }
        List<BetSpiritInfo> spirits = actInfo.getSpirits();
        if(CollectionUtils.isNotEmpty(spirits)){
            for(BetSpiritInfo spirit:spirits){
                spirit.setSpiritName(messageSource.getMessage(spirit.getSpiritName()));
                spirit.setRemark(messageSource.getMessage(spirit.getRemark()));
            }
        }
        if(StringUtils.isNotBlank(actInfo.getRemark())){
            actInfo.setRemark(messageSource.getMessage(actInfo.getRemark()));
        }
    }

    private void i18n(BetGameRoundInfo gameRoundInfo){
        if(gameRoundInfo == null){
            return;
        }
        List<BetGameSpiritInfo> spirits = gameRoundInfo.getSpirits();
        if(CollectionUtils.isNotEmpty(spirits)){
            for(BetGameSpiritInfo spirit:spirits){
                spirit.setSpiritName(messageSource.getMessage(spirit.getSpiritName()));
            }
        }
        List<BetResultInfo> results = gameRoundInfo.getResultInfos();
        if(CollectionUtils.isNotEmpty(results)){
            for(BetResultInfo result:results){
                List<BetUserAwardInfo> userAwards = result.getUserAwards();
                if(CollectionUtils.isEmpty(userAwards)){
                    continue;
                }
                for(BetUserAwardInfo userAward:userAwards){
                    userAward.setName(messageSource.getMessage(userAward.getName()));
                }
                result.setAwards(JsonUtils.toJson(userAwards));
            }
        }
    }
}
