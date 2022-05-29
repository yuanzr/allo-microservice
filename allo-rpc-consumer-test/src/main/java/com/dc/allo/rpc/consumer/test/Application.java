package com.dc.allo.rpc.consumer.test;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.constants.Constant;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rpc.api.finance.DcVirtualItemService;
import com.dc.allo.rpc.api.roomplay.DcHelloService;
import com.dc.allo.rpc.api.roomplay.pk.DcSampleService;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemBill;
import com.dc.allo.rpc.domain.finance.virtualitem.VirtualItemWallet;
import com.dc.allo.rpc.domain.page.Page;
import com.dc.allo.rpc.domain.roomplay.Hello;
import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import com.dc.allo.rpc.proto.AlloResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/17.
 */
@EnableDiscoveryClient
@SpringBootApplication
@RestController
@Slf4j
public class Application {
    @Reference
    DcHelloService helloService;
    @Reference
    DcSampleService sampleService;

    @Reference
    DcVirtualItemService virtualItemService;

    @GetMapping("/testVirtualItem")
    public void testAddVirtualItemBill(){
        virtualItemService.addVirtualItemBill(Constant.App.ALLO, 123456, 1, 100, "test", "helleo");
        AlloResp<VirtualItemWallet> result = virtualItemService.getVirtualWallet(Constant.App.ALLO, 123456, 1,false);
        log.info(JsonUtils.toJson(result));
        AlloResp<Page<VirtualItemBill>> page = virtualItemService.pageVirtualItemBill(Constant.App.ALLO, 123456, "2020_04", 0, 3);
        log.info(JsonUtils.toJson(page));
    }

    @GetMapping("/hello")
    public void hello(String msg){
        Hello hello = new Hello();
        hello.setText(msg);
        helloService.sayHello(hello);
    }

    @GetMapping("/getUser")
    public AlloResp<String> getUser(long uid){

        return AlloResp.success(sampleService.getUser(uid));
    }

    @GetMapping("/pageAdmins")
    @SentinelResource(value = "consumer-test",fallback = "fallbackHandler",blockHandler = "exceptionHandler")
    public AlloResp<List<Sample>> pageAdmins(long offset,int pageSize){
        return AlloResp.success(sampleService.pageAdmins(offset, pageSize));
    }

    // Fallback 函数
    public AlloResp<String> fallbackHandler(String test) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return AlloResp.failed(BusiStatus.BUSIERROR.value(), "[FallBack] Dubbo Sentinel Nacos Consumer:  - fallbackHandler " + df.format(new Date()), null);
    }

    // Block 异常处理函数
    public AlloResp<List<Sample>> exceptionHandler(long offset,int pageSize, BlockException ex) {
        ex.printStackTrace();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return AlloResp.failed(BusiStatus.BUSIERROR.value(),"[FallBack] Dubbo Sentinel Nacos Consumer:  - exceptionHandler " + df.format(new Date()),null);
    }

    @GetMapping("/testFallBack")
    @SentinelResource(value = "consumer-test",fallback = "fallbackHandler")
    public AlloResp<String> testFallBack(String test){
        System.out.println(test);
        throw new RuntimeException("test fall back ");
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }


}
