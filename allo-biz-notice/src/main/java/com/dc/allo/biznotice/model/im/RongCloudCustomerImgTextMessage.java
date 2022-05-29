package com.dc.allo.biznotice.model.im;

import com.alibaba.fastjson.JSON;

public class RongCloudCustomerImgTextMessage extends RongCloudCustomerMessage {

    private String imageUri;
    private String title;
    private String url;
    private String extra;

    public RongCloudCustomerImgTextMessage(String objectName, SendImgTextMessageParam param) {
        super(objectName,param.getContent());
        this.imageUri = param.getImgUrl();
        this.title = param.getTitle();
        this.url = param.getUrl();
        this.extra = param.getExtra();
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
