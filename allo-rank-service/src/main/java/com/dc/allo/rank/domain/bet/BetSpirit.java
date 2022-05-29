package com.dc.allo.rank.domain.bet;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
public class BetSpirit implements Serializable{
    //id
    private long id;

    //精灵名字
    private String name;

    //精灵图片
    private String url;

    //精灵svga资源
    private String svgaUrl;

    //备注
    private String remark;
}
