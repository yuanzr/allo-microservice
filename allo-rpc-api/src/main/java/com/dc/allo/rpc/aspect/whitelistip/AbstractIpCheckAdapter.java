package com.dc.allo.rpc.aspect.whitelistip;

/**
 * Created by zhangzhenjun on 2020/5/18.
 */
public abstract class AbstractIpCheckAdapter {

    public abstract boolean isPass(String ip);
}
