package com.dc.allo.task.service;

import com.dc.allo.task.domain.entity.PrizeRankActItem;
import com.dc.allo.task.domain.vo.SignRecordVO;
import com.dc.allo.task.domain.vo.SignVO;
import java.util.List;

/**
 * description: SignInService
 * date: 2020年05月26日 17:59
 * author: qinrenchuan
 */
public interface SignInService {

    /**
     * 签到
     * @param uid
     * @return com.dc.allo.task.domain.vo.SignVO
     * @author qinrenchuan
     * @date 2020/5/26/0026 18:01
     */
    List<PrizeRankActItem> signIn(Long uid);

    /**
     * 查询用户的签到记录
     * @param uid
     * @return com.dc.allo.task.domain.vo.SignRecordVO
     * @author qinrenchuan
     * @date 2020/5/26/0026 18:08
     */
    SignRecordVO signRecord(Long uid);

    /**
     * 查询用户今天是否已经签到
     * @param uid
     * @return java.lang.Boolean
     * @author qinrenchuan
     * @date 2020/6/3/0003 17:43
     */
    Boolean getSignStatusForToday(Long uid);
}
