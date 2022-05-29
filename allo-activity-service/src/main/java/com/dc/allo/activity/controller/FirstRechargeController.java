package com.dc.allo.activity.controller;

import com.dc.allo.activity.service.FirstRechargeService;
import com.dc.allo.activity.domain.vo.recharge.FirstRechargePrizeVO;
import com.dc.allo.activity.domain.vo.recharge.PrizeRankActItem;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.PrizeTypeEnum;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: FirstRechargeController
 *
 * @date: 2020年05月11日 14:31
 * @author: qinrenchuan
 */
@RestController
@Slf4j
@RequestMapping("/activity")
public class FirstRechargeController extends BaseController {

    @Autowired
    private FirstRechargeService firstRechargeService;

    /**
     * 首充礼包
     * @param actType
     * @return com.dc.allo.rpc.proto.AlloResp
     * @author qinrenchuan
     * @date 2020/5/12/0012 16:51
     */
    @RequestMapping(value = "/h5/firstRecharge/prizes", method = RequestMethod.GET)
    public AlloResp getFirstRechargePrize(Byte actType) {
        try {
            List<FirstRechargePrizeVO> prizeVOS = new ArrayList<>();
            List<PrizeRankActItem> prizeRankActItems =
                    firstRechargeService.queryFirstRechargePrize(actType);
            if (prizeRankActItems != null && prizeRankActItems.size() > 0) {
                for (PrizeRankActItem prizeItem : prizeRankActItems) {
                    FirstRechargePrizeVO prizeVO = new FirstRechargePrizeVO();
                    prizeVO.setPrizeId(prizeItem.getActPrizeId());
                    prizeVO.setPrizeType(prizeItem.getPrizeType());
                    prizeVO.setPrizePic(prizeItem.getPrizeImgUrl());
                    prizeVO.setUnit(prizeItem.getTimeUnit());
                    if (PrizeTypeEnum.coins.getValue() == prizeItem.getPrizeType().intValue() ||
                            PrizeTypeEnum.gift.getValue() == prizeItem.getPrizeType().intValue()) {
                        prizeVO.setPrizeNum(prizeItem.getPrizeValue());
                    } else {
                        prizeVO.setPrizeNum(prizeItem.getTimeNum());
                    }
                    prizeVO.setPrizeName(getMessage(prizeItem.getPrizeName()));
                    prizeVOS.add(prizeVO);
                }
            }
            return AlloResp.success(prizeVOS);
        } catch (Exception e) {
            log.error("getFirstRechargePrize error", e);
            return AlloResp.failed(BusiStatus.SERVERERROR);
        }
    }
}
