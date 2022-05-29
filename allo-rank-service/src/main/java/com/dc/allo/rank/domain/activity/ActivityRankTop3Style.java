package com.dc.allo.rank.domain.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/7/14.
 */
@Data
public class ActivityRankTop3Style implements Serializable {

    //领讲台url
    private String rostrumUrl;
    private String top1Url;
    private String top2Url;
    private String top3Url;
}
