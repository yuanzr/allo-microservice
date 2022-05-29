package com.dc.allo.rpc.proto.roomplay;

import com.dc.allo.rpc.domain.roomplay.pk.Sample;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangzhenjun on 2020/3/30.
 */
@Data
public class SamplesResp implements Serializable {
    List<Sample> samples;
}
