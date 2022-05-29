package com.dc.allo.common.utils;


import org.apache.commons.lang3.RandomUtils;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
public class RedisKeyUtil {

    private static String RedisKey_Base_Pre = "allo";

    public static class RedisKey_Module_Pre {
        public static String RoomPlay = "room-play";
        public static String User = "user";
        public static String Finance = "finance";
        public static String SysConfig = "sys-config";
        public static String Activity = "acitivity";
        public static String Task = "task";
        public static String Room = "room";
        public static String Rank = "rank";
    }

    //秒为单位
    public static class RedisExpire_Time {
        public static long OneMinute = 60;
        public static long FiveMinutes = 300;
        public static long TenMinutes = 600;
        public static long ThirtyMinutes = 1800;
        public static long OneHour = 3600;
        public static long OneDay = 86400;
        public static long OneWeek = 604800;
        public static long OneMonth = 2592000;//按30天算
    }

    public enum RedisKey {
        room_combat_power,//房间战力值
        room_pk_record,//房间PK记录 key是记录id value是房间pl记录
        room_pk_uid_record,//房间pk记录-用户指向对应的pk记录 key是roomUid value是房间pk记录Id 做一个二次寻址
        room_pk_today_reject,//发起pk当天被拒绝次数 key为本房Uid#对方房间Uid
        room_pk_room_lock,//房间pk房间锁
        pking_room_gold,//房间中的房间流水
        pking_room_user_record,//pk中的房间用户送记记录汇总 sort Set 结构 key为记录id+roomUid value为流水
        mq_pk_status,
        pk_square_all_room,//Pk广场所有房间
        pk_square_user_zone,//存储用户其他对手的分页数据 参数zone为0时重新查数据
        // 后台定时任务发起PK
        room_pk_admin_lock,
        //普通用户每天提醒pk的次数
        room_pk_today_want_pk_num,
        //房间pk今日胜的场次
        room_pk_today_win_num,
        //房间上一次发起pk的时间毫秒值
        choose_pk_inteterval,
        pking_user_zone,//存储用户的pk中的分页数据 参数zone为0时重新查数据
        sys_conf,
        suc_pk_today_num,//今天成功开启pk,增加热度值的次数
        pk_win_today_num,//今天pk胜利,增加热度值的次数
        room_heat_value_total,//记录每个房间总的热度情况
        room_heat_value_totay,//记录每个房间今天的热度情况
        room_heat_value_totay_score,//记录每个房间今天的热度值
        pk_room_user_today_gift,//当天pk的房间用户送记记录汇总 sort Set 结构 key为roomUid  value为流水

        user_heat_level_coin,//用户热力级别发的金币 用于校验这个级别是否已经发送了金币
        user_heat_combat_power,//用户热力级别对应可以加的战力值
        pk_ctivity_rank_history,//pk活动榜单历史
        pk_ctivity_rank_today,//pk活动当天榜单
        new_user,//是否是新用户 key为uid value为1代表是 value为0代表不是

        pk_activity_heat_lock,//pk活动热度值锁
        activity_rank_award,        //活动奖拼列表
        act_charge_summer_job_total, //夏日充值活动累计分数
        act_charge_summer_job_status, //夏日充值任务状态
        activity_job_user,//用户活动任务完成进度
    }

    /**
     * 冒号拼接key
     *
     * @param key
     * @param appendStr
     * @return
     */
    public static String appendCacheKeyByColon(Object key, Object... appendStr) {
        if (key == null) {
            key = "";
        }
        StringBuilder s = new StringBuilder(RedisKey_Base_Pre);
        s.append(":").append(key);
        if (appendStr != null && appendStr.length > 0) {
            for (Object append : appendStr) {
                if (append == null) {
                    append = "";
                }
                s.append(":").append(append);
            }
        }
        return s.toString();
    }

    public static String getKey(String key) {
        return ("erban_" + key).toLowerCase();
    }

    public static String getKey(String key,String suffix) {
        if (StringUtils.isEmpty(suffix)) {
            return getKey(key);
        } else {
            return getKey(key) + "_" + suffix;
        }

    }
    
    
    /**
     * 获取百分之十以内的随机偏移值
     * @param seconds
     * @return long
     * @author qinrenchuan
     * @date 2020/6/4/0004 14:30
     */
    public static long getRandomOffset(long seconds) {
        return RandomUtils.nextLong(1, seconds / 10);
    }
}
