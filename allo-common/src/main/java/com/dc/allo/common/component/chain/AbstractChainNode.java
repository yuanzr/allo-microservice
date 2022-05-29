package com.dc.allo.common.component.chain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;

/**
 * Created by zhangzhenjun on 2020/5/06.
 */
public abstract class AbstractChainNode<T> {
    @Autowired
    private ChainManager chainManager;

    public abstract boolean doProcess(T context) throws Exception;

    public abstract ChainRegister belongTo();

    public abstract Integer priority();


    @PostConstruct
    public void addToChain() throws Exception {
        Class contextClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        chainManager.register(belongTo(), priority(), this, contextClass);
    }
}
