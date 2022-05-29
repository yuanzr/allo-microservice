package com.dc.allo.rpc.domain.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page<T> implements Serializable{
    private List<T> rows;
    private long id;          //当前索引id
    private String suffix;    //表名后缀
    private long pageSize;    //分页大小
    private boolean hasMore;  //是否有下页
}
