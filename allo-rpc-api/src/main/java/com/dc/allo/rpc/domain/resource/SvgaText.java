package com.dc.allo.rpc.domain.resource;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/6/19.
 */
@Data
public class SvgaText implements Serializable {
    //key
    String key;
    //value
    String text;
    //字体size
    int size;
    //字体颜色 类似 #000000
    String color;
    //对齐方式
    int alignment = 5;
}
