package com.dc.allo.room.service.room.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.room.service.room.cache.RoomMemberCache;
import com.dc.allo.rpc.domain.room.RoomMember;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: RoomMemberCacheImpl
 *
 * @date: 2020年04月30日 11:27
 * @author: qinrenchuan
 */
@Service
public class RoomMemberCacheImpl implements RoomMemberCache {
    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.Room;

    /** 房间成员管理员列表 */
    private static final String roomMemberManagerKey = "roomMemberManager";

    /**
     * 获取该管理员列表
     * @param roomUid
     * @return java.util.List<com.dc.allo.rpc.domain.room.RoomMember>
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:31
     */
    @Override
    public List<RoomMember> getRoomManagerList(Long roomUid) {
        Object managersObj = redisUtil.get(
                RedisKeyUtil.appendCacheKeyByColon(
                        redisKeyPre, roomMemberManagerKey, roomUid.toString()));
        if (managersObj == null) {
            return new ArrayList<>();
        }

        String managersStr = managersObj.toString();
        if (StringUtils.isNotBlank(managersStr)) {
            return JSONObject.parseArray(managersStr, RoomMember.class);
        }

        return new ArrayList<>();
    }

    /**
     * 设置房间管理员缓存
     * @param roomUid
     * @param list
     * @author qinrenchuan
     * @date 2020/4/30/0030 11:54
     */
    @Override
    public void setRoomMemberManagerList(Long roomUid, List<RoomMember> list) {
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, roomMemberManagerKey, roomUid.toString()),
                JSONObject.toJSONString(list),
                RedisKeyUtil.RedisExpire_Time.FiveMinutes);
    }

}
