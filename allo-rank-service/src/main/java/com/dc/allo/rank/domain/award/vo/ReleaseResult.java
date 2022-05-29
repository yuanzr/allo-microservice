package com.dc.allo.rank.domain.award.vo;

import lombok.Data;
import lombok.ToString;

/**
 * Created by zhangzhenjun on 2020/5/9.
 */
@Data
@ToString
public class ReleaseResult {
    boolean success;
    String msg;

    public ReleaseResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static ReleaseResult fail(String msg) {
        msg = "奖励发放失败:" + msg;
        if (msg.length() > 100) {
            msg = msg.substring(0, 100);
        }
        return new ReleaseResult(false, msg);
    }

    public static ReleaseResult success() {
        return new ReleaseResult(true, "奖励发放成功");
    }
}
