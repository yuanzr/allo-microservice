package com.dc.allo.rpc.domain.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/6/9.
 */
@Data
public class AdminPage<T> implements Serializable {
    long total;
    List<T> rows;
}
