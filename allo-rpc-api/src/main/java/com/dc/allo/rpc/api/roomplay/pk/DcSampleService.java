package com.dc.allo.rpc.api.roomplay.pk;

import com.dc.allo.rpc.domain.roomplay.pk.Sample;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
public interface DcSampleService {
    void setUser(long uid);
    String getUser(long uid);
    List<Sample> pageAdmins(long offset,int pageSize);
}
