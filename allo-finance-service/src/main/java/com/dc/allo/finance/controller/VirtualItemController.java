package com.dc.allo.finance.controller;

import com.dc.allo.common.component.kafka.KafkaSender;
import com.dc.allo.common.constants.Constant.App;
import com.dc.allo.common.constants.KafkaTopic;
import com.dc.allo.common.utils.Builder;
import com.dc.allo.finance.service.VirtualItemService;
import com.dc.allo.rpc.domain.finance.GiftSendRecord;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBillVo;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.proto.AlloResp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: VirtualItemController
 * @Description: 虚拟道具
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/4/21/10:30
 */
@RestController
@RequestMapping("/virtualItem")
public class VirtualItemController extends BaseController{

    @Autowired
    private VirtualItemService virtualItemService;

    @Autowired
    KafkaSender kafkaSender;

    /**
     * 获取用户虚拟道具钱包金额
     * @param uid
     * @param itemId  道具ID
     */
    @RequestMapping("/wallet/get")
    public AlloResp<VirtualItemWallet> getVirtualItemUserWallet(Long uid ,Integer itemId ){
        VirtualItemWallet virtualWallet = virtualItemService.getVirtualWallet(App.ALLO, uid, itemId, false);
        return AlloResp.success(virtualWallet);
    }



    /**
     * 查询用户虚拟道具的账单
     * @param uid
     * @param itemId
     * @param recordId        账单ID,首次传0,后面的传最后一条记录的ID
     * @param pageSize  一次查询的记录数量
     */
    @RequestMapping("/bill/get")
    public AlloResp getVirtualItemUserBill(Long uid ,Integer itemId ,
                                            @RequestParam(value = "recordId",defaultValue = "0") Long recordId,
                                            @RequestParam(value = "pageSize",defaultValue = "10")Long pageSize){
        Page<VirtualItemBill> billListPage = virtualItemService.pageVirtualItemBillByItem(App.ALLO, null, uid, itemId, recordId, pageSize);
        List<VirtualItemBill>   rows = billListPage.getRows();
        List<VirtualItemBillVo> list = new ArrayList<>(rows.size());
        if (CollectionUtils.isNotEmpty(rows)){
            rows.forEach(bill->{
                //设置翻译
                String itemBillKey = virtualItemService.getItemBillKey(itemId, bill.getContext());
                VirtualItemBillVo vo = Builder.of(VirtualItemBillVo::new)
                        .with(VirtualItemBillVo::setId, bill.getId())
                        .with(VirtualItemBillVo::setUid, bill.getUid())
                        .with(VirtualItemBillVo::setVal, bill.getVal())
                        .with(VirtualItemBillVo::setCreateTime, bill.getCtime().getTime())
                        .with(VirtualItemBillVo::setBizId, bill.getBizId())
                        .with(VirtualItemBillVo::setContext, getMessage(itemBillKey))
                        .with(VirtualItemBillVo::setPrice, bill.getPrice())
                        .build();
                list.add(vo);
            });
        }
        return AlloResp.success(list);
    }

    /**
     * 更新虚拟钱包
     * @param itemId    虚拟道具ID
     * @param billType  账单类型
     * @param value     更新值
     * @param uid       用户uid
     */
    @RequestMapping(value = "/wallet/update",method = RequestMethod.POST)
    public AlloResp<VirtualItemWallet> updateVirtualWallet(Integer itemId ,Byte billType,Integer value, Long uid ){
            virtualItemService.addVirtualItemBill(App.ALLO, uid, itemId, value, null, billType.toString());

        return AlloResp.success();
    }



    @RequestMapping(value = "/kafka/testSendGift")
    public void giftSendKafka() {

        GiftSendRecord record = new GiftSendRecord();
        record.setUid(1111111111111L);
        record.setReciveUid(22222222222L);
        record.setGiftId(1);
        record.setGiftNum(20);
        record.setCreateTime(new Date());
        kafkaSender.send(KafkaTopic.Finance.DC_GIFT_SEND, KafkaTopic.EventType.GIFT_SEND_EVENT, record);
    }
}
