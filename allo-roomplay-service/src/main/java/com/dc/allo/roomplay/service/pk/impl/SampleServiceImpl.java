package com.dc.allo.roomplay.service.pk.impl;

import com.dc.allo.roomplay.mapper.pk.SampleMapper;
import com.dc.allo.roomplay.service.pk.SampleService;
import com.dc.allo.roomplay.service.pk.cache.SampleCache;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
@Service
@RefreshScope
public class SampleServiceImpl implements SampleService {

    @Autowired
    SampleCache sampleCache;

    @Autowired
    SampleMapper sampleMapper;

    @Override
    public void setUser(long uid) {
        sampleCache.setUserCache(uid);
    }

    @Override
    public String getUser(long uid) {
        return sampleCache.getUserCache(uid);
    }

    @Override
    public long addAdmin(Sample sample) {
        return sampleMapper.addAdminUser(sample);
    }

    @Override
    public List<Sample> querySamples() {
        return sampleMapper.queryAdmins();
    }

    @Override
    public List<Sample> pageAdmins(long offset, int pageSize) {
        return sampleMapper.pageAdmins(offset,pageSize);
    }

    @Override
    public long updateSamplePhone(long id, String phone) {
        return sampleMapper.updateAdminPhone(id,phone);
    }
}
