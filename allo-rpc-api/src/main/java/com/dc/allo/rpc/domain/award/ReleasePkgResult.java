package com.dc.allo.rpc.domain.award;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/5/26.
 */
@Data
public class ReleasePkgResult implements Serializable {
    boolean result ;
    List<AwardOfPackage> awardOfPackages;
    String msg;
}
