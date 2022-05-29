package com.dc.allo.finance.service.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.redis.RedisLockExcutor;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.finance.contants.VirtualItemDefInnerFlagEnums;
import com.dc.allo.finance.mapper.VirtualItemMapper;
import com.dc.allo.finance.service.VirtualItemService;
import com.dc.allo.finance.service.cache.VirtualItemCache;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemAppDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemDef;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.dc.allo.rpc.domain.page.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
@Service
@Slf4j
public class VirtualItemServiceImpl implements VirtualItemService {

    @Autowired
    VirtualItemMapper virtualItemMapper;

    @Autowired
    RedisTemplate<String, String> stringTemplate;

    @Autowired
    VirtualItemCache virtualItemCache;

    // app 所关联的虚拟道具id 配置
    private Map<String, Set<Integer>> appItemIdMap = new HashMap<>();
    private Map<Integer, String> itemDefNameMap = new HashMap<>();
    private Map<Integer, VirtualItemDef> itemDefMap = new HashMap<>();
    private long appItemIdMapUpdateTime = 0;

    /**
     * 初始化虚拟道具配置
     */
    private void initAppItemIdMap() {
        Map<String, Set<Integer>> appItemIdMapTmp = new HashMap<>();
        Map<Integer, String> itemDefNameMapTmp = new HashMap<>();
        Map<Integer, VirtualItemDef> itemDefMapTmp = new HashMap<>();

        if (appItemIdMapUpdateTime <= 0 || (System.currentTimeMillis() - appItemIdMapUpdateTime) > 60000) {
            synchronized (VirtualItemServiceImpl.class) {
                if (appItemIdMapUpdateTime <= 0 || (System.currentTimeMillis() - appItemIdMapUpdateTime) > 60000) {
                    List<VirtualItemAppDef> appItemDefs = virtualItemMapper.queryAllAppDef();
                    for (VirtualItemAppDef appItemDef : appItemDefs) {

                        String app = appItemDef.getApp();
                        Set<Integer> appItemSet = appItemIdMapTmp.get(app);
                        if (appItemSet == null) {
                            appItemSet = new HashSet<>();
                            appItemIdMapTmp.put(app, appItemSet);
                        }
                        appItemSet.add(appItemDef.getItemId());
                    }

                    List<VirtualItemDef> itemDefs = virtualItemMapper.queryAllItemDef();
                    for (VirtualItemDef itemDef : itemDefs) {
                        itemDefNameMapTmp.put(itemDef.getId(), itemDef.getName());
                        itemDefMapTmp.put(itemDef.getId(), itemDef);
                    }
                }
            }
            appItemIdMap = appItemIdMapTmp;
            itemDefNameMap = itemDefNameMapTmp;
            itemDefMap = itemDefMapTmp;
        }
    }

    private String getItemName(int moneyId) {
        String name = itemDefNameMap.get(moneyId);
        if (name == null) {
            name = "";
        }
        return name;
    }

    private Set<Integer> getAppItemIdSet(String app) {
        initAppItemIdMap();
        Set<Integer> appMoneyIdSet = appItemIdMap.get(app);
        if (appMoneyIdSet == null) {
            appMoneyIdSet = new HashSet<>();
        }
        return appMoneyIdSet;
    }

    private VirtualItemDef getItemDef(int itemId) {
        return itemDefMap.get(itemId);
    }

    @Override
    public long addVirtualItemAppDef(String app, int itemId) {
        if (StringUtils.isBlank(app) || itemId <= 0) {
            return 0;
        }
        return virtualItemMapper.addAppDef(app, itemId);
    }

    @Override
    public List<VirtualItemAppDef> queryAllVirtualItemAppDef() {
        return virtualItemMapper.queryAllAppDef();
    }

    @Override
    public long addVirtualItemDef(String name, int price) {
        if (StringUtils.isBlank(name) || price < 0) {
            return 0;
        }
        return virtualItemMapper.addItemDef(name, price);
    }

    @Override
    public List<VirtualItemDef> queryAllVirtualItemDef(String app) {
        return virtualItemMapper.queryAllItemDefByApp(app);
    }

