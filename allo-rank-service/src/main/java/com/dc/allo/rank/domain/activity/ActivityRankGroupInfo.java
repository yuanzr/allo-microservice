package com.dc.allo.rank.domain.activity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/14.
 */
@Data
public class ActivityRankGroupInfo implements Serializable {
    //分组标签，json对象
    private String label;
    //具体榜单列表，如日榜，总榜
    private String rankTypes;
}
