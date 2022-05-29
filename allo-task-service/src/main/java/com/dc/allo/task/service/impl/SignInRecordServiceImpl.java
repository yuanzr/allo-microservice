package com.dc.allo.task.service.impl;

import com.dc.allo.task.domain.entity.SignInRecord;
import com.dc.allo.task.mapper.SignInRecordMapper;
import com.dc.allo.task.service.SignInRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: SignInRecordServiceImpl
 *
 * @date: 2020年05月27日 11:30
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class SignInRecordServiceImpl implements SignInRecordService {

    @Autowired
    private SignInRecordMapper signInRecordMapper;

    /**
     * 根据UID查询最近X次签到记录
     * @param uid
     * @param count 次数
     * @return com.dc.allo.task.domain.entity.SignInRecord
     * @author qinrenchuan
     * @date 2020/5/27/0027 11:43
     */
    @Override
    public List<SignInRecord> getLatestSignInRecordByUid(Long uid, Integer count) {
        return signInRecordMapper.getLatestSignInRecordByUid(uid, count);
    }

    /**
     * 保存签到记录
     * @param signInRecord
     * @return void
     * @author qinrenchuan
     * @date 2020/5/27/0027 14:58
     */
    @Override
    public void save(SignInRecord signInRecord) {
        signInRecordMapper.save(signInRecord);
    }


    /**
     * 分页查询
     * @param pageNumber
     * @param pageSize
     * @param uid
     * @return com.github.pagehelper.PageInfo<com.dc.allo.task.domain.entity.SignInRecord>
     * @author qinrenchuan
     * @date 2020/5/28/0028 16:02
     */
    @Override
    public PageInfo<SignInRecord> listByPage(Integer pageNumber, Integer pageSize, Long uid) {
        PageHelper.startPage(pageNumber, pageSize);
        List<SignInRecord> list = signInRecordMapper.listByUid(uid);
        return new PageInfo<>(list);
    }
}
