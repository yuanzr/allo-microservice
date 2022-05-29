package com.dc.allo.task.service;

import com.dc.allo.task.domain.entity.SignInRecord;
import com.github.pagehelper.PageInfo;
import java.util.List;

/**
 * description: 签到记录Service
 * date: 2020年05月27日 11:30
 * author: qinrenchuan
 */
public interface SignInRecordService {

    /**
     * 根据UID查询最近x次签到记录
     * @param uid
     * @param count 最近count次
     * @return com.dc.allo.task.domain.entity.SignInRecord
     * @author qinrenchuan
     * @date 2020/5/27/0027 11:43
     */
    List<SignInRecord> getLatestSignInRecordByUid(Long uid, Integer count);

    /**
     * 保存签到记录
     * @param signInRecord
     * @return void
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:58
     */
    void save(SignInRecord signInRecord);

    /**
     * 分页查询
     * @param pageNumber
     * @param pageSize
     * @param uid
     * @return com.github.pagehelper.PageInfo<com.dc.allo.task.domain.entity.SignInRecord>
     * @author qinrenchuan
     * @date 2020/5/28/0028 16:02
     */
    PageInfo<SignInRecord> listByPage(Integer pageNumber, Integer pageSize, Long uid);
}
