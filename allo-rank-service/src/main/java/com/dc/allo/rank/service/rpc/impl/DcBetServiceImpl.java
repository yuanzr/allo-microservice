package com.dc.allo.rank.service.rpc.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.service.bet.BetFacadeService;
import com.dc.allo.rpc.api.rank.DcBetService;
import com.dc.allo.rpc.domain.bet.BetActInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundInfo;
import com.dc.allo.rpc.domain.bet.BetGameRoundRecord;
import com.dc.allo.rpc.domain.bet.BetResultInfo;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/27.
 */
@Service
@Slf4j
public class DcBetServiceImpl implements DcBetService {

    @Autowired
    BetFacadeService betFacadeService;

    @Override
    public AlloResp<BetActInfo> getActInfo(long actId) {
        if (actId <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            return AlloResp.success(betFacadeService.getActInfo(actId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }

    @Override
    public AlloResp<Boolean> check2CreatNextMonthTable() {
        try {
            betFacadeService.check2CreateBetResultTable();
            betFacadeService.check2CreateBetDetailTable();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", false);
        }
        return AlloResp.success(true);
    }

    @Override
    public AlloResp<Long> genGameRound(long actId) {
        if (actId <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            long result = betFacadeService.genGameRound(actId);
            return AlloResp.success(result);
        } catch (DCException dce) {
            return AlloResp.failed(dce, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }

    @Override
    public AlloResp<BetGameRoundInfo> getCurBetGameRound(long actId, long uid) {
        if (actId <= 0 || uid < 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            BetGameRoundInfo result = betFacadeService.getCurBetGameRound(actId, uid);
            return AlloResp.success(result);
        } catch (DCException dce) {
            return AlloResp.failed(dce, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }

    @Override
    public AlloResp<BetGameRoundInfo> getBetGameRound(long gameId, long uid) {
        if (gameId <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            BetGameRoundInfo gameRound = betFacadeService.getBetGameRound(gameId, uid);
            return AlloResp.success(gameRound);
        } catch (DCException dce) {
            return AlloResp.failed(dce, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }

    @Override
    public AlloResp<Long> bet(long actId, long gameId, long uid, long spiritId, int amount) {
        try {
            long result = betFacadeService.bet(actId, gameId, uid, spiritId, amount);
            log.info(BetConstants.INFO_LOG + " bet resp result:{}", result);
            if (result >= 0) {
                return AlloResp.success(result);
            } else {
                if (result == BetConstants.GAME_ROUND_ERR.PAY_NOT_ENOUGH) {
                    return AlloResp.failed(BetConstants.GAME_ROUND_ERR.PAY_NOT_ENOUGH, "pay not enough", null);
                } else if (result == BetConstants.GAME_ROUND_ERR.BET_SO_FAST) {
                    return AlloResp.failed(BetConstants.GAME_ROUND_ERR.BET_SO_FAST, "bet so fast", null);
                } else if (result == BetConstants.GAME_ROUND_ERR.BET_NUM_LIMIT) {
                    return AlloResp.failed(BetConstants.GAME_ROUND_ERR.BET_NUM_LIMIT, "bet num limit", null);
                } else if (result == BetConstants.GAME_ROUND_ERR.PAY_ERR) {
                    return AlloResp.failed(BetConstants.GAME_ROUND_ERR.PAY_ERR, "bet pay err", null);
                } else if(result == BetConstants.GAME_ROUND_ERR.NOT_GAMEING){
                    return AlloResp.failed(BetConstants.GAME_ROUND_ERR.NOT_GAMEING, "bet not gamming", null);
                } else {
                    return AlloResp.failed(BusiStatus.BUSIERROR.value(), "busi err", null);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", 0L);
        }
    }

    @Override
    public AlloResp<Page<BetGameRoundRecord>> pageBetGameRound(long actId, long id, int pageSize) {
        if (actId <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            Page<BetGameRoundRecord> page = betFacadeService.pageGameRound(actId, id, pageSize);
            //第一条记录未结束，排除
            if(id == 0){
                List<BetGameRoundRecord> rows = page.getRows();
                if(CollectionUtils.isNotEmpty(rows)){
                    if(rows.get(0).getGameStatus()==0){
                        rows.remove(0);
                    }
                }
            }
            return AlloResp.success(page);
        } catch (DCException dce) {
            return AlloResp.failed(dce, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }

    @Override
    public AlloResp<Page<BetResultInfo>> pageBetResult(long actId, long uid, String month, long id, int pageSize) {
        if (actId <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "param err", null);
        }
        try {
            Page<BetResultInfo> page = betFacadeService.pageBetResult(actId, uid, month, id, pageSize);
            return AlloResp.success(page);
        } catch (DCException dce) {
            return AlloResp.failed(dce, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", null);
        }
    }

    @Override
    public AlloResp<Long> betStatistic() {
        try {
            long result = betFacadeService.betStatistic();
            return AlloResp.success(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server busy", 0L);
        }
    }
}
