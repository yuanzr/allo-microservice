package com.dc.allo.rpc.domain.room;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 * description: SimpleRoom
 *
 * @date: 2020年04月17日 15:37
 * @author: qinrenchuan
 */
@Data
@ToString
public class SimpleRoom implements Serializable {
    /** 主键UID */
    private Long uid;
    /** roomId */
    private Long roomId;
    /** 房间名 */
    private String title;
    /** 房间头像 */
    private String avatar;
}
