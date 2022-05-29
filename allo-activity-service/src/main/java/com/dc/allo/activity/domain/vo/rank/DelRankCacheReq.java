package com.dc.allo.activity.domain.vo.rank;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/7/24.
 */
@Data
public class DelRankCacheReq implements Serializable {

    String key;
    String rankRedisKey;
}
