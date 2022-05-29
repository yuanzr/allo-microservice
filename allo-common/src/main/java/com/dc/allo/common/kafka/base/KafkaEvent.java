package com.dc.allo.common.kafka.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEvent implements Serializable{

    private String app;     // 应用程序：allo、国内
    private int eventType;  // 事件业务类型：10001～99999
    private String ip;      // 发出时间的服务器ip
    private long time;      // 时间：毫秒
    private String context; // 上下文
    private String data;    // 具体业务内容，json串

    public KafkaEvent(int eventType, String data) {
        this.eventType = eventType;
        this.ip = "0:0:0:0";
        this.time = System.currentTimeMillis();
        this.context = UUID.randomUUID().toString();
        this.data = data;
    }
}
