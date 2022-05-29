package com.dc.allo.rpc.proto;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
public class AlloResp<T> implements Serializable{
    /** see {@link BusiStatus} */
    private int code;

    private String message;

    private T data;

    public int getCode() {
        return code;
    }

    public AlloResp<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AlloResp<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public AlloResp<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.code == BusiStatus.SUCCESS.value();
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public static <T> AlloResp<T> success() {
        return new AlloResp<T>().setCode(BusiStatus.SUCCESS.value()).setMessage("success");
    }

    public static <T> AlloResp<T> success(T respData) {
        return new AlloResp<T>().setCode(BusiStatus.SUCCESS.value()).setMessage("success").setData(respData);
    }

    public static <T> AlloResp<T> failed(DCException e, T respData) {
        return new AlloResp<T>().setCode(e.getCode()).setMessage(e.getMessage()).setData(respData);
    }


    public static <T> AlloResp<T> failed(DCException e) {
        return new AlloResp<T>().setCode(e.getCode()).setMessage(e.getMessage());
    }

    public static <T> AlloResp<T> failed(BusiStatus busiStatus) {
        return new AlloResp<T>().setCode(busiStatus.value()).setMessage(busiStatus.getReasonPhrase());
    }

    public static <T> AlloResp<T> failed(int code, String msg, T respData) {
        return new AlloResp<T>().setCode(code).setMessage(msg).setData(respData);
    }
}
