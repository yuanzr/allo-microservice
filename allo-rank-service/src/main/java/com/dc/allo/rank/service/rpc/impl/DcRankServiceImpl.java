package com.dc.allo.rank.service.rpc.impl;

import com.dc.allo.common.constants.BusiStatus;
import com.dc.allo.common.exception.DCException;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.rank.constants.BetConstants;
import com.dc.allo.rank.domain.rank.Rank;
import com.dc.allo.rank.service.activity.ActivityService;
import com.dc.allo.rank.service.rank.RankManager;
import com.dc.allo.rank.service.rank.data.AutoRewardService;
import com.dc.allo.rank.service.rank.data.DataRecordService;
import com.dc.allo.rpc.api.rank.DcRankService;
import com.dc.allo.rpc.api.room.DcRoomInfoService;
import com.dc.allo.rpc.api.user.baseinfo.DcUserInfoService;
import com.dc.allo.rpc.domain.activity.ActivityInfo;
import com.dc.allo.rpc.domain.rank.RankDetail;
import com.dc.allo.rpc.domain.rank.RankQueryReq;
import com.dc.allo.rpc.domain.rank.RankQueryResp;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.proto.AlloResp;
import com.dc.allo.rank.constants.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by zhangzhenjun on 2020/5/8.
 */
@Slf4j
@Service
public class DcRankServiceImpl implements DcRankService {

    @Autowired
    RankManager rankManager;

    @Autowired
    AutoRewardService autoRewardService;

    @Autowired
    DataRecordService dataRecordService;

    @Autowired
    ActivityService activityService;

    @Reference
    DcUserInfoService userInfoService;

    @Reference
    DcRoomInfoService roomInfoService;

