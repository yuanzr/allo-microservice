package com.dc.allo.rpc.domain.bet;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenjun on 2020/5/22.
 */
@Data
public class BetGameRoundInfo implements Serializable {
    private long actId;
    private long gameId;
    private long winSpiritId;
    private List<BetGameSpiritInfo> spirits;
    private List<BetResultInfo> resultInfos;
    private List<Long> resultSpirits;
    private BetWinSpiritInfo winSpiritInfo;
    private List<Integer> payPrices;
    private Map<Long,BetAnimationData> animations;
    private int gameStatus;
    private int transAnimationDuration;
    private String transAnimationUrl ;
    private long atime;
    private long rtime;
    private long stime;
    private long etime;
    private long systime;
    private String remark;

    public String getTransAnimationUrl(){
        transAnimationUrl = "https://img.scarllet.cn/bet/svga/321go2.svga";
        return transAnimationUrl;
    }
}
