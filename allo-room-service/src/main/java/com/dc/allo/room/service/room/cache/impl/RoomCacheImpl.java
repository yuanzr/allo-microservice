package com.dc.allo.room.service.room.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.dc.allo.common.component.redis.RedisUtil;
import com.dc.allo.common.domain.room.RoomVo;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.dc.allo.room.domain.room.RunningRoomVo;
import com.dc.allo.room.service.room.cache.RoomCache;
import com.dc.allo.rpc.domain.room.Room;
import com.dc.allo.rpc.domain.room.SimpleRoom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: RoomCacheImpl
 *
 * @date: 2020年04月17日 15:08
 * @author: qinrenchuan
 */
@Service
public class RoomCacheImpl implements RoomCache {

    @Autowired
    private RedisUtil redisUtil;

    /** 模块缓存Key前缀 */
    private static final String redisKeyPre = RedisKeyUtil.RedisKey_Module_Pre.Room;
    /** 房间详情缓存key： 字符串数据结构 */
    private static final String ROOMINFO = "roomInfo";
    private static final String SIMPLE_ROOMINFO = "simpleRoom";
    /** 在线列表 */
    private static final String userInRoom = "user_in_room";
    /** 运行中房间Key */
    private static final String roomRunningKey = "room_running";

    /**
     * 根据主键（UID）查询房间全量信息
     * @param uid 房间主UID，room表主键
     * @return com.dc.allo.rpc.domain.room.Room
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:39
     */
    @Override
    public Room getByUid(Long uid) {
        Object roomInfoCacheObj = redisUtil.get(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, ROOMINFO, uid));
        if (roomInfoCacheObj != null && StringUtils.isNotBlank(roomInfoCacheObj.toString())) {
            return JSONObject.parseObject(roomInfoCacheObj.toString(), Room.class);
        }
        return null;
    }

    /**
     * 将Room信息保存到redis
     * @param room
     * @author qinrenchuan
     * @date 2020/4/17/0017 15:47
     */
    @Override
    public void addRoomCache(Room room) {
        redisUtil.set(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, ROOMINFO, room.getUid()),
                JSONObject.toJSONString(room), RedisKeyUtil.RedisExpire_Time.OneDay);
    }

    /**
     * 批量保存room列表到缓存
     * @param roomList
     * @return void
     * @author qinrenchuan
     * @date 2020/4/29/0029 14:40
     */
    @Override
    public void addRoomListCache(List<Room> roomList) {
        Map<String, String> maps = new HashMap<>();
        for (Room room : roomList) {
            maps.put(RedisKeyUtil.appendCacheKeyByColon(
                        redisKeyPre, ROOMINFO, room.getUid()),
                    JSONObject.toJSONString(room));
        }
        redisUtil.multiSet(maps, RedisKeyUtil.RedisExpire_Time.OneDay);
    }


    /**
     * 根据房间主uid批量查询
     * @param roomUids 房间主uid列表
     * @return java.util.List<com.dc.allo.rpc.domain.room.Room>
     * @author qinrenchuan
     * @date 2020/4/29/0029 11:05
     */
    @Override
    public List<Room> queryByRoomUids(List<Long> roomUids) {
        List<String> keyList = new ArrayList<>(roomUids.size());
        for (Long roomUid : roomUids) {
            keyList.add(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, ROOMINFO, roomUid));
        }

        List<Room> resultList = new ArrayList<>();

        List<String> roomStrList = redisUtil.multiGet(keyList);
        if (roomStrList == null && roomStrList.size() == 0) {
            return resultList;
        }

        for (String roomStr : roomStrList) {
            if (StringUtils.isNotBlank(roomStr)) {
                resultList.add(JSONObject.parseObject(roomStr, Room.class));
            }
        }
        return resultList;
    }

    /**
     * 根据UID查询该用户当前所在的房间
     * @param
     * @return java.util.List<java.lang.String>
     * @author qinrenchuan
     * @date 2020/4/30/0030 14:07
     */
    @Override
    public List<RoomVo> queryOnlineRoomManagerUids(List<Long> uidList) {
        List<Object> uidObjs = new ArrayList<>(uidList);
        List<Object> objects = redisUtil.hMultiGet(RedisKeyUtil.getKey(userInRoom), uidObjs);
        if (objects == null || objects.size() == 0) {
            return new ArrayList<>();
        }

        List<RoomVo> resultList = new ArrayList<>();

        Gson gson = new Gson();
        RoomVo roomVo = null;
        for(Object obj : objects){
            if (obj == null || StringUtils.isBlank(obj.toString())) {
                continue;
            }

            roomVo = gson.fromJson(obj.toString(), new TypeToken<RoomVo>(){}.getType());
            if(roomVo == null || roomVo.getUid() ==null) {
                continue;
            }

            resultList.add(roomVo);
        }
        return resultList;
    }

    /**
     * 根据管理员用户id查询他所在的房间
     * @param uid
     * @return com.dc.allo.common.domain.room.RoomVo
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:45
     */
    @Override
    public RoomVo getOnlineRoomManagerByUid(Long uid) {
        Object obj = redisUtil.hGet(RedisKeyUtil.getKey(userInRoom), uid.toString());
        if (obj == null || StringUtils.isBlank(obj.toString())) {
            return null;
        }
        return new Gson().fromJson(obj.toString(), RoomVo.class);
    }

    /**
     * 查询所有正在运行的房间
     * @return java.util.List<com.dc.allo.room.domain.room.RunningRoomVo>
     * @author qinrenchuan
     * @date 2020/4/30/0030 15:16
     */
    @Override
    public List<RunningRoomVo> queryAllRunningRoom() {
        List<RunningRoomVo> list = new ArrayList<>();

        Map<Object, Object> runningRoomMap = redisUtil.hGetAll(RedisKeyUtil.getKey(roomRunningKey));
        if (runningRoomMap == null || runningRoomMap.size() == 0) {
            return list;
        }

        Gson gson = new Gson();
        Set<Entry<Object, Object>> entries = runningRoomMap.entrySet();
        for (Entry<Object, Object> entry : entries) {
            if (entry == null || StringUtils.isBlank(entry.toString())) {
                continue;
            }
            RunningRoomVo runningRoomVo = gson.fromJson(entry.getValue().toString(), RunningRoomVo.class);
            list.add(runningRoomVo);
        }

        return list;
    }

    /**
     * 设置缓存
     * @param runningRoom
     * @author qinrenchuan
     * @date 2020/4/30/0030 16:55
     */
    @Override
    public void setRunningRoom(RunningRoomVo runningRoom) {
        redisUtil.hPut(RedisKeyUtil.getKey(roomRunningKey),
                runningRoom.getUid().toString(),
                new Gson().toJson(runningRoom));
    }

    /**
     * 批量设置运行中的房间
     * @param runningRoomVos
     * @author qinrenchuan
     * @date 2020/4/30/0030 17:02
     */
    @Override
    public void addRunningRoomListCache(List<RunningRoomVo> runningRoomVos) {
        Map<String, String> maps = new HashMap<>();
        Gson gson = new Gson();
        for (RunningRoomVo runningRoomVo : runningRoomVos) {
            maps.put(runningRoomVo.getUid().toString(),
                    gson.toJson(runningRoomVo));
        }
        redisUtil.hPutAll(RedisKeyUtil.getKey(roomRunningKey), maps);
    }

    /**
     * 根据roomUid列表查询房间列表（简体）
     * @param roomUids
     * @return java.util.List<com.dc.allo.rpc.domain.room.SimpleRoom>
     * @author qinrenchuan
     * @date 2020/6/3/0003 15:28
     */
    @Override
    public List<SimpleRoom> querySimpleRoomsByRoomUids(List<Long> roomUids) {
        List<String> keyList = new ArrayList<>(roomUids.size());
        for (Long uid : roomUids) {
            keyList.add(RedisKeyUtil.appendCacheKeyByColon(redisKeyPre, SIMPLE_ROOMINFO, uid));
        }

        List<SimpleRoom> resultList = new ArrayList<>();

        List<String> roomStrList = redisUtil.multiGet(keyList);
        if (roomStrList == null && roomStrList.size() == 0) {
            return resultList;
        }

        for (String roomStr : roomStrList) {
            if (StringUtils.isNotBlank(roomStr)) {
                resultList.add(JSONObject.parseObject(roomStr, SimpleRoom.class));
            }
        }
        return resultList;
    }

    /**
     * 批量保存room列表到缓存
     * @param roomList
     * @return void
     * @author qinrenchuan
     * @date 2020/6/3/0003 16:03
     */
    @Override
    public void addSimpleRoomListCache(List<SimpleRoom> roomList) {
        Map<String, String> maps = new HashMap<>();
        for (SimpleRoom room : roomList) {
            maps.put(RedisKeyUtil.appendCacheKeyByColon(
                    redisKeyPre, SIMPLE_ROOMINFO, room.getUid()),
                    JSONObject.toJSONString(room));
        }
        redisUtil.multiSet(maps, RedisKeyUtil.RedisExpire_Time.OneDay);
    }
}
