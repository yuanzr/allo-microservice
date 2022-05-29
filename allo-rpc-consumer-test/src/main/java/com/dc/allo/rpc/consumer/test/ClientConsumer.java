package com.dc.allo.rpc.consumer.test;

import com.dc.allo.rpc.api.roomplay.pk.DcSampleService;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/25.
 */
@Service
public class ClientConsumer {

    @Reference
    DcSampleService sampleService;

    public String getUser(long uid){
        return sampleService.getUser(uid);
    }

    public List<Sample> pageAdmins(long offset,int pageSize){
        return sampleService.pageAdmins(offset,pageSize);
    }
}
