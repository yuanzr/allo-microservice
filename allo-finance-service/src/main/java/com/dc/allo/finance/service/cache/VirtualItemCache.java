package com.dc.allo.finance.service.cache;

import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/13.
 */
public interface VirtualItemCache {

    void setVirtualWallet(VirtualItemWallet wallet);

    VirtualItemWallet getVirtualWallet(String app, long uid, int itemId);

    void setVirtualWallets(String app, long uid, List<VirtualItemWallet> wallets);

    List<VirtualItemWallet> queryVirtualWallets(String app, long uid);
}
