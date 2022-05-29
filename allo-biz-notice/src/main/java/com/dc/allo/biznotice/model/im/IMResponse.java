package com.dc.allo.biznotice.model.im;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 *@description
 *@author Administrator
 *@datetime 2019-11-20 18:11
 *@since v1.0
 */
@Data
public class IMResponse {
    private Integer code;
    private String message;
    private Object data;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
