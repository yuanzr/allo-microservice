package com.dc.allo.biznotice.service.firebase.impl;

import com.dc.allo.biznotice.mapper.firebase.NoticeFirebaseTokenUserMapper;
import com.dc.allo.biznotice.model.firebase.NoticeFirebaseTokenUser;
import com.dc.allo.biznotice.service.firebase.NoticeFirebaseTokenUserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: NoticeFirebaseTokenUserServiceImpl
 * @Description: TODO
 * @Author: YuanZhenRong
 * @Version: 1.0.0
 * @CreateDate: 2020/5/26/12:09
 */
@Slf4j
@Service
public class NoticeFirebaseTokenUserServiceImpl implements NoticeFirebaseTokenUserService {

    @Autowired
    private NoticeFirebaseTokenUserMapper noticeFirebaseTokenUserMapper;

    @Override
    public NoticeFirebaseTokenUser get(Long uid) {
        NoticeFirebaseTokenUser tokenUser = noticeFirebaseTokenUserMapper.get(uid);
        return tokenUser;
    }

    @Override
    public int saveOrUpdate(Long uid, String token) {
        int result = noticeFirebaseTokenUserMapper.insert(uid, token);
        return result;
    }

    @Override
    @Transactional
    public int delete(Long uid,String token) {
        int result = noticeFirebaseTokenUserMapper.delete(uid,token);
        return result;
    }

    @Override
    public List<String> queryToken(List<Long> uidList) {
        List<String>  tokenList =  noticeFirebaseTokenUserMapper.queryToken(uidList);
        return tokenList;
    }
}
