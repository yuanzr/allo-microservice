package com.dc.allo.user.service.rpc.baseinfo;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.user.service.baseinfo.UserInfoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: DcUserInfoServiceImpl
 *
 * @date: 2020年04月02日 17:25
 * @author: qinrenchuan
 */
@Service
@Slf4j
public class DcUserInfoServiceImpl implements DcUserInfoService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public AlloResp<UserInfo> getByUid(Long uid) {
        if (uid == null || uid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        UserInfo userInfo;
        try {
            userInfo = userInfoService.getByUid(uid);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(userInfo);
    }

    @Override
    public AlloResp<UserInfo> getByErbanNo(Long erbanNo) {
        if (erbanNo == null || erbanNo <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        UserInfo userInfo;
        try {
            userInfo = userInfoService.getByErbanNo(erbanNo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }

        return AlloResp.success(userInfo);
    }


    /**
     * 根据Uid查询用户简单信息
     * @param uid
     * @return com.dc.allo.user.domain.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 16:12
     */
    @Override
    public AlloResp<SimpleUserInfo> getSimpleUserInfoByUid(Long uid) {
        if (uid == null || uid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        SimpleUserInfo simpleUserInfo = null;
        try {
            UserInfo userInfo = userInfoService.getByUid(uid);
            if (userInfo != null) {
                simpleUserInfo = new SimpleUserInfo();
                BeanUtils.copyProperties(userInfo, simpleUserInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }

        return AlloResp.success(simpleUserInfo);
    }

    /**
     * 根据耳伴号查询用户简单信息
     * @param erbanNo
     * @return com.dc.allo.user.domain.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 18:02
     */
    @Override
    public AlloResp<SimpleUserInfo> getSimpleUserInfoByErbanNo(Long erbanNo) {
        if (erbanNo == null || erbanNo <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        SimpleUserInfo simpleUserInfo = null;
        try {
            UserInfo userInfo = userInfoService.getByErbanNo(erbanNo);
            if (userInfo != null) {
                simpleUserInfo = new SimpleUserInfo();
                BeanUtils.copyProperties(userInfo, simpleUserInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }

        return AlloResp.success(simpleUserInfo);
    }

    /**
     * 根据uidList列表获取用户列表
     * @param uidList
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.user.baseinfo.UserInfo>>
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:42
     */
    @Override
    public AlloResp<List<UserInfo>> getUserList(List<Long> uidList) {
        if (uidList == null || uidList.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            List<UserInfo> userInfos = userInfoService.queryByUids(uidList);
            return AlloResp.success(userInfos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    /**
     * 根据uidList列表获取用户<简体></>列表
     * @param uidList
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.List<com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:20
     */
    @Override
    public AlloResp<List<SimpleUserInfo>> getSimpleUserList(List<Long> uidList) {
        if (uidList == null || uidList.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            List<SimpleUserInfo> userInfos = userInfoService.querySimpleUserList(uidList);
            return AlloResp.success(userInfos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    /**
     * 根据uidList列表获取用户key-value对(uid->SimpleUserInfo)
     * @param uidList
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.Map<java.lang.Long,com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:22
     */
    @Override
    public AlloResp<Map<Long, SimpleUserInfo>> getSimpleUsersMap(List<Long> uidList) {
        if (uidList == null || uidList.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        try {
            Map<Long, SimpleUserInfo> simpleUserInfoMap = new HashMap<>();

            List<SimpleUserInfo> userInfos = userInfoService.querySimpleUserList(uidList);
            if (userInfos == null || userInfos.size() == 0) {
                return AlloResp.success(simpleUserInfoMap);
            }

            for (SimpleUserInfo simpleUserInfo : userInfos) {
                simpleUserInfoMap.put(simpleUserInfo.getUid(), simpleUserInfo);
            }
            return AlloResp.success(simpleUserInfoMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }


    /**
     * 根据耳伴号列表查询与UID的对应关系    erbanNo->Uid
     * @param erbanNoList
     * @return com.dc.allo.rpc.proto.AlloResp<java.util.Map<java.lang.Long,java.lang.Long>>
     * @author qinrenchuan
     * @date 2020/6/4/0004 10:18
     */
    @Override
    public AlloResp<Map<Long, Long>> queryErbanNo2UidMapByErbanNos(List<Long> erbanNoList) {
        if (erbanNoList == null || erbanNoList.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR);
        }

        try {
            Map<Long, Long> erbanNo2UidMap = userInfoService.queryErbanNo2UidMapByErbanNos(erbanNoList);
            return AlloResp.success(erbanNo2UidMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR);
        }
    }

    @Override
    public AlloResp<List<UserInfo>> getUserByErbanNoList(List<String> erbanNoList) {
        if (erbanNoList == null || erbanNoList.size() == 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }
        try {
            List<UserInfo> userInfos = userInfoService.queryByErbanNos(erbanNoList);
            return AlloResp.success(userInfos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
    }

    @Override
    public AlloResp<UserInfo> getRegionByUid(Long uid) {
        if (uid == null || uid <= 0) {
            return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params error", null);
        }

        UserInfo userInfo;
        try {
            userInfo = userInfoService.getRegionByUid(uid);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "system error", null);
        }
        return AlloResp.success(userInfo);
    }
}
