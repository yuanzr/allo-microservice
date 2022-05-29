package com.dc.allo.common.component.i18n.impl;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: NacosConfigI18nClient
 * @Description: nacos配置文件i18n初始化
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/1/10/14:06
 */
@Component
public class NacosConfigI18nClient {

    protected final Logger log = LoggerFactory.getLogger(NacosConfigI18nClient.class);

    private String serverAddr ;

    private String group;

    private String namespace;

    private String messagesConfigIds;

    public String getServerAddr() {
        return serverAddr;
    }
    @Value("${i18n.nacos.config.server-addr}")
    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getGroup() {
        return group;
    }
    @Value("${i18n.nacos.config..group}")
    public void setGroup(String group) {
        this.group = group;
    }

    public String getNamespace() {
        return namespace;
    }
    @Value("${i18n.nacos.config.namespace}")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getMessagesConfigIds() {
        return messagesConfigIds;
    }
    @Value("${i18n.nacos.config.dataIds}")
    public void setMessagesConfigIds(String messagesConfigIds) {
        this.messagesConfigIds = messagesConfigIds;
    }

    private ConfigService configService;

    public static final Map<String, Properties> messageMap = new ConcurrentHashMap<>();


    public void initI18nMessage(){

        try {
            String serverAddr    = this.getServerAddr();
            String namespace     = getNamespace();
            String group         = getGroup();
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.put(PropertyKeyConst.NAMESPACE, namespace);
            configService = NacosFactory.createConfigService(properties);
            String[] dataIdArr = this.getMessagesConfigIds().split(",");
            for (String dataId : dataIdArr) {
                //1.获取配置文件内容
                String content = configService.getConfig(dataId, group, 3000);
                //2.加载配置文件
                Properties message = new Properties();
                message.load(new StringReader(content));
                //3.缓存到map
                messageMap.put(dataId,message);
                //4.添加配置监听器
                addNacosListener(dataId);
            }
        } catch (NacosException e) {
            log.error("initI18nMessage-NacosException",e);
        } catch (IOException e) {
            log.error("initI18nMessage-IOException",e);
        }
    }

    /**
     * 添加配置文件的监听器
     * @param dataId
     * @throws NacosException
     */
    private void addNacosListener(String dataId) throws NacosException{
        configService.addListener(dataId, this.group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                    log.info("NacosListener-dataId={}",dataId);
                try {
                    Properties properties = messageMap.get(dataId);
                    properties.load(new StringReader(configInfo));
                    messageMap.put(dataId,properties);
                } catch (IOException e) {
                    log.error("NacosListener-receive-dataId={},content={}",dataId,configInfo);
                }
            }
            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }

}
