package com.dc.allo.rank.domain.rank.config;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/7/20.
 */
@Data
public class BizIdConfig implements Serializable{

    private long id;
    //榜单id
    private long rankId;
    //业务id
    private String bizIds;
}
