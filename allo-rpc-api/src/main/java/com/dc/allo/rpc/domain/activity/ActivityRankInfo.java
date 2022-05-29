package com.dc.allo.rpc.domain.activity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/7/14.
 * 入库
 */
@Data
public class ActivityRankInfo implements Serializable {

    private long id;
    //活动id
    private long actId;
    //榜单数据前三名样式
    private String top3Style;
//    private String bgUrl;
    private String bgColor;
//    private String tabBgUrl;
//    private String tabBgUrlSelect;
    private String tabBgColor;
    private String tabBgColorSelect;
//    private String tabRankBgUrl;
//    private String tabRankBgUrlSelect;
    private String tabRankBgColor;
    private String tabRankBgColorSelect;
    //榜单分组列表，如收礼榜，送礼榜
    private String rankGroups;
//    private String awardUrls;
    private boolean withOne = false;
}