    private boolean reqParamCheck(RankQueryReq req) {
        if (req == null) {
            return false;
        }
        if (StringUtils.isBlank(req.getAppKey())) {
            return false;
        }
        if (StringUtils.isBlank(req.getRankKey())) {
            return false;
        }
        if (req.getTimeOffset() == null | req.getTimeOffset() < 0) {
            return false;
        }
        if (req.getPage() == null || req.getPage() <= 0) {
            return false;
        }
        if (req.getRows() == null || req.getRows() < 0 || req.getRows() > Constant.RankQuery.MAX_ROWS) {
            return false;
        }
        if (req.getWithOne() != null && req.getWithOne()) {
            if (StringUtils.isBlank(req.getOneId())) {
                return false;
            }
        }
        if (req.getWithRelation() != null && req.getWithRelation()) {
            if (req.getRelationPage() == null || req.getRelationPage() <= 0) {
                return false;
            }
            if (req.getRelationRows() == null || req.getRelationRows() < 0 || req.getRelationRows() > Constant.RankQuery.MAX_ROWS) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AlloResp<Boolean> check2CreatNextMonthTable() {
        try {
            dataRecordService.createTable();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            AlloResp.failed(BusiStatus.BUSIERROR.value(), "server err...", false);
        }
        return AlloResp.success(true);
    }

    @Override
    public AlloResp<RankQueryResp> queryRank(RankQueryReq req) {
        try {
            //请求参数校验
            if (!reqParamCheck(req)) {
                log.warn(Constant.Rank.WARN + "榜单查询参数校验失败 req:{}", req);
                return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params err", null);
            }
            //获取榜单实例对象
            Rank rank = rankManager.getRankById(req.getAppKey(), req.getRankKey());
            if (Rank.isNull(rank) || !req.getRankKey().equals(rank.getKey())) {
                return AlloResp.failed(BusiStatus.PARAMERROR.value(), "params err", null);
            }
            boolean needBizInfo = req.isNeedBizInfo();
            RankQueryResp resp = new RankQueryResp();
            resp.setRankId(rank.getId());
            resp.setRankKey(rank.getKey());
            resp.setRankName(rank.getName());
            resp.setRankStartTime(rank.getStartTime());
            resp.setRankEndTime(rank.getEndTime());
            //根据分页查询榜单明细列表
            resp.setRankList(wrapBizInfo(rank.queryRankListByTimeOffset(req.getTimeOffset(), req.getPage(), req.getRows(), req.getDivideKey()), needBizInfo));
            //查询关联榜单
            if (req.getWithRelation() && CollectionUtils.isNotEmpty(resp.getRankList())) {
                for (RankDetail rankDetail : resp.getRankList()) {
                    rankDetail.setRelationRankList(wrapBizInfo(rank.queryRelationRankListByTimeOffset(rankDetail.getId(), req.getTimeOffset(), req.getRelationPage(), req.getRelationRows(), req.getDivideKey()), needBizInfo));
                }
            }
            //查询单个id榜单明细
            if (req.getWithOne()) {
                resp.setOneRank(wrapBizInfo(rank.queryOneRankByTimeOffset(req.getOneId(), req.getTimeOffset(), req.getDivideKey()), needBizInfo));
                //查询单个id关联榜单
                if (req.getWithRelation() && resp.getOneRank() != null) {
                    resp.getOneRank().setRelationRankList(wrapBizInfo(rank.queryRelationRankListByTimeOffset(req.getOneId(), req.getTimeOffset(), req.getRelationPage(), req.getRelationRows(), req.getDivideKey()), needBizInfo));
                }
            }
            return AlloResp.success(resp);
        } catch (DCException dce) {
            return AlloResp.failed(dce, null);
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "榜单查询请求失败 req:{}", req, e);
            return AlloResp.failed(BusiStatus.SERVER_BUSY.value(), "server err", null);
        }
    }

    @Override
    public AlloResp<Boolean> autoReward() {
        try {
            autoRewardService.autoRewardTask();
        } catch (Exception e) {
            log.error(Constant.Rank.ERROR + "榜单自动发奖失败。", e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(), "server err", false);
        }
        return AlloResp.success(true);
    }

    @Override
    public AlloResp<ActivityInfo> getAcitivityInfo(long actId) {
        if(actId<=0){
            return AlloResp.failed(BusiStatus.PARAMERROR.value(),"params err...",null);
        }
        ActivityInfo activityInfo = null;
        try{
            activityInfo = activityService.getAcitivityInfo(actId);
        }catch (Exception e){
            log.error(Constant.Rank.ERROR + "活动配置信息获取失败",e);
        }
        return AlloResp.success(activityInfo);
    }

    @Override
    public AlloResp<Boolean> delRankCache(String rankRedisKey) {
        try{
            rankManager.delRankCache(rankRedisKey);
        }catch (Exception e){
            log.error(Constant.Rank.ERROR+" 删除榜单缓存失败",e);
            return AlloResp.failed(BusiStatus.BUSIERROR.value(),"busi err...",false);
        }
        return AlloResp.success(true);
    }

    private RankDetail wrapBizInfo(RankDetail rankDetail, boolean needBizInfo) {
        if (rankDetail == null || !needBizInfo) {
            return null;
        }
        int rankType = rankDetail.getRankIdType();
        if (rankType == Constant.Rank.RankMemberType.USER.val()) {
            AlloResp<SimpleUserInfo> resp = null;
            try {
                resp = userInfoService.getSimpleUserInfoByUid(Long.valueOf(rankDetail.getId()));
                log.info(BetConstants.INFO_LOG+" users info resp:{}", JsonUtils.toJson(resp));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (resp != null && resp.getCode() == BusiStatus.SUCCESS.value()) {
                SimpleUserInfo userInfo = resp.getData();
                if (userInfo != null) {
                    rankDetail.setAvatar(userInfo.getAvatar());
                    rankDetail.setBizId(String.valueOf(userInfo.getErbanNo()));
                    rankDetail.setName(userInfo.getNick());
                }
            }
        } else if (rankType == Constant.Rank.RankMemberType.ROOM.val()) {
            AlloResp<SimpleRoom> resp = null;
            try {
                resp = roomInfoService.getSimpleRoomByUid(Long.valueOf(rankDetail.getId()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (resp != null && resp.getCode() == BusiStatus.SUCCESS.value()) {
                SimpleRoom roomInfo = resp.getData();
                if (roomInfo != null) {
                    rankDetail.setAvatar(roomInfo.getAvatar());
                    rankDetail.setBizId(String.valueOf(roomInfo.getRoomId()));
                    rankDetail.setName(roomInfo.getTitle());
                }
            }
        }
        return rankDetail;
    }

    private List<RankDetail> wrapBizInfo(List<RankDetail> rankDetails, boolean needBizInfo) {
        if (CollectionUtils.isEmpty(rankDetails) || !needBizInfo) {
            return rankDetails;
        }
        List<Long> ids = new ArrayList<>(rankDetails.size());
        int rankType = 0;
        for (RankDetail detail : rankDetails) {
            ids.add(Long.valueOf(detail.getId()));
            rankType = detail.getRankIdType();
        }
        if (rankType == Constant.Rank.RankMemberType.USER.val()) {
            AlloResp<Map<Long, SimpleUserInfo>> resp = null;
            try {
                resp = userInfoService.getSimpleUsersMap(ids);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (resp != null && resp.getCode() == BusiStatus.SUCCESS.value()) {
                Map<Long, SimpleUserInfo> userInfoMap = resp.getData();
                if (MapUtils.isNotEmpty(userInfoMap)) {
                    SimpleUserInfo userInfo = null;
                    for (RankDetail detail : rankDetails) {
                        userInfo = userInfoMap.get(Long.valueOf(detail.getId().trim()));
                        if (userInfo != null) {
                            detail.setAvatar(userInfo.getAvatar());
                            detail.setBizId(String.valueOf(userInfo.getErbanNo()));
                            detail.setName(userInfo.getNick());
                        }
                    }
                }
            }
        } else if (rankType == Constant.Rank.RankMemberType.ROOM.val()) {
            AlloResp<Map<Long, SimpleRoom>> resp = null;
            try {
                resp = roomInfoService.querySimpleRoomMapByUids(ids);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (resp != null && resp.getCode() == BusiStatus.SUCCESS.value()) {
                Map<Long, SimpleRoom> roomInfoMap = resp.getData();
                if (MapUtils.isNotEmpty(roomInfoMap)) {
                    SimpleRoom roomInfo = null;
                    for (RankDetail detail : rankDetails) {
                        roomInfo = roomInfoMap.get(Long.valueOf(detail.getId().trim()));
                        if (roomInfo != null) {
                            detail.setAvatar(roomInfo.getAvatar());
                            detail.setBizId(String.valueOf(roomInfo.getRoomId()));
                            detail.setName(roomInfo.getTitle());
                        }
                    }
                }
            }
        }
        return rankDetails;
    }
}
