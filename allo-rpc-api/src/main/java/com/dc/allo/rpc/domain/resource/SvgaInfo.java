package com.dc.allo.rpc.domain.resource;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/6/19.
 */
@Data
public class SvgaInfo implements Serializable {
    //svga 路径
    String url;
    //文字属性
    List<SvgaText> texts;
    //图片属性
    List<SvgaImage> images;
}
