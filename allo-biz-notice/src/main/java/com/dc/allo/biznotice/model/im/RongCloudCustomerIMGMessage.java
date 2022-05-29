package com.dc.allo.biznotice.model.im;

import com.alibaba.fastjson.JSON;

public class RongCloudCustomerIMGMessage extends RongCloudCustomerMessage {

    private String imageUri;

    public RongCloudCustomerIMGMessage(String objectName, String content,String imageUri) {
        super(objectName,content);
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
