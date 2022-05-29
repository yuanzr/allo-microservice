package com.dc.allo.rpc.domain.finance.virtualitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirtualItemWallet implements Serializable {
    private long id;
    private long uid;
    private String app;     // allo，国内等
    private int itemId;     // 道具id
    private String name;    // 道具名称
    private long preVal;    // 上一次值
    private long val;       // 当前存量
    private int price;      // 对应金币价值
    private Date ctime;
    private Date utime;

    public VirtualItemWallet(long uid, String app, int itemId, String name, long val, Date ctime, Date utime) {
        this.uid = uid;
        this.app = app;
        this.itemId = itemId;
        this.name = name;
        this.val = val;
        this.ctime = ctime;
        this.utime = utime;
    }
}