    @Override
    @Transactional
    public long addVirtualItemBill(String app, long uid, int itemId, long val, String bizId, String context) {
        String logMsg = "app:" + app + ", uid:" + uid + ", itemId:" + itemId + ", val:" + val + ", bizId:" + bizId + ", context:" + context;

        Set<Integer> appItemIdSet = getAppItemIdSet(app);
        if (!appItemIdSet.contains(itemId)) {
            throw new DCException(BusiStatus.PARAMERROR.value(), "参数错误，app不存在此道具或已冻结, app:" + app + ", itemId:" + itemId);
        }

        try(HintManager hintManager = HintManager.getInstance()) {
            String lockKey = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Finance, "lock:addItemBill") + uid + "-" + itemId;
            RedisLockExcutor.executeUntilLock(stringTemplate, lockKey, 3000, () -> {

                //指定只从主库读写
                hintManager.setMasterRouteOnly();

                VirtualItemWallet itemNow = virtualItemMapper.getWallet(app, uid, itemId);
                // 防止金额出现负数
                if (val < 0) {
                    long itemNowVal = itemNow == null ? 0 : itemNow.getVal();
                    if ((itemNowVal + val) < 0) {
                        throw new DCException(BusiStatus.VIRTUAL_ITEM_NOT_ENOUGH.value(), "数额不足");
                    }
                }
                long walletVal = itemNow == null?0:itemNow.getVal();

                VirtualItemDef itemDef = getItemDef(itemId);

                // 记录流水账单
                if (val != 0) {
                    int price = 0;
                    if (itemDef != null) {
                        price = itemDef.getPrice();
                    }
                    long row = virtualItemMapper.addBill(app, uid, itemId, val, bizId, context, price,walletVal);
                    if (row > 0) {
                        log.info("[virtualItem] addItem bill success. " + logMsg);
                    } else {
                        log.error("[virtualItem] addItem bill failed. " + logMsg);
                        throw new DCException(BusiStatus.SERVERERROR.value(), "记录流水失败");
                    }
                }
                //增加/扣减钱包
                long row2 = virtualItemMapper.incrWallet(app, uid, itemId, val);
                if (row2 > 0) {
                    log.info("[virtualItem] addItemWallet update success. " + logMsg);
                } else {
                    log.info("[virtualItem] addItemWallet update failed. try insert it " + logMsg);
                    try {
                        //初始化钱包
                        long row3 = virtualItemMapper.addWallet(app, uid, itemId, val);
                        if (row3 > 0) {
                            log.info("[virtualItem] addItemBill insert success. " + logMsg);
                        } else {
                            log.info("[virtualItem] addItemBill insert failed. " + logMsg);
                            throw new DCException(BusiStatus.SERVERERROR.value(), "调整虚拟道具数量失败，新增用户虚拟道具记录失败");
                        }
                    } catch (DuplicateKeyException dke) {
                        log.warn("[virtualItem] addItemBill insert DuplicateKeyException, try incrMoney. " + logMsg);
                        long row4 = virtualItemMapper.incrWallet(app, uid, itemId, val);
                        if (row4 > 0) {
                            log.info("[virtualItem] addItemBill update again success. " + logMsg);
                        } else {
                            log.error("[virtualItem] addItemBill update again failed. " + logMsg);
                            throw new DCException(BusiStatus.SERVERERROR.value(), "调整虚拟道具数量失败");
                        }
                    }
                }

            });
        } catch (DCException dce) {
            if (dce.getCode() != BusiStatus.VIRTUAL_ITEM_NOT_ENOUGH.value()) {
                throw dce;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        VirtualItemWallet virtualItem = virtualItemMapper.getWallet(app, uid, itemId);
        return virtualItem == null ? 0 : virtualItem.getVal();
    }

    @Override
    public Page<VirtualItemBill> pageVirtualItemBill(String app, long uid, String suffix, long id, long pageSize) {
        Page<VirtualItemBill> page = new Page<>();
        //多取一条记录，判断是否有下页
        List<VirtualItemBill> bills = virtualItemMapper.pageBill(app, uid, suffix, id, pageSize + 1);
        if (CollectionUtils.isNotEmpty(bills)) {
            int size = bills.size();
            if (size > pageSize) {
                page.setHasMore(true);
                page.setRows(bills.subList(0, size - 2));
            } else {
                page.setHasMore(false);
                page.setRows(bills);
            }
            long lastId = bills.get(bills.size() - 1).getId();
            page.setId(lastId);
        } else {
            page.setHasMore(false);
        }
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public Page<VirtualItemBill> pageVirtualItemBillByItem(String app, String suffix, Long uid, Integer itemId, Long recordId, Long pageSize) {
        Page<VirtualItemBill> page = new Page<>();
        //多取一条记录，判断是否有下页
        List<VirtualItemBill> bills = virtualItemMapper.pageBillByItem(app, uid, suffix,itemId, recordId, pageSize);
        if (CollectionUtils.isNotEmpty(bills)) {
            int size = bills.size();
            if (size > pageSize) {
                page.setHasMore(true);
                page.setRows(bills.subList(0, size - 2));
            } else {
                page.setHasMore(false);
                page.setRows(bills);
            }
            long lastId = bills.get(bills.size() - 1).getId();
            page.setId(lastId);
            page.setPageSize(pageSize);
        } else {
            page.setHasMore(false);
        }
        return page;
    }

    /**
     * 自动扣金币购买道具（预留）
     *
     * @param app
     * @param uid
     * @param itemId
     * @param val
     * @return
     */
    private boolean costDimon(String app, long uid, int itemId, long val) {
        if (!itemDefMap.containsKey(itemId)) {
            log.info("[virtualItem] itemId=" + itemId + ",itemDefMap配置为空");
            return false;
        }

        VirtualItemDef moneyDef = itemDefMap.get(itemId);
        //内购标识
        long innerFlag = moneyDef.getInnerFlag();
        if (!VirtualItemDefInnerFlagEnums.isValid(innerFlag, VirtualItemDefInnerFlagEnums.NOT_ENOUGH_COST_DIMON)) {
            log.info("itemId=" + itemId + ",ItemDefMap未配置自动扣金币");
            return false;
        }

        // 需要扣除的金币(这里val是负数，所以要取相反数)
        long totalCostDimon = 0 - val * moneyDef.getPrice();

        String desc = "购买道具：" + moneyDef.getName() + " 数量：" + (0 - val);

        // 金币扣除失败，返回false
        int result = -1;
        try {
            //todo 扣金币逻辑

        } catch (Exception e) {
            log.info("", e);
            return false;
        }
        //金币不足
        if (result == 0) {
            throw new DCException(BusiStatus.VIRTUAL_ITEM_NOT_ENOUGH.value(), "余额不够，请先充值");
        }

        if (result != 1) {
            return false;
        }
        return true;
    }

    @Override
    public VirtualItemWallet getVirtualWallet(String app, long uid, int itemId, boolean useCache) {
        Set<Integer> appItemIdSet = getAppItemIdSet(app);
        VirtualItemWallet wallet = null;
        if (!appItemIdSet.contains(itemId)) {
            return wallet;
        }

        if (useCache) {
            wallet = virtualItemCache.getVirtualWallet(app, uid, itemId);
            if (wallet != null) {
                return wallet;
            }
        }
        // 没数据默认构造一条初始值为0的数据
        wallet = virtualItemMapper.getWallet(app, uid, itemId);
        if (wallet == null) {
            addVirtualItemBill(app, uid, itemId, 0, "system init.", "system init.");
            String name = getItemName(itemId);
            wallet = new VirtualItemWallet(uid, app, itemId, name, 0, new Date(), new Date());
        }
        virtualItemCache.setVirtualWallet(wallet);
        return wallet;
    }

    @Override
    public List<VirtualItemWallet> queryVirtualWallets(String app, long uid, boolean useCache) {
        Set<Integer> appItemIdSet = getAppItemIdSet(app);
        Set<Integer> existItemId = new HashSet<>();

        List<VirtualItemWallet> wallets = new ArrayList<>();
        if (useCache) {
            wallets = virtualItemCache.queryVirtualWallets(app, uid);
            if (CollectionUtils.isNotEmpty(wallets)) {
                return wallets;
            }
        }

        List<VirtualItemWallet> walletsTmp = virtualItemMapper.queryWallets(app, uid);
        //已存在的道具钱包
        for (VirtualItemWallet wallet : walletsTmp) {
            if (appItemIdSet.contains(wallet.getItemId())) {
                wallets.add(wallet);
                existItemId.add(wallet.getItemId());
            }
        }
        //不存在的钱包初始化一条为0的数据
        for (int itemId : appItemIdSet) {
            if (!existItemId.contains(itemId)) {
                addVirtualItemBill(app, uid, itemId, 0, "system init.", "system init.");
                String name = getItemName(itemId);
                wallets.add(new VirtualItemWallet(uid, app, itemId, name, 0, new Date(), new Date()));
            }
        }
        virtualItemCache.setVirtualWallets(app, uid, wallets);
        return wallets;
    }
    @Override
    public List<VirtualItemWallet> queryVirtualWallets(String app, List<Integer> itemIds, long uid, boolean useCache) {
        List<VirtualItemWallet> wallets = queryVirtualWallets(app, uid, useCache);
        if (!CollectionUtils.isEmpty(wallets)) {
            virtualItemCache.setVirtualWallets(app, uid, wallets);
            wallets = wallets.stream().filter(wallet -> itemIds.contains(wallet.getItemId()) == true).collect(Collectors.toList());
        }
        return wallets;
    }

    @Override
    public String getItemBillKey(Integer itemId, String context) {
        return "act_item_bill_"+itemId+"_"+context;
    }

}
