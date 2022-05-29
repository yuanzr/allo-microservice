package com.dc.allo.biznotice.model.im;

import com.alibaba.fastjson.JSON;

/**
 *@description 融云消息自定义 Content 实体
 *@author Administrator
 *@datetime 2019-11-20 18:39
 *@since v1.0
 */
public class RongCloudMessageContent {

    private String content;

    private String extra;

    public RongCloudMessageContent() {
    }

    public RongCloudMessageContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
