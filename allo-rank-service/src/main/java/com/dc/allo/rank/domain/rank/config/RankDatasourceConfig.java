package com.dc.allo.rank.domain.rank.config;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/6.
 */
@Data
@ToString
public class RankDatasourceConfig implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * appid
     */
    private Integer appId;

    /**
     * appkey
     */
    private String appKey;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源key
     */
    private String dataSourceKey;

    /**
     * appid+appkey+key+ctime md5加盐后的结果 用于过滤非法数据
     */
    private String secret;

    /**
     * 数据源类型(用于数据具体值校验)：1礼物流水、2礼物流水不带房间id、3无发送方(如完成某个任务成功)、4无接收方(如跑骆驼投注成功)
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;


}