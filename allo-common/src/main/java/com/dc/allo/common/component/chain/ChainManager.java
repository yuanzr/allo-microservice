package com.dc.allo.common.component.chain;



import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangzhenjun on 2020/5/06.
 */
@Component
public class ChainManager {
    private Map<ChainRegister, TreeMap<Integer, AbstractChainNode>> chains = new ConcurrentHashMap<>();
    private Map<ChainRegister, Class> contextClasses = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        Arrays.stream(ChainRegister.values()).forEach(chain -> chains.put(chain, new TreeMap<>(Comparator.comparingInt(o -> o))));
    }

    public void register(ChainRegister chainRegister, Integer priority, AbstractChainNode node, Class registerContextClass) throws Exception {
        if (chainRegister == null || node == null || priority == null) {
            throw new IllegalArgumentException("[ChainManager]err chainRegister and node is null!");
        }
        TreeMap<Integer, AbstractChainNode> nodes = chains.get(chainRegister);
        if (nodes.get(priority) != null) {
            throw new IllegalArgumentException("[ChainManager]err duplicate priority in this chain: " + chainRegister);
        }
        Class nowContextClass = contextClasses.get(chainRegister);
        if (nowContextClass == null) {
            synchronized (chainRegister) {
                nowContextClass = contextClasses.get(chainRegister);
                if (nowContextClass == null) {
                    contextClasses.put(chainRegister, registerContextClass);
                    nowContextClass = registerContextClass;
                }
            }
        }
        if (registerContextClass != nowContextClass) {
            throw new IllegalArgumentException("[ChainManager]err different context class in this chain: " + chainRegister + "[nowContextClass: " + nowContextClass + ", registerContextClass:" + registerContextClass + " ] ");
        }
        nodes.put(priority, node);
    }

    public <T> void process(ChainRegister chainRegister, T context) throws Exception {
        TreeMap nodes = chains.get(chainRegister);
        if (MapUtils.isEmpty(nodes)) {
            return;
        }
        if (context.getClass() != contextClasses.get(chainRegister)) {
            throw new IllegalArgumentException("[ChainManager]err context class:" + context.getClass() + " doesn't match this chain: " + chainRegister);
        }
        Collection<AbstractChainNode<T>> chainNodes = nodes.values();
        for (AbstractChainNode chainNode : chainNodes) {
            boolean doNext = chainNode.doProcess(context);
            if (!doNext) {
                break;
            }
        }
    }

}
