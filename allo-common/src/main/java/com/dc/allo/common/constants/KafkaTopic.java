package com.dc.allo.common.constants;

/**
 * Created by zhangzhenjun on 2020/3/19.
 */
public class KafkaTopic {

    public interface EventType{
        int SAMPLE_EVENT = 10001;

        int GIFT_SEND_EVENT = 20001;              //礼物流水
        int BILL_RECORD_ADD_EVENT = 20011;        //新增账单
        int BILL_RECORD_UPDATE_EVENT = 20012;     //更新账单
        int CHARGE = 20031 ;                      //充值

        int OPEN_BOX = 30001;                     //砸蛋
        int GAME_WHEEL_CREATE = 30011;            //发起转盘
        int GAME_WHEEL_JOIN = 30012;              //加入转盘

        int ACTIVITY_EVENT_INVITE = 40001;        //活动邀请码
        int ACTIVITY_EVENT_CHARGE_SUMMER = 40002;        //活动邀请码

        int ACTIVITY_EVENT_CHARGE_CAMEL = 40003; //骆驼充值活动
        int DC_ACT_RAMADAN_ITEM = 40011;        //斋月活动碎片事件队列

        int DC_ROOM_PK_ACTIVITY_UP = 1;        //房间pk活动升级
        int DC_ROOM_PK_ACTIVITY_OWNER_AWARD = 2; //房间pk活动房主奖励
        int DC_ROOM_PK_ACTIVITY_SEND_GIFT_AWARD =3; //房间pk活动送礼奖励

        int DC_RANK_BET_CAMAL = 50010;               //榜单-骆驼赛跑投注
        int DC_RANK_AWARD_COINS = 50020;             //金币发奖
        int DC_RANK_AWARD_AVATAR = 50030;            //头饰发奖
        int DC_RANK_AWARD_GIFT = 50040;              //礼物发奖
        int DC_RANK_AWARD_NOBLE = 50050;             //贵族发奖
        int DC_RANK_AWARD_CAR = 50060;               //座驾发奖
        int DC_RANK_AWARD_BADGE = 50070;             //勋章发奖
        int DC_RANK_AWARD_BACKGROUND = 50080;        //背景发奖

        /** 签到发奖 */
        int DC_TASK_SIGN_AWARD = 50001;
        /** 完成签到任务 */
        int DC_TASK_SIGN_Finish_MISSION = 50002;
    }

    public interface Finance{
        String DC_GIFT_SEND = "dc-gift-send";      //礼物流水队列
        String DC_BILL_RECORD = "dc-bill-record";  //账单队列
        String DC_OPEN_BOX = "dc-open-box";        //砸蛋队列
        String DC_GAME_WHEEL = "dc-game-wheel";    //大转盘队列
        String DC_CHARGE = "dc-charge";            //充值队列
        String DC_ACTIVITY_EVENT = "dc-activity-event";   //活动事件队列
        String DC_ACT_RAMADAN_ITEM = "dc_act_ramadan_item";//斋月活动碎片事件
        String DC_ROOM = "dc_room";//房间事件
    }

    public interface Activity{
        String DC_CHARGE_SUMMER_PRIZE_SEND = "dc-charge-summer-prize-send";      //发奖队列
        String DC_CHARGE_CAMEL_PRIZE_SEND = "dc-charge-camel-prize";//骆驼活动发奖队列

    }

    public interface Rank{
        String DC_RANK_DATA_STREAM = "dc-rank-data-stream";
        String DC_RANK_AWARD = "dc-rank-award";
    }

    public interface RoomPlay{
        String DC_SAMPLE_TEST = "dcSampleTest";
        String ROOM_PK = "dcSampleTest";
    }

    public interface Task {
        /** 签到 */
        String DC_TASK_SIGN = "dc-task-sign";
    }
}
