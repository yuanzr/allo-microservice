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
public class App implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * app中文名称
     */
    private String name;

    /**
     * app英文标识
     */
    private String appKey;

    /**
     * 是否启用，0不启用，1启用
     */
    private  int enable;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 更新时间
     */
    private Date utime;

}