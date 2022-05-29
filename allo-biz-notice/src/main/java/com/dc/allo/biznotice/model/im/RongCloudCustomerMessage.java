package com.dc.allo.biznotice.model.im;

import com.alibaba.fastjson.JSON;
import io.rong.messages.BaseMessage;

/**
 *@description
 *@author Administrator
 *@datetime 2019-11-28 18:21
 *@since v1.0
 */
public class RongCloudCustomerMessage extends BaseMessage {

    private String type;
    private String content;

    public RongCloudCustomerMessage(String objectName, String content) {
        this.type = objectName;
        this.content = content;
    }

    @Override
    public String getType() {
        return type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
