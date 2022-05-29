package com.dc.allo.finance.service;

import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemAppDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.dc.allo.rpc.domain.page.Page;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
public interface VirtualItemService {

    /**
     * 新增app虚拟道具定义
     *
     * @param app
     * @param itemId
     * @return
     */
    long addVirtualItemAppDef(String app, int itemId);

    /**
     * 获取所有app虚拟道具定义
     *
     * @return
     */
    List<VirtualItemAppDef> queryAllVirtualItemAppDef();

    /**
     * 新增虚拟道具配置
     *
     * @param name
     * @param price
     * @return
     */
    long addVirtualItemDef(String name, int price);

    /**
     * 查询虚拟道具配置
     *
     * @param app
     * @return
     */
    List<VirtualItemDef> queryAllVirtualItemDef(String app);

    /**
     * 记录虚拟道具流水
     *
     * @param app
     * @param uid
     * @param itemId
     * @param val
     * @param bizId
     * @param context
     * @return
     */
    long addVirtualItemBill(String app, long uid, int itemId, long val, String bizId, String context);

    /**
     * 分页查询账单
     *
     * @param app
     * @param uid
     * @param suffix
     * @param id
     * @param pageSize
     * @return
     */
    Page<VirtualItemBill> pageVirtualItemBill(String app, long uid, String suffix, long id, long pageSize);

    /**
     * 分页查询账单
     *
     * @param app
     * @param uid
     * @param suffix
     * @param recordId
     * @param pageSize
     * @return
     */
    Page<VirtualItemBill> pageVirtualItemBillByItem(String app, String suffix, Long uid, Integer itemId, Long recordId, Long pageSize);

    /**
     * 获取用户虚拟道具
     *
     * @param app
     * @param uid
     * @param itemId
     * @return
     */
    VirtualItemWallet getVirtualWallet(String app, long uid, int itemId, boolean useCache);

    /**
     * 获取用户所有虚拟道具
     *
     * @param app
     * @param uid
     * @return
     */
    List<VirtualItemWallet> queryVirtualWallets(String app, long uid, boolean useCache);

    /**
     * 获取用户指定的多个虚拟道具
     *
     * @param app
     * @param itemIds
     * @param uid
     * @return
     */
    List<VirtualItemWallet> queryVirtualWallets(String app, List<Integer> itemIds, long uid, boolean useCache);

    String getItemBillKey(Integer itemId,String context);

}
