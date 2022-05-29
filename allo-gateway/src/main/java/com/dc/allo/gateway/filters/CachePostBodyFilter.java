package com.dc.allo.gateway.filters;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Created by zhangzhenjun on 2020/3/28.
 */
@Component
public class CachePostBodyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String method = serverHttpRequest.getMethodValue();
        String path = serverHttpRequest.getPath().toString();
        if("POST".equalsIgnoreCase(method) && path.contains("auth")) {
            // 将 request body 中的内容 copy 一份，记录到 exchange 的一个自定义属性中
            Object cachedRequestBodyObject = exchange.getAttributeOrDefault("cachedRequestBody", null);
            // 如果已经缓存过，略过
            if (cachedRequestBodyObject != null) {
                return chain.filter(exchange);
            }
            // 如果没有缓存过，获取字节数组存入 exchange 的自定义属性中
            return DataBufferUtils.join(serverHttpRequest.getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    exchange.getAttributes().put("cachedRequestBody", bytes);

                    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                        DataBuffer buffer = exchange.getResponse().bufferFactory()
                                .wrap(bytes);
                        return Mono.just(buffer);
                    });

                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(serverHttpRequest) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                        }
                    };
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                });

        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -99;
    }
}
