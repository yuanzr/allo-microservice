package com.dc.allo.activity.service.cache.impl;

import com.dc.allo.activity.service.cache.RoomPkActivityCache;
import com.dc.allo.activity.domain.vo.room.RoomActivityRankInfoVo;
import com.dc.allo.activity.domain.vo.room.RoomPkActivityInfoMissionVo;
import com.dc.allo.activity.domain.vo.room.RoomPkActivityInfoVo;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.domain.room.RoomHeatValueTotal;
import com.dc.allo.common.utils.DateTimeUtil;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.common.vo.RoomActivityRankVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoomPkActivityCacheImpl implements RoomPkActivityCache {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public RoomPkActivityInfoVo getRoomPkActivityInfoVo(Long roomUid) {
        Object roomHeatValueTotalObject = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.room_heat_value_total.name()),roomUid.toString());
        RoomHeatValueTotal roomHeatValueTotal = null;
        if(roomHeatValueTotalObject != null){
            roomHeatValueTotal = JsonUtils.fromJson(roomHeatValueTotalObject.toString(),RoomHeatValueTotal.class);
        }
        RoomPkActivityInfoVo activityInfoVo = new RoomPkActivityInfoVo();
        activityInfoVo.setHeatValue(BigDecimal.ZERO);
        activityInfoVo.setLevel((byte)0);
        if(roomHeatValueTotal != null){
            activityInfoVo.setRoomUid(roomUid);
            activityInfoVo.setHeatValue(roomHeatValueTotal.getHeatValue());
            activityInfoVo.setLevel(roomHeatValueTotal.getLevel());
            activityInfoVo.setRoomAvatar(roomHeatValueTotal.getRoomAvatar());
            activityInfoVo.setRoomTitle(roomHeatValueTotal.getRoomTitle());
        }
        Object sucPkObj = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.suc_pk_today_num.name()), roomUid.toString());
        int sucPkNum = 0;
        if(sucPkObj != null){
            sucPkNum = Integer.parseInt(sucPkObj.toString());
        }
        Object pkWinObj = this.redisUtil.hGet(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_win_today_num.name()), roomUid.toString());
        int pkWinNum = 0;
        if(pkWinObj != null){
            pkWinNum = Integer.parseInt(pkWinObj.toString());
        }

        List<RoomPkActivityInfoMissionVo> missionList = new ArrayList<RoomPkActivityInfoMissionVo>(2);
        RoomPkActivityInfoMissionVo sucPkVo = new RoomPkActivityInfoMissionVo();
        sucPkVo.setName("sucPk");
        sucPkVo.setTotalNum(5);
        sucPkVo.setFinishNum(sucPkNum);
        RoomPkActivityInfoMissionVo pkWinVo = new RoomPkActivityInfoMissionVo();
        pkWinVo.setName("pkWin");
        pkWinVo.setTotalNum(5);
        pkWinVo.setFinishNum(pkWinNum);
        missionList.add(sucPkVo);
        missionList.add(pkWinVo);
        activityInfoVo.setMissionList(missionList);
        return activityInfoVo;
    }

    @Override
    public RoomActivityRankInfoVo getRoomActivityRankHistoryVo() {
        Object roomActivityRankHistoryObj = this.redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_ctivity_rank_history.name()));
        RoomActivityRankInfoVo roomActivityRankInfoVo = null;
        if(roomActivityRankHistoryObj != null){
            roomActivityRankInfoVo = JsonUtils.fromJson(roomActivityRankHistoryObj.toString(),RoomActivityRankInfoVo.class);
        }
        return roomActivityRankInfoVo;
    }

    @Override
    public RoomActivityRankVo getRoomActivityRankTodayVo() {
        String date = DateTimeUtil.convertDate(DateTimeUtil.getNextHour(new Date(),-5),DateTimeUtil.DEFAULT_DATE_PATTERN);
        Object roomActivityRankObj = this.redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_ctivity_rank_today.name(),date));
        RoomActivityRankVo roomActivityRankVo = null;
        if(roomActivityRankObj != null){
            roomActivityRankVo = JsonUtils.fromJson(roomActivityRankObj.toString(),RoomActivityRankVo.class);
        }
        return roomActivityRankVo;
    }

    public void setRoomActivityRankHistoryVo(RoomActivityRankInfoVo roomActivityRankHistoryVo){
        String key = RedisKeyUtil.appendCacheKeyByColon(RedisKeyUtil.RedisKey.pk_ctivity_rank_history.name());
        this.redisUtil.set(key,JsonUtils.toJson(roomActivityRankHistoryVo));
    }
}
