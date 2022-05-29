package com.dc.allo.roomplay.service.rpc.pk.impl;

import com.dc.allo.roomplay.service.pk.SampleService;
import com.dc.allo.rpc.api.roomplay.pk.DcSampleService;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Service
public class DcSampleServiceImpl implements DcSampleService {

    @Autowired
    SampleService sampleService;

    @Override
    public void setUser(long uid) {
        sampleService.setUser(uid);
    }

    @Override
    public String getUser(long uid) {
        return sampleService.getUser(uid);
    }

    @Override
    public List<Sample> pageAdmins(long offset, int pageSize) {
        return sampleService.pageAdmins(offset,pageSize);
    }
}
