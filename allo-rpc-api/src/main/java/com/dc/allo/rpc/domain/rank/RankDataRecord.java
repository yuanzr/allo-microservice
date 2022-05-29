package com.dc.allo.rpc.domain.rank;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankDataRecord implements Serializable, Cloneable {
    private static final long serialVersionUID = -6469980859916587642L;
    /**
     * app英文标识key，标识是哪个app的数据
     */
    private String appKey;
    /**
     * 数据源key，标识是哪个数据源的数据
     */
    private String dataSourceKey;
    /**
     * 数据源密钥，创建数据源时生成，用于check数据合法性
     */
    private String dataSourceSecret;
    /**
     * 发送id
     */
    private String sendId;
    /**
     * 接受id
     */
    private String recvId;
    /**
     * 分值
     */
    private Double score;
    /**
     * 用于计次 默认为1，但是如果是礼物流水类型的数据源，则需要取实际赠送的礼物数量为count的值
     */
    private Integer count=1;
    /**
     * 房间id(非必需，目前仅礼物流水需要)
     */
    private String roomId;
    /**
     * 房间类型(非必需)
     */
    private Integer roomType;
    /**
     * 业务id(非必需，如礼物流水中的礼物id，配置后将根据divideKey生成不同的榜单key)
     */
    private String bizId;
    /**
     * 时间戳(用来判断时效性)
     */
    private Long timestamp;

    @Override
    public RankDataRecord clone() {
        RankDataRecord copy = new RankDataRecord();
        copy.setAppKey(appKey);
        copy.setBizId(bizId);
        copy.setDataSourceKey(dataSourceKey);
        copy.setDataSourceSecret(dataSourceSecret);
        copy.setRecvId(recvId);
        copy.setSendId(sendId);
        copy.setRoomId(roomId);
        copy.setRoomType(roomType);
        copy.setScore(new Double(score));
        copy.setCount(count);
        copy.setTimestamp(new Long(timestamp));
        return copy;
    }

}
