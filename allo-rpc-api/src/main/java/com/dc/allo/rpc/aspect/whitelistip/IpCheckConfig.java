package com.dc.allo.rpc.aspect.whitelistip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IpCheckConfig implements Serializable{

    private boolean usingWhitelist;     // 是否启用白名单检查
    private boolean usingBlacklist;     // 是否启用黑名单检查
    private Set<String> whitelist;     // 白名单列表
    private Set<String> blacklist;     // 黑名单列表
}
