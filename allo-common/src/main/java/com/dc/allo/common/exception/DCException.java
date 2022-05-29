package com.dc.allo.common.exception;

import com.dc.allo.common.constants.BaseBusiStatus;
import com.dc.allo.common.constants.BusiStatus;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
public class DCException extends RuntimeException {

    protected int code;

    public DCException(int code, String msg) {
        super("[" + code + "]" + msg);
        this.code = code;
    }

    public DCException(int code, String msg, Throwable throwable) {
        super("[" + code + "]" + msg, throwable);
        this.code = code;
    }

    public DCException(BusiStatus busiStatus) {
        super("[" + busiStatus.value() + "]" + busiStatus.getReasonPhrase());
        this.code = busiStatus.value();
    }

    public DCException(BaseBusiStatus busiStatus) {
        super("[" + busiStatus.getCode() + "]" + busiStatus.getMsg());
        this.code = busiStatus.getCode();
    }

    public int getCode() {
        return code;
    }
}
