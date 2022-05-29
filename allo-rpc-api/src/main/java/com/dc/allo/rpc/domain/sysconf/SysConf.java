package com.dc.allo.rpc.domain.sysconf;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SysConf implements Serializable {

    private String configId;

    private String configName;

    private String configValue;

    private String nameSpace;

    private Byte configStatus;

}
