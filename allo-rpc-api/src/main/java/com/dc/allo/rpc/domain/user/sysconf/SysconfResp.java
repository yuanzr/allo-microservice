package com.dc.allo.rpc.domain.user.sysconf;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Data
@ToString
public class SysconfResp implements Serializable{

    Map<String,String> configs;
}
