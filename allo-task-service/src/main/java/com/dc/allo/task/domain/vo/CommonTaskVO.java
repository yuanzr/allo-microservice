package com.dc.allo.task.domain.vo;

import com.dc.allo.task.domain.entity.CommonTask;
import com.dc.allo.task.domain.entity.CommonTaskType;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * description: CommonTaskVO
 *
 * @date: 2020年05月26日 11:09
 * @author: qinrenchuan
 */
@Data
@ToString
public class CommonTaskVO extends CommonTask {
    List<CommonTaskType> taskTypes;
}
