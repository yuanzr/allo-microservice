package com.dc.allo.rank.domain.bet.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by zhangzhenjun on 2020/5/19.
 */
@Getter
@Setter
@ToString
public class BetSpiritAmount implements Serializable {
    long spiritId;
    long totalAmount;
    long uid;
    long totalAwardAmount;
}
