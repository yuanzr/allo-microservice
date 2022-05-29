package com.dc.allo.finance.service.rpc.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.finance.service.VirtualItemService;
import com.dc.allo.rpc.api.finance.DcVirtualItemService;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/11.
 */
@Service
@Slf4j
public class DcVirtualItemServiceImpl implements DcVirtualItemService {

    @Autowired
    VirtualItemService virtualItemService;

    @Override
    public AlloResp<Long> addVirtualItemBill(String app, long uid, int itemId, long val, String bizId, String context) {
        if (StringUtils.isBlank(app)||uid<=0||itemId<=0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        long result;
        try {
            result = virtualItemService.addVirtualItemBill(app,uid,itemId,val,bizId,context);
        }catch (DCException dce){
            return AlloResp.failed(dce,null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(result);
    }

    @Override
    public AlloResp<Page<VirtualItemBill>> pageVirtualItemBill(String app, long uid, String suffix, long id, long pageSize) {
        if (StringUtils.isBlank(app)||uid<=0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        Page<VirtualItemBill> result;
        try {
            result = virtualItemService.pageVirtualItemBill(app,uid,suffix,id,pageSize);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(result);
    }

    @Override
    public AlloResp<VirtualItemWallet> getVirtualWallet(String app, long uid, int itemId,boolean useCache) {
        if (StringUtils.isBlank(app)||uid<=0||itemId<0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        VirtualItemWallet result;
        try {
            result = virtualItemService.getVirtualWallet(app,uid,itemId,useCache);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(result);
    }

    @Override
    public AlloResp<List<VirtualItemWallet>> queryVirtualWallets(String app, List<Integer> itemIds, long uid,boolean useCache) {
        if (StringUtils.isBlank(app)||uid<=0|| CollectionUtils.isEmpty(itemIds)) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        List<VirtualItemWallet> result;
        try {
            result = virtualItemService.queryVirtualWallets(app,itemIds,uid,useCache);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(result);
    }
}
