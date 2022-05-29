package com.dc.allo.rank.domain.rank.config;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankDataRecordDb implements Serializable {
    /**
     * id
     */
    private Long id;

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
    private BigDecimal score;

    /**
     * 分值
     */
    private Integer count;

    /**
     * 频道id(非必需，目前仅礼物流水需要)
     */
    private String roomId;

    /**
     * 频道类型(非必需，目前仅礼物流水需要)
     */
    private Integer roomType;

    /**
     * 业务id(非必需，如礼物流水中的礼物id，或者接唱点赞流水中的歌曲id)
     */
    private String bizId;

    /**
     * 时间戳(用来判断时效性)
     */
    private Long timestamp;

    /**
     * 数据是否合法 1合法 0不合法
     */
    private Integer checkPass;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;


}