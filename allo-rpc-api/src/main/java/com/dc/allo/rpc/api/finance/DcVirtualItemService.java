package com.dc.allo.rpc.api.finance;

import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.proto.AlloResp;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/4/11.
 */
public interface DcVirtualItemService {

    /**
     * 虚拟道具流水
     *
     * @param app     app，allo或其它
     * @param uid     用户id
     * @param itemId  道具id
     * @param val     负数表示扣除
     * @param bizId   业务id，发生该流水的业务id
     * @param context 业务上下文，可加一些描述
     * @return
     */
    AlloResp<Long> addVirtualItemBill(String app, long uid, int itemId, long val, String bizId, String context);

    /**
     * 分页查虚拟道具流水，需传月份
     *
     * @param app
     * @param uid
     * @param suffix
     * @param id
     * @param pageSize
     * @return
     */
    AlloResp<Page<VirtualItemBill>> pageVirtualItemBill(String app, long uid, String suffix, long id, long pageSize);

    /**
     * 虚拟道具钱包记录
     *
     * @param app
     * @param uid
     * @param itemId
     * @return
     */
    AlloResp<VirtualItemWallet> getVirtualWallet(String app, long uid, int itemId, boolean useCache);

    /**
     * 获取指定道具的钱包记录
     *
     * @param app
     * @param itemIds
     * @param uid
     * @return
     */
    AlloResp<List<VirtualItemWallet>> queryVirtualWallets(String app, List<Integer> itemIds, long uid, boolean useCache);
}
