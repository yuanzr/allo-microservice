package com.dc.allo.rpc.domain.message;

import lombok.Data;

/**
 * @ClassName: ActRamadanPeciesMessage
 * @Description: 碎片消息
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/4/20/18:40
 */
@Data
public class ActRamadanPiecesMessage {
    Long uid;
    //3.增加碎片4减少碎片
    Byte jobType;
    Integer jobCode;
    Integer piecesValue;
}
