package com.dc.allo.task.domain.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * description: SignVO
 *
 * @date: 2020年05月26日 17:45
 * @author: qinrenchuan
 */
@Data
@ToString
public class SignVO implements Serializable {

    private List<TaskPrizeDetailVO> prizes;


}
