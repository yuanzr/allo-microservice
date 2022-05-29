package com.dc.allo.rank.domain.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/7/14.
 */
@Data
public class ActivityRankTypeInfo implements Serializable {
    //榜单key，定位具体的榜单数据
    private String rankKey;
    //榜单类型标签，如日榜，小时榜，总榜（多语言）
    private String label;
}
