package com.dc.allo.roomplay.service.pk;

import com.dc.allo.rpc.domain.roomplay.pk.Sample;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
public interface SampleService {

    void setUser(long uid);

    String getUser(long uid);

    long addAdmin(Sample sample);

    List<Sample> querySamples();

    List<Sample> pageAdmins(long offset,int pageSize);

    long updateSamplePhone(long id,String phone);
}
