package com.dc.allo.activity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.activity.constant.Constant;
import com.dc.allo.activity.service.FirstRechargeService;
import com.dc.allo.activity.domain.vo.recharge.PrizeRankActItem;
import com.dc.allo.rpc.api.utils.HttpUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * description: FirstRechargeServiceImpl
 *
 * @date: 2020年05月11日 14:56
 * @author: qinrenchuan
 */

@Service
@Slf4j
public class FirstRechargeServiceImpl implements FirstRechargeService {

    @Value("${xchat.web.domain}")
    private String xchatApiDomain;

    /**
     * 首充奖品查询
     * @param actType
     * @return java.util.List<FirstRechargePrizeVO>
     * @author qinrenchuan
     * @date 2020/5/11/0011 14:59
     */
    @Override
    public List<PrizeRankActItem> queryFirstRechargePrize(Byte actType) {
       try {
           Map<String, String> map = new HashMap<>();
           map.put("actType", actType.toString());
           List list = HttpUtils.doGetWithClass(
                   xchatApiDomain + Constant.queryFirstRechargePrizes,
                   map, List.class);

           log.info("list: {}", list);
           if (list != null && list.size() > 0) {
               List<PrizeRankActItem> prizeVOS = new ArrayList<>(list.size());
               for (Object obj : list) {
                   PrizeRankActItem prizeRankActItem = JSONObject.parseObject(
                           obj.toString(), PrizeRankActItem.class);
                   prizeVOS.add(prizeRankActItem);
               }
               return prizeVOS;
           }
           return new ArrayList<>();
       } catch (Exception e) {
            log.error("queryFirstRechargePrize http error", e);
            return new ArrayList<>();
       }
    }
}
