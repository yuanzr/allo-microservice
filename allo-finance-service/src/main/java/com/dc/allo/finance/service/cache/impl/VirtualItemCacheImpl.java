package com.dc.allo.finance.service.cache.impl;

import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.finance.service.cache.VirtualItemCache;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
@Service
public class VirtualItemCacheImpl implements VirtualItemCache {

    @Autowired
    RedisUtil redisUtil;

    private String getWalletKey(String app, long uid, long itemId) {
        return RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Finance, "wallet", app, uid, itemId);
    }

    private String getWalletsKey(String app, long uid) {
        return RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey_Module_Pre.Finance, "wallets", app, uid);
    }

    @Override
    public void setVirtualWallet(VirtualItemWallet wallet) {
        if (wallet != null && StringUtils.isNotBlank(wallet.getApp()) && wallet.getUid() > 0 && wallet.getItemId() > 0) {
            redisUtil.set(getWalletKey(wallet.getApp(), wallet.getUid(), wallet.getItemId()), JsonUtils.toJson(wallet), RedisKeyUtil.RedisExpire_Time.OneMinute);
        }
    }

    @Override
    public VirtualItemWallet getVirtualWallet(String app, long uid, int itemId) {
        VirtualItemWallet wallet = null;
        String json = redisUtil.get(getWalletKey(app, uid, itemId));
        if (StringUtils.isNotBlank(json)) {
            wallet = JsonUtils.fromJson(json, VirtualItemWallet.class);
        }
        return wallet;
    }

    @Override
    public void setVirtualWallets(String app, long uid, List<VirtualItemWallet> wallets) {
        if (CollectionUtils.isNotEmpty(wallets)) {
            redisUtil.set(getWalletsKey(app, uid), JsonUtils.toJson(wallets), RedisKeyUtil.RedisExpire_Time.OneMinute);
        }
    }

    @Override
    public List<VirtualItemWallet> queryVirtualWallets(String app, long uid) {
        List<VirtualItemWallet> wallets = null;
        String json = redisUtil.get(getWalletsKey(app, uid));
        if (StringUtils.isNotBlank(json)) {
            wallets = JsonUtils.fromJson(json, new TypeReference<List<VirtualItemWallet>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
        }
        return wallets;
    }
}
