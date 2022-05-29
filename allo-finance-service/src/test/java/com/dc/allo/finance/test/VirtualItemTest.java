package com.dc.allo.finance.test;

import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.finance.Application;
import com.dc.allo.finance.service.VirtualItemService;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemAppDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.dc.allo.rpc.domain.page.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/10.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class VirtualItemTest {

    @Autowired
    VirtualItemService virtualItemService;

    @Test
    public void testAddItemDef(){
        virtualItemService.addVirtualItemDef("炸弹",10);
    }

    @Test
    public void testAddAppItemDef(){
        virtualItemService.addVirtualItemAppDef(Constant.App.ALLO, 1);
    }

    @Test
    public void testQueryDef(){
        List<VirtualItemAppDef> appDefs = virtualItemService.queryAllVirtualItemAppDef();
        log.info(JsonUtils.toJson(appDefs));
        List<VirtualItemDef> itemDefs = virtualItemService.queryAllVirtualItemDef(Constant.App.ALLO);
        log.info(JsonUtils.toJson(itemDefs));
    }

    @Test
    public void testAddBill(){
        virtualItemService.addVirtualItemBill(Constant.App.ALLO, 123400, 1, -5, "test bill", " test test");
        VirtualItemWallet wallet = virtualItemService.getVirtualWallet(Constant.App.ALLO, 123400, 1,true);
        log.info(wallet.toString());
        Page<VirtualItemBill> page = virtualItemService.pageVirtualItemBill(Constant.App.ALLO, 123400, "2020_04", 0, 10);
        log.info(page.toString());
    }
}
