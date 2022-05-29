package com.dc.allo.roomplay.service.pk.cache;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
public interface SampleCache {

    void setUserCache(long uid);

    String getUserCache(long uid);
}
