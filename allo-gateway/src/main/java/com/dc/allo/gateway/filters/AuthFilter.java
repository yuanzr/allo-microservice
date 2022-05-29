package com.dc.allo.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.utils.PbUtils;
import com.dc.allo.rpc.proto.AlloResp;
import com.erban.main.proto.PbCommon;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by zhangzhenjun on 2020/3/28.
 */
@Component
public class AuthFilter implements GlobalFilter,Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private RedisUtil redisUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        String method = serverHttpRequest.getMethodValue().toUpperCase();
        String path = serverHttpRequest.getPath().toString();

       boolean isContainPk = path.contains("cancelRoomPk") || path.contains("getRoomPkingList") || path.contains("targetChooseResult")
                || path.contains("chooseRoomPk") || path.contains("pkSquare") || path.contains("wantPk");
        if((path.contains("auth") || isContainPk)) {

            try {
                String uid = null;
                String ticket = null;
                Boolean isPbReqeust = false;
                if ("POST".equals(method) && !path.contains("h5")) {
                    isPbReqeust = true;
                    byte[] body = exchange.getAttribute("cachedRequestBody");
                    if (body != null) {
                        PbCommon.PbHttpBizReq req = PbCommon.PbHttpBizReq.parseFrom(body);
                        if(req != null) {
                            uid = req.getUid();
                            ticket = req.getTicket();
                        }
                    }
                } else {
                    isPbReqeust = false;
                    HttpHeaders headers = serverHttpRequest.getHeaders();
                    uid = headers.get("uid").get(0);
                    ticket = headers.get("ticket").get(0);
                }

                if (StringUtils.isEmpty(uid) || StringUtils.equalsIgnoreCase(uid, "null") || !StringUtils.isNumeric(uid)) {
                    logger.warn("uid illegal, uri={}, uid={}",path, uid);
                    return validFailedResp(serverHttpResponse, isPbReqeust);
                }
                if (StringUtils.isEmpty(ticket)) {
                    logger.warn("ticket illegal,uid={}, uri={}, ticket={}",uid, path, ticket);
                    return validFailedResp(serverHttpResponse, isPbReqeust);
                }

                String ticketCache = getTicketCacheByUid(uid);
                if (StringUtils.isEmpty(ticketCache)) {
                    logger.warn("catch ticket not exists,uid={}, uri={}, uid={}, ticket={}",uid,path, uid, ticket);
                    return validFailedResp(serverHttpResponse, isPbReqeust);
                }

                if (!ticketCache.equals(ticket)) {
                    logger.warn("ticket illegal,uid={}, uri={}, ticket={}, cache ticket={}",uid, path, ticket, ticketCache);
                    return validFailedResp(serverHttpResponse, isPbReqeust);
                }
            } catch (InvalidProtocolBufferException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -98;
    }


    public Mono<Void> validFailedResp(ServerHttpResponse serverHttpResponse, Boolean isPbReqeust){
        if (isPbReqeust) {
            PbCommon.PbHttpBizResp.Builder respBaseBuilder = PbUtils.genCommRespBaseBuilder(PbCommon.Module.httpModule_VALUE, PbCommon.PbHttpBizResp.class.getSimpleName());
            respBaseBuilder.setCode(BusiStatus.NOAUTHORITY.value()).setMessage("need login");
            DataBuffer bodyDataBuffer = serverHttpResponse.bufferFactory().wrap(respBaseBuilder.build().toByteArray());
            serverHttpResponse.getHeaders().add("Content-Type", "application/x-protobuf");
            return serverHttpResponse.writeWith(Mono.just(bodyDataBuffer));
        }

        AlloResp alloResp = new AlloResp();
        alloResp.setCode(BusiStatus.NEEDLOGIN.value());
        alloResp.setMessage(BusiStatus.NEEDLOGIN.getReasonPhrase());
        DataBuffer bodyDataBuffer = serverHttpResponse.bufferFactory().wrap(JSONObject.toJSONBytes(alloResp));
        return serverHttpResponse.writeWith(Mono.just(bodyDataBuffer));
    }

    /**
     * 获取缓存中的ticket
     * @param uid
     * @return
     */
    private String getTicketCacheByUid(String uid) {
        Object  ticketObj = redisUtils.hGet("erban_uid_ticket", uid);
        if (ticketObj == null) {
            return null;
        }
        String ticketStr = ticketObj.toString();
        if (StringUtils.isEmpty(ticketStr)) {
            return null;
        }
        return ticketStr;
    }
}
